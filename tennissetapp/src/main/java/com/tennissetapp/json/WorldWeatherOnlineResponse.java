package com.tennissetapp.json;

import java.util.List;

public class WorldWeatherOnlineResponse {
	
	public Data data;
	
	public static class Data{
		public List<CurrentConditionItem> current_condition;
		public List<Request> request;
		public List<Weather> weather;
		
		@Override
		public String toString() {
			return "Data [current_condition=" + current_condition
					+ ", request=" + request + ", weather=" + weather + "]";
		}
	}
	
	public static class Request{
		public String query;
		public String type;
	}
	
	public static class Weather{
		public String date;
		public String precipMM;
		public String tempMaxC;
		public String tempMaxF;
		public String tempMinC;
		public String tempMinF;
		public String weatherCode;
		public List<String> weatherDesc;
		public List<String> weatherIconUrl;
		public String winddir16Point;
		public String winddirDegree;
		public String winddirection;
		public String windspeedKmph;
		public String windspeedMiles;
	}
	
	public static class CurrentConditionItem{
		public String cloudcover;
		public String humidity;
		public String observation_time;
		public String precipMM;
		public String pressure;
		public String temp_C;
		public String temp_F;
		public String visibility;
		public String weatherCode;
		public List<String> weatherDesc;
		public List<String> weatherIconUrl;
		
		public String winddir16Point;
		public String winddirDegree;
		public String windspeedKmph;
		public String windspeedMiles;
		@Override
		public String toString() {
			return "CurrentConditionItem [cloudcover=" + cloudcover
					+ ", humidity=" + humidity + ", observation_time="
					+ observation_time + ", precipMM=" + precipMM
					+ ", pressure=" + pressure + ", temp_C=" + temp_C
					+ ", temp_F=" + temp_F + ", visibility=" + visibility
					+ ", weatherCode=" + weatherCode + ", weatherDesc="
					+ weatherDesc + ", weatherIconUrl=" + weatherIconUrl
					+ ", winddir16Point=" + winddir16Point + ", winddirDegree="
					+ winddirDegree + ", windspeedKmph=" + windspeedKmph
					+ ", windspeedMiles=" + windspeedMiles + "]";
		}
		
		
	}
	@Override
	public String toString() {
		return "WorldWeatherOnlineResponse [data=" + data + "]";
	}

//	{
//	    "data": {
//	        "current_condition": [{
//	            "cloudcover": "50",
//	            "humidity": "58",
//	            "observation_time": "03:03 PM",
//	            "precipMM": "0.0",
//	            "pressure": "1005",
//	            "temp_C": "4",
//	            "temp_F": "39",
//	            "visibility": "16",
//	            "weatherCode": "116",
//	            "weatherDesc": [{
//	                "value": "Partly Cloudy"
//	            }],
//	            "weatherIconUrl": [{
//	                "value": "http:\/\/cdn.worldweatheronline.net\/images\/wsymbols01_png_64\/wsymbol_0002_sunny_intervals.png"
//	            }],
//	            "winddir16Point": "WSW",
//	            "winddirDegree": "250",
//	            "windspeedKmph": "7",
//	            "windspeedMiles": "4"
//	        }],
//	        "request": [{
//	            "query": "Lat 40.42 and Lon -74.42",
//	            "type": "LatLon"
//	        }],
//	        "weather": [{
//	            "date": "2014-01-20",
//	            "precipMM": "0.0",
//	            "tempMaxC": "9",
//	            "tempMaxF": "49",
//	            "tempMinC": "-4",
//	            "tempMinF": "24",
//	            "weatherCode": "116",
//	            "weatherDesc": [{
//	                "value": "Partly Cloudy"
//	            }],
//	            "weatherIconUrl": [{
//	                "value": "http:\/\/cdn.worldweatheronline.net\/images\/wsymbols01_png_64\/wsymbol_0002_sunny_intervals.png"
//	            }],
//	            "winddir16Point": "WSW",
//	            "winddirDegree": "258",
//	            "winddirection": "WSW",
//	            "windspeedKmph": "35",
//	            "windspeedMiles": "22"
//	        }]
//	    }
//	}
	
	
}
