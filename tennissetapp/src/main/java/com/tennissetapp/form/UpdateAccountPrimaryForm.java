package com.tennissetapp.form;

import java.util.Arrays;

public class UpdateAccountPrimaryForm  extends AddressForm {
	private static final long serialVersionUID = 1L;
	
	public String firstName;
	public String lastName;
	public String gender;
	public String userAccountId;
	public String birthDay;
	public String birthMonth;
	public String birthYear;
	public String agreesToTerms;
	@Override
	public String toString() {
		return "UpdateAccountPrimaryForm [firstName=" + firstName
				+ ", lastName=" + lastName + ", gender=" + gender
				+ ", userAccountId=" + userAccountId + ", birthDay=" + birthDay
				+ ", birthMonth=" + birthMonth + ", birthYear=" + birthYear
				+ ", agreesToTerms=" + agreesToTerms + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", streetNumber=" + streetNumber
				+ ", route=" + route + ", routeShortName=" + routeShortName
				+ ", neighborhood=" + neighborhood + ", neighborhoodShortName="
				+ neighborhoodShortName + ", political=" + political
				+ ", politicalShortName=" + politicalShortName + ", locality="
				+ locality + ", localityShortName=" + localityShortName
				+ ", sublocality=" + sublocality + ", sublocalityShortName="
				+ sublocalityShortName + ", administrativeAreaLevel2="
				+ administrativeAreaLevel2
				+ ", administrativeAreaLevel2ShortName="
				+ administrativeAreaLevel2ShortName
				+ ", administrativeAreaLevel1=" + administrativeAreaLevel1
				+ ", administrativeAreaLevel1ShortName="
				+ administrativeAreaLevel1ShortName + ", country=" + country
				+ ", countryShortName=" + countryShortName + ", postalCode="
				+ postalCode + ", addressTypes="
				+ Arrays.toString(addressTypes) + "]";
	}
}
