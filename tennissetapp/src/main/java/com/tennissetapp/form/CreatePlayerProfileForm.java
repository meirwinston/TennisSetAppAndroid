package com.tennissetapp.form;

public class CreatePlayerProfileForm extends AddressForm {
	private static final long serialVersionUID = 1L;
	
	public String firstName;
	public String lastName;
	public String gender;
	public String userAccountId;
	public String birthDay;
	public String birthMonth;
	public String birthYear;
	public String profileFileItemId;
	public String aboutMe;
	
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
	public String favoriteCourts; //comma separated values
	
	public String agreesToTerms = "on";

	@Override
	public String toString() {
		return "CreatePlayerProfileForm [firstName=" + firstName
				+ ", lastName=" + lastName + ", gender=" + gender
				+ ", userAccountId=" + userAccountId + ", birthDay=" + birthDay
				+ ", birthMonth=" + birthMonth + ", birthYear=" + birthYear
				+ ", profileFileItemId=" + profileFileItemId + ", levelOfPlay="
				+ levelOfPlay + ", hand=" + hand + ", singlesCheck="
				+ singlesCheck + ", doublesCheck=" + doublesCheck
				+ ", fullMatchCheck=" + fullMatchCheck + ", pointsCheck="
				+ pointsCheck + ", hittingAroundCheck=" + hittingAroundCheck
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
				+ weekdayAvailabilityEveningCheck + ", favoriteCourts="
				+ favoriteCourts + ", agreesToTerms=" + agreesToTerms + "]";
	}
}
