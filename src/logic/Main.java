package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.lucene.queryparser.classic.ParseException;


public class Main {

	public static void main(String[] args){

		Manager manager = new Manager( args[0] );
		try {
			manager.index();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		boolean esc = false;
		while (!esc){
			//  prompt the user to enter their name
		    System.out.print("\nEscriba 'campo/consulta' para buscar o 'C' para salir $ ");
		 
		    //  open up standard input
		    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		    String input = null;
		    try {
		    	input = br.readLine();
		    } catch (IOException ioe) {
		    }
		    if( input.compareTo("C") != 0 ){
		    	String[] query = input.split("/");
		    	if (query.length == 2){
					try {
						manager.search(query[0], query[1]);
					} catch (IOException | ParseException e) {
						e.printStackTrace();
					}
		    	} else{
		    		System.out.println("Formato invalido");
		    	}

		    } else{
		    	System.out.print("\nHasta pronto!");
		    	esc = true;
		    }
		}
		
	}
}
