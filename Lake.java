//Ozgur Savascioglu, 2022400366, 9 May 2023
//This is the class for the Lake object. This class also includes the methods to merge, edit and delete the lakes. 
//Lake class also includes the methods to calculate the game score. 
//Environment class has 2 static variables (allLakes and numOfLakes).
//Environment class has 7 instance variables (xCoordinates, yCoordinates, heights, finalIndex, surfaceLevel, maxHeightLevel and deleted). 
//Lake class has a no-argument constructor
//The constructor creates the initial data fields
//addNewElement method add cells the the lake
//removeElement method add cells the the lake
//deleteTheLake method deletes the lake
//revertToSoil change the types of all the elements in the lake as soil
//mergeLakes method is used to merge the lakes
//lowerTheSurface method is used to make calculations to lower the surface level and delete the lake if necessary
//restarTheLakeFinder method is used for recursion to recall the findTheLakes methods
//getLakeVolumeScore method is used to calculate the score contribution from the lake to the total score
//setFinalIndexList method is used to calculate the index value of the lake in the final lake list
//getGameScore method is used to calculate the final game score

import java.util.ArrayList;
import java.util.Collections;

public class Lake {//This is the class for the Lake object. This class also includes the methods to merge, edit, delete the lakes and calculate the game score. 
	//static variables
	public static ArrayList<Lake> allLakes=new ArrayList<Lake>();//stores all lakes in the game
	public static int numOfLakes;//stores the number of lakes
	//instance variables
	public ArrayList<Integer> xCoordinates;//stores the xCoordinates of the lake members
	public ArrayList<Integer> yCoordinates;//stores the yCoordinates of the lake members
	public ArrayList<Integer> heights;//stores the heights of the lake members
	public int finalIndex;//stores the final index of the lake
	public int surfaceLevel;//stores the surface level of the lake
	public int maxHeightLevel;//stores the max height level of the lake
	public boolean deleted;//stores if it is a deleted lake
	
	//constructor
	public Lake() {
		this.xCoordinates=new ArrayList<Integer>();//creates the xCoordinates ArrayList
		this.yCoordinates=new ArrayList<Integer>();//creates the yCoordinates ArrayList
		this.heights=new ArrayList<Integer>();//creates the heights ArrayList
	}// end of the constructor
	
	public void addNewElement(int i, int j) {//method to add new cell elements to the lake
		this.xCoordinates.add(i);// add the x coordinate of the cell to the lake
		this.yCoordinates.add(j);// add the y coordinate of the cell to the lake
		this.heights.add(Environment.getTerrainArray()[i][j].getHeight());// add the height of the cell to the lake
	}// end of the add element method
	
	public void removeElement(int i) {//method to remove a cell from the lake
		Environment.getTerrainArray()[xCoordinates.get(i)][yCoordinates.get(i)]= new Soil(this.heights.get(i), true);//make the cell element definite soil
		this.xCoordinates.set(i,null);// remove the x coordinate of the cell from the lake
		this.yCoordinates.set(i,null);// remove the y coordinate of the cell from the lake
		this.heights.set(i,null);// remove the height of the cell from the lake
	}//end of the removeElement method
	
	
	public void deleteTheLake() {//method to delete a lake
		this.revertToSoil();//make all the elements of the lake soil
		this.xCoordinates=null;//make xCoordinates ArrayList null
		this.yCoordinates=null;//make yCoordinates ArrayList null
		this.heights=null;//make heights ArrayList null
		this.deleted=true;//make deleted true
	}
	
	public void revertToSoil() {//method to revert all the elements of the lake to soil
		for(int i=0;i<xCoordinates.size();i++) {//for loop starts
			int height=Environment.getTerrainArray()[xCoordinates.get(i)][yCoordinates.get(i)].getHeight();//calculate the height
			Environment.getTerrainArray()[xCoordinates.get(i)][yCoordinates.get(i)]= new Soil(height, true);//create a new soil element with calculated height
		}//for loop ends
	}//end of the revertToSoil method
	
	public void mergeLakes(Lake inputLake, int thisLakeIndex, int index) {//method to merge two lakes

		if(thisLakeIndex<index) {//check if the first index is less than the second one	
			int size=inputLake.xCoordinates.size();//calculate the size of the second lake
			for(int i=0;i<size;i++) {//for loop starts
				this.addNewElement(inputLake.xCoordinates.get(i),inputLake.yCoordinates.get(i));//add the element of second lake to the first lake
				((Water)(Environment.getTerrainArray())[inputLake.xCoordinates.get(i)][inputLake.yCoordinates.get(i)]).setLakeIndex(thisLakeIndex);//change the lake indexes of the elements of the second lake
			}//for loop ends
			inputLake.deleted=true;//makes inputlake deleted
		}//end of if block
		
		else if(thisLakeIndex>index){//check if the first index is greater than the second one
			int j=this.xCoordinates.size();//calculate the size of the first lake
			
			for(int i=0;i<j;i++) {//for loop starts
				inputLake.addNewElement(this.xCoordinates.get(i),this.yCoordinates.get(i));//add the element of first lake to the second lake
				((Water)(Environment.getTerrainArray())[this.xCoordinates.get(i)][this.yCoordinates.get(i)]).setLakeIndex(index);//change the lake indexes of the elements of the first lake
			}//for loop ends
			
			Lake.allLakes.get(thisLakeIndex).deleted=true;//makes first lake deleted
		}//end of else if block
		
	}//end of mergeLakes method
	
