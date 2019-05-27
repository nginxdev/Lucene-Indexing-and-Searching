package com.gryffindor.ir;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.store.FSDirectory;

public class Searcher {
	
	private static EnglishAnalyzer englishAnalyzer = new EnglishAnalyzer();
	//this method searches for the query in the index
	void search(String searchQuery, String indexDir, String rankingAlgo) throws IOException, ParseException {
		
		Path path = FileSystems.getDefault().getPath(indexDir);
		IndexReader indexReader= DirectoryReader.open(FSDirectory.open(path));
		IndexSearcher indexSearcher= new IndexSearcher(indexReader);
		TopScoreDocCollector topScoreDocCollector = TopScoreDocCollector.create(50);
		//Choosing the appropriate similarity algorithm to rank the documents
		Query query = new QueryParser("contents", englishAnalyzer).parse(searchQuery);
        if(rankingAlgo.equals("OK")) {
			indexSearcher.setSimilarity(new BM25Similarity());
		}else {
			indexSearcher.setSimilarity(new ClassicSimilarity());
		}
        //searching on the index for query with the top scope docuement collector
        indexSearcher.search(query, topScoreDocCollector);
        ScoreDoc[] hitscontent = topScoreDocCollector.topDocs().scoreDocs;
        if(hitscontent.length>=50) {
        	System.out.println(PrintUtils.makeFullLine(" -> Found more than 50 hits in search files.", " ", 176, "|"));
        	//System.out.println(" -> Found more than 50 hits in search files.");
        }else {
        	System.out.println(PrintUtils.makeFullLine("> Found " + hitscontent.length + " hits in search files <", " ", 176, "|"));
        	//System.out.println(" -> Found " + hitscontent.length + " hits in search files.");
        }
        //if the search contains no results exit
        if(hitscontent.length==0) {
        	System.exit(0);
        }
        System.out.println(PrintUtils.makeFullLine("> Listing top 10 Documents from the Search. <", " ", 176, "|"));
        PrintUtils.printRow();
        //printing the top 10 results from the search
        System.out.format(PrintUtils.getPrintFormat("header"),
        		"Rank","Title"+PrintUtils.getChars(19," "),"Score"+PrintUtils.getChars(3," "),"Time Stamp"+PrintUtils.getChars(6," "),"Path"+PrintUtils.getChars(40," ")+"|");
        PrintUtils.printRow();
        int rank;String title; String score;
        int dispLength=10;
        //if hit results are less than 10 decrease the hit content length
        if(hitscontent.length<10) dispLength=hitscontent.length;
        for(int i=0;i<dispLength;++i) {
          int documentId = hitscontent[i].doc;
          Document d = indexSearcher.doc(documentId);
          rank = i+1;
          title=d.get("title");
          if(title.equals("")) {
        	  title="Plain Text: "+d.get("fileName");
          }
          title=PrintUtils.formatTitle(title);
          score =PrintUtils.formatScore(hitscontent[i].score);
          //printing the details for the results
          System.out.format(PrintUtils.getPrintFormat("content"),rank,title,score,d.get("timeStamp"),d.get("path"));
        }
        PrintUtils.printRow();
        
	}
	
	
}
