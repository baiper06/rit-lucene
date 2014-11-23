package logic;

import java.io.IOException;
import java.io.StringReader;
import java.text.Normalizer;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.es.SpanishLightStemFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class CleanerText {

	private static SpanishAnalyzer mAnalyzer = new SpanishAnalyzer();
	
	public static String cleanAlphaNumeric( String pText ) {
		return pText.replaceAll("[^0-9a-zA-ZáéíóúüñÁÉÉÍÓÚÜÑ]+", " ");
	}
	
	public static String stemming( String pText ) {
        TokenStream tokenStream = new StandardTokenizer( new StringReader(pText) );
        StringBuilder sb = new StringBuilder();
        tokenStream = new SpanishLightStemFilter( tokenStream );
        CharTermAttribute token = tokenStream.getAttribute(CharTermAttribute.class);
		try {
			tokenStream.reset();
			while ( tokenStream.incrementToken() ){
			    if (sb.length() > 0) 
			    {
			        sb.append(" ");
			    }
			    sb.append(token.toString());
			}
			tokenStream.end();
			tokenStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return sb.toString();
	}
	
	public static String lower( String pText ) {
		return pText.toLowerCase();
	}
	
	public static String deleteSpecialChars( String pText ) {
		pText = Normalizer.normalize(pText, Normalizer.Form.NFD);
		pText = pText.replaceAll("[^\\p{ASCII}]", "");
		return pText;
	}
	
	public static String removeStopwords(String pText) {
        TokenStream tokenStream = new StandardTokenizer( new StringReader(pText) );
        StringBuilder sb = new StringBuilder();
        tokenStream = new StopFilter( tokenStream, mAnalyzer.getStopwordSet() );
        CharTermAttribute token = tokenStream.getAttribute(CharTermAttribute.class);
		try {
			tokenStream.reset();
			while ( tokenStream.incrementToken() ){
			    if (sb.length() > 0) 
			    {
			        sb.append(" ");
			    }
			    sb.append(token.toString());
			}
			tokenStream.end();
			tokenStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return sb.toString();
    }

}