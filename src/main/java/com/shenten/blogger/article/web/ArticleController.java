/* 
 * 
 */
package com.shenten.blogger.article.web;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.shenten.blogger.article.model.ArticlePage;
import com.shenten.blogger.article.service.ArticlePageNotFoundException;
import com.shenten.blogger.article.service.ArticleService;
import com.shenten.blogger.comment.model.Comment;


@Controller
@RequestMapping("/articles")
public final class ArticleController {
	private static final Logger log = LoggerFactory.getLogger(ArticleController.class);
	
	@Inject private ArticleService articleService;
	
	@Value("#{viewNames.articleList}") private String articleListViewName;
	@Value("#{viewNames.articlePage}") private String articlePageViewName;
	@Value("#{viewNames.postCommentFailed}") private String postCommentFailedViewName;
	@Value("#{viewNames.articlePageFailed}") private String articlePageFailedViewName;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { "id", "name", "email", "web", "markdownText" });
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getArticles(Model model) {
		model.addAttribute(articleService.getAllArticles());
		return articleListViewName;
	}
	
	@RequestMapping(value = "/{articleName}/{pageNumber}", method = RequestMethod.GET)
	public String getArticlePage(
			@PathVariable String articleName,
			@PathVariable int pageNumber,
			Model model) {
		
		if (prepareModel(model, articleName, pageNumber)) {
		model.addAttribute(new Comment());
		return articlePageViewName;
		}
		else {
			return articlePageFailedViewName;
		}
	}
	
	@RequestMapping(value = "/{articleName}/comments", method = RequestMethod.POST)
	public String postComment(
			HttpServletRequest req,
			@PathVariable String articleName,
			@RequestParam("p") int pageNumber,
			Model model,
			@ModelAttribute @Valid Comment comment,
			BindingResult result) {
		
		if (result.hasErrors()) {
			log.debug("Comment validation error");
			result.reject("global.error");
			prepareModel(model, articleName, pageNumber);
			return postCommentFailedViewName;
		}
		
		// No validation errors; post comment.
		log.debug("Posting comment");
		comment.setIpAddress(req.getRemoteAddr());
		articleService.postComment(articleName, comment);
		return "redirect:" + pageNumber + "#comment-" + comment.getId();
	}

	/**
	 * @param articleName
	 * @param pageNumber
	 * @param model
	 */
	private Boolean prepareModel(Model model, String articleName, int pageNumber) {
		
		ArticlePage page = new ArticlePage();
		
		try {
			page = articleService.getArticlePage(articleName, pageNumber);
		}
		catch(ArticlePageNotFoundException e) {
			// send an empty article page
			log.error("article page not found", e);
			model.addAttribute(page);
			return false;
		}

		// articlePage.jsp expects this
		model.addAttribute(page);
		
		// list.jspf (comment list) expects this
		model.addAttribute(page.getArticle().getComments());
		return true;
	}
}
