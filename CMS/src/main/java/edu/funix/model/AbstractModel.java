/*
 * @(#) AbstractModel.java 1.0 2021/09/06
 * 
 * Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package edu.funix.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an abstract object with common attributes for inheritance
 * 
 * @author Phoi Nguyen
 * @version 1.0 06 September 2021
 */
public class AbstractModel<T> {
    private Long id;
    private Date createdDate;
    private Date modifiedDate;
    private Long createdBy;
    private Long modifiedBy;
    private PageModel pagination;
    /** Represents a list of generic object */
    private List<T> listResult = new ArrayList<>();

    /** Create an abstract object */
    public AbstractModel() {
    }

    /**
     * Gets the pagination's information
     * 
     * @return A PageModel representing the pagination's information
     */
    public PageModel getPage() {
	return pagination;
    }

    /**
     * Sets the pagination's information
     * 
     * @param pagination A PageModel containing the pagination's information
     */
    public void setPage(PageModel pagination) {
	this.pagination = pagination;
    }

    /**
     * Gets the user's id who created the object
     * 
     * @return A long representing the user's id
     */
    public Long getCreatedBy() {
	return createdBy;
    }

    /**
     * Sets the user's id who created the object
     * 
     * @param createdBy A long containing the user's id
     */
    public void setCreatedBy(Long createdBy) {
	this.createdBy = createdBy;
    }

    /**
     * Gets the user's id who modified the object
     * 
     * @return A long representing the user's id
     */
    public Long getModifiedBy() {
	return modifiedBy;
    }

    /**
     * Gets the user's id who modified the object
     * 
     * @param modifiedBy A long containing the user's id
     */
    public void setModifiedBy(Long modifiedBy) {
	this.modifiedBy = modifiedBy;
    }

    /**
     * Gets the object's id
     * 
     * @return A long representing the object's id
     */
    public Long getId() {
	return id;
    }

    /**
     * Sets the object's id
     * 
     * @param id A long containing the object's id
     */
    public void setId(Long id) {
	this.id = id;
    }

    /**
     * Gets the object's created date
     * 
     * @return A date representing the object's created date
     */
    public Date getCreatedDate() {
	return createdDate;
    }

    /**
     * Sets the object's created date
     * 
     * @param createdDate A Date containing the object's created date
     */
    public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
    }

    /**
     * Gets the object's modified date
     * 
     * @return A date representing the object's modified date
     */
    public Date getModifiedDate() {
	return modifiedDate;
    }

    /**
     * Sets the object's modified date
     * 
     * @param createdDate A Date containing the object's modified date
     */
    public void setModifiedDate(Date modifiedDate) {
	this.modifiedDate = modifiedDate;
    }

    /**
     * Gets a list of generic objects
     * 
     * @return A list representing the generic objects in the generic class
     */
    public List<T> getListResult() {
	return listResult;
    }

    /**
     * Sets a list of generic objects
     * 
     * @param listResult A list containing the generic objects in the generic class
     */
    public void setListResult(List<T> listResult) {
	this.listResult = listResult;
    }

    @Override
    public String toString() {
	return "AbstractModel [id=" + id + ", createdDate=" + createdDate + "]";
    }
    
    
}
