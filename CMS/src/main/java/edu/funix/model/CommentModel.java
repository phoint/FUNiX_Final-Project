package edu.funix.model;

import java.util.List;

public class CommentModel extends AbstractModel<CommentModel> {
    private String comContent;
    private Long submitTo;
    private Long replyTo;
    private Boolean confirm;
    private List<CommentModel> replies;
    private UserModel author;
    private PostModel responseIn;

    public CommentModel() {
	super();
    }

    public String getComContent() {
	return comContent;
    }

    public void setComContent(String comContent) {
	this.comContent = comContent;
    }

    public Long getSubmitTo() {
	return submitTo;
    }

    public void setSubmitTo(Long submitTo) {
	this.submitTo = submitTo;
    }

    public Long getReplyTo() {
	return replyTo;
    }

    public void setReplyTo(Long replyTo) {
	this.replyTo = replyTo;
    }

    public Boolean getConfirm() {
	return confirm;
    }

    public void setConfirm(Boolean confirm) {
	this.confirm = confirm;
    }

    public List<CommentModel> getReplies() {
	return replies;
    }

    public void setReplies(List<CommentModel> replies) {
	this.replies = replies;
    }

    public UserModel getAuthor() {
	return author;
    }

    public void setAuthor(UserModel author) {
	this.author = author;
    }

    public PostModel getResponseIn() {
	return responseIn;
    }

    public void setResponseIn(PostModel responseIn) {
	this.responseIn = responseIn;
    }

    @Override
    public String toString() {
	return "CommentModel [comContent=" + comContent + ", submitTo=" + submitTo + ", replyTo=" + replyTo
		+ ", confirm=" + confirm + ", toString()=" + super.toString() + "]";
    }

}
