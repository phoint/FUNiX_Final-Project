package edu.funix.model;

import java.util.List;

public class CommentModel extends AbstractModel<CommentModel> {
	private String comContent;
	private Long submitTo;
	private Long replyTo;
	private Boolean confirm;
	private List<CommentModel> replies;
	
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
	
}
