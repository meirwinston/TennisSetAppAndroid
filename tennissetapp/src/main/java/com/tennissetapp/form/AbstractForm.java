package com.tennissetapp.form;

import android.util.Log;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.http.NameValuePair;
import com.tennissetapp.Constants;

public abstract class AbstractForm implements Serializable{
	private static final long serialVersionUID = 1L;

	public String toRest(){
		StringBuilder sb = new StringBuilder();
		try {
			Field[] fields = this.getClass().getFields();
			for(Field field : fields){
				Object val = field.get(this);
				if(val != null){
					
					if(Collection.class.isAssignableFrom(field.getType()) || field.getType().isArray()){
						Collection<?> list;
						if(field.getType().isArray()){
							list = Arrays.asList((Object[])val);
						}
						else{
							list = (Collection<?>)val;
						}
						
						if(list.size() > 0){
							Iterator<?> itr = list.iterator();
							while(itr.hasNext()){
								Object o = (Object)itr.next();
								sb.append(field.getName());
								sb.append("=");
								sb.append(URLEncoder.encode(o.toString(),"UTF-8"));
								
								if(itr.hasNext()){
									sb.append("&");
								}
							}
						}
					}
					else{
						sb.append(field.getName());
						sb.append("=");
						sb.append(URLEncoder.encode(field.get(this).toString(),"UTF-8"));
					}
					
					sb.append("&");
				}
			}
		} catch (Exception exp) {
            Log.e(getClass().getSimpleName(),exp.getMessage(),exp);
        }
		return sb.toString();
		
//		try {
//			return URLEncoder.encode(sb.toString(),"UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			C.loge(e.getMessage(), e);
//		}
//		return null;
	}
	
	
	public List<NameValuePair> nameValueList(){
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		try {
			Field[] fields = this.getClass().getFields();
			for(Field field : fields){
				Object val = field.get(this);
				String name = field.getName();
				if(val != null){
					if(field.getType().isArray()){
						name += "[]";
					}
					else if(Collection.class.isAssignableFrom(field.getType())){
						name += "[]";
					}
					else{
						NameValuePair o = new NameValuePairImpl(name, val.toString());
						list.add(o);
					}
					
				}
			}
		} catch (Exception exp) {
			Log.e(getClass().getSimpleName(),exp.getMessage(),exp);
		}
		return list;
	}
	
	public static class NameValuePairImpl implements NameValuePair{
		private String name,value;
		public NameValuePairImpl(String name,String value){
			this.name = name;
			this.value = value;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String getValue() {
			return value;
		}
		
	}
}
