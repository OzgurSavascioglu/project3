//Ozgur Savascioglu, 2022400366, 9 May 2023
//This is the class for the Environment object. This class also includes the methods that for the game calculations and game play. 
//Environment class has 2 static variables (radegeastTerrain and terrainArray).
//Environment class has 4 instance variables (numberOfRows, numberOfColumns, columnNames and maxLevel). 
//Environment() constructor takes the input file as its argument
//The constructor sets the static environment object of the class(radegastTerrain) with all the data fields in accordance with the input file data
//The constructor also calls the gameplay method to initiate the game
//startTheGame method is used to start the game, this method calls the constructor for the environment class
//gameplay method initiates the game, gets user inputs and calls relevant methods to modify and draw the terrainArray
//setTerrainArray method is called by the constructor to set the initial array according to file inputs
//setModifications method is called to modify the terrainArray according to the inputs of user
//drawTerrainArray is called to print the terrainArray to the console
//findTheLakes method is used to determine the lakes at the end of the game
//findMaxLevel method is used to find the highest terrain element, I use this value in surface height calculations
//changeToWater, changeToSoil and changeToTerrain methods are used to change the type of terrain array elements


import java.util.Arrays;
import java.util.Scanner;

public class Environment {//
	//static variables
	public static Environment radegastTerrain;//Stores the game environment
	private static Terrain[][] terrainArray;//Stores the terrainArray
	
	//instance variables
	private int numberOfRows;//Stores the number of rows in the terrain array
	private int numberOfColumns;//Stores the number of columns in the terrain array
	private String[] columnNames;//Stores the column names in string form
	private int maxLevel;//Stores the max height level in the terrain array
	
	//constructor
	public Environment(Scanner inputFile) {//gets file scanner as the argument

		radegastTerrain=this;//sets radegeastTerrain(static environment object of the class) as this environment
		this.numberOfColumns=inputFile.nextInt();//sets number of columns
		this.numberOfRows=inputFile.nextInt();//sets number of rows
		terrainArray=new Terrain [numberOfRows][numberOfColumns];//creates the terrain array with given # of rows and columns
		this.columnNames=new String[numberOfColumns];//sets the size of the column names array
		this.setColumnNames();//sets the column names
		this.maxLevel=0;//sets the initial max level as zero
		this.setTerrainArray(inputFile);// call the method that populates terrain array
		this.gameplay();//call the method that starts the game
	}//end of the constructor
	
	public static void startTheGame(Scanner inputFile) {//the method receive start the game command and call the constructor
		new Environment(inputFile);//call the constructor
	}//end of the start the game method
	
	public void gameplay() {//the method initiates the game, gets user inputs, calls relevant methods etc
		Scanner input = new Scanner(System.in);//open the Scanner that reads the console input
		for(int i=1;i<=10;i++) {//for loop-starts
			
			boolean inputCheck=false;//create and initialize the inputcheck boolean value
			this.drawTerrainArray();//draw the terrain array
			
			if(i!=1)//check if this is the first iteration of the for loop
				System.out.print("\n---------------");//if not print "\n---------------"
			
			while(!inputCheck) {//while loop-starts - checks if the input is valid
				System.out.print("\nAdd stone " +i+ "/ 10 to coordinate:");//print the command and the step number to the console
				inputCheck=this.setModifications(input);//calls the set modifications method, method return false if the input is wrong and while loop reiterates
			}//while loop-ends
			
		}//for loop-ends
		
		input.close();//close the scanner for console input
		
		this.drawTerrainArray();//draw the terrain array
		this.findMaxLevel();//finds the max level
		this.findTheLakes(false);//call the method to find the lakes, false means it is the initial call of the method, not a restart call
		Lake.setFinalIndexList();// sets the final index list of terrain array
		System.out.println("\n---------------");//print "\n---------------"
		this.drawTerrainArray();//draw the terrain array with lakes
		System.out.print("\n");//new line
		System.out.printf("Final score: %.2f", Lake.getGameScore());// calculate and print game score	
		
	}//end of the gameplay method
	
