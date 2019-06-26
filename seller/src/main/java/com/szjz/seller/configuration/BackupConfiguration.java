package com.szjz.seller.configuration;

import com.szjz.model.Order;
import com.szjz.seller.repositoryBackup.VerificationOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

/**
 * author:szjz
 * date:2019/6/26
 */
@Slf4j
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(
//        basePackageClasses = VerificationOrderRepository.class, //VerificationOrderRepository 所在包路径
//        entityManagerFactoryRef = "backupEntityManagerFactory",
//        transactionManagerRef = "backupTransactionManager")
public class BackupConfiguration {

    @Autowired(required = false)
    private JpaProperties jpaProperties;

    @Autowired
    @Qualifier("backupDataSource")
    private DataSource backupDataSource;

//    @Bean(name = "backupEntityManager")
//    @Primary
//    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
//        return entityManagerFactory(builder).getObject().createEntityManager();
//    }

    /**
     * 配置备份库实体类管理工厂
     */
    @Bean(name = "backupEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        log.info("配置备份库实体类管理工厂");
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(backupDataSource);
        em.setPackagesToScan(new String[] {"com.szjz.model"});
        JpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(jpaAdapter);
        em.setJpaProperties(jpaProperties());

        return em;

    }

    /**
     * org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration
     * 配置备份库事务管理
     */
    @Bean(name = "backupTransactionManager")
    public PlatformTransactionManager backupTransactionManager(
            @Qualifier("backupEntityManagerFactory") EntityManagerFactory emf) {
        log.info("配置备份库事务管理");
//        JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory(builder).getObject());
//        return transactionManager;
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(emf);

        return jpaTransactionManager;
    }

//    private Map<String, Object> getVendorProperties() {
//        log.info("get Vendor Properties");
//        return jpaProperties.getHibernateProperties(new HibernateSettings());
//    }

    private final Properties jpaProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");

        return properties;
    }
}

