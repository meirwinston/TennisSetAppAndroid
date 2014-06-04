package com.tennissetapp.form;

public class ScrollForm extends AbstractForm{
	private static final long serialVersionUID = 1L;
	
	public String maxResults;
	public String firstResult;
	@Override
	public String toString() {
		return "ScrollForm [maxResults=" + maxResults + ", firstResult="
				+ firstResult + "]";
	}
}
