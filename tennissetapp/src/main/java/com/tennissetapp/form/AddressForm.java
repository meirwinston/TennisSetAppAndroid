package com.tennissetapp.form;

import java.util.Arrays;

import com.tennissetapp.json.GeoCodeResult;

public class AddressForm extends AbstractForm{
	private static final long serialVersionUID = 1L;
	
	public String latitude;
	public String longitude;
	public String streetNumber;
	public String route;
	public String routeShortName;
	public String neighborhood;
	public String neighborhoodShortName;
	public String political;
	public String politicalShortName;
	public String locality;
	public String localityShortName;
	public String sublocality;
	public String sublocalityShortName;
	public String administrativeAreaLevel2;
	public String administrativeAreaLevel2ShortName;
	public String administrativeAreaLevel1;
	public String administrativeAreaLevel1ShortName;
	public String country;
	public String countryShortName;
	public String postalCode;
	public String[] addressTypes;
	
	
	public void populate(GeoCodeResult.Result geoLocation){
		if(geoLocation != null){
			this.latitude = String.valueOf(geoLocation.geometry.location.lat);
			this.longitude = String.valueOf(geoLocation.geometry.location.lng);
			this.addressTypes = geoLocation.types.toArray(new String[]{});

			for(GeoCodeResult.AddressComponent c : geoLocation.address_components){
				if(c.types.contains("administrative_area_level_1")){
					this.administrativeAreaLevel1 = c.long_name;
					this.administrativeAreaLevel1ShortName = c.short_name;
				}
				else if(c.types.contains("administrative_area_level_2")){
					this.administrativeAreaLevel2 = c.long_name;
					this.administrativeAreaLevel2ShortName = c.short_name;
				}
				else if(c.types.contains("street_number")){
					this.streetNumber = c.long_name;
				}
				else if(c.types.contains("route")){
					this.route = c.long_name;
					this.routeShortName = c.short_name;
				}
				else if(c.types.contains("locality")){
					this.locality = c.long_name;
					this.localityShortName = c.short_name;
				}
				else if(c.types.contains("country")){
					this.country = c.long_name;
					this.countryShortName = c.short_name;
				}
				else if(c.types.contains("postal_code")){
					this.postalCode = c.long_name;
				}
				else if(c.types.contains("sublocality")){
					this.sublocality = c.long_name;
					this.sublocalityShortName = c.short_name;
				}
				else if(c.types.contains("neighborhood")){
					this.neighborhood = c.long_name;
					this.neighborhoodShortName = c.short_name;
				}
				else if(c.types.contains("political")){
					this.political = c.long_name;
					this.politicalShortName = c.short_name;
				}
			}
		}
	}
	
	@Override
	public String toString() {
		return "AddressForm [latitude=" + latitude + ", longitude=" + longitude
				+ ", streetNumber=" + streetNumber + ", route=" + route
				+ ", routeShortName=" + routeShortName + ", neighborhood="
				+ neighborhood + ", neighborhoodShortName="
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
