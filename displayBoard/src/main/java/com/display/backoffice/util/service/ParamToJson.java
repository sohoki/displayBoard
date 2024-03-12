package com.display.backoffice.util.service;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


//
public class ParamToJson {

	/* 특정 키값 받아오기 */
	public static String JsonKeyToString (Object param, String Reqkey)   throws Exception {
		if (param == null || param == "") return "";
		String ReqValue = "";
		try{
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			System.out.println("Reqkey:" + Reqkey);
			try {
				Map<String, String>  map = gson.fromJson(gson.toJson(param), Map.class);
				System.out.println("2" );
				map.values().removeAll(Collections.singleton(""));
				System.out.println("3" );
				Iterator<String> keys = map.keySet().iterator();
				for( String key : map.keySet() ){
					Object object = map.get(key);
					if ( object instanceof Double){
						 map.put(key,  String.valueOf(   ((Double) object).intValue()  ) ) ;
					}
					
					if (key.equals(Reqkey)){
						ReqValue = object.toString();
						break;
					}
		        }   
				return ReqValue;
			}catch(Exception e){
	    		System.err.println("ERROR:" + e);
	    		return "";
			}
		}catch(Exception e){
    		System.err.println("ERROR:" + e);
    		return "";
	    }
		
	}
	public static String paramListToJson (Object param)   throws Exception {
		 if (param == null || param == "") return "";
		 
		 if (param != null && param instanceof Number) return param.toString();
		 
		 try{
			
			    Gson gson = new GsonBuilder().setPrettyPrinting().create();
				
				Type listType = new TypeToken<List<HashMap<String, String>>>(){}.getType();
				List<HashMap<String, String>> listOfCountry =  gson.fromJson(gson.toJson(param), listType);
				
				for(HashMap<String, String> map : listOfCountry){
					map.values().removeAll(Collections.singleton(""));
					Iterator<String> keys = map.keySet().iterator();
					for( String key : map.keySet() ){
						Object object = map.get(key);
						if ( object instanceof Double){
							 map.put(key,  String.valueOf(   ((Double) object).intValue()  ) ) ;
						}
			        } 
					
				}
				return  gson.toJson(listOfCountry); 
		 }catch(Exception e){
	    	System.err.println("ERROR:" + e);
	    	return "";
	    }
		
	}
	
	public static String paramToJson (Object param)   throws Exception {
		
		
	    if (param == null || param == "") return "";
	    try{
	    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
			Map<String, String>  map = gson.fromJson(gson.toJson(param), Map.class);
			map.values().removeAll(Collections.singleton(""));
			Iterator<String> keys = map.keySet().iterator();
			for( String key : map.keySet() ){
				Object object = map.get(key);
				if ( object instanceof Double){
					 map.put(key,  String.valueOf(((Double) object).intValue())) ;
				}
	        }   

			return  gson.toJson(map);
	    }catch(Exception e){
	    	System.err.println("ERROR:" + e);
	    	return "";
	    }
	}
}