	public void setTerrainArray(Scanner inputFile) {//set the initial array according to file inputs
		
		for(int i=0;i<numberOfRows;i++) {//for loop-1 starts
			for(int j=0;j<numberOfColumns;j++) {//for loop-2 starts
				terrainArray [i][j]=new Soil(inputFile.nextInt());//sets the value as a new soil by using the height data in the file input
				if(i==0 || j==0 || i==numberOfRows-1 || j==numberOfColumns-1) {//checks if it is a border, if it is, the value becomes a controlled soil, which means definitely a soil
					terrainArray [i][j].setControlled(true);//identify the value as a controlled soil, definitely a soil
				}//end of if bracket
			}//for loop-1 ends
		}//for loop-2 ends
	}//end of the setTerrainArray method
	
	public boolean setModifications(Scanner input) {//method that modifies the terrainArray according to the inputs of user
		
		String next=input.next();//gets the next input in string format
		int maxRowInputLength=1;//sets the default maxRowInputLength
		int maxColumnInputLength=1;//sets the default maxRowInputLength
		int rowIndex;//row index of the element
		int columnIndex;//column index of the element
		
		if(this.numberOfRows>99)//checks if the numberOfRows is greater than 99
			maxRowInputLength+=2;//increase the maxRowInputLength by 2
		else if(this.numberOfRows>9)//checks if the numberOfRows is greater than 9
			maxRowInputLength+=1;//increase the maxRowInputLength by 1
		if(this.numberOfColumns>26)//checks if the numberOfColumns is greater than 26
			maxColumnInputLength+=1;//increase the maxColumnInputLength by 1
		
		if(next.length()>maxRowInputLength+maxColumnInputLength || next.length()<2) {//checks if the input length is valid
			System.out.print("Wrong input!");//print wrong input message to the console
			return false;//returns false
		}//if block ends
		
		if(maxColumnInputLength==2 && !Character.isDigit(next.charAt(1))){//checks if the input is in zz999, zz99 or zz9 format
			int j1=(int)(next.charAt(0)-'a'+1);//converts the first char value to integer and stores it
			
			if(next.charAt(1)<'a' || next.charAt(1)>'z'){//checks if the second char is a valid input
				System.out.print("Wrong input!");//print wrong input message to the console
				return false;//returns false
			}//if block ends
			
			int j0=(int)(next.charAt(1)-'a');//converts the second char value to integer and stores it
			
			columnIndex=26*j1+j0;//calculates the column number in integer format
			
			for(int k=2;k<next.length();k++) {// for loop starts - reads the integer part(row index) of the input
				
				if(!Character.isDigit(next.charAt(k))) {//checks if the input is valid
					System.out.print("Wrong input!");//print wrong input message to the console
					return false;//returns false
				}//if block ends
				
			}// for loop ends
			
			rowIndex=Integer.parseInt(next.substring(2));//reads the row index and assign it
			
			if(!(columnIndex<this.numberOfColumns && columnIndex>-1) || !(rowIndex<this.numberOfRows && rowIndex>-1)) {//checks if the indexes are within the range of the array
				System.out.print("Wrong input!");//print wrong input message to the console
				return false;//returns false
			}//if block ends
		}
		
		else {//inputs in z999, z99 or z9 format
			columnIndex=(int)(next.charAt(0)-'a');//read and assign the column index
		
			for(int k=1;k<next.length();k++) {// for loop starts - reads the integer part(row index) of the input
				
				if(!Character.isDigit(next.charAt(k))) {//checks if the input is valid
					System.out.print("Wrong input!");//print wrong input message to the console
					return false;//returns false
				}//if block ends
			}//for loop ends
			
			rowIndex=Integer.parseInt(next.substring(1));//reads the row index and assign it
		
			if(!(columnIndex<this.numberOfColumns && columnIndex>-1)|| !(rowIndex<this.numberOfRows && rowIndex>-1)) {//checks if the indexes are within the range of the array
				System.out.print("Wrong input!");//print wrong input message to the console
				return false;//returns false
			}//if block ends
			
		}//else block ends
		
		if(terrainArray[rowIndex][columnIndex].getHeight()+1>=100) {//checks if the new height is valid
			System.out.print("Wrong input!");//print wrong input message to the console
			return false;//returns false
		}
		(terrainArray[rowIndex][columnIndex]).setHeight(terrainArray[rowIndex][columnIndex].getHeight()+1);//increase the height value for the input cell
		return true;//returns true
		
	}// end of the setModifications method
	
	
	
