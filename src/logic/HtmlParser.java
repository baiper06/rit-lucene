package logic;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import model.DocumentModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
public class HtmlParser {
	
	private String mFolder;
	
	public HtmlParser( String pFolder ){
		mFolder = pFolder;
	}
 
  public ArrayList<DocumentModel> getDocuments() {
		File folder = new File(mFolder);
		File[] listOfFiles = folder.listFiles();
		ArrayList<DocumentModel> docs = new ArrayList<DocumentModel>();
		  
		for (int i = 0; i < listOfFiles.length; i++) {
		  File file = listOfFiles[i];
		  
		  if ( file.isFile() ) {
			  DocumentModel doc = getDocument(file);
			  docs.add( doc );
		  } 
		}
		return docs;
  }
		
public DocumentModel getDocument( File pFile ) {
		Document doc = null;
		try {
			doc = Jsoup.parse(pFile, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		DocumentModel doc_model = new DocumentModel();
		
		doc_model.setPath(pFile.getName());
		// get page title
		String title = doc.title();
		doc_model.setTitle(title);
		// get page content
		String content = doc.text();
		doc_model.setContent(content);
		// get all heads
		Elements heads = doc.select("h1, h2, h3, h4, h5, h6");
		for (Element head : heads) {
			doc_model.addHeads( head.text() );
		}
		// get all links
		Elements links = doc.select("a[href]");
		for (Element link : links) {
			doc_model.addLinks( link.attr("title") );
		}		
		
		//System.out.println(doc_model);
		
		return doc_model;
  }
 
}