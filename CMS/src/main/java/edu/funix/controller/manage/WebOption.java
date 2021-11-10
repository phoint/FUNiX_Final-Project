package edu.funix.controller.manage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.LoggerFactory;

import edu.funix.common.IOptionService;
import edu.funix.common.imp.OptionService;
import edu.funix.model.OptionModel;
import edu.funix.utils.PageInfo;
import edu.funix.utils.PageType;
import edu.funix.utils.SlackApiUtil;

/**
 * Servlet implementation class WebOption
 */
@WebServlet("/admin/option")
public class WebOption extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(WebOption.class);
    private IOptionService optionService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public WebOption() {
	optionService = new OptionService();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	logger.info("called http get method, referer is: " + request.getHeader("referer"));
	PageInfo.PrepareAndForward(request, response, PageType.OPTION);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	logger.info("called http post method, referer is: " + request.getHeader("referer"));
	OptionModel option = new OptionModel();
	String message = null;
	String error = null;
	try {
	    logger.debug("Mapping Option's attributes to OptionModel");
	    BeanUtils.populate(option, request.getParameterMap());
	    logger.debug("{}", option);
	    try {
		optionService.save(option);
		optionService.pushParam(request);
		message = "The setting is saved";
		logger.debug("{}", optionService.currentOpt());
	    } catch (Exception e) {
		error = e.getMessage();
		logger.error(e.getMessage(), e);
		SlackApiUtil.pushLog(request, e.getMessage());
	    }
	} catch (IllegalAccessException | InvocationTargetException e) {
	    error = e.getMessage();
	    logger.error(e.getMessage(), e);
	    SlackApiUtil.pushLog(request, e.getMessage());
	}
	request.setAttribute("message", message);
	request.setAttribute("error", error);
	PageInfo.PrepareAndForward(request, response, PageType.OPTION);

    }

}
