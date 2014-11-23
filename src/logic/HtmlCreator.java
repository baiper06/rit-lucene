package logic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;

public class HtmlCreator {
	
	public static final int CNT_CHARS = 200;
	
	private String mPathOutputs;
	private String mQuery;
	private String mDatatime;
	private String mPathColection;
	private String mPathFile;
	private ScoreDoc[] mHits;
	private IndexSearcher mSearcher;
	
	public HtmlCreator( String pPathOutputs, String pPathColection ){
		mPathOutputs = pPathOutputs;
		mPathColection = pPathColection;
	}
	
	public void setAttr( String pQuery, ScoreDoc[] pHits, IndexSearcher pSearcher ){
		Date dateNow = new Date( );
		SimpleDateFormat formatPrint = new SimpleDateFormat ("E yyyy.MM.dd 'a las' hh:mm:ss");
		SimpleDateFormat formatFile  = new SimpleDateFormat ("yyyyMMdd'_'hhmmss");
	      
		mQuery 		= pQuery;
		mDatatime 	= formatPrint.format(dateNow);
		mHits 		= pHits;
		mSearcher 	= pSearcher;
		mPathFile	= mPathOutputs +"/"+pQuery+"_"+formatFile.format(dateNow)+".html";
	}

	 public void createHtml(){
		 String head;
		 String header;
		 String content;
		 String footer;
		 String html;
		 
		 head = "<head>" + 
				 " <meta charset='utf-8'>" + 
				 " <meta http-equiv='X-UA-Compatible' content='IE=edge'>" + 
				 " <meta name='viewport' content='width=device-width, initial-scale=1'>" + 
				 " <title>Ranking</title>" + 
				 " <link href='css/bootstrap.min.css' rel='stylesheet'>" + 
				 " <link href='css/bootstrap-theme.min.css' rel='stylesheet'>" + 
				 " </head>";
		 
		 header= "<br></br>" + 
				 "<div class='row'>" + 
				 " <div class='col-sm-8 col-sm-offset-2'>" + 
				 " <div class='well'>" + 
				 " <div class='col-sm-7'>" + 
				 " <p>"+ this.mDatatime +"</p>" + 
				 " </div>" + 
				 " <div class='col-sm-5'>" + 
				 " </div><br></br><br></br>" + 
				 " <h1 class='text-center'>"+this.mQuery+" <small>(1-"+(mHits.length)+" resultados)</small></h1><br />" + 
				 " <p class='text-right'><a href='"+this.mPathColection+"' class='btn btn-default' target='_blank'>Ir a la colecci&oacute;n</a></p>" + 
				 " </div>" + 
				 " </div>" + 
				 "</div>";
		 
		content="<div class='row'>";
		
		for( int i=0; i<mHits.length; i++ ){
			
			 int docId = mHits[i].doc;
			 Document d = null;
			 try {
				d = mSearcher.doc(docId);
			 } catch (IOException e) {
				e.printStackTrace();
			 }
			 String cntnt = d.get("contenido");
			 
			 // truncar contenido
			 if( cntnt.length() > CNT_CHARS ){
				 cntnt = cntnt.substring(0, CNT_CHARS)+"...";
			 }
			 /*
			 cntnt = "";
			 for(int x=0; x<d.getFields("referencias").length; x++){
				 cntnt += "<br />"+d.getFields("referencias")[x].stringValue();
			 }
			 */
			 String itm= "<div class='col-sm-8 col-sm-offset-2'>" 	+ 
						 " <div class='panel panel-default'>" 		+ 
						 " <div class='panel-heading'>" 			+ 
						 " <h3 class='panel-title'>" 				+ 
						 " <div class='col-sm-8'>" 					+ 
						 " <span class='badge'>"+(i+1)+"</span> "+d.get("titulo")+ 
						 " </div>" 									+ 
						 " <div class='col-sm-4'></div>" 			+ 
						 " <p class='text-right'>" 					+ 
						 " <span class='label label-primary'>id:" +mHits[i].doc+"</span>" + 
						 " <span class='label label-success'>sim:"+mHits[i].score+"</span>" + 
						 " </p>"  									+ 
						 " </h3>"  									+ 
						 " </div>"  								+ 
						 " <div class='panel-body'>" 				+ 
						 " " + cntnt  								+ 
						 " <p class='text-right'><a href='"+d.get("path")+"' class='btn btn-default' target='_blank'>Ir al archivo</a></p>" + 
						 " </div>"  								+ 
						 " </div>" 									+ 
						 "</div>";
			 content = content + itm;
		}
		
		content = content + "</div>";
				 		 
		footer = "<div class='footer'>" 	+ 
				 " <div class='container'>" + 
				 " <p class='text-muted text-center'>Karla Madrigal & Bairon P&eacute;rez. &copy; 2014</p>" + 
				 " </div>" 					+ 
				 "</div>";
				 
		 html  = "<!DOCTYPE html>"				+ 
				 "<html lang='es'>" 			+ 
					 head 						+ 
					 "<body role='document'>"	+ 
						 header 				+ 
						 content 				+ 
						 footer 				+ 
					 "</body>" 					+ 
				 "</html>";
		
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(mPathFile, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		writer.print(html);
		writer.close();
		 
		System.out.println( "Se ha guardado el ranking en '" + mPathFile + "'" );
		
	 }
		 
}
