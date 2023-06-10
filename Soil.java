//Ozgur Savascioglu, 2022400366, 9 May 2023
//This is the class for the Soil object. 
//Soil class extends Terrain class.
//Soil class has a no-arg constructor, a second constructor which takes a single int argument and a third constructor which takes int and boolean argument.

public class Soil extends Terrain {

	//no-arg constructor
	public Soil(){
		
	}
	
	//constructor 2
	public Soil(int height){
		super(height);//set the height
	}
	
	//constructor 3
	public Soil(int height, boolean controlled){
		super(height);//set the height
		this.controlled=controlled;//set the controlled value
	}

	//getters and setters
	@Override
	public boolean isControlled() {
		return this.controlled;
	}

	@Override
	public void setControlled(boolean controlled) {
		this.controlled=controlled;
	}

}
