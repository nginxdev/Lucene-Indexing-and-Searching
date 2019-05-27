#Programming Assignment Document


Indexing and Searching with Lucene


Command to run the project:
java -jar lucene_searcher.jar "search directory" "index directory" "algorithm" "query"


example:
java -jar lucene_searcher.jar "search/text" "index/text" "ok" "region"

#Introduction:
A java application is written which takes the Search file directory, Index directory, Search algorithm and search query to fetch the hit documents using the Lucene Core and Lucene libraries.

Libraries Used:
Lucene 7.5.0:
1. Lucene Analyzers Common 7.5.0
2. Lucene Core 7.5.0
3. Lucene Highlighter 7.5.0
4. Lucene Query Parser 7.5.0

Jsoup 1.11.3

Custom Library implemented:
Print utils custom class is written to format the output to the tabular format.

#Information on the code:
Main.java
This class contains the argument parser and core application flow and parsing and passing the intermediate result between the method in the application flow.
Indexer.java
This class fetches the files from the search directory and checks if already exists and indexes it to the index directory. It uses the “englishAnayzer” to create an index which performs stop word elimination and stemming.
Jsoup library is used to parse the HTML file c\`ontents.
Searcher.java
This class chooses between the Okapi and Vector space models to perform the search. It searches for query terms and ranks the documents. Top 10 documents are printed rank-wise.
FileListHandler.java
This class fetches the Information on Existing index. Checks if the file existing in the index. Stores the information on the indexed files.
PrintUtils.java
Custom print functions are implemented to beautify the console output.

Output:
The output is formatted into a table and printed:
It contains the Rank, Title, Score, Timestamp, Path of the Result file.
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
|------------------------------------------------------------------Starting Search using OK For Query:region----------------------------------.--------------------------|
----------------------------------------------------------------------------------------------------------------------------------------.---------------------------------
| > Found 44 hits in search files < |
| > Listing top 10 Documents from the Search. < |
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
| Rank | Title | Score | Time Stamp | Path |
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
| 1 | Plain Text: Oceania.txt | 3.89937300 | 10/26/2018-21:51:12 | search\text\wikipedia\Oceania.txt
| 2 | Plain Text: Los Angeles.txt | 3.51274280 | 10/26/2018-21:51:12 | search\text\wikipedia\Los Angeles.txt
| 3 | Plain Text: Bush_1991.txt | 3.37483880 | 10/26/2018-21:51:12 | search\text\sotu\Bush_1991.txt
| 4 | Plain Text: Lemon.txt | 3.35414580 | 10/26/2018-21:51:12 | search\text\wikipedia\Lemon.txt
| 5 | Plain Text: Bush_2005.txt | 3.31650110 | 10/26/2018-21:51:12 | search\text\sotu\Bush_2005.txt
| 6 | Plain Text: Tornado.txt | 3.23489500 | 10/26/2018-21:51:12 | search\text\wikipedia\Tornado.txt
| 7 | Plain Text: Bush_2008.txt | 2.99854140 | 10/26/2018-21:51:12 | search\text\sotu\Bush_2008.txt
| 8 | Plain Text: nafta-4.txt | 2.96556740 | 10/26/2018-21:51:12 | search\text\legal\nafta-4.txt
| 9 | Plain Text: Bush_2003.txt | 2.76247930 | 10/26/2018-21:51:12 | search\text\sotu\Bush_2003.txt
| 10 | Plain Text: Clinton_1994.txt | 2.74361180 | 10/26/2018-21:51:12 | search\text\sotu\Clinton_1994.txt
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
