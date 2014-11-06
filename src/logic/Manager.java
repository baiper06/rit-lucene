package logic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import model.DocumentModel;

import org.apache.lucene.analysis.es.SpanishAnalyzer;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
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
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;


public class Manager{
	
	public static final String FOLDER_INDEX 	= "/index";
	public static final String FOLDER_COLECTION = "/coleccion";
	public static final String FOLDER_OUTPUTS	= "/outputs";
	public static final int LEN_HITS			= 20;
	
	
	private String 		mFolderIndex;
	private String 		mFolderColection;
	private String 		mFolderOutputs;
	private HtmlCreator mHtmlCreator;
	private Directory 	mIndex;
	private SpanishAnalyzer mAnalyzer 	= new SpanishAnalyzer();
	
	
	public Manager( String pFolder ){
		mFolderColection = this.savePath( pFolder+FOLDER_COLECTION );
		mFolderIndex	 = this.savePath( pFolder+FOLDER_INDEX );
		mFolderOutputs	 = this.savePath( pFolder+FOLDER_OUTPUTS );
		mHtmlCreator 	 = new HtmlCreator(mFolderOutputs, mFolderColection);
	}
	
	private void addDocs(IndexWriter pWriter) throws IOException {
		
		HtmlParser parser = new HtmlParser(mFolderColection);
		ArrayList<DocumentModel> docs = parser.getDocuments();
		for( int i=0; i<docs.size(); i++){
			  System.out.println(docs.get(i).getPath());
			  Document doc = new Document();
			  // TODO: investigar cuando usar TextField() o StringField()
			  doc.add(new TextField("content", 	docs.get(i).getContent(), 	Field.Store.YES));
			  doc.add(new TextField("heads", 	docs.get(i).getHeads(), 	Field.Store.YES));
			  doc.add(new TextField("links", 	docs.get(i).getLinks(), 	Field.Store.YES));
			  doc.add(new TextField("title", 	docs.get(i).getTitle(), 	Field.Store.YES));
			  doc.add(new TextField("path", 	docs.get(i).getPath(), 		Field.Store.YES));
			  pWriter.addDocument(doc);
		}
	}

	
	public void index() throws IOException{
		mIndex = FSDirectory.open(new File(mFolderIndex));
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_1, mAnalyzer);
		if (!DirectoryReader.indexExists(mIndex)) {
			System.out.println("\nIndexando...");
			IndexWriter writer = new IndexWriter(mIndex, config);
			addDocs(writer);
			writer.close();
		} else {
			System.out.println("\nUsando index previamente creado...");
		}
	}
	
	public void search( String pField, String pQuery ) throws IOException, ParseException{
		System.out.println("Buscando '"+pQuery+"' en el campo '"+pField+"'...");
		
		// limpiar query
		pQuery = CleanerText.cleanAlphaNumeric(pQuery);
		pQuery = CleanerText.lower(pQuery);
		
		//	The \"title\" arg specifies the default field to use when no field is explicitly specified in the query
		Query q = new QueryParser(pField, mAnalyzer).parse(pQuery);
		
		// Searching code
		int hitsPerPage = 20;
	    IndexReader reader = DirectoryReader.open(mIndex);
	    IndexSearcher searcher = new IndexSearcher(reader);
	    TopScoreDocCollector collector = TopScoreDocCollector.create(LEN_HITS, true);
	    searcher.search(q, collector);
	    ScoreDoc[] hits = collector.topDocs().scoreDocs;
	    
	    //	Code to display the results of search
	    System.out.println("Se encontraron " + hits.length + " resultados");
	    /*
	    for(int i=0;i<hits.length;++i){
	      int docId = hits[i].doc;
	      Document d = searcher.doc(docId);
	      System.out.println((i + 1) + ". " + d.get("title"));
	    }
	    */
	    mHtmlCreator.setAttr(pQuery, hits, searcher);
	    mHtmlCreator.createHtml();
	    // reader can only be closed when there is no need to access the documents any more
	    reader.close();
	}
	
	
	private String savePath( String pFolder ){
		File theDir = new File(pFolder);
		if (!theDir.exists()) {
		    try{
		        theDir.mkdir();
		     } catch(SecurityException se){
		     } 
		  }
		return pFolder;
	}
	
}