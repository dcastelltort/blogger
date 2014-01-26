/* 
 * 
 */
package com.shenten.blogger.comment.service;

import com.shenten.blogger.comment.model.Comment;


/**
 * Comment service interface.
 * 
 * @author 
 */
public interface CommentService {

	/**
	 * Template method for posting a comment. Prepares the comment for storage (e.g., input sanitization, setting
	 * creation dates, etc.) before invoking the callback to do the actual work of saving the comment. Using a callback
	 * facilitates the integration of the comment engine with apps that want to use it, such as blogs and other
	 * publishing websites, product catalogs and so forth.
	 * 
	 * @param comment comment
	 * @param callback callback
	 */
	void postComment(Comment comment, PostCommentCallback callback);
}
