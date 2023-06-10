//Ozgur Savascioglu, 2022400366, 9 May 2023
//Main reads the input file and calls the method to initiate the game
//Environment.startTheGame method starts the game

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Ozgur_Savascioglu {

	public static void main (String[] args) throws FileNotFoundException{
		

		// Reading the coordinates file to create the objects
		String fileName = "input4.txt";

		// file object is required to open the file
		File file = new File(fileName);
		
		// if the file is not found, issue an error message and quit
		if (!file.exists()) {
			System.out.printf("%s can not be found.", fileName);
			System.exit(1); // exit the program
		}
		
		Scanner inputFile = new Scanner(file);//create the file scanner
		Environment.startTheGame(inputFile);//start the game
		inputFile.close();//close the file scanner
	}
}
