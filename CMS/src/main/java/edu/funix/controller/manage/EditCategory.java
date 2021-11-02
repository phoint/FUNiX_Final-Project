/*
 * @(#) EditCategory.java 1.0 2021/09/06
 * 
 * Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package edu.funix.controller.manage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.LoggerFactory;

import edu.funix.Utils.PageInfo;
import edu.funix.Utils.PageType;
import edu.funix.Utils.SlackApiUtil;
import edu.funix.common.ICategoryService;
import edu.funix.common.imp.CategoryService;
import edu.funix.model.CategoryModel;

/**
 * Servlet implementation class EditCategory
 */
@WebServlet("/admin/edit-category")
public class EditCategory extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(EditCategory.class);
    private ICategoryService categoryService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditCategory() {
	super();
	categoryService = new CategoryService();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	logger.info("called http get method, referer is: " + request.getHeader("referer"));
	String id = request.getParameter("id");
	String message = null;
	String error = null;
	CategoryModel category = null;
	try {
	    if (id != null) {
		logger.debug("Find category by id: " + id);
		category = categoryService.findCategoryById(Long.parseLong(id));
		logger.debug("{}", category);
	    }
	} catch (NumberFormatException e) {
	    error = e.getMessage();
	    logger.error(e.getMessage(), e);
	    SlackApiUtil.pushLog(request, e.getMessage());
	} catch (SQLException e) {
	    error = e.getMessage();
	    logger.error(e.getMessage(), e);
	    SlackApiUtil.pushLog(request, e.getMessage());
	} catch (Exception e) {
	    error = e.getMessage();
	    logger.error(e.getMessage(), e);
	    SlackApiUtil.pushLog(request, e.getMessage());
	}
	request.setAttribute("category", category);
	request.setAttribute("error", error);
	request.setAttribute("message", message);
	logger.info("Forward to edit category page");
	PageInfo.PrepareAndForward(request, response, PageType.EDIT_CATEGORY);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	logger.info("called http post method, referer is: " + request.getHeader("referer"));
	response.setContentType("text/html;charset=UTF-8");
	request.setCharacterEncoding("UTF-8");
	String message = null;
	String error = null;
	CategoryModel category = new CategoryModel();

	try {
	    logger.debug("Mapping category's attributes to category model");
	    BeanUtils.populate(category, request.getParameterMap());
	    if (category.getDesc().equals("")) {
		category.setDesc(null);
	    }
	    logger.debug("{}", category);
	    try {
		logger.debug("Update category");
		categoryService.edit(category);
		message = "Update success!";
		logger.debug("Success");
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
	request.setAttribute("category", category);
	request.setAttribute("message", message);
	request.setAttribute("error", error);
	logger.info("Forward to edit category page");
	PageInfo.PrepareAndForward(request, response, PageType.EDIT_CATEGORY);
    }

}
