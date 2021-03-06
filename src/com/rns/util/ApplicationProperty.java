package com.rns.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperty {
	
	private static Properties property = null;
	private static final String PROPERTIES_FILE = "service.properties";
	
	private static ApplicationProperty instance = null;
	protected ApplicationProperty(){
		LoadProperty();
	}
	
	public static ApplicationProperty getInstance() {
		if(instance == null) {
			instance = new ApplicationProperty();
		}
		return instance;
	}
	
	private void LoadProperty(){
		
		try {
	    	
			File file = null;
			String propertiesFile;
			property = new Properties();
			InputStream propsFile;
	
	    	String localConfigPath = System.getProperty("user.dir") + "/conf/";
	    	
			file = new File(localConfigPath,PROPERTIES_FILE);
			
		    if ( ( file.exists()) || file.isDirectory() ) {
		    	
		    	propertiesFile = localConfigPath + PROPERTIES_FILE;
		    	
		    	propsFile = new FileInputStream(propertiesFile );
		        property.load(propsFile);
		        propsFile.close();
		    }   
	
		} catch (IOException ioe) {
	    System.out.println("I/O Exception.");
	        ioe.printStackTrace();
	        System.exit(0);
	    }
	}
	
    public String GetPropertyValue(String aPropertyValue) {
    	
		String propertyValue = null;
    	if ( aPropertyValue != null && ! aPropertyValue.equals("")){
    		
			if (property == null)
				LoadProperty();
				
			if (property != null)
			{
				propertyValue = property.getProperty(aPropertyValue);	
			}
		}
		return propertyValue;
    }
    
    public int GetIntProperty(String propertyValue){
    	int returnValue = 0;
    	String value = GetPropertyValue(propertyValue);
    	returnValue = Integer.parseInt(value);
    	return returnValue;
    }
    
    public int GetIntProperty(String propertyValue, int defaultValue){
    	
    	int returnValue = 0;
    	String value = GetPropertyValue(propertyValue);
    	
    	if ( !NumericUtil.isEmptyOrNull(value)){
    		
    		if ( NumericUtil.isNumeric(value)){
            	returnValue = Integer.parseInt(value);
    		}
    	}
    
    	if ( returnValue <= -1 )
    		returnValue = defaultValue;
    	
    	return returnValue;
    }
  

    public boolean GetBooleanProperty(String propertyValue){
    	boolean returnValue = false;
    	String value = GetPropertyValue(propertyValue);
    	
    	if ( !NumericUtil.isEmptyOrNull(value)){
    		
            	returnValue = Boolean.parseBoolean(value);
    	}
    	return returnValue;
    }
    
    public String[] getList(String propertyValue){
    	
    	String [] items = null;
    	
    	String value = GetPropertyValue(propertyValue);
    	System.out.print(value);
    	
    	if ( !NumericUtil.isEmptyOrNull(value)){
    		if ( value.contains(",")){
    			items = value.split(",");
    		} else {
    			items = new String [1];
    			items[0] = value;
    			
    		}
    	}
    	return items;
    }
   
}
