package com.hapis.customer.ui.models;

import java.util.ArrayList;
import java.util.List;

public abstract class FindResultModel<M extends MessageModel> extends ResponseModel{

	private static final long serialVersionUID = -6551022950627738650L;

	private List<M> results = new ArrayList<>();

	/**
	 * Total number of elements in database.
	 */
	private long totalElements;

	/**
	 * Number of elements for the current draw.
	 */
	private long numberOfElements;

	/**
	 * Requested size of the elements.
	 */
	private long size;

	/**
	 * Total pages.
	 */
	private long totalPages;

	/**
	 * Is lage page.
	 */
	private boolean isLastPage;

	/**
	 * Is first page.
	 */
	private boolean isFirstPage;

	/**
	 * Current page number.
	 */
	private int pageNumber;

	/**
	 * Gets the totalElements.
	 * 
	 * @return the totalElements
	 */
	public long getTotalElements() {
		return totalElements;
	}

	/**
	 * Set totalElements to the totalElements.
	 * 
	 * @param totalElements the totalElements to set
	 */
	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	/**
	 * Gets the numberOfElements.
	 * 
	 * @return the numberOfElements
	 */
	public long getNumberOfElements() {
		return numberOfElements;
	}

	/**
	 * Set numberOfElements to the numberOfElements.
	 * 
	 * @param numberOfElements the numberOfElements to set
	 */
	public void setNumberOfElements(long numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	/**
	 * Gets the size.
	 * 
	 * @return the size
	 */
	public long getSize() {
		return size;
	}

	/**
	 * Set size to the size.
	 * 
	 * @param size the size to set
	 */
	public void setSize(long size) {
		this.size = size;
	}

	/**
	 * Gets the totalPages.
	 * 
	 * @return the totalPages
	 */
	public long getTotalPages() {
		return totalPages;
	}

	/**
	 * Set totalPages to the totalPages.
	 * 
	 * @param totalPages the totalPages to set
	 */
	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * Gets the isLastPage.
	 * 
	 * @return the isLastPage
	 */
	public boolean isLastPage() {
		return isLastPage;
	}

	/**
	 * Set isLastPage to the isLastPage.
	 * 
	 * @param isLastPage the isLastPage to set
	 */
	public void setLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}

	/**
	 * Gets the isFirstPage.
	 * 
	 * @return the isFirstPage
	 */
	public boolean isFirstPage() {
		return isFirstPage;
	}

	/**
	 * Set isFirstPage to the isFirstPage.
	 * 
	 * @param isFirstPage the isFirstPage to set
	 */
	public void setFirstPage(boolean isFirstPage) {
		this.isFirstPage = isFirstPage;
	}

	/**
	 * Gets the pageNumber.
	 * 
	 * @return the pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * Set pageNumber to the pageNumber.
	 * 
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * Gets entity result.
	 * 
	 * @return Result Model
	 */
	public List<M> getResults() {
		return this.results;
	}

	public void setResults(List<M> results) {
		this.results = results;
	}

	/**
	 * Gets count.
	 * 
	 * @return Count
	 */
	public long getCount() {
		if (this.results != null) {
			return results.size();
		}
		return 0;
	}

}
