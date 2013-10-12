package com.momoplan.pet.commons.spring;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.Resource;

import com.momoplan.pet.commons.zoo.config.ConfigWatcher;

/**
 * 当前应用的静态资源，包括 spring 对象池，以及 zoo watche 对象
 * 属性从 BootstrapContextLoaderListener 中初始化
 * @author liangc
 */
public class Bootstrap {
	
	/**
	 * 存放端口的文件名
	 */
	public final static String PORT_FILE = "port.txt";
	/**
	 * 容器监听的端口
	 */
	public static int http_port;
	
	static ConfigWatcher configWatcher = null;//不用对外了，包装起来
	
    static ApplicationContext appContext;
    private static Map<String, Object> testBeansByName = new HashMap<String, Object>();
    private static Map<Class<?>, Object> testBeansByType = new HashMap<Class<?>, Object>();
    private static String[] springConfigLocations ;  
    
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> beanClass) {

        if (Bootstrap.testBeansByType.get(beanClass) != null) {
            return (T) Bootstrap.testBeansByType.get(beanClass);
        }
        Collection<T> beans = appContext.getBeansOfType(beanClass).values();
        if (beans.isEmpty() && appContext.getParent() != null) {
            beans = appContext.getParent().getBeansOfType(beanClass).values();
        }
        T bean = beans.iterator().next();
        if (bean == null) {
            throw new IllegalArgumentException("Couldn't locate bean of " + beanClass + " type");
        }
        return bean;
    }

    public static Object getBean(String name) {
        if (Bootstrap.testBeansByName.get(name) != null) {
            return Bootstrap.testBeansByName.get(name);
        }
        Object bean = appContext.getBean(name);
        if (bean == null && appContext.getParent() != null) {
            bean = appContext.getParent().getBean(name);
        }
        return bean;
    }

    public static boolean hasAppContext() {
        return Bootstrap.appContext != null;
    }
    
    public static boolean isServer() {
    	// this is a convenient method that should be used with care as it relies
    	// on the fact that currently the agent is not deployed with spring framework
    	return hasAppContext();
    }

    /**
     * Sets the {@link ApplicationContext} subsequent to disposing of an existing 
     * context.
     * @param newAppContext New {@link ApplicationContext} instance 
     */
    public static final void setAppContext(final ApplicationContext newAppContext) { 
        //first dispose of any existing application context 
        dispose() ; 
        appContext = newAppContext ; 
    }//EOM 
    
    public static final ApplicationContext getApplicationContext() { 
    	return appContext ; 
    }//EOM 
    
    public static final void setSpringConfigLocations(final String...arrSpringConfigLocations) { 
    	springConfigLocations = arrSpringConfigLocations ; 
    }//EOM 
    
    public static final String[] getSpringConfigLocations() { 
    	return springConfigLocations ; 
    }//EOM 
    
    public static final void dispose() { 
        if(appContext != null && appContext instanceof GenericApplicationContext) { 
            ((GenericApplicationContext)appContext).close() ;
        }//EO there was an existing application context
        appContext = null ; 
    }//EOM 

    public static Resource getResource(String location) {
        return Bootstrap.appContext.getResource(location);
    }

    /**
     * For unit testing purposes only - controls what gets returned by
     * getBean(name)
     * @param name The lookup bean name
     * @param bean The bean
     */
    public static void setBean(String name, Object bean) {
        Bootstrap.testBeansByName.put(name, bean);
    }

    /**
     * For unit testing purposes only - controls what gets returned by
     * getBean(class)
     * @param beanClass The lookup bean class
     * @param bean The bean
     */
    public static void setBean(Class<?> beanClass, Object bean) {
        Bootstrap.testBeansByType.put(beanClass, bean);
    }
    
}