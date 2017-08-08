<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<html>
<head>
<title>${param.menu eq "search" ? "상품목록조회" : "상품 관리"}</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

 <c:if test="${wishSuccess eq 'success'}">
	 <script type="text/javascript">
	<!--
		alert("장바구니에 등록되었습니다.");
	//-->
	</script>
 </c:if>

<script type="text/javascript">
	// 검색 / page 두가지 경우 모두 Form 전송을 위해 JavaScrpt 이용  
	function fncGetList(currentPage) {
		document.getElementById("currentPage").value = currentPage;
	   	document.detailForm.submit();		
	}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/product/listProduct?menu=${param.menu}" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
							
							
							${param.menu eq "search" ? "상품 목록조회" : "상품관리"}
					
					</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">

				
				<option value="0" ${ !empty search.searchCondition && search.searchCondition eq 0 ? "selected" : "" }>상품명</option>
				<option value="1" ${ !empty search.searchCondition && search.searchCondition eq 1 ? "selected" : "" }>상품번호</option>
				<option value="2" ${ !empty search.searchCondition && search.searchCondition eq 2 ? "selected" : "" }>상품가격</option>
				
			</select>
			<input 	type="text" name="searchKeyword"  value="${search.searchKeyword}" 
							class="ct_input_g" style="width:200px; height:19px" >
			<!--  -->
		</td>	
		
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetList('1');">검색</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>

		<td colspan="11" >전체 ${resultPage.totalCount} 건수, 현재 ${resultPage.currentPage} 페이지</td>
	</tr>

	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">가격
		<select name="searchSortPrice" onchange="this.form.submit()">
			<option value="0" ${ !empty search.searchSortPrice && search.searchSortPrice eq 0 ? "selected" : "" }>전체보기</option>
			<option value="1" ${ !empty search.searchSortPrice && search.searchSortPrice eq 1 ? "selected" : "" }>높은 가격순</option>
			<option value="2" ${ !empty search.searchSortPrice && search.searchSortPrice eq 2 ? "selected" : "" }>낮은 가격순</option>
		</select>
		</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">등록일</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">현재상태</td>
		<td class="ct_line02"></td>
		<c:if test="${!empty param.menu && param.menu ne 'manage' }">
		<td class="ct_list_b">장바구니</td>
		</c:if>
		<c:if test="${!empty param.menu && param.menu eq 'manage' }">
		<td></td>
		</c:if>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
	<c:set var="i" value="0"/>
	<c:forEach var="product" items="${list}">
		<c:set var="i" value="${i+1}"/>
		<tr class="ct_list_pop">
		<td align="center">${i}</td>
		<td></td>		
		<td align="left">
			<a href="/product/getProduct?prodNo=${product.prodNo}&menu=${param.menu}">${product.prodName}</a>
		</td>	
		<td></td>
		<td align="left">${product.price }</td>
		<td></td>
		<td align="left">${product.regDate }</td>
		<td></td>
		<td align="left"> 
				<c:if test="${!empty param.menu && param.menu eq 'search'}">		
					<c:choose>
						<c:when test="${product.tranStatusCode eq null }">
							판매중
						</c:when>
						<c:when test="${product.tranStatusCode <= 2 && product.tranStatusCode>=1 }">
							재고없음
						</c:when>
						<c:when test="${!empty product.tranStatusCode && product.tranStatusCode eq 3 }">
							판매완료
						</c:when>
					</c:choose>
					<c:if test="${product.tranStatusCode ne null ? '재고없음' : '판매중'}"></c:if>				
				</c:if>
				<c:if test="${!empty param.menu && param.menu eq 'manage' }">
					<c:choose>
						<c:when test="${product.tranStatusCode eq 1 }">
							구매완료 / <a href="/purchase/updateTranCodeByProd?prodNo=${product.prodNo}&tranCode=2">배송하기</a>
						</c:when>
						<c:when test="${product.tranStatusCode eq 2 }">
							배송중
						</c:when>
						<c:when test="${product.tranStatusCode eq 3 }">
							배송완료
						</c:when>
						<c:otherwise>
							판매중
						</c:otherwise>
					</c:choose>
				</c:if>
		</td>
		<td></td>
		<c:if test="${!empty param.menu && param.menu ne 'manage' }">
		<td align="center">
			<c:if test="${product.tranStatusCode ne null}">
			장바구니 불가(재고X)
			</c:if>
			<c:if test="${product.tranStatusCode eq null}">
			<a href="/purchase/addWishPurchase?prodNo=${product.prodNo }">장바구니 담기</a>
			</c:if>
		</td>
		</c:if>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>	
	</c:forEach>
	
	
	
	
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
		
		<input type="hidden" id="currentPage" name="currentPage" value="0"/>
			
			<jsp:include page="../common/pageNavigator.jsp"/>	
    	
    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->

</form>

</div>
</body>
</html>
