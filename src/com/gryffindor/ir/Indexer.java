package com.gryffindor.ir;

//importing the required libraries
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.jsoup.Jsoup;

//Indexer class contains all the methods and logic required for the Indexing
public class Indexer{
	IndexWriter indexWriter;
	private static EnglishAnalyzer englishAnalyzer = new EnglishAnalyzer();
	private ArrayList<File> fileList=new ArrayList<File>();
	FileListHandler fileListHandler = new FileListHandler();
	Indexer(String indexDir) throws IOException{
		/*Indexer constructor initializes the indexer using the index directory
		 * Indexer creates a index directory in said location 
		 */
		
		Path path = FileSystems.getDefault().getPath(indexDir);
		FSDirectory fsDirectory = FSDirectory.open(path);
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(englishAnalyzer);
		indexWriter = new IndexWriter(fsDirectory, indexWriterConfig);
	}
	
	public void addToIndex(String searchDir, String indexDir) throws IOException {
		//For each file in file list add files to the indexer Call to add all files to the file list
		addToFileList(new File(searchDir));
		FileReader fileReader = null;
		//List to store the file information
		List<String> fileInfoList = new ArrayList<String>();
        
		// For each file in file list 
		for( File file: fileList) {
			// create lucene document object
			try {
			//jSoup invoker to to parse the HTML documents 
			org.jsoup.nodes.Document jSoupdoc = Jsoup.parse(file, "utf-8");
			
			// parse file contents 
			fileReader = new FileReader(file);
			//Creating the Date format for readability
			String lastModified = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss").format(file.lastModified()).toString();
			//Get existing file info gets the previously indexed file information
			List<String> existingFileInfoList = fileListHandler.getExistingFileInfo(indexDir+"/file_info.tsv");
	    	//formatting the name,path and last-modified info to compare with the existing file information
			String currentFileInfo = (file.getName()+lastModified+file.getPath()).replaceAll("\\s","").toLowerCase();
			
			//Instantiating the document object
			Document document = new Document();
			//adding the required file info to the document instance
			//adding the title, body and file name to the contents of the document object to be indexed
			document.add(new TextField("contents", jSoupdoc.body().text()+"\n"+jSoupdoc.title()+"\n"+file.getName(),Field.Store.YES));
			//adding the file path,title,timestamp and file name  to the respective variables
			document.add(new StringField("path", file.getPath(), Field.Store.YES));
			document.add(new StringField("title", jSoupdoc.title(), Field.Store.YES));
			document.add(new StringField("timeStamp", lastModified, Field.Store.YES));
			document.add(new StringField("fileName", file.getName(), Field.Store.YES));
			
			//Checking if the current file is already indexed to avoid re indexing			
	    	if(fileListHandler.checkIfAlreadyExists(currentFileInfo,existingFileInfoList)){
	    		System.out.println("Skipping - Already Indexed: "+file.getName()+" Lastmodified: "+lastModified+
		    			" Located at: "+file.getPath()); 
	    		}else {
	    			//adding the document information to the indexer
	    			indexWriter.addDocument(document);
	    			System.out.println("Indexed file: "+file.getName()+" Lastmodified: "+lastModified+
		    			" Located at: "+file.getPath()); 
	    			//adding the indexed file info to the list 
	    			fileInfoList.add((file.getName()+lastModified+file.getPath()).replaceAll("\\s","").toLowerCase()); 
	    		}
			} catch (IOException e) {
				System.out.println("Could not add "+file.getName()+" to index");
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("Unable to fetch previously indexed files");
				e.printStackTrace();
			} finally {
				fileReader.close();
			}
		}
		
		indexWriter.close();
		//write indexed file info to tsv
		fileListHandler.writeFileInfoToTSV(fileInfoList, indexDir+"/file_info.tsv");
	}
	
	public void addToFileList(File searchDir) {
		/* To check if given search directory is file or directory
		 * If its a directory then call recursively to add the files
		 * of the directory to the list of files to add
		 */
		/*if(!fileName.exists()) {								//Check if file exists
			System.out.println(fileName + " does not exist.");
		}else*/ 
		if (searchDir.isDirectory()) {						
			for (File f : searchDir.listFiles()) {				//For each file in directory call recursively
		        addToFileList(f);
		      }
		}else {
			String lowerCaseName = searchDir.getName().toLowerCase();
			// Check if files are valid : HTML or Text documents
			
			if(lowerCaseName.endsWith(".htm")||lowerCaseName.endsWith(".html")||lowerCaseName.endsWith(".txt")) {
				fileList.add(searchDir);
			}
			else {
				System.out.println("Skipping: "+searchDir + " Not of type: HTML / Text");
			}
			
		}
	
	}
}


