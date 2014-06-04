package com.tennissetapp.form;


public class FindByLocationForm extends AbstractForm{
	private static final long serialVersionUID = 1L;
	
	public String maxResults;
	public String firstResult;
	public String latitude;
	public String longitude;
	public String distance;
	
	@Override
	public String toString() {
		return "FindByLocationForm [maxResults=" + maxResults
				+ ", firstResult=" + firstResult + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", distance=" + distance + "]";
	}
}
