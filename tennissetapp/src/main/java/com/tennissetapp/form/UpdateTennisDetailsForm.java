package com.tennissetapp.form;

public class UpdateTennisDetailsForm extends AbstractForm{
	private static final long serialVersionUID = 1L;

	public String levelOfPlay;
	public String hand;
	public String singlesCheck;
	public String doublesCheck;
	public String fullMatchCheck;
	public String pointsCheck;
	public String hittingAroundCheck;
	public String weekendAvailabilityMorningCheck;
	public String weekendAvailabilityAfternoonCheck;
	public String weekendAvailabilityEveningCheck;
	public String weekdayAvailabilityMorningCheck;
	public String weekdayAvailabilityAfternoonCheck;
	public String weekdayAvailabilityEveningCheck;
	@Override
	public String toString() {
		return "UpdateTennisDetailsForm [levelOfPlay=" + levelOfPlay
				+ ", hand=" + hand + ", singlesCheck=" + singlesCheck
				+ ", doublesCheck=" + doublesCheck + ", fullMatchCheck="
				+ fullMatchCheck + ", pointsCheck=" + pointsCheck
				+ ", hittingAroundCheck=" + hittingAroundCheck
				+ ", weekendAvailabilityMorningCheck="
				+ weekendAvailabilityMorningCheck
				+ ", weekendAvailabilityAfternoonCheck="
				+ weekendAvailabilityAfternoonCheck
				+ ", weekendAvailabilityEveningCheck="
				+ weekendAvailabilityEveningCheck
				+ ", weekdayAvailabilityMorningCheck="
				+ weekdayAvailabilityMorningCheck
				+ ", weekdayAvailabilityAfternoonCheck="
				+ weekdayAvailabilityAfternoonCheck
				+ ", weekdayAvailabilityEveningCheck="
				+ weekdayAvailabilityEveningCheck + "]";
	}
	
	

}
