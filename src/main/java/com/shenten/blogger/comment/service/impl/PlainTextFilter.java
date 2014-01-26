/* 
 * 
 */
package com.shenten.blogger.comment.service.impl;

import com.shenten.blogger.comment.service.TextFilter;


/**
 * @author 
 */
public final class PlainTextFilter implements TextFilter {
	
	@Override
	public String filter(String text) {
		return text.replace("&", "&amp;")
			.replace("<", "&lt;")
			.replace(">", "&gt;")
			.replace("\n", "<br />");
	}
}
