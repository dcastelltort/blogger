/* 
 * 
 */
package com.shenten.blogger.comment.service.impl;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.shenten.blogger.comment.model.Comment;
import com.shenten.blogger.comment.service.CommentService;
import com.shenten.blogger.comment.service.PostCommentCallback;
import com.shenten.blogger.comment.service.TextFilter;
import com.shenten.framework.web.WebUtils;


/**
 * @author 
 */
@Service
public class CommentServiceImpl implements CommentService {
	@Inject private TextFilter textFilter;
	@Inject private CommentMailSender mailSender;
	
	public TextFilter getTextFilter() { return textFilter; }

	public void setTextFilter(TextFilter filter) { this.textFilter = filter; }
	
	/* (non-Javadoc)
	 * @see com.springinpractice.ch09.comment.service.CommentService#postComment
	 * (com.springinpractice.ch09.comment.model.Comment, com.springinpractice.ch09.comment.service.PostCommentCallback)
	 */
	@Override
	public void postComment(final Comment comment, final PostCommentCallback callback) {
		prepareComment(comment);
		callback.post(comment);
		mailSender.sendNotificationEmail(comment);
	}

	private void prepareComment(final Comment comment) {
		comment.setWeb(WebUtils.cleanupWebUrl(comment.getWeb()));
		comment.setDateCreated(new Date());
		comment.setHtmlText(textFilter.filter(comment.getMarkdownText()));
	}
}
