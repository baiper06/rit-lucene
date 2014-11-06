package model;

import logic.CleanerText;

public class DocumentModel {

	private String mPath;
	private String mContent;
	private String mTitle;
	private String mHeads;
	private String mLinks;
	
	public DocumentModel(){
		mHeads = "";
		mLinks = "";
	}
	
	public String toString(){
		return "\nContent: "+mContent + "\nTitle:   "+mTitle+ "\nHeads:   "+mHeads+	"\nLinks:   "+mLinks;
	}
	
	public void setPath(String pPath) {
		this.mPath = pPath;
	}

	public void setContent(String pContent) {
		pContent = CleanerText.cleanAlphaNumeric(pContent);
		pContent = CleanerText.lower(pContent);
		// se aplica stemming si la palabra son solo letras (Lucene lo aplica automaticamente)
		pContent = CleanerText.deleteSpecialChars(pContent);
		// eliminar stopwords (Lucene lo aplica)
		this.mContent = pContent;
	}

	public void setTitle(String pTitle) {
		pTitle = CleanerText.cleanAlphaNumeric(pTitle);
		pTitle = CleanerText.lower(pTitle);
		// se aplica stemming si la palabra son solo letras (Lucene lo aplica automaticamente)
		pTitle = CleanerText.deleteSpecialChars(pTitle);
		// eliminar stopwords (Lucene lo aplica)
		this.mTitle = pTitle;
	}
	
	public void addHeads(String pHeads) {
		pHeads = CleanerText.cleanAlphaNumeric(pHeads);
		pHeads = CleanerText.lower(pHeads);
		// se aplica stemming si la palabra son solo letras (Lucene lo aplica automaticamente)
		pHeads = CleanerText.deleteSpecialChars(pHeads);
		// eliminar stopwords (Lucene lo aplica)
		this.mHeads = this.mHeads+" "+pHeads;
	}
	
	public void addLinks(String pLinks) {
		// NO SE SEPARA EN PALABRAS. Se desean preservar las frases  (Lucene lo aplica)
		pLinks = CleanerText.cleanAlphaNumeric(pLinks);
		pLinks = CleanerText.lower(pLinks);
		pLinks = CleanerText.deleteSpecialChars(pLinks);
		/*
		
		*/
		this.mLinks = this.mLinks+" "+pLinks;
	}

	public String getPath() {
		return mPath;
	}
	
	public String getContent() {
		return mContent;
	}

	public String getTitle() {
		return mTitle;
	}

	public String getHeads() {
		return mHeads;
	}

	public String getLinks() {
		return mLinks;
	}
	
	
}
