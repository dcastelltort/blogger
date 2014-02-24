/* 
 * 
 */
package com.shenten.blogger.article.service;

import java.util.List;

import com.shenten.blogger.article.model.Article;
import com.shenten.blogger.article.model.ArticlePage;
import com.shenten.blogger.comment.model.Comment;



public interface ArticleService {

	/**
	 * Returns all articles. The comments are not loaded.
	 * 
	 * @return all articles, but without comments
	 */
	List<Article> getArticles(String category);
	
	List<String> getCategories();
	
	ArticlePage getArticlePage(String articleName, int pageNumber) throws ArticlePageNotFoundException;
	
	void postComment(String articleName, Comment comment);
}
