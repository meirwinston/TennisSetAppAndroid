package com.tennissetapp.json;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.tennissetapp.form.AddressForm;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GeoCodeResult {
	public List<Result> results;
	public String status;
	
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class Result{
		public List<AddressComponent> address_components;
		public String formatted_address;
		public Geometry geometry;
		public List<String> types;
		@Override
		public String toString() {
			return formatted_address;
		}
		
		public void populateForm(AddressForm f){
			f.latitude = String.valueOf(geometry.location.lat);
			f.longitude = String.valueOf(geometry.location.lng);
			f.addressTypes = types.toArray(new String[]{});

			for(AddressComponent c : address_components){
				if(c.types.contains("administrative_area_level_1")){
					f.administrativeAreaLevel1 = c.long_name;
					f.administrativeAreaLevel1ShortName = c.short_name;
				}
				else if(c.types.contains("administrative_area_level_2")){
					f.administrativeAreaLevel2 = c.long_name;
					f.administrativeAreaLevel2ShortName = c.short_name;
				}
				else if(c.types.contains("street_number")){
					f.streetNumber = c.long_name;
				}
				else if(c.types.contains("route")){
					f.route = c.long_name;
					f.routeShortName = c.short_name;
				}
				else if(c.types.contains("locality")){
					f.locality = c.long_name;
					f.localityShortName = c.short_name;
				}
				else if(c.types.contains("country")){
					f.country = c.long_name;
					f.countryShortName = c.short_name;
				}
				else if(c.types.contains("postal_code")){
					f.postalCode = c.long_name;
				}
				else if(c.types.contains("sublocality")){
					f.sublocality = c.long_name;
					f.sublocalityShortName = c.short_name;
				}
				else if(c.types.contains("political")){
					f.political = c.long_name;
					f.politicalShortName = c.short_name;
				}
				else if(c.types.contains("neighborhood")){
					f.neighborhood = c.long_name;
					f.neighborhoodShortName = c.short_name;
				}
			}
		}
		
	}
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class AddressComponent{
		public String long_name;
		public String short_name;
		public List<String> types;
	}

	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class Geometry{
		public Location location;
		public String location_type;
		public NorthEastSouthWest viewport;
		public NorthEastSouthWest bounds;
		
		@JsonIgnoreProperties(ignoreUnknown=true)
		public static class Location{
			public double lat;
			public double lng;
		}
		
		@JsonIgnoreProperties(ignoreUnknown=true)
		public static class NorthEastSouthWest{
			public Location northeast;
			public Location southwest;
			
		}
		
	}

	@Override
	public String toString() {
		System.out.println("");
		return "GeoCodeResult [results=" + results + ", status=" + status + "]";
	}
	
	
}

/*
{
"results" : [
   {
      "address_components" : [
         {
            "long_name" : "1600",
            "short_name" : "1600",
            "types" : [ "street_number" ]
         },
         {
            "long_name" : "Amphitheatre Parkway",
            "short_name" : "Amphitheatre Pkwy",
            "types" : [ "route" ]
         },
         {
            "long_name" : "Mountain View",
            "short_name" : "Mountain View",
            "types" : [ "locality", "political" ]
         },
         {
            "long_name" : "Santa Clara",
            "short_name" : "Santa Clara",
            "types" : [ "administrative_area_level_2", "political" ]
         },
         {
            "long_name" : "California",
            "short_name" : "CA",
            "types" : [ "administrative_area_level_1", "political" ]
         },
         {
            "long_name" : "United States",
            "short_name" : "US",
            "types" : [ "country", "political" ]
         },
         {
            "long_name" : "94043",
            "short_name" : "94043",
            "types" : [ "postal_code" ]
         }
      ],
      "formatted_address" : "1600 Amphitheatre Parkway, Mountain View, CA 94043, USA",
      "geometry" : {
         "location" : {
            "lat" : 37.4219988,
            "lng" : -122.083954
         },
         "location_type" : "ROOFTOP",
         "viewport" : {
            "northeast" : {
               "lat" : 37.42334778029149,
               "lng" : -122.0826050197085
            },
            "southwest" : {
               "lat" : 37.42064981970849,
               "lng" : -122.0853029802915
            }
         }
      },
      "types" : [ "street_address" ]
   }
],
"status" : "OK"
}

//--

 
 {
      "address_components" : [
         {
            "long_name" : "1600",
            "short_name" : "1600",
            "types" : [ "street_number" ]
         },
         {
            "long_name" : "Amphitheatre Parkway",
            "short_name" : "Amphitheatre Pkwy",
            "types" : [ "route" ]
         },
         {
            "long_name" : "Mountain View",
            "short_name" : "Mountain View",
            "types" : [ "locality", "political" ]
         },
         {
            "long_name" : "Santa Clara",
            "short_name" : "Santa Clara",
            "types" : [ "administrative_area_level_2", "political" ]
         },
         {
            "long_name" : "California",
            "short_name" : "CA",
            "types" : [ "administrative_area_level_1", "political" ]
         },
         {
            "long_name" : "United States",
            "short_name" : "US",
            "types" : [ "country", "political" ]
         },
         {
            "long_name" : "94043",
            "short_name" : "94043",
            "types" : [ "postal_code" ]
         }
      ],
      "formatted_address" : "1600 Amphitheatre Parkway, Mountain View, CA 94043, USA",
      "geometry" : {
         "location" : {
            "lat" : 37.4219988,
            "lng" : -122.083954
         },
         "location_type" : "ROOFTOP",
         "viewport" : {
            "northeast" : {
               "lat" : 37.42334778029149,
               "lng" : -122.0826050197085
            },
            "southwest" : {
               "lat" : 37.42064981970849,
               "lng" : -122.0853029802915
            }
         }
      },
      "types" : [ "street_address" ]
   }
 */
