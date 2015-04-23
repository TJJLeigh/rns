package com.rns.util;

public class NumericUtil {
	
	public static float getFloatValue(String value){

		//Returns -1.0f if the String value cannot be parsed
		float returnValue = -1.0f;
		
		if ( ! isEmptyOrNull(value) && isNumeric(value) ){
			try {
				returnValue = Float.parseFloat(value);
			} catch ( NumberFormatException nfe){
				System.out.println("value passed is non numeric");
			}
		}
		return returnValue;
	}
	
	public static boolean isNumeric(String value){
		
		boolean numeric = false;
		if (!isEmptyOrNull(value)){
			
			numeric = value.matches("[+-]?(?:\\d+(?:\\.\\d*)?|\\.\\d+)");  
	    }
		
		return numeric;
	}
		
	public static boolean isEmptyOrNull(String value){
		
		boolean emptyOrNull = false;
		if ( value != null ){
			
			String string = value.trim();
			
	        if (string == null || ( string != null && string.equals("")))
	        {
	        	emptyOrNull = true; 
	        }
		} else {
			emptyOrNull = true;
		}
		
	    return emptyOrNull;
	}
}
