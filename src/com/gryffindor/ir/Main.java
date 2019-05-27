package com.gryffindor.ir;
//importing the required libraries
import java.io.File;
import java.io.IOException;
//main class to read arguments and control the flow of program
public class Main{
	//Static Variables that are used throughout the program
	static String searchDir="";
	static String indexDir="";
	static String rankingAlgo="";
	static String query="";
	 
	public static void main(String[] args) throws Exception {
			//calling method to parse and validate the input arguments
			parseArguments(args);
			try {
				// Create an index for the search directory in the given index directory
				Indexer indexer=new Indexer(indexDir);
				PrintUtils.printRow();
				System.out.println(PrintUtils.makeFullLine("Starting indexing of files", "-", 176,"|"));
				PrintUtils.printRow();
				//Add the files from search directory to the index
				indexer.addToIndex(searchDir,indexDir);
			} catch (IOException e) {
				// Handling index i/o exceptions
				e.printStackTrace();
				System.out.println("Could not create Index !");
			}		
			// Search the query
			//creating the searcher instance to carry-out the search on the created index
			Searcher searcher=new Searcher();
			PrintUtils.printRow();
			System.out.println(PrintUtils.makeFullLine("Starting Search using "+rankingAlgo+" For Query:"+Main.query, "-", 176,"|"));
			PrintUtils.printRow();
			//calling the search method with the query and other parameters
			searcher.search(query,indexDir,rankingAlgo);	
		}
	 
	 public static void parseArguments(String[] args) throws Exception {
	    	//Check for required number of arguments
		 	if(args.length != 4) {
	    		System.out.println("Illegal Arguements only 4 args are allowed");
	    		System.exit(0);
	    	}
		 	//Search directory contains all the documents in which the searching has to be done
	    	Main.searchDir = args[0];
	    	//index directory contains the path to index if index does not exist it will be created
	    	Main.indexDir =  args[1];
	    	if (!new File(indexDir).isDirectory()){
	    		System.out.println("No index directory Creating new directory");
	    		new File(indexDir).mkdirs();
	    	}
	    	//Checking if the search directory is valid
	    	if (!new File(searchDir).isDirectory()){
	    		System.out.println("Illegal file search directory");
	    		System.exit(0);
	    	}
	    	//reading and validating the ranking algorithm
	    	Main.rankingAlgo = args[2].toUpperCase();
	    	if(!(rankingAlgo.equals("OK")||rankingAlgo.equals("VS"))){
	    		System.out.println("Unknown Ranking Algorithm only OK / VS are allowed");
	    		System.exit(0);
	    	}
	    	//reading the query term
	    	Main.query = args[3];
	    }

}