	public void drawTerrainArray() {//method that draws the terrain array
		int rowIndex=0;//set initial row index as zero
		
		for(int i=0;i<numberOfRows;i++) {//for loop level 1 - starts
			if(numberOfRows>99) {//checks if numberOfRows is greater than 99
				if(i<10)//check if the row index of this iteration less than 10
					System.out.print("  ");//prints double space
				else if(i<100)//check if the row index of this iteration less than 100
					System.out.print(" ");//prints single space
			}// if block ends-checks if numberOfRows is greater than 99
			
			else if(numberOfRows>9) {//checks if numberOfRows is greater than 9(between 9 and 99)
				if(i<10)//check if the row index of this iteration less than 10
					System.out.print(" ");//prints single space
			}
			
			System.out.print(rowIndex);//prints rowIndex
			
			if(Lake.numOfLakes<=26) {//checks if number of lakes are less than or equal to 26
				System.out.print(" ");//prints single space
				for(int j=0;j<numberOfColumns;j++) {//for loop level 2 - starts
					if(terrainArray [i][j] instanceof Soil){// checks if soil instance
						if(terrainArray [i][j].getHeight()<10)//checks if the height has single digits
							System.out.print(" ");//prints single space
						System.out.print(terrainArray [i][j].getHeight());//prints the height value
					}
					else if(terrainArray [i][j]instanceof Water){// checks if water instance
						System.out.print(" ");//prints single space
						System.out.print(((Water)terrainArray [i][j]).getLakeName());//prints the height value
					}
					if(j!=numberOfColumns-1)//check if this is the last element of the row
						System.out.print(" ");//prints single space
				}//for loop level 2 - ends
			}//if block ends
			
			else{//if the number of lakes are greater than 26
				System.out.print(" ");//prints single space
				for(int j=0;j<numberOfColumns;j++) {//for loop level 2 - starts
					
					if(terrainArray [i][j] instanceof Soil) {//checks if soil instance
						if(terrainArray [i][j].getHeight()<10)//checks if the height has single digits
							System.out.print(" ");//prints single space
						System.out.print(terrainArray [i][j].getHeight());//prints the height value
					}//if block ends
					
					else if(terrainArray [i][j]instanceof Water) {//checks if water instance
						if(Lake.allLakes.get(((Water)terrainArray [i][j]).getLakeIndex()).finalIndex<26)//check if the lake index is less than 26 to determine lake name format
							System.out.print(" ");//prints single space
						System.out.print(((Water)terrainArray [i][j]).getLakeName());//print the lake name
					}//if block ends
					if(j!=numberOfColumns-1)//check if this is the last element of the row
						System.out.print(" ");//prints single space
					
				}//for loop level 2 - ends
			}//else block ends
			System.out.print("\n");//new line
			rowIndex++;//increase row index by one
		}//for loop level 1 - ends
		
		//put spaces before the column names
		if(10<numberOfRows)//check if total number of rows is greater than 10 
			System.out.print("  ");//prints double space
		else//if number of rows is less than 10
			System.out.print(" ");//prints single space
		
		for(int i=0;i<numberOfColumns;i++) {//for loop starts
			
			if(i<26) {//checks if the column index is less than 26
				System.out.print("  ");//prints double space
				System.out.print(this.columnNames[i]);//prints the name of the column
			}//if block ends
			
			else {//if column index is greater than equal to 26
				System.out.print(" ");//prints single space
				System.out.print(this.columnNames[i]);//prints the name of the column
			}//else block ends
			
		}//for loop ends
	}// end of the drawTerrainArray
	
