package com.tennissetapp.json;

import android.util.Log;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;
import org.codehaus.jackson.type.TypeReference;

public class JacksonUtils {
	protected static ObjectMapper objectMapper = new ObjectMapper();
	protected static MappingJsonFactory jsonFactory = new MappingJsonFactory();
	static TypeReference<Map<String, Object>> objectMapType = new TypeReference<Map<String, Object>>(){};
	
	public static String serialize(Object o) throws IOException {
		StringWriter sw = new StringWriter(); // serialize
		serialize(o, sw);
		sw.close();
		return sw.toString();
	}
	
	public static String serialize(Object o, JsonSerializer<?> serializer) throws IOException {
		if(o == null) return null;
		
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule("SimpleModule",new Version(1,0,0,null));
		module.addSerializer(serializer);
		mapper.registerModule(module);
		
		StringWriter writer = new StringWriter();
		JsonGenerator jsonGenerator = jsonFactory.createJsonGenerator(writer);
		mapper.writeValue(jsonGenerator, o);
		writer.close();
		
		return writer.toString();
	}
	
	public static void serialize(Object o, Writer writer) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		
		JsonGenerator jsonGenerator = jsonFactory.createJsonGenerator(writer);
		mapper.writeValue(jsonGenerator, o);
	}
	
	public static void serialize(Object o, JsonSerializer<?> serializer, Writer writer) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule("SimpleModule",new Version(1,0,0,null));
		module.addSerializer(serializer);
		mapper.registerModule(module);
		JsonGenerator jsonGenerator = jsonFactory.createJsonGenerator(writer);
		mapper.writeValue(jsonGenerator, o);
	}
	
	public static Map<String,Object> objectToMap(Object o) 
	throws JsonParseException, JsonMappingException, IOException{
		Map<String,Object> map = objectMapper.convertValue(o, objectMapType);
		return map;
	}
	
	public static Map<String,Object> deserializeObjectMap(String value) 
	throws JsonParseException, JsonMappingException, IOException{
		Map<String,Object> map = objectMapper.readValue(value, objectMapType);
		return map;
	}
	
	public static Map<String,String> deserializeStringMap(String value) 
	throws JsonParseException, JsonMappingException, IOException{
		if(value == null) return null;
		return objectMapper.readValue(value, new TypeReference<Map<String, String>>(){});
	}
	
	public static JsonNode toNode(String value) 
	throws JsonParseException, JsonMappingException, IOException{
		return objectMapper.readTree(value);
	}
	
	public static <T> T deserializeAs(String value, Class<T> as) throws JsonParseException, JsonMappingException, IOException{
		if(value == null) return null;
		return objectMapper.readValue(value, as);
	}
	
	public static void main(String[] args){
		String s = "{\"results\" : [{\"address_components\" : [{\"long_name\" : \"1600\",\"short_name\" : \"1600\",\"types\" : [ \"street_number\" ]},{\"long_name\" : \"Amphitheatre Parkway\",\"short_name\" : \"Amphitheatre Pkwy\",\"types\" : [ \"route\" ]},{\"long_name\" : \"Mountain View\",\"short_name\" : \"Mountain View\",\"types\" : [ \"locality\", \"political\" ]},{\"long_name\" : \"Santa Clara\",\"short_name\" : \"Santa Clara\",\"types\" : [ \"administrative_area_level_2\", \"political\" ]},{\"long_name\" : \"California\",\"short_name\" : \"CA\",\"types\" : [ \"administrative_area_level_1\", \"political\" ]},{\"long_name\" : \"United States\",\"short_name\" : \"US\",\"types\" : [ \"country\", \"political\" ]},{\"long_name\" : \"94043\",\"short_name\" : \"94043\",\"types\" : [ \"postal_code\" ]}],\"formatted_address\" : \"1600 Amphitheatre Parkway, Mountain View, CA 94043, USA\",\"geometry\" : {\"location\" : {\"lat\" : 37.4219988,\"lng\" : -122.083954},\"location_type\" : \"ROOFTOP\",\"viewport\" : {\"northeast\" : {\"lat\" : 37.42334778029149,\"lng\" : -122.0826050197085},\"southwest\" : {\"lat\" : 37.42064981970849,\"lng\" : -122.0853029802915}}},\"types\" : [ \"street_address\" ]}],\"status\" : \"OK\"}";
		try {
			GeoCodeResult g = JacksonUtils.deserializeAs(s, GeoCodeResult.class);
			System.out.println(g);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
