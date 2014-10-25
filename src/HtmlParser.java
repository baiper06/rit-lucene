import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
public class HtmlParser {
 
  public void run() {
 
	Document doc;
	try {
 
		File input = new File("/home/bairon/workspace/rit-lucene/doc/coleccion/Costa_Rica.htm");
		doc = Jsoup.parse(input, null);
 
		// get page title
		String title = doc.title();
		System.out.println("title : " + title);
		
		// get page content
		String content = doc.text();
		System.out.println("content : " + content);
		
		/*		
		PrintWriter writer = new PrintWriter("/home/bairon/workspace/rit-lucene/filename.txt", "UTF-8");
		writer.println(content);
		writer.close();
		*/		
		
		// get all heads
		Elements heads = doc.select("h1, h2, h3, h4, h5, h6");
		for (Element head : heads) {
			System.out.println("head : " + head.text());
		}
		
		// get all links
		Elements links = doc.select("a[href]");
		for (Element link : links) {
			System.out.println("link : " + link.attr("title"));
		}

	} catch (IOException e) {
		e.printStackTrace();
	}
 
  }
 
}