public class Simulate {

	public static void main(String[] args) { // Start 
		Airport simulation = new Airport(100);
		simulation.start();
		simulation.end();
		System.out.println("End of simulation");
	}

}
