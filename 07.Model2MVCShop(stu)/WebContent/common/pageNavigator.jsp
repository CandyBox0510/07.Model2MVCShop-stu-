<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
	<c:if test="${resultPage.currentPage <= resultPage.pageUnit}">
		¢¸
	</c:if>
	<c:if test="${resultPage.currentPage > resultPage.pageUnit }">
		<a href="javascript:fncGetList('${resultPage.currentPage-1}')">¢¸ </a>
	</c:if>
	
	<c:forEach var="page" begin="${resultPage.beginUnitPage}" end="${resultPage.endUnitPage}" step="1">
		<a href="javascript:fncGetList('${ page }');">${ page }</a>
	</c:forEach>
	
	<c:if test="${resultPage.currentPage >= resultPage.maxPage}">
		¢º
	</c:if>
	<c:if test="${resultPage.currentPage < resultPage.maxPage }">
		<a href="javascript:fncGetList('${resultPage.currentPage+1}')">¢º</a>
	</c:if>