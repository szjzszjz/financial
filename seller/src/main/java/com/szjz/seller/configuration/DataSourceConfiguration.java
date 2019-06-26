package com.szjz.seller.configuration;

import com.szjz.model.Order;
import com.szjz.seller.repository.OrderRepository;
import com.szjz.seller.repositoryBackup.VerificationOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
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
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

/**
 * author:szjz
 * date:2019/6/26
 * 多数据源配置
 */
@Slf4j
//@Configuration
public class DataSourceConfiguration {


    /**
     * 配置主库数据源
     */
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDataSource() {
        log.info("配置主库数据源");
        return DataSourceBuilder.create().build();
    }

    /**
     * 配置备份库数据源
     */
    @Bean(name = "backupDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.backup")
    public DataSource backupDataSource() {
        log.info("配置备份库数据源");
        return DataSourceBuilder.create().build();
    }


//    /**
//     * repository 扫描的时候并不确定先扫描哪一个
//     */
//    @Configuration
//    @EnableTransactionManagement
//    @EnableJpaRepositories(
//            basePackageClasses = OrderRepository.class, //OrderRepository 所在的包路径
//            entityManagerFactoryRef = "primaryEntityManagerFactory",
//            transactionManagerRef = "primaryTransactionManager")
//    public class PrimaryConfiguration {
//
//        @Autowired(required = false)
//        private JpaProperties jpaProperties;
//
//        @Autowired
//        @Qualifier("primaryDataSource")
//        private DataSource primaryDataSource;
//
//        @Bean(name = "primaryEntityManager")
//        @Primary
//        public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
//            return entityManagerFactory(builder)
//                    .getObject()
//                    .createEntityManager();
//        }
//
//        /**
//         * 配置主库实体类管理工厂
//         */
//        @Bean(name = "primaryEntityManagerFactory")
//        @Primary
//        public LocalContainerEntityManagerFactoryBean entityManagerFactory(
//                EntityManagerFactoryBuilder builder) {
//            log.info("配置主库实体类管理工厂bean");
//            return builder
//                    .dataSource(primaryDataSource) //数据源
//                    .packages(Order.class) //实体类所在包路径
//                    .properties(getVendorProperties())
//                    .persistenceUnit("backup") //持久单元
//                    .build();
////            LocalContainerEntityManagerFactoryBean entityManagerFactory
////                    = builder
////                    .dataSource(primaryDataSource) //数据源
////                    .packages(Order.class) //实体类所在包路径
//////                    .properties(getVendorProperties())
////                    .persistenceUnit("primary") //持久单元
////                    .build();
////            entityManagerFactory.setJpaProperties(properties);
////            return entityManagerFactory;
//        }
//
//        /**
//         * org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration
//         * 配置主库事务管理
//         */
//        @Bean(name = "primaryTransactionManager")
//        @Primary
//        public PlatformTransactionManager primaryTransactionManager(
//                EntityManagerFactoryBuilder builder) {
//            log.info("配置主库事务管理");
//            JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory(builder).getObject());
//            return transactionManager;
//        }
//
//        private Map<String, Object> getVendorProperties() {
//            log.info("get Vendor Properties");
//            return jpaProperties.getHibernateProperties(new HibernateSettings());
//        }
//    }

//    @Configuration
//    @EnableTransactionManagement
//    @EnableJpaRepositories(
//            basePackageClasses = VerificationOrderRepository.class, //VerificationOrderRepository 所在包路径
//            entityManagerFactoryRef = "backupEntityManagerFactory",
//            transactionManagerRef = "backupTransactionManager")
//    public class BackupConfiguration {
//
//        @Autowired(required = false)
//        private JpaProperties jpaProperties;
//
//        @Autowired
//        @Qualifier("backupDataSource")
//        private DataSource backupDataSource;
//
//        @Bean(name = "backupEntityManager")
//        @Primary
//        public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
//            return entityManagerFactory(builder).getObject().createEntityManager();
//        }
//
//        /**
//         * 配置备份库实体类管理工厂
//         */
//        @Bean(name = "backupEntityManagerFactory")
//        public LocalContainerEntityManagerFactoryBean entityManagerFactory(
//                EntityManagerFactoryBuilder builder) {
//            log.info("配置备份库实体类管理工厂");
//            return builder
//                    .dataSource(backupDataSource) //数据源
//                    .packages(Order.class) //实体类所在包路径
//                    .properties(getVendorProperties())
//                    .persistenceUnit("backup") //持久单元
//                    .build();
////            LocalContainerEntityManagerFactoryBean entityManagerFactory
////                    = builder
////                    .dataSource(backupDataSource) //数据源
////                    .packages(Order.class) //实体类所在包路径
//////                    .properties(getVendorProperties())
////                    .persistenceUnit("backup") //持久单元
////                    .build();
////            entityManagerFactory.setJpaProperties(properties);
////            return entityManagerFactory;
//
//        }
//
//        /**
//         * org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration
//         * 配置备份库事务管理
//         */
//        @Bean(name = "backupTransactionManager")
//        public PlatformTransactionManager backupTransactionManager(
//                EntityManagerFactoryBuilder builder) {
//            log.info("配置备份库事务管理");
//            JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory(builder).getObject());
//            return transactionManager;
//        }
//
//        private Map<String, Object> getVendorProperties() {
//            log.info("get Vendor Properties");
//            return jpaProperties.getHibernateProperties(new HibernateSettings());
//        }
//    }


    /**
     * spring boot 2.0.0 jpaProperties.getHibernateProperties(new HibernateSettings())
     * spring boot 1.5.0 jpaProperties.getHibernateProperties(DataSource datasource)
     */
//    private Map<String, Object> getVendorProperties() {
//        log.info("get Vendor Properties");
//        return jpaProperties.getHibernateProperties(new HibernateSettings());
//    }


}
