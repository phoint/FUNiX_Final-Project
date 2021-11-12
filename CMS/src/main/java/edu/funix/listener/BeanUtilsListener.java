package edu.funix.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.DateConverter;

/**
 * Application Lifecycle Listener implementation class BeanUtilsListener
 *
 */
@WebListener
public class BeanUtilsListener implements ServletContextListener {

    /**
     * Default constructor.
     */
    public BeanUtilsListener() {
	// TODO Auto-generated constructor stub
    }

    /**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
	// TODO Auto-generated method stub
    }

    /**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
	/* Convert empty to null for java.sql.Date type */
	ConvertUtils.register(new Converter() {

	    @SuppressWarnings("unchecked")
	    @Override
	    public <T> T convert(Class<T> type, Object value) {
		String tmp = null;
		if (value == null) {
		    return null;
		}

		if (value instanceof String) {
		    tmp = (String) value;
		    if (tmp.trim().length() == 0) {
			return null;
		    }
		} else {
		    throw new ConversionException("not String");
		}
		return (T) tmp;
	    }

	}, String.class);
	
	/* Convert empty to null for java.sql.Date type */
	ConvertUtils.register(new DateConverter(null),java.sql.Date.class);
    }

}
