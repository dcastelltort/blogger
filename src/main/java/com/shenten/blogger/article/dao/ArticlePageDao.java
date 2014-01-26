/* 
 * 
 */
package com.shenten.blogger.article.dao;

import com.shenten.blogger.article.model.ArticlePage;
import com.shenten.framework.dao.Dao;


public interface ArticlePageDao extends Dao<ArticlePage> {
	
	ArticlePage getByArticleNameAndPageNumber(String articleName, int pageNumber);
}
