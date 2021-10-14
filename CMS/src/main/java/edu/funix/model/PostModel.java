/*
 * @(#) PostModel.java 1.0 2021/09/06
 * 
 * Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package edu.funix.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Part;

/**
 * Represent a post
 * 
 * @author Phoi Nguyen
 * @version 1.0 06 September 2021
 */
public class PostModel extends AbstractModel<PostModel> {
    private String title;
    private String excerpt;
    private String content;
    private String postUrl;
    private Date publishDate;
    private int postStatus;
    private int isVisible;
    /** Represent a feature image of post */
    private Long feature;
    /** Represent an image inserted to post */
    private Part imageUpload;
    private MediaModel image;
    private UserModel author;
    private List<CategoryModel> categories;
    private List<CommentModel> comments;

    /** Create a post */
    public PostModel() {
	super();
	author = new UserModel();
	categories = new ArrayList<CategoryModel>();
    }

    /**
     * Gets the author created the post
     * 
     * @return An instance representing author of post
     */
    public UserModel getAuthor() {
	return author;
    }

    /**
     * Sets the author
     * 
     * @param author An instance containing the author of post
     */
    public void setAuthor(UserModel author) {
	this.author = author;
    }

    /**
     * Gets the list of category instances grouping the post
     * 
     * @return A list representing category instances
     */
    public List<CategoryModel> getCategories() {
	return categories;
    }

    /**
     * Sets the list of category instances
     * 
     * @param categories A list of category instance
     */
    public void setCategories(List<CategoryModel> categories) {
	this.categories = categories;
    }

    /**
     * Gets the post's title
     * 
     * @return A string representing the post's title
     */
    public String getTitle() {
	return title;
    }

    /**
     * Sets the post's title
     * 
     * @param title A string containing the post's title
     */
    public void setTitle(String title) {
	this.title = title;
    }

    /**
     * Gets the post's excerpt
     * 
     * @return A string representing the post's excerpt
     */
    public String getExcerpt() {
	return excerpt;
    }

    /**
     * Sets the post's excerpt
     * 
     * @param excerpt A string containing the post excerpt
     */
    public void setExcerpt(String excerpt) {
	this.excerpt = excerpt;
    }

    /**
     * Gets the post's content
     * 
     * @return A string representing the post's content
     */
    public String getContent() {
	return content;
    }

    /**
     * Sets the post's content
     * 
     * @param content A string containing the post's content
     */
    public void setContent(String content) {
	this.content = content;
    }

    /**
     * Gets the post's url
     * 
     * @return A string representing the post's url
     */
    public String getPostUrl() {
	return postUrl;
    }

    /**
     * Sets the post's url
     * 
     * @param postUrl A string containing the post's url
     */
    public void setPostUrl(String postUrl) {
	this.postUrl = postUrl;
    }

    /**
     * Gets the post's published date
     * 
     * @return a date representing the post's published date
     */
    public Date getPublishDate() {
	return publishDate;
    }

    /**
     * Sets the post's published date
     * 
     * @param publishDate A date containing the post's published date
     */
    public void setPublishDate(Date publishDate) {
	this.publishDate = publishDate;
    }

    /**
     * Gets the post's status
     * 
     * @return An integer representing post's status code
     */
    public int getPostStatus() {
	return postStatus;
    }

    /**
     * Sets the post's status
     * 
     * @param postStatus An integer containing the post's status code
     */
    public void setPostStatus(int postStatus) {
	this.postStatus = postStatus;
    }

    /**
     * Gets the post's visibility
     * 
     * @return An integer representing the post's visibility code
     */
    public int getIsVisible() {
	return isVisible;
    }

    /**
     * Sets the post's visibility
     * 
     * @param isVisible An integer containing the post's visibility code
     */
    public void setIsVisible(int isVisible) {
	this.isVisible = isVisible;
    }

    /**
     * Gets the post's feature image
     * 
     * @return A string representing the url of post's feature image
     */
    public Long getFeature() {
	return feature;
    }

    /**
     * Sets the post's feature image
     * 
     * @param feature A string containing the url of post's feature image
     */
    public void setFeature(Long feature) {
	this.feature = feature;
    }

    /**
     * Gets the post's comments
     * 
     * @return A list of instances representing the post's comment
     */
    public List<CommentModel> getComments() {
	return comments;
    }

    /**
     * Sets the post's comments
     * 
     * @param comments A list of instances containing the post's comment
     */
    public void setComments(List<CommentModel> comments) {
	this.comments = comments;
    }

    /**
     * Gets the image uploaded from UI
     * 
     * @return An instance representing the image uploaded
     */
    public Part getImageUpload() {
	return imageUpload;
    }

    /**
     * Sets the image uploaded from UI
     * 
     * @param imageUpload An Part containing the image uploaded
     */
    public void setImageUpload(Part imageUpload) {
	this.imageUpload = imageUpload;
    }

    /**
     * Gets image's information
     * 
     * @return An instance representing the image's attribute
     */
    public MediaModel getImage() {
	return image;
    }

    /**
     * Sets image's information
     * 
     * @param image An instance containing image's attribute
     */
    public void setImage(MediaModel image) {
	this.image = image;
    }

}
