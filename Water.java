//Ozgur Savascioglu, 2022400366, 9 May 2023
//This is the class for the Water object. 
//Water class extends Terrain class.
//Water class has a single instance variable (lakeIndex). 
//Water class has a no-arg constructor and three other constructors
//getLakeName method calculates the lakeIndex in alphabetical notation

public class Water extends Terrain {
	private int lakeIndex;//stores the lake index
	
	//no-arg constructor
	public Water() {
		
	}
	
	//constructor 2
	public Water(int height) {
		super(height);//sets the height
	}
	
	//constructor 3
	public Water(int height, char lakeName) {
		super(height);//sets the height
		this.setLakeIndex(lakeName);//sets the lakeIndex
	}
	
	//constructor 4
	public Water(int height, char lakeName, boolean controlled) {
		super(height);//sets the height
		this.setLakeIndex(lakeName);//sets the lakeIndex
		this.setControlled(controlled);//sets the controlled value
	}
	
	public String getLakeName() {//calculates the lakeIndex in alphabetical notation
		int index=(Lake.allLakes.get(lakeIndex).finalIndex);//calculates and stores the final lake index
		char s1;//stores the first char
		char s0;//stores the second char
		String returnValue="";//initialize the string
		if(index<26)//checks if the index number is less than 26, which means single char notation
			returnValue+=(char)('A'+index);//calculates and adds the lake name to the string
		else {//index number is greater than or equal to 26, which means double char notation
			s1=(char)((index/26)-1+'A');//calculates the first char element
			s0=(char)((index%26)+'A');//calculates the second char element
			returnValue+=s1;//add the first char element to the string
			returnValue+=s0;//add the second char element to the string
		}
		return returnValue;//return the lake name
	}//end of the getLakeName method

	//getters and setters
	@Override
	public boolean isControlled() {
		return this.controlled;
	}

	@Override
	public void setControlled(boolean controlled) {
		this.controlled=controlled;
		
	}
	
	public int getLakeIndex() {
		return lakeIndex;
	}

	public void setLakeIndex(int lakeIndex) {
		this.lakeIndex = lakeIndex;
	}
	
}
