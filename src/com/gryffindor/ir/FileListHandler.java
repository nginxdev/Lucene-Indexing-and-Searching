package com.gryffindor.ir;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileListHandler {
	//This function collects the information on Already Indexed files
    public List<String> getExistingFileInfo(String fileInfoTSVLocation) throws  Exception {
    	
    	File f = new File(fileInfoTSVLocation);
    	if(!f.exists()){
    	  f.createNewFile();
    	  System.out.println("No index in index location | Creating New Index ");
    	  
    	}
    	List<String> existingFileInfo = new ArrayList<>();
    	
    	BufferedReader reader = new BufferedReader(new FileReader(fileInfoTSVLocation));
		String line = reader.readLine();
		while (line != null) {
			existingFileInfo.add(new String(line));
			line=reader.readLine();
		}
		reader.close();
        return existingFileInfo;
    }
    
    //this function writes the information about the indexed documents
    public void writeFileInfoToTSV(List<String> fileInfoList,String fileInfoTSVLocation) throws IOException {
	    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileInfoTSVLocation, true));
	    StringBuilder stringBuilder = new StringBuilder();
	    for (String fileInfo : fileInfoList) {
	    	stringBuilder.append(fileInfo);
	    	stringBuilder.append("\n");
	    }
	    bufferedWriter.write(stringBuilder.toString());
	    bufferedWriter.close();
    }
    
    //this function checks if the document is already indexed
    public boolean checkIfAlreadyExists(String currentFileInfo, List<String> existingFileInfoList) {
		for(String existingFileInfo : existingFileInfoList) {
			if(existingFileInfo.equals(currentFileInfo)) {
				return true;
			}
		}
    	return false;
    	
		
	}
}