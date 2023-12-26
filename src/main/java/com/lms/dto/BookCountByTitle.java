package com.lms.dto;

public class BookCountByTitle {
    private String title;
    private long count;
    private long uniqueAuthors;
    
	public BookCountByTitle() {
		
	}

	public BookCountByTitle(String title, long count, long uniqueAuthors) {
		this.title = title;
		this.count = count;
		this.uniqueAuthors = uniqueAuthors;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public long getUniqueAuthors() {
		return uniqueAuthors;
	}

	public void setUniqueAuthors(long uniqueAuthors) {
		this.uniqueAuthors = uniqueAuthors;
	}
    
}

