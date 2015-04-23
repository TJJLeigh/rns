package com.rns.manager;


/**
 * Created by Jack on 2015-04-15.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.rns.entity.Person;
import org.apache.log4j.Logger;

import com.rns.util.ApplicationProperty;
import com.rns.util.NumericUtil;

public class BMIManager {

	private static Logger logger = Logger.getLogger(BMIManager.class);
	
	private ArrayList<String[]> readStrings(){
		
		logger.info("ENTER processStrings()");
		ArrayList<String[]> strings = new ArrayList<String[]>();
		
		String fileName = ApplicationProperty.getInstance().GetPropertyValue("input.fileName");
		String filePath = ApplicationProperty.getInstance().GetPropertyValue("input.filePath");
		
		BufferedReader inputFile = null;
		
		try 
		{
			String line = "";
			
			inputFile = new BufferedReader(new FileReader(filePath + "\\" +fileName));
			if ( inputFile != null ){
			
				while ((line = inputFile.readLine()) != null) {

					if ( ! NumericUtil.isEmptyOrNull(line)){
						strings.add(line.split(","));

					}
				}

			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.info("EXIT readStrings() ");
		return strings;
	}
	public ArrayList<Person> getPeople(){
		//ArrayList that is returned with the Person objects
		ArrayList<Person> returnPeople = new ArrayList<Person>();

		//ArrayList of string arrays that are to be parsed into person objects
		ArrayList<String[]> rawStrings = readStrings();

		//Loop iterates through list of String[]s are parses out relevant data into a Person object then
		//Adds each object to the ArrayList of Persons
		for (String[] s: rawStrings){
			if (s != null && s.length == 4){
				Person person = new Person();
				person.setHeight(NumericUtil.getFloatValue(s[1]));
				person.setWeight(NumericUtil.getFloatValue(s[2]));
				person.setName(s[0]);
				returnPeople.add(person);
			}
		}
		//Returns the ArrayList of Persons
		return returnPeople;
	}

}
