package com.gryffindor.ir;
//This class contains the formatting for printing into the java classic console
//These methods allows to avoid custom output formatting libraries
public class PrintUtils {
	static void printRow() {
		 System.out.println(getChars(176,"-"));
	}
	static String getPrintFormat(String var) {
		if(var=="header") { 
		return("| %4s  |  %40s  |  %10s  |  %20s  |  %80s \n");
		}
		return("|  %3d  |  %40s  |  %10s  |  %20s  |  %s \n");
	}
	static String getChars(int len,String filler) {
		StringBuilder sb= new StringBuilder();
		for(int i=0;i<len;i++) {
			sb.append(filler);
		}
		return sb.toString();
	}
	static String formatTitle(String title) {
		if(title.equals("")) {
      	  title="Untitled / Plain Text Document";
      	}
		title = (title+"                                        ").substring(0, 40);
        if(title.length()>40) {
      	  title = (title+"                                        ").substring(0, 37)+"...";
        }
    return title;
	}
	static String formatScore(Float score) {
		return(score+"0000000000").substring(0, 10);
	}
	static String makeFullLine(String data,String filler,int length,String edge) {
		int before= (int) Math.ceil((length-data.length())/2)-1;
		int after = length-before-data.length()-2;
		data=edge+getChars(before,filler)+data+getChars(after,filler)+edge;
		
		return data;
	}
	
}
