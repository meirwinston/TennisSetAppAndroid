package com.tennissetapp.form;

public class SearchTennisMatesForm extends ScrollForm{
	private static final long serialVersionUID = 1L;
	
	public String playSingles;
	public String playDoubles;
	public String playFullMatch;
	public String playPoints;
	public String playHittingAround;
	
	//location
	public String latitude;
	public String longitude;
	
	//level of play
	public String levelOfPlayMin;
	public String levelOfPlayMax;
	
	//availability
	public String availableWeekendMorning;
	public String availableWeekendAfternoon;
	public String availableWeekendEvening;
	public String availableWeekdayMorning;
	public String availableWeekdayAfternoon;
	public String availableWeekdayEvening;
	
	//distance
	public String distance = "50";

	@Override
	public String toString() {
		return "SearchTennisMatesForm [playSingles=" + playSingles
				+ ", playDoubles=" + playDoubles + ", playFullMatch="
				+ playFullMatch + ", playPoints=" + playPoints
				+ ", playHittingAround=" + playHittingAround + ", latitude="
				+ latitude + ", longitude=" + longitude + ", levelOfPlayMin="
				+ levelOfPlayMin + ", levelOfPlayMax=" + levelOfPlayMax
				+ ", availableWeekendMorning=" + availableWeekendMorning
				+ ", availableWeekendAfternoon=" + availableWeekendAfternoon
				+ ", availableWeekendEvening=" + availableWeekendEvening
				+ ", availableWeekdayMorning=" + availableWeekdayMorning
				+ ", availableWeekdayAfternoon=" + availableWeekdayAfternoon
				+ ", availableWeekdayEvening=" + availableWeekdayEvening
				+ ", distance=" + distance + "]";
	}
	
	
}
