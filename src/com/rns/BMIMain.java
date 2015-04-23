package com.rns;


import com.rns.entity.Person;
import com.rns.manager.BMIManager;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

import java.util.ArrayList;

/**
 * Created by Jack on 2015-04-15.
 */



public class BMIMain {
	
    private static String LOG_PROPERTIES_FILE = "conf/log4j.configuration";

	public static void main(String[] args) {
		SetUp();

        BMIManager bmiManager = new BMIManager();
        ArrayList<Person> test2 = bmiManager.getPeople();

        for(Person s: test2){
            System.out.println(s.getName() + "'s BMI is: " + s.calculateBodyMassIndex());
        }
	}
	
    private static void SetUp (){

        BasicConfigurator.configure();
        PropertyConfigurator.configure(LOG_PROPERTIES_FILE);


    }

}
