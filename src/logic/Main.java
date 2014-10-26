package logic;

import java.io.IOException;
import org.apache.lucene.queryparser.classic.ParseException;


public class Main {

	public static void main(String[] args){
		
		Manager manager = new Manager("/home/bairon/workspace/rit-lucene/doc/coleccion");
		try {
			manager.index();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			manager.search("content", "Costa");
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

	}
}
