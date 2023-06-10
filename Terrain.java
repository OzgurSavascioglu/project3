//Ozgur Savascioglu, 2022400366, 9 May 2023
//This is the class for the Terrain object. 
//Terrain class is the super class of Water and Soil classes
//Terrain class has two instance variables (height and controlled). 
//Terrain class has a no-arg constructor and a second constructor which takes a single int argument.
//All instance variables are protected and reached by getter and setter functions

public class Terrain {
	//instance variables
	protected int height;//stores height
	protected boolean controlled;//stores if the terrain is controlled
	
	//no argument constructor
	public Terrain() {
		
	}
	
	//constructor
	public Terrain(int height) {
		this.height=height;//sets the height of the terrain
	}
	
	//getters and setters
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	protected boolean isControlled() {
		return controlled;
	}

	protected void setControlled(boolean controlled) {
		this.controlled=controlled;
	}

}//end of the Terrain class
