package com.dynamic.mutidatasource.config;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态数据源注册器
 */
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private static final String DATASOURCE_TYPE_DEFAULT = "com.zaxxer.hikari.HikariDataSource";
    //默认数据源
    private DataSource defaultDataSource;
    //用户自定义数据源
    private Map<String, DataSource> slaveDataSources = new HashMap<String, DataSource>();

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        //添加默认数据源
        targetDataSources.put(DataType.main.name(), this.defaultDataSource);
        DynamicDataSourceContextHolder.dataSourceIds.add(DataType.main.name());

        //添加其他数据源
        targetDataSources.putAll(slaveDataSources);
        for (String key : slaveDataSources.keySet()) {
            DynamicDataSourceContextHolder.dataSourceIds.add(key);
        }

        //创建DynamicDataSource
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicDataSource.class);
        beanDefinition.setSynthetic(true);
        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
        mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
        mpv.addPropertyValue("targetDataSources", targetDataSources);
        //注册 - BeanDefinitionRegistry
        beanDefinitionRegistry.registerBeanDefinition(DataType.main.name(), beanDefinition);
    }

    @Override
    public void setEnvironment(Environment environment) {
        try {
            firstDataSource(environment);
            secondDataSource(environment);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void firstDataSource(Environment env) throws ClassNotFoundException {
        DataSourceBuilder factory = DataSourceBuilder
                .create()
                .driverClassName(env.getProperty("spring.datasource.driver-class-name"))
                .url(env.getProperty("spring.datasource.url"))
                .username(env.getProperty("spring.datasource.username"))
                .password(env.getProperty("spring.datasource.password"))
                .type((Class<? extends DataSource>) Class.forName(DATASOURCE_TYPE_DEFAULT));
        this.defaultDataSource = factory.build();
    }

    private void secondDataSource(Environment env) throws ClassNotFoundException {
        DataSourceBuilder factory = DataSourceBuilder
                .create()
                .driverClassName(env.getProperty("slave.datasource.second.driver-class-name"))
                .url(env.getProperty("slave.datasource.second.url"))
                .username(env.getProperty("slave.datasource.second.username"))
                .password(env.getProperty("slave.datasource.second.password"))
                .type((Class<? extends DataSource>) Class.forName(DATASOURCE_TYPE_DEFAULT));
        this.slaveDataSources.put(DataType.slave1.name(), factory.build());
    }
}