	public void lowerTheSurface(int newSurfaceHeight, int a, int b) {//method to calculate surface level changes and if there is a need to delete the lake
		
		ArrayList<Integer> removeList=new ArrayList<Integer> ();//this is used to store element that wil be removed from the lake
		this.maxHeightLevel=Collections.max(this.heights);//stores the max height lavel of the lake
		
		if(this.xCoordinates==null) {//checks if the lake is null
			this.surfaceLevel=newSurfaceHeight;//set the surface height
			if(this.surfaceLevel<=Environment.getTerrainArray()[a][b].getHeight()) {//check if the given element is over the level of max possible surface level
				Environment.getTerrainArray()[a][b]= new Soil(Environment.getTerrainArray()[a][b].getHeight(), true);//makes the cell controlled soil
			}//if block ends
		}//if block ends
		
		else if(this.xCoordinates.size()==1) {//checks if the size of the lake is 1
			this.surfaceLevel=newSurfaceHeight;//set the surface height
			if(this.surfaceLevel<=Environment.getTerrainArray()[a][b].getHeight())//check if the given element is over the level of max possible surface level
				this.deleteTheLake();//deletes the lake
		}//else if block ends
		
		else if(this.xCoordinates.size()==2) {//checks if the size of the lake is 2
			this.surfaceLevel=newSurfaceHeight;//set the surface height
			
			int size=this.heights.size();//store the size
			
			for(int i=0;i<size;i++) {//for loop starts
				if(this.heights.get(i)>=newSurfaceHeight){//check if the given element is over the level of max possible surface level
					removeList.add(i);//add element to the removeList
				}//end of the if block
			}//for loop ends
			
			if(removeList.size()==2) {//check if all the element are in the remove list
				this.deleteTheLake();//delete the lake
			}//end of if block
			
			else {//if one or less elements are in the removeList
				
				for(int i=0;i<removeList.size();i++) {//for loop ends
					this.removeElement(removeList.get(i));//call the remove element method
				}//for loop ends
			}//end of the else block
		}//end of the else if block
		
		else if(this.maxHeightLevel<newSurfaceHeight) {//for lakes with more than two elements: check if there is no need to remove any element
			this.surfaceLevel=newSurfaceHeight;
		}//end of the else if block
			
		else {//for lakes with >2 elements, with need to remove elements and recalculate surface level
			Environment.changeToSoil(a,b);//change the current element to soil
			restartTheLakeFinder();//call the method that will initiate the recursion process
		}//end of the else block
		
	}//end of the lowerTheSurface method
	
	public static void restartTheLakeFinder() {//method to initiate recursion process for the findTheLakes method
		allLakes=new ArrayList<Lake>();//assign a new ArrayList to the static element of the class
		
		for(int i=0;i<Environment.radegastTerrain.getNumberOfRows();i++) {//for loop level 1 starts
			for(int j=0;j<Environment.radegastTerrain.getNumberOfColumns();j++) {//for loop level 2 starts
				if(!(Environment.getTerrainArray()[i][j] instanceof Soil)) {//checks if the element is not soil
					Environment.changeToTerrain(i,j);//makes non-soil elements terrain
					Environment.getTerrainArray()[i][j].setControlled(false);//makes element uncontrolled
				}//end of the if block
			}//for loop level 2 ends
		}//for loop level 1 ends
		
		Environment.radegastTerrain.findTheLakes(true);//recall find the lakes function, true means this is a restart call
	}
	
	public double getLakeVolumeScore() {//method to calculate the score contribution from a single lake
		double returnValue=0;//initialize return value as zero
		
		for (int i=0;i<heights.size();i++){//for loop starts
			if(this.heights.get(i)!=null)//checks if the element has an height value
				returnValue+=this.getSurfaceLevel()-this.heights.get(i);//add the value to the score
		}//for loop ends
		returnValue=Math.sqrt(returnValue);//calculate square root of the result
		return returnValue;//returns the score
	}//end of the getLakeVolumeScore method
	
	public static void setFinalIndexList() {//calculates the final indexes of the lakes
		int index=0;//initialize the index
		for (int i=0;i<allLakes.size();i++)//for loop starts
			if(allLakes.get(i).deleted==false) {
				allLakes.get(i).finalIndex=index;//assign index value to the lake
				index++;//increase the index
			}//for loop ends
		Lake.numOfLakes=index;//assign the value to the numOfLakes
	}
	
	public static double getGameScore() {//method to calculate total game score
		double returnValue=0;//stores game score
		for (int i=0;i<allLakes.size();i++){//for loop starts
			if(allLakes.get(i).deleted==false)//checks if it is not a deleted lake
				returnValue+=allLakes.get(i).getLakeVolumeScore();//calculate and add the score contribution
		}//for loop ends
		return returnValue;//return the total score
	}//end of the getGameScore method
	
	
	//getters and setters
	public int getSurfaceLevel() {
		return surfaceLevel;
	}

	public void setSurfaceLevel(int surfaceHeight) {
		this.surfaceLevel = surfaceHeight;
	}

	public static int getNumOfLakes() {
		return numOfLakes;
	}

	public static void setNumOfLakes(int numOfLakes) {
		Lake.numOfLakes = numOfLakes;
	}

	//toString method
	@Override
	public String toString() {
		return "Lake [xCoordinates=" + xCoordinates + ", yCoordinates=" + yCoordinates + ", heights=" + heights +", surfaceHeight="
				+ surfaceLevel + "]";
	}
}
