package com.model2.mvc.common;

public class Page {

	private int currentPage;	 //현재페이지
	private int beginUnitPage;	 //끝페이지
	private int endUnitPage; 	 //페이지 시작
	private int totalCount;	 //총 행의 수(게시물 수)
	private int maxPage;		 //총 페이지 수
	private int pageUnit;		 //나타낼 페이지 수
	private int pageSize; 	  	 //한페이지의 행갯수
 
	public Page(int currentPage, int totalCount, int pageUnit, int pageSize){
		this.totalCount = totalCount;
		this.pageUnit = pageUnit;
		this.pageSize = pageSize;

		this.maxPage = (pageSize == 0) ? totalCount : (totalCount-1)/pageUnit+1;

		this.currentPage = ( currentPage > maxPage) ? maxPage : currentPage;

		this.beginUnitPage = pageUnit*((currentPage-1)/pageUnit)+1;
		
		if(maxPage <= pageUnit){
			this.endUnitPage= maxPage;
		}else{
			this.endUnitPage = beginUnitPage+(pageUnit-1);
			if(maxPage <= endUnitPage){
				this.endUnitPage = maxPage;
			}
		}
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getBeginUnitPage() {
		return beginUnitPage;
	}

	public void setBeginUnitPage(int beginUnitPage) {
		this.beginUnitPage = beginUnitPage;
	}

	public int getEndUnitPage() {
		return endUnitPage;
	}

	public void setEndUnitPage(int endUnitPage) {
		this.endUnitPage = endUnitPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageUnit() {
		return pageUnit;
	}

	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	@Override
	public String toString() {
		return "Page [currentPage=" + currentPage + ", beginUnitPage=" + beginUnitPage + ", endUnitPage=" + endUnitPage
				+ ", totalCount=" + totalCount + ", maxPage=" + maxPage + ", pageUnit=" + pageUnit + ", pageSize="
				+ pageSize + "]";
	}
	
}