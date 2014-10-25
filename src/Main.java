

public class Main {

	public static void main(String[] args){
		
		Manager manager = new Manager();
		manager.run(args);
		
		HtmlParser parser = new HtmlParser();
		parser.run();
	}
}
