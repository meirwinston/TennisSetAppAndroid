package com.tennissetapp.form;

public class ScrollMateConversationForm extends AbstractForm{
	private static final long serialVersionUID = 1L;
	
	public String maxResults;
	public String firstResult;
	public String mateAccountId;
	public String startDate;
	@Override
	public String toString() {
		return "ScrollMateConversationForm [maxResults=" + maxResults
				+ ", firstResult=" + firstResult + ", mateAccountId="
				+ mateAccountId + ", startDate=" + startDate + "]";
	}


}
