package edu.funix.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import edu.funix.common.IOptionService;
import edu.funix.common.imp.OptionService;
import edu.funix.model.OptionModel;

/**
 * Application Lifecycle Listener implementation class OptionConfigListener
 *
 */
@WebListener
public class OptionConfigListener implements ServletContextListener {
    private IOptionService optionService;
    /**
     * Default constructor. 
     */
    public OptionConfigListener() {
        optionService = new OptionService();
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
         ServletContext context = sce.getServletContext();
	try {
	    OptionModel option = optionService.currentOpt();
	    context.setAttribute("webOpt", option);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
	
}
