/**
 * 
 */
package com.shenten.blogger.article.service;

/**
 * @author shenten
 *
 */
public class ArticlePageNotFoundException extends RuntimeException {

	private String articleName;
	private int pageNumber;
	
	public ArticlePageNotFoundException(String articleName, int pageNumber) {
		super();
		setArticleName(articleName);
		setPageNumber(pageNumber);
	}
	/**
	 * @return the articleName
	 */
	public String getArticleName() {
		return articleName;
	}
	/**
	 * @param articleName the articleName to set
	 */
	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}
	/**
	 * @return the pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}
	/**
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	@Override 
	public String getMessage() {
		StringBuilder tmp = new StringBuilder();
		tmp.append("getPageNumber())Article Page Not Found: ");
		tmp.append("articleName : ");
		tmp.append(getArticleName());
		tmp.append(" pageNumber: ");
		tmp.append(getPageNumber());
		
		return tmp.toString();	
	}
}