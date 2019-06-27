package com.szjz.seller.configuration;

import com.szjz.seller.org.springframework.data.repository.config.RepositoryBeanNamePrefix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

/**
 * author:szjz
 * date:2019/6/26
 *
 * 备份库 读取操作
 */
@Slf4j
@Configuration
@EnableTransactionManagement
@RepositoryBeanNamePrefix("read")
@EnableJpaRepositories(
        entityManagerFactoryRef = "backupEntityManagerFactory",
        transactionManagerRef = "backupTransactionManager",
        basePackages = {"com.szjz.seller.repository"}) //设置备份库Repository包所在的位置
public class ReadConfiguration {


    @Autowired
    private JpaProperties jpaProperties;

    /**
     * 配置备份库数据源
     */
    @Bean(name = "backupDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.backup")
    public DataSource backupDataSource() {
        DataSource dataSource = DataSourceBuilder.create().build();
        log.info("配置备份库数据源:{}",dataSource);
        return dataSource;
    }

    @Bean(name = "backupEntityManager")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        log.info("backupEntityManager");
        return entityManagerFactory(builder).getObject().createEntityManager();
    }

    @Bean(name = "backupEntityManagerFactory") //注：实例默认名称为方法名，在此，如果不指定bean的name，方法名必须为entityManagerFactory 否则，报错
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
        log.info("配置备份库实体类管理工厂实例:{}",backupDataSource());
        LocalContainerEntityManagerFactoryBean build = builder
                .dataSource(backupDataSource())
                .properties(getVendorProperties())
                .packages("com.szjz.model") //设置备份库Repository对应实体类所在位置
                .persistenceUnit("backupPersistenceUnit")
                .build();
        return build;
    }

    /**
     * spring boot 2.0.0 jpaProperties.getHibernateProperties(new HibernateSettings())
     * spring boot 1.5.0 jpaProperties.getHibernateProperties(DataSource datasource)
     */
    private Map<String, Object> getVendorProperties() {
        return jpaProperties.getHibernateProperties(new HibernateSettings());
    }

    @Bean(name = "backupTransactionManager") //注**：方法名必须为transactionManager
    public PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
        log.info("配置备份库事务管理器");
        return new JpaTransactionManager(entityManagerFactory(builder).getObject());
    }
}

