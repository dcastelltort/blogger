/* 
 * 
 */
package com.shenten.blogger.article.dao.hbn;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.shenten.blogger.article.dao.ArticlePageDao;
import com.shenten.blogger.article.model.Article;
import com.shenten.blogger.article.model.ArticlePage;
import com.shenten.framework.dao.hibernate.AbstractHbnDao;


@Repository
public class HbnArticlePageDao extends AbstractHbnDao<ArticlePage> implements ArticlePageDao {
	
	@Override
	public ArticlePage getByArticleNameAndPageNumber(String articleName, int pageNumber) {
		Query q = getSession()
			.getNamedQuery("getArticlePageByArticleNameAndPageNumber")
			.setParameter("articleName", articleName)
			.setParameter("pageNumber", pageNumber);
		
		Object[] result = (Object[]) q.uniqueResult();
		ArticlePage page = (ArticlePage) result[0];
		int numPages = (Integer) result[1];
		
		Article article = page.getArticle();
		article.setCalculateStats(false);
		article.setNumPages(numPages);
		
		return page;
	}
}