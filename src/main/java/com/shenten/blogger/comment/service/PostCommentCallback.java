/* 
 * 
 */
package com.shenten.blogger.comment.service;

import com.shenten.blogger.comment.model.Comment;



/**
 * @author 
 */
public interface PostCommentCallback {
	
	void post(Comment comment);
}
