package logic;

import java.text.Normalizer;

public class CleanerText {

	public static String cleanAlphaNumeric( String pText ) {
		return pText.replaceAll("[^0-9a-zA-ZáéíóúüñÁÉÉÍÓÚÜÑ]+", " ");
	}
	
	public static String stemming( String pText ) {
		//se aplica stemming si la palabra son solo letras
		return pText;
	}
	
	public static String lower( String pText ) {
		return pText.toLowerCase();
	}
	
	public static String deleteSpecialChars( String pText ) {
		pText = Normalizer.normalize(pText, Normalizer.Form.NFD);
		pText = pText.replaceAll("[^\\p{ASCII}]", "");
		return pText;
	}

}


