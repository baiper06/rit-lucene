package logic;
import java.io.IOException;




import java.util.ArrayList;

import model.DocumentModel;

import org.apache.lucene.analysis.es.SpanishAnalyzer;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;


public class Manager{
	
	private String mFolder;
	private SpanishAnalyzer mAnalyzer 	= new SpanishAnalyzer();;
	private Directory mIndex 			= new RAMDirectory();
	
	public Manager( String pFolder ){
		mFolder = pFolder;
	}
	
	private void addDocs(IndexWriter pWriter) throws IOException {
		
		HtmlParser parser = new HtmlParser(mFolder);
		ArrayList<DocumentModel> docs = parser.getDocuments();
		for( int i=0; i<docs.size(); i++){
			  System.out.println(docs.get(i).getPath());
			  Document doc = new Document();
			  // TODO: investigar cuando usar TextField() o StringField()
			  doc.add(new TextField("content", 	docs.get(i).getContent(), 	Field.Store.YES));
			  doc.add(new TextField("heads", 	docs.get(i).getHeads(), 	Field.Store.YES));
			  doc.add(new TextField("links", 	docs.get(i).getLinks(), 	Field.Store.YES));
			  doc.add(new TextField("title", 	docs.get(i).getTitle(), 	Field.Store.YES));
			  pWriter.addDocument(doc);
		}
	}

	
	public void index() throws IOException{
		System.out.println("\nIndexando...");
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_1, mAnalyzer);
		IndexWriter writer = new IndexWriter(mIndex, config);
		addDocs(writer);
		writer.close();
	}
	
	public void search( String pField, String pQuery ) throws IOException, ParseException{
		System.out.println("\nBuscando '"+pQuery+"' en el campo '"+pField+"'...");
		//	The \"title\" arg specifies the default field to use when no field is explicitly specified in the query
		Query q = new QueryParser(pField, mAnalyzer).parse(pQuery);
		
		// Searching code
		int hitsPerPage = 10;
	    IndexReader reader = DirectoryReader.open(mIndex);
	    IndexSearcher searcher = new IndexSearcher(reader);
	    TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
	    searcher.search(q, collector);
	    ScoreDoc[] hits = collector.topDocs().scoreDocs;
	    
	    //	Code to display the results of search
	    System.out.println("\nSe encontraron " + hits.length + " resultados");
	    for(int i=0;i<hits.length;++i){
	      int docId = hits[i].doc;
	      Document d = searcher.doc(docId);
	      System.out.println((i + 1) + ". " + d.get("title"));
	    }
	    
	    // reader can only be closed when there is no need to access the documents any more
	    reader.close();
	}
}