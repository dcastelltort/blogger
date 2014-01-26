/* 
 * 
 */
package com.shenten.blogger.article.dao;

import com.shenten.blogger.article.model.Article;
import com.shenten.framework.dao.Dao;


public interface ArticleDao extends Dao<Article> {
	
	Article getByName(String name);
}