	public void findTheLakes(boolean restartCall) {// this is the method that finds the lakes in the final terrain array
		
		for(int i=1;i<numberOfRows-1;i++) {//loop level 1-starts-the loops do not include border elements since they are definitely soil
			for(int j=1;j<numberOfColumns-1;j++) {//loop 2-starts-the loops do not include border elements since they are definitely soil
				
				int thisLakeIndex;//stores the index number of the lake that current cell belongs
				Lake thisLake=new Lake();//create a lake for current cell
				
				if(!restartCall)//checks if this is not restart call
					changeToWater(i, j);//make the terrain water temporarily	
				
				else if(!(terrainArray [i][j] instanceof Soil))// checks if terrain cell is not an instance of soil, which means either terrain or water
					changeToWater(i, j);//make the terrain water temporarily
				
				if(terrainArray [i][j] instanceof Water){//checks if the element is an instance of water
					
					Lake.allLakes.add(thisLake);//add the lake to the allLakes ArrayList
					thisLakeIndex=Lake.allLakes.indexOf(thisLake);//get the index of the lake and assign the value to thisLakeIndex
					((Water)terrainArray [i][j]).setLakeIndex(thisLakeIndex);//set the Water index value
					Lake.allLakes.get(thisLakeIndex).setSurfaceLevel(this.maxLevel);//set the initial surface level as the maxLevel
					Lake.allLakes.get(thisLakeIndex).addNewElement(i, j);//call add element method to add the height, row and column values of this cell to this lake

					for(int k=i-1;k<=i+1;k++) {//loop level 3-starts-check the neighbors of the cell
		
						for(int s=j-1;s<=j+1;s++) {//loop level 4-starts-check the neighbors of the cell
						
							if(!(s==j && k==i)&& !(terrainArray [i][j].isControlled())){//check if it is the same cell
						
								if(terrainArray [k][s] instanceof Water) {//case 1: instanceof water
								
									int index=((Water)terrainArray [k][s]).getLakeIndex();//stores the lake index of the neighbor
								
									if(Lake.allLakes.get(index).getSurfaceLevel()>terrainArray [i][j].getHeight()) {//control if there is a need to recalculate surface level and check the lake still exists
										thisLake.mergeLakes(Lake.allLakes.get(index),thisLakeIndex,index);//merge the lakes
										thisLakeIndex=Math.min(thisLakeIndex, index);//set the new lake index
										thisLake=Lake.allLakes.get(thisLakeIndex);//assign new lake as thisLake
										((Water)terrainArray [i][j]).setLakeIndex(thisLakeIndex);//assign new lake index to this cell
										}//end of control
								
									else if(Lake.allLakes.get(index).getSurfaceLevel()<=terrainArray [i][j].getHeight()) {//if there is a need to lower the surface or possibly destroy the lake
										Lake.allLakes.get(thisLakeIndex).lowerTheSurface(terrainArray [k][s].getHeight(),i,j);//method make calculations for decrease surface level or delete the lake if necessary 
										terrainArray [i][j].setControlled(true);//set cell as controlled
									}
									
								}//end of case 1: instanceof water
						
								else if((terrainArray [k][s].isControlled()&&!restartCall) || ((restartCall && terrainArray [k][s] instanceof Terrain))){// instance of soil or terrain - there is a difference between restart call and initial call, so that we cannot use isControlled anymore to only filter definite soils
								
									if(terrainArray [i][j].getHeight()>=terrainArray [k][s].getHeight()) {//if level 1 - checks if the height of current element is greater than height of the neighbor
										if(k==0 ||k==numberOfRows-1||s==0||s==numberOfColumns-1 || terrainArray [k][s].isControlled()==true) {//if level 2 - checks if the neighbor is a definite soil
											Lake.allLakes.get(thisLakeIndex).lowerTheSurface(terrainArray [k][s].getHeight(),i,j);//method make calculations to delete the lake if necessary
											terrainArray [i][j].setControlled(true);//set cell as controlled
										}//if block level 2 ends
									}//if block level 1 ends
							
									else { //condition: terrainArray [i][j].getHeight()<terrainArray [k][s].getHeight()
								
										if(Lake.allLakes.get(thisLakeIndex).getSurfaceLevel()>terrainArray [k][s].getHeight()) {//check if there is a need to recalculate the surface level
											Lake.allLakes.get(thisLakeIndex).lowerTheSurface(terrainArray [k][s].getHeight(),i,j);//recalculate surface level
										}	//if block ends
									}//else block ends
								}// ends- instance of soil	
							}//if block ends - check if it is the same cell
						}//loop level 4-ends
					}//loop level 3-ends
					
					terrainArray [i][j].setControlled(true);//set cell as controlled
				
				}//if block ends-checks if the current cell terrainArray [i][j] is an instance of water
			}//loop level 2-ends	
		}//loop level 1-ends
	}//end of the findTheLakes method
	
