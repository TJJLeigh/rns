package com.rns;

import com.rns.dal.BMIDAL;
import com.rns.entity.Person;
import com.rns.manager.BMIManager;
import java.sql.*;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

import java.util.ArrayList;



public class BMIMain {
	
    private static String LOG_PROPERTIES_FILE = "conf/log4j.configuration";

	public static void main(String[] args) {
		SetUp();
        BMIManager m = new BMIManager();
        ArrayList<Person> p = m.getPeople();

        for (Person l:p){
            System.out.println(l.getName() + "," + l.calculateBodyMassIndex());
        }
        BMIDAL.getInstance().stopConnection();
    }
	
    private static void SetUp (){

        BasicConfigurator.configure();
        PropertyConfigurator.configure(LOG_PROPERTIES_FILE);


    }

}
