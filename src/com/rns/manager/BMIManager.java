package com.rns.manager;


/**
 * Created by Jack on 2015-04-15.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.rns.dal.BMIDAL;
import com.rns.entity.Person;
import com.rns.entity.Planet;
import org.apache.log4j.Logger;

import com.rns.util.ApplicationProperty;
import com.rns.util.NumericUtil;

public class BMIManager {

	private static Logger logger = Logger.getLogger(BMIManager.class);
	
	private ArrayList<String[]> readStrings(){
		ArrayList<String[]> strings = new ArrayList<String[]>();

		logger.info("ENTER readStrings()");
		if(ApplicationProperty.getInstance().GetBooleanProperty("input.database")){
			ResultSet bmiRecords = BMIDAL.getInstance().readBMIRecords();
			if (bmiRecords != null){
				try {
					while (bmiRecords.next()) {
						String[] s = new String[4];
						s[0] = bmiRecords.getString("fullName");
						s[2] = bmiRecords.getString("weight");
						s[1] = bmiRecords.getString("height");
						s[3] = bmiRecords.getString("planet");
						strings.add(s);
					}
				}catch (SQLException e){
					e.printStackTrace();
				}
				logger.info("EXIT readStrings()");
				return strings;
			}
			else{
				logger.warn("Strings is empty");
				return strings;
			}
		}
		else {
			String fileName = ApplicationProperty.getInstance().GetPropertyValue("input.fileName");
			String filePath = ApplicationProperty.getInstance().GetPropertyValue("input.filePath");

			BufferedReader inputFile = null;

			try {
				String line = "";

				inputFile = new BufferedReader(new FileReader(filePath + "\\" + fileName));
				if (inputFile != null) {

					while ((line = inputFile.readLine()) != null) {

						if (!NumericUtil.isEmptyOrNull(line)) {
							strings.add(line.split(","));

						}
					}

				}
			} catch (IOException e) {
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

				person.setName(s[0]);
				person.setHeight(NumericUtil.getFloatValue(s[1]));
				person.setWeight(NumericUtil.getFloatValue(s[2]));
				person.setPlanet(planetFromString(s[3]));

				returnPeople.add(person);
			}
		}
		//Returns the ArrayList of Persons
		return returnPeople;
	}
	public Planet planetFromString(String planet){
		if(Planet.values() != null) {
			for (Planet p : Planet.values()) {
				if (planet.toLowerCase().equals(p.getName())) {
					return p;
				}
			}
		}
		return Planet.NOT_A_PLANET;

	}

}