	public void findMaxLevel() {//method to find the highest terrain element, I use this value in surface height calculations
		for(int i=0;i<numberOfRows;i++) {//for loop level 1 starts
			for(int j=0;j<numberOfColumns;j++) {//for loop level 2 starts
				if(terrainArray[i][j].getHeight()>this.maxLevel)//checks if the value is higher than current maxLevel
					this.maxLevel=terrainArray[i][j].getHeight();//set maxLevel as the height of this element
			}//for loop level 2 ends
		}//for loop level 1 ends
	}//end of the findMaxLevel method
	
	public static void changeToWater(int i, int j) {//method to change the  type of the cell to water
			int height=Environment.getTerrainArray()[i][j].getHeight();//gets and stores the height of the cell
			Environment.getTerrainArray()[i][j]= new Water(height);//assign a new water element with the same height to the cell
	}//end of the changeToWater method
	
	public static void changeToSoil(int i, int j) {//method to change the  type of the cell to soil
		int height=Environment.getTerrainArray()[i][j].getHeight();//gets and stores the height of the cell
		Environment.getTerrainArray()[i][j]= new Soil(height,true);//assign a new soil element with the same height to the cell
	}//end of the changeToSoil method
	
	public static void changeToTerrain(int i, int j) {//method to change the  type of the cell to terrain
		int height=Environment.getTerrainArray()[i][j].getHeight();//gets and stores the height of the cell
		Environment.getTerrainArray()[i][j]= new Terrain(height);//assign a new terrain element with the same height to the cell
	}//end of the changeToTerrain method
	
	public void setColumnNames() {//method to calculate and set the column names
		char s1;//stores the first char
		char s0;//stores the second char
		
		if(numberOfColumns>26) {//checks if the numberOfColumns is greater than 26
			
			for(int i=0;i<this.numberOfColumns;i++) {//for loop starts
				s1=(char)((i/26)-1+'a');//calculates and assigns the first element
				s0=(char)((i%26)+'a');//calculates and assigns the second element
				this.columnNames[i]="";//initialize the columName
				if(i>25)//checks if the column index is larger than 25
					this.columnNames[i]+=s1;//add the first element to the columnNames string array
				this.columnNames[i]+=s0;//add the second element to the columnNames string array
			}//for loop ends
			
		}//end of the if block
		
		else if(numberOfColumns<=26) {//checks if the numberOfColumns is less than or equal to 26
			
			for(int i=0;i<this.numberOfColumns;i++) {//for loop starts
				s0=(char)((i%26)+'a');////calculates and assigns the second element
				this.columnNames[i]="";//initialize the columName
				this.columnNames[i]+=s0;//add the second element to the columnNames string array
			}//for loop ends
			
		}//end of the if block
			
	}

	//getters and setters
	public int getNumberOfRows() {
		return numberOfRows;
	}

	public void setNumberOfRows(int width) {
		this.numberOfRows = width;
	}

	public int getNumberOfColumns() {
		return numberOfColumns;
	}

	public void setNumberOfColumns(int height) {
		this.numberOfColumns = height;
	}

	public static Terrain[][] getTerrainArray() {
		return terrainArray;
	}

	public void setTerrainArray(Terrain[][] terrainArray) {
		Environment.terrainArray = terrainArray;
	}

	public static Environment getRadegastTerrain() {
		return radegastTerrain;
	}

	public static void setRadegastTerrain(Environment radegastTerrain) {
		Environment.radegastTerrain = radegastTerrain;
	}
	
	//toString method
	@Override
	public String toString() {
		return "Terrain [width=" + numberOfRows + ", height=" + numberOfColumns + ", terrainArray=" + Arrays.toString(terrainArray)
				+ "]";
	}
	
}
