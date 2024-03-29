package com.szjz.seller.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

/**
 * 主库 写入操作
 * ***************修改源码之后主从数据库同用相同的repository
 */
@Slf4j
@Primary
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactory", //引用名称必须为entityManagerFactory
        transactionManagerRef = "transactionManager",
        basePackages = {"com.szjz.seller.repository"}) //设置主库Repository包所在的位置
public class PrimaryConfiguration {

    @Autowired
    private JpaProperties jpaProperties;

    /**
     * 配置主库数据源
     */
    @Bean(name = "primaryDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDataSource() {
             DataSource dataSource = DataSourceBuilder.create().build();
        log.info("配置主库数据源:{},{}",dataSource);
        return dataSource;
    }

    @Primary
    @Bean
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactory(builder).getObject().createEntityManager();
    }


    @Primary
    @Bean(name = "entityManagerFactory") //注**：实例默认名称为方法名，主库实体管理工厂方法名必须为entityManagerFactory
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
        log.info("配置主库实体类管理工厂实例");
        return builder
                .dataSource(primaryDataSource())
                .properties(getVendorProperties())
                .packages("com.szjz.model")//设置主库Repository对应实体类所在位置
                .persistenceUnit("primaryPersistenceUnit")
                .build();
    }

    /**
     * spring boot 2.0.0 jpaProperties.getHibernateProperties(new HibernateSettings())
     * spring boot 1.5.0 jpaProperties.getHibernateProperties(DataSource datasource)
     */
    private Map<String, Object> getVendorProperties() {
        return jpaProperties.getHibernateProperties(new HibernateSettings());
    }

    @Primary
    @Bean(name = "transactionManager") //注：主库事务管理方法名必须为transactionManager
    public PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
        log.info("配置主库事务管理器");
        return new JpaTransactionManager(entityManagerFactory(builder).getObject());
    }
}