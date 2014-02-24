/* 
 * 
 */
package com.shenten.blogger.article.dao;

import java.util.List;

import com.shenten.blogger.article.model.Article;
import com.shenten.framework.dao.Dao;


public interface ArticleDao extends Dao<Article> {
	
	Article getByName(String name);
	List<Article> getArticles(String category);
	List<String> getCategories();
}
