/* 
 * 
 */
package com.shenten.blogger.article.dao.hbn;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.shenten.blogger.article.dao.ArticleDao;
import com.shenten.blogger.article.model.Article;
import com.shenten.framework.dao.hibernate.AbstractHbnDao;


@Repository
public class HbnArticleDao extends AbstractHbnDao<Article> implements ArticleDao {
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Article> getAll() {
		return getArticles("");
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Article> getArticles(String category) {
		Query q = getSession().getNamedQuery("getArticlesWithNumPages");
		List<Object[]> results = (List<Object[]>) q.list();
		List<Article> articles = new ArrayList<Article>();
		for (Object[] result : results) {
			Article article = (Article) result[0];
			// no category (empty) or matching category then add
			if (category == null || category.isEmpty() || article.getCategory().equals(category)) {
				long resultLong = (Long) result[1]; 
				int numPages =  resultLong > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) resultLong; 
				
				article.setCalculateStats(false);
				article.setNumPages(numPages);
				articles.add(article);
			}
		}
		return articles;
	}
	
	
	@Override
	public Article getByName(String name) {
		return (Article) getSession()
			.getNamedQuery("getArticleByName")
			.setParameter("name", name)
			.uniqueResult();
	}
	
	@Override
	public List<String> getCategories() {
		List<String> categories = new ArrayList<String>();
		
		Query q = getSession().getNamedQuery("getCategories");
		List<Object> results = (List<Object>) q.list();
		
		for (Object result : results) {
			String category = (String) result;
			categories.add(category);
			}
		
		return categories;
	}
}
