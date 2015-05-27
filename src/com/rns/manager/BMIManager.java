package com.rns.manager;


/**
 * Created by Jack on 2015-04-15.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.rns.dal.BMIDAL;
import com.rns.entity.Person;
import com.rns.entity.Planet;
import org.apache.log4j.Logger;

import com.rns.util.ApplicationProperty;
import com.rns.util.NumericUtil;

public class BMIManager {

	private static Logger logger = Logger.getLogger(BMIManager.class);
	//This isn't used if strings are being read from a database. Deprecated?
	private ArrayList<String[]> readStrings(){
		ArrayList<String[]> strings = new ArrayList<String[]>();

		logger.info("ENTER readStrings()");

			String fileName = ApplicationProperty.getInstance().GetPropertyValue("input.fileName");
			String filePath = ApplicationProperty.getInstance().GetPropertyValue("input.filePath");

			BufferedReader inputFile = null;

			try {
				String line;

				inputFile = new BufferedReader(new FileReader(filePath + "\\" + fileName));
					while ((line = inputFile.readLine()) != null) {

						if (!NumericUtil.isEmptyOrNull(line)) {
							strings.add(line.split(","));

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
	public ArrayList<Person> getPeople(){

		//ArrayList that is returned with the Person objects
		ArrayList<Person> returnPeople = new ArrayList<Person>();
		if (ApplicationProperty.getInstance().GetBooleanProperty("input.database"))
		{
			ResultSet bmiRecords;
			try {
				logger.info("Reading BMI records");
				Statement st = BMIDAL.getInstance().getDatabaseConnection().createStatement();
				bmiRecords = st.executeQuery("SELECT * FROM bmi");
				while (bmiRecords.next()){
                returnPeople.add(new Person(bmiRecords.getString("fullName"),bmiRecords.getFloat("weight"),
                                            bmiRecords.getFloat("height"),planetFromString(bmiRecords.getString("planet"))));
                }
			} catch (SQLException e) {
				e.printStackTrace();
				logger.warn("BMI record reading failed");
			}
			return returnPeople;
		}
		else{
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
