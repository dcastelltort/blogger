/* 
 * 
 */
package com.shenten.blogger.article.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.shenten.blogger.article.dao.ArticleDao;
import com.shenten.blogger.article.dao.ArticlePageDao;
import com.shenten.blogger.article.model.Article;
import com.shenten.blogger.article.model.ArticlePage;
import com.shenten.blogger.article.service.ArticlePageNotFoundException;
import com.shenten.blogger.article.service.ArticleService;
import com.shenten.blogger.comment.model.Comment;
import com.shenten.blogger.comment.service.CommentService;
import com.shenten.blogger.comment.service.PostCommentCallback;

@Service
@Transactional(
	propagation = Propagation.REQUIRED,
	isolation = Isolation.DEFAULT,
	readOnly = true)
public class ArticleServiceImpl implements ArticleService {
	@Inject private ArticleDao articleDao;
	@Inject private ArticlePageDao pageDao;
	@Inject private CommentService commentService;
	
	public List<Article> getArticles(String category) { 
		
		return articleDao.getArticles(category); 
	}
	
	public List<String> getCategories() {
		return articleDao.getCategories();
	}
	
	public ArticlePage getArticlePage(String articleName, int pageNumber) throws ArticlePageNotFoundException {
		try {
			ArticlePage page = pageDao.getByArticleNameAndPageNumber(articleName, pageNumber);
			Hibernate.initialize(page.getArticle().getComments());
			return page;
		}
		catch (IllegalArgumentException cause) {
			throw new ArticlePageNotFoundException(cause, articleName, pageNumber);
		}
	}
	
	@Transactional(
		propagation = Propagation.REQUIRED,
		isolation = Isolation.DEFAULT,
		readOnly = false)
	public void postComment(final String articleName, Comment comment) {
		commentService.postComment(comment, new PostCommentCallback() {
			public void post(Comment comment) {
				Article article = articleDao.getByName(articleName);
				article.getComments().add(comment);
				articleDao.update(article);
			}
		});
	}
}
