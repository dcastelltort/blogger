<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="article" value="${articlePage.article}" />

<fmt:formatDate var="articleDate" value="${article.dateCreated}" />

<c:url var="articlesCssUrl" value="/styles/articles.css" />
<c:url var="articlesUrl" value="/articles.html" />
<c:url var="postCommentUrl" value="/articles/${article.name}/comments?p=${articlePage.pageNumber}#postComment" />
<c:url var="zoneItUrl" value="/images/zone_it.png" />

<%-- Syntax Highlighter --%>
<c:url var="shCssUrl" value="/styles/SyntaxHighlighter.css" />
<c:url var="shCoreJsUrl" value="/scripts/shCore.js" />
<c:url var="shBrushJavaJsUrl" value="/scripts/shBrushJava.js" />
<c:url var="shBrushXmlJsUrl" value="/scripts/shBrushXml.js" />
<c:url var="clipboardSwfUrl" value="/swf/clipboard.swf" />

<%-- WMD --%>
<c:url var="wmdCssUrl" value="/scripts/pagedown/wmd.css" />
<c:url var="markdownConverterJsUrl" value="/scripts/pagedown/Markdown.Converter.js" />
<c:url var="markdownEditorJsUrl" value="/scripts/pagedown/Markdown.Editor.js" />
<c:url var="markdownSanitizerJsUrl" value="/scripts/pagedown/Markdown.Sanitizer.js" />

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<meta charset="utf-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <meta name="description" content="">
	    <meta name="author" content="">
	    <link rel="shortcut icon" href="images/favicon.png">

	
	    <!-- Bootstrap core CSS -->
	    <link href="/css/bootstrap.min.css" rel="stylesheet">
	
	    <!-- Custom styles for this template -->
	    <link href="/css/offcanvas.css" rel="stylesheet">
	
	    <!-- Just for debugging purposes. Don't actually copy this line! -->
	    <!--[if lt IE 9]><script src="../../docs-assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
	
	    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	    <!--[if lt IE 9]>
	      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
	      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
	    <![endif]-->
	<c:if test="${not empty article.description}">
			<meta name="description" content="${article.description}" />
		</c:if>
		<c:if test="${not empty article.keywords}">
			<meta name="keywords" content="${article.description}" />
		</c:if>
		
		<title><c:out value="${articlePage.title}" /></title>
		
		<link rel="stylesheet" type="text/css" href="${shCssUrl}" />
		<link rel="stylesheet" type="text/css" href="${wmdCssUrl}" />
		<link rel="stylesheet" type="text/css" href="${articlesCssUrl}" />
	</head>
	<body>
	
		<%@ include file="/WEB-INF/jspf/navbar.jspf" %>
	<div class="container">
	 <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-xs-12 col-sm-9">
          <p class="pull-right visible-xs">
            <button type="button" class="btn btn-primary btn-xs" data-toggle="offcanvas">Toggle nav</button>
          </p>
          
		<div id="articlePageBody">
			
			<c:choose>
				<c:when test="${articlePage.pageNumber == 1}">
					<div>
			
						<c:if test="${not empty article.category}">
							<div id="kicker">${article.category}</div>
						</c:if>
						
						<h1 id="articleTitle">${articlePage.title}</h1>
						
						<div id="byline">
							by <span class="user icon">${article.author}</span>
							on <span class="date icon">${articleDate}</span>
						</div>
						
						<c:if test="${not empty article.deck}">
							<div id="deck">${article.deck}</div>
						</c:if>
					</div>
				</c:when>
				<c:otherwise>
					<div id="articleTitle">${article.title} - page ${articlePage.pageNumber}</div>
				</c:otherwise>
			</c:choose>
			
			<div>${articlePage.body}</div>
		</div>
		
		<%@ include file="/WEB-INF/jspf/articles/pageNav.jspf" %>
		<br />
		<div class="panel"><%@ include file="/WEB-INF/jspf/comment/list.jspf" %></div>
		<div class="panel"><%@ include file="/WEB-INF/jspf/comment/post.jspf" %></div>
	 </div> <!--  row -->
	</div> <!--  container -->	
		<script type="text/javascript" src="${shCoreJsUrl}"></script>
		<script type="text/javascript" src="${shBrushJavaJsUrl}"></script>
		<script type="text/javascript" src="${shBrushXmlJsUrl}"></script>
		<script type="text/javascript" src="${markdownConverterJsUrl}"></script>
		<script type="text/javascript" src="${markdownSanitizerJsUrl}"></script>
		<script type="text/javascript" src="${markdownEditorJsUrl}"></script>
		<script type="text/javascript">
			$(function () {
				dp.SyntaxHighlighter.ClipboardSwf = '${clipboardSwfUrl}';
				dp.SyntaxHighlighter.HighlightAll('code');
				
				var converter = Markdown.getSanitizingConverter();
				var editor = new Markdown.Editor(converter);
				editor.run();
			});
		</script>
		<!-- Bootstrap core JavaScript
    	================================================== -->
    	<!-- Placed at the end of the document so the pages load faster -->
    	<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    	<script src="/scripts/boostrap/bootstrap.min.js"></script>
    	<script src="/scripts/bootstrap/offcanvas.js"></script>
	</body>
</html>
