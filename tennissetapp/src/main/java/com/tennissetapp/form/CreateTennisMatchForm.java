package com.tennissetapp.form;

public class CreateTennisMatchForm extends AbstractForm{
	private static final long serialVersionUID = 1L;
	
	public String playSingles;
	public String playDoubles;
	public String playFullMatch;
	public String playPoints;
	public String playHittingAround;
	
	//location
//	public String latitude;
//	public String longitude;
	public String tennisCenterId;
	
	//level of play
	public String levelOfPlayMin;
	public String levelOfPlayMax;
	
	public String startTime;
	public String endTime;
	
	//distance
	public String distance = "50";

	public String visibility;

	@Override
	public String toString() {
		return "CreateTennisMatchForm [playSingles=" + playSingles
				+ ", playDoubles=" + playDoubles + ", playFullMatch="
				+ playFullMatch + ", playPoints=" + playPoints
				+ ", playHittingAround=" + playHittingAround
				+ ", tennisCenterId=" + tennisCenterId + ", levelOfPlayMin="
				+ levelOfPlayMin + ", levelOfPlayMax=" + levelOfPlayMax
				+ ", startTime=" + startTime + ", endTime=" + endTime
				+ ", distance=" + distance + ", visibility=" + visibility + "]";
	}

	
}
