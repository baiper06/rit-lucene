package model;

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
		/*
		se extrae palabras alfanumericas
		se convierten las letras a minusculas
		se aplica stemming si la palabra son solo letras
		eliminar tildes; preservar enne
		eliminar stopwords (Lucene lo aplica automaticamente)
		*/
		this.mContent = pContent;
	}

	public void setTitle(String pTitle) {
		/*
		se extrae palabras alfanumericas
		se convierten las letras a minusculas
		se aplica stemming si la palabra son solo letras
		eliminar tildes; preservar enne
		eliminar stopwords (Lucene lo aplica automaticamente)
		*/
		this.mTitle = pTitle;
	}
	
	public void addHeads(String pHeads) {
		/*
		se extrae palabras alfanumericas
		se convierten las letras a minusculas
		se aplica stemming si la palabra son solo letras
		eliminar tildes; preservar enne
		eliminar stopwords (Lucene lo aplica automaticamente)
		*/
		this.mHeads = this.mHeads+" "+pHeads;
	}
	
	public void addLinks(String pLinks) {
		/*
		NO SE SEPARA EN PALABRAS. Se desean preservar las frases.
		eliminar los caracteres que no sean letras o números
		se convierten las letras a minúsculas
		eliminar tildes; preservar eñe
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
