package com.tennissetapp.form;

public class SearchTennisCourtsForm extends ScrollForm{
	private static final long serialVersionUID = 1L;
	
	public String courtName;
	public String outdoor, indoor;
	public String hard,concrete,clay,grass,synthetic,carpet;
	
	//location
	public String latitude;
	public String longitude;
	
	//distance
	public String distance = "50";

	@Override
	public String toString() {
		return "SearchTennisCourtsForm [courtName=" + courtName + ", outdoor="
				+ outdoor + ", indoor=" + indoor + ", hard=" + hard
				+ ", concrete=" + concrete + ", clay=" + clay + ", grass="
				+ grass + ", synthetic=" + synthetic + ", carpet=" + carpet
				+ ", latitude=" + latitude + ", longitude=" + longitude
				+ ", distance=" + distance + "]";
	}
}
