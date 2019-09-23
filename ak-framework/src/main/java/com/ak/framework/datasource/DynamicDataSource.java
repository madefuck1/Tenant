package com.ak.framework.datasource;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.ak.common.exception.BusinessException;
import com.alibaba.druid.pool.DruidDataSource;

import lombok.extern.log4j.Log4j2;

/**
 * 动态数据源
 *
 * @author Vean
 */
@Log4j2
final class DynamicDataSource extends AbstractRoutingDataSource implements ApplicationContextAware {

    private static final String DATA_SOURCES_NAME = "targetDataSources";

    private ApplicationContext applicationContext;

    public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSources);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        DataSourceBeanBuilder dataSourceBeanBuilder = DataSourceHolder.getDataSource();
        if (dataSourceBeanBuilder == null) {
            return null;
        } else {
            DataSourceBean dataSourceBean = new DataSourceBean(dataSourceBeanBuilder);
            log.debug("dataSourceBean ->" + dataSourceBean.getBeanName());
            try {
                Map<Object, Object> map = getTargetDataSources();
                synchronized (map) {
                    if (!map.containsKey(dataSourceBean.getBeanName())) {
                        map.put(dataSourceBean.getBeanName(), createDataSource(dataSourceBean));
                        super.afterPropertiesSet();// 通知spring有bean更新
                    }
                }
                return dataSourceBean.getBeanName();
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new BusinessException(e.getMessage());
            }
        }
    }

    private Object createDataSource(DataSourceBean dataSourceBean) throws IllegalAccessException {
        // 在spring容器中创建并且声明bean
        ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
                .genericBeanDefinition(DruidDataSource.class);
        // 将dataSourceBean中的属性值赋给目标bean
        Map<String, Object> properties = getPropertyKeyValues(DataSourceBean.class, dataSourceBean);
        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            beanDefinitionBuilder.addPropertyValue((String) entry.getKey(), entry.getValue());
        }
        beanFactory.registerBeanDefinition(dataSourceBean.getBeanName(), beanDefinitionBuilder.getBeanDefinition());
        return applicationContext.getBean(dataSourceBean.getBeanName());
    }

    @SuppressWarnings("unchecked")
    private Map<Object, Object> getTargetDataSources() throws NoSuchFieldException, IllegalAccessException {
        Field field = AbstractRoutingDataSource.class.getDeclaredField(DATA_SOURCES_NAME);
        field.setAccessible(true);
        return (Map<Object, Object>) field.get(this);
    }

    private <T> Map<String, Object> getPropertyKeyValues(Class<T> clazz, Object object) throws IllegalAccessException {
        Field[] fields = clazz.getDeclaredFields();
        Map<String, Object> result = new HashMap<>();
        for (Field field : fields) {
            field.setAccessible(true);
            result.put(field.getName(), field.get(object));
        }
        result.remove("beanName");
        return result;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}