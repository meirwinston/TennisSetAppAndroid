package com.tennissetapp.form;

public class SearchTennisMatchesForm extends ScrollForm{
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
	
	public String startTime;
	public String endTime;
	
	//distance
	public String distance = "50";

	@Override
	public String toString() {
		return "SearchTennisMatchesForm [playSingles=" + playSingles
				+ ", playDoubles=" + playDoubles + ", playFullMatch="
				+ playFullMatch + ", playPoints=" + playPoints
				+ ", playHittingAround=" + playHittingAround + ", latitude="
				+ latitude + ", longitude=" + longitude + ", levelOfPlayMin="
				+ levelOfPlayMin + ", levelOfPlayMax=" + levelOfPlayMax
				+ ", startTime=" + startTime + ", endTime=" + endTime
				+ ", distance=" + distance + ", maxResults=" + maxResults
				+ ", firstResult=" + firstResult + "]";
	}
}
