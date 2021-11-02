/*
 * @(#) Categories.java 1.0 2021/09/06
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
import edu.funix.model.PageModel;

/**
 * Servlet implementation class Categories
 */
@WebServlet("/admin/categories")
public class Categories extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Categories.class);

    private ICategoryService categoryService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Categories() {
	categoryService = new CategoryService();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	logger.info("called http get method, referer is: " + request.getHeader("referer"));
	response.setContentType("text/html;charset=UTF-8");
	request.setCharacterEncoding("UTF-8");
	/* All categories' id selected for action */
	String[] id = request.getParameterValues("id");
	String action = request.getParameter("action");
	/* Search term for querying categories */
	String searchKey = request.getParameter("searchKey");
	CategoryModel categories = new CategoryModel();
	PageModel page = new PageModel();
	/* Message and error return on process */
	String message = null;
	String error = null;
	
	/* Delete a category has matching id */
	try {
	    if (id != null && action.equals("delete")) {
		logger.debug("Delete category by id(s): " + id);
		categoryService.delete(id);
		message = "Delete success";
	    }
	} catch (Exception e) {
	    error = e.getMessage();
	    logger.error(e.getMessage(), e);
	    SlackApiUtil.pushLog(request, e.getMessage());
	}
	
	/* Mapping parameter to PageModel */
	try {
	    logger.debug("Mapping pagination's attributes to page model");
	    BeanUtils.populate(page, request.getParameterMap());
	    logger.debug("{}", page);
	} catch (IllegalAccessException | InvocationTargetException e) {
	    error = e.getMessage();
	    logger.error(e.getMessage(), e);
	    SlackApiUtil.pushLog(request, e.getMessage());
	}
	if (searchKey != null && searchKey.trim().equals("")) {
	    searchKey = null;
	}
	/* Display a category list base on search term and pagination */
	try {
	    if (searchKey != null) {
		logger.debug("Display categories by search key: " + searchKey + " on page " + page.getCurrentPage());
		categories.setListResult(categoryService.search(page, searchKey));
	    } else {
		logger.debug("Display categories on page " + page.getCurrentPage());
		categories.setListResult(categoryService.findAll(page));
	    }
	} catch (Exception e) {
	    error = e.getMessage();
	    logger.error(e.getMessage(), e);
	    SlackApiUtil.pushLog(request, e.getMessage());
	}
	
	request.setAttribute("error", error);
	request.setAttribute("message", message);
	request.setAttribute("searchKey", searchKey);
	request.setAttribute("page", page);
	request.setAttribute("categories", categories);
	logger.info("Forward to category management page");
	PageInfo.PrepareAndForward(request, response, PageType.CATEGORY_MANAGEMENT_PAGE);

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
	String action = request.getParameter("action");
	String[] ids = request.getParameterValues("id");
	PageModel page = new PageModel();
	CategoryModel category = new CategoryModel();
	/* Message and error return on process */
	String message = null;
	String error = null;

	try {
	    if (ids != null && action.equals("delete")) {
		logger.debug("delete categories by ids: " + ids);
		categoryService.delete(ids);
		message = "Delete Success";
	    } else if (ids == null) {
		logger.debug("Mapping category's attribute to category model");
		BeanUtils.populate(category, request.getParameterMap());
		if (category.getDesc() != null && category.getDesc().equals("")) {
		    category.setDesc(null);
		}
		logger.debug("{}", category);
		logger.debug("Insert new category into database");
		categoryService.save(category);
		message = "New category inserted";
	    }
	    logger.debug("Get new category list");
	    category.setListResult(categoryService.findAll(page));
	    logger.debug("{}", category.getListResult());
	} catch (IllegalAccessException | InvocationTargetException e) {
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
	request.setAttribute("message", message);
	request.setAttribute("error", error);
	request.setAttribute("categories", category);
	request.setAttribute("page", page);
	logger.info("Forward to category management page");
	PageInfo.PrepareAndForward(request, response, PageType.CATEGORY_MANAGEMENT_PAGE);
    }
}
