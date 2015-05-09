package com.rns.entity;

public class Person {

	private float _Height = 0.0f;
	private float _Weight = 0.0f;
	private String _Name = "";
	private Planet _Planet = Planet.EARTH;

	public Person(){
		
	}

	public Person(String name, float weight, float height){
		
		_Name = name;
		_Weight = weight;
		_Height = height;
		
	}

	public void setHeight(float height){
		_Height = height;
	}
	public float getHeight(){
		return _Height;
	}
	
	public void setWeight(float weight){
		_Weight = weight;
	}
	public float getWeight(){
		return _Weight;
	}
	
	public void setName(String name){
		_Name = name;
	}
	public String getName(){
		return _Name;
	}

	public void setPlanet(Planet planet){
		_Planet = planet;
	}
	public Planet getPlanet(){
		return _Planet;
	}

	public double calculateBodyMassIndex(){
		float gravity = _Planet.getGravity();
		double bmi = ( _Weight/gravity ) / ( (_Height/100) * (_Height/100));

		return bmi;
	}

	
}
