package model;

import java.util.ArrayList;

import logic.CleanerText;

public class DocumentModel {

	private String mPath;
	private String mContent;
	private String mTitle;
	private ArrayList<String> mHeads;
	private ArrayList<String> mLinks;
	
	public DocumentModel(){
		mHeads = new ArrayList<String>();
		mLinks = new ArrayList<String>();
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
		pContent = CleanerText.stemming(pContent);
		pContent = CleanerText.deleteSpecialChars(pContent);
		pContent = CleanerText.removeStopwords(pContent);
		this.mContent = pContent;
	}

	public void setTitle(String pTitle) {
		pTitle = CleanerText.cleanAlphaNumeric(pTitle);
		pTitle = CleanerText.lower(pTitle);
		pTitle = CleanerText.stemming(pTitle);
		pTitle = CleanerText.deleteSpecialChars(pTitle);
		pTitle = CleanerText.removeStopwords(pTitle);
		this.mTitle = pTitle;
	}
	
	public void addHeads(String pHead) {
		pHead = CleanerText.cleanAlphaNumeric(pHead);
		pHead = CleanerText.lower(pHead);
		pHead = CleanerText.stemming(pHead);
		pHead = CleanerText.deleteSpecialChars(pHead);
		pHead = CleanerText.removeStopwords(pHead);
		this.mHeads.add(pHead);
	}
	
	public void addLinks(String pLink) {
		// NO SE SEPARA EN PALABRAS. Se desean preservar las frases
		pLink = CleanerText.cleanAlphaNumeric(pLink);
		pLink = CleanerText.lower(pLink);
		pLink = CleanerText.stemming(pLink);
		pLink = CleanerText.deleteSpecialChars(pLink);
		//pLink = CleanerText.removeStopwords(pLink);
		this.mLinks.add(pLink);
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

	public ArrayList<String> getHeads() {
		return mHeads;
	}

	public ArrayList<String> getLinks() {
		return mLinks;
	}
	
	
}
