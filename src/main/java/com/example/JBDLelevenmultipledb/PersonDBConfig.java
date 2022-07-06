package com.example.JBDLelevenmultipledb;

//How to connect to 2 different databases - NO RECOMMENDED, but just example

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
        basePackages = {"com.example.JBDLelevenmultipledb.persondb"},
        entityManagerFactoryRef = "personEntityManager",
        transactionManagerRef = "personTxnManager"
)
public class PersonDBConfig {

    @Autowired
    Environment environment;

    //To properly "manually" configure a DB configuration, we have to include the
    // 3 things: 1) DataSource 2) EntityManager 3) Transaction Manager

    @Bean
    @ConfigurationProperties(prefix = "spring.personds")
    public DataSource personDataSource(){
        return DataSourceBuilder.create().build();
    }

    //JPA requires below bean when doing 2 databases in one application, for one DB, JPA creates the EntityManager automatically
    @Bean
    public LocalContainerEntityManagerFactoryBean personEntityManager(){
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();

        em.setDataSource(personDataSource());
        //whatever model is in the below folder it will be mapped to the datasource
        em.setPackagesToScan("com.example.JBDLelevenmultipledb.persondb");

        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();

        HashMap<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.ddl-auto", "update");
        //update property
        properties.put("hibernate.hbm2ddl.auto", environment.getProperty("personds.hibernate.ddl-auto"));
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");

        em.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    public PlatformTransactionManager personTxnManager(){
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(personEntityManager().getObject());

        return jpaTransactionManager;
    }

}
