import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


public class Airport {
	
	final static int minArrival = 0;
	final static int maxAirrival = 5;
	final static int minFuel = 1;
	final static int maxFuel = 25;
	
	private int simulationTime;
	private int crashCounter = 0;
	private int Landed = 0;
	private int tookOff = 0;
	
	private double lengthArrival = 0;
	private double lengthDeparture = 0;
	private double elpasedArrival = 0;
	private double elpasedDeparture = 0;
	
	private int runTime = 0;
	
	Airport() {
        this.simulationTime = 120;
    }

    Airport(int simulationTime) {
        this.simulationTime = simulationTime;
    }
	
    private int RandomInRange(int min, int max) {
    	Random randomNumbers = new Random();
		return min + randomNumbers.nextInt(max - min);
    }
    
    private void takeOff(DepartingPlane flight) //Take off Start 
    {
    
    	System.out.println("ID." + flight.getflightID() + " Took Off.");
    	this.inctookOff();
    	elpasedDeparture += flight.gettimeWait();
    }
    
	public void start() {
		
		PriorityQueue<ArrivingPlane> arrivingList = new PriorityQueue<ArrivingPlane>(simulationTime);
		LinkedList<DepartingPlane> departingList = new LinkedList<DepartingPlane>();
		AtomicInteger GUID = new AtomicInteger();

		System.out.println("<><><> Start <><><>");
        while ((runTime <= simulationTime) || !arrivingList.isEmpty()) {
        	int currentLanded = 0, currenttookOff = 0, currentcrashCounter = 0; //Current interval time
        	
        	System.out.println("Time: " + runTime); // up to five airplanes can arrive each interval time
        	
        	for (int i = 0; runTime <= simulationTime && i < RandomInRange(minArrival, maxAirrival); ++i) {
        		arrivingList.offer(new ArrivingPlane(GUID.incrementAndGet(), RandomInRange(minFuel, maxFuel)));
        		
        	} // Each runway can carry up to two events
        	
        	for (int i = 0; i < 2; ++i) {
        		for (int j = 0; j < arrivingList.size(); ++j) {
        			// crashCounter!
            		if (arrivingList.peek().getFuel() <= 0) {
            			System.out.println("The Plane" + arrivingList.poll().getflightID() + " ran out of fuel and there for crashed");
            			this.inccrashCounter();
            			++currentcrashCounter;
            		}
        		}
    			if (!arrivingList.isEmpty()) {
	        		if (arrivingList.peek().getFuel() < 2 || departingList.isEmpty()) {
	        			ArrivingPlane flight = arrivingList.poll();
	        			System.out.println("ID." + flight.getflightID() + " has arrived.");
	        			this.incLanded();
	        			departingList.add((new DepartingPlane(flight.getflightID())));
	        			++currentLanded;
	        			elpasedArrival += flight.gettimeWait();
	        		}
            		else {
            			takeOff(departingList.poll());
            			++currenttookOff;
            		}
    			}
        		else if (!departingList.isEmpty()) {
        			takeOff(departingList.poll());
        			++currenttookOff;
        		}
        		else { //Nothing well happen.
        			
        		}
    		}
        	
        	//// Show and print //// 
        	System.out.println("Size of landing queue: " + arrivingList.size());
        	
    		System.out.println("landing queue:"); // ID each plane in the land queue and the availablelity of its fuel time
        	for (ArrivingPlane flight : arrivingList) {
        		System.out.print("ID." + flight.getflightID() + ":" + flight.getFuel() + " ");
        	}
    		System.out.println();
    		System.out.println("Size of departing queue: " + departingList.size()); // ID each plane in the depart queue
    		
    		System.out.println("departing queue:");
        	for (DepartingPlane flight : departingList) {
        		System.out.print("ID." + flight.getflightID() + " ");
        	}
    		System.out.println();
    		// total number of successful landings during the interval
    		System.out.println("Counter of success landing: " + currentLanded);
    		// total number of successful departures for the interval
    		System.out.println("Current of success departs: " + currenttookOff);
    		// the number of crashes occurring in this interval
    		System.out.println("Current crashed: " + currentcrashCounter);
    		System.out.println("<><><><><><><><><><><>");
			
	    	lengthArrival += arrivingList.size(); //AVERAGE
	    	lengthDeparture += departingList.size();
        	for (ArrivingPlane flight : arrivingList) {
        		flight.timeWaiti();
        		flight.decFuel();
        	} //Track of the depart wait time
        	
        	for (DepartingPlane flight : departingList) {
        		flight.timeWaiti();
        		
        	} //Add Time
        	++runTime;
        } //Planes will land and take off until there is no plane reamaining for processing 
        while (!departingList.isEmpty()) {
        	System.out.println("Time: " + runTime);
        	for (int i = 0; i < 2; ++i) {
        		if (!departingList.isEmpty()) {
        			takeOff(departingList.poll());
        			
        		}
        	} // the size of the departing queue 
    		System.out.println("Size of depart queue: " + departingList.size());
    		// ID of planes in the depart queue
    		System.out.println("depart queue:");
        	for (DepartingPlane flight : departingList) {
        		System.out.print("ID." + flight.getflightID() + " ");
        	}
    		System.out.println();
	    	lengthDeparture += departingList.size();
        	++runTime;
        }
        
	}
	
	public void end()
	{
		System.out.println("<><><> Results <><><>");
		System.out.printf("Arrival Average queue length: %3.2f\n", getlengthArrival() / getrunTime());
		System.out.printf("Departure Average queue length: %3.2f\n", getlengthDeparture() / getrunTime());
		System.out.printf("Arrival Average elapsed time: %3.2f\n", getelpasedArrival() / getrunTime());
		System.out.printf("Departure Average elapsed time: %3.2f\n", getelpasedDeparture() / getrunTime());
		System.out.println("Arrivals successed: " + getLanded());
		System.out.println("Departure successed: " + gettookOff());
		System.out.println("Number of crashed planes: " + getcrashCounter());
	}

	public int getrunTime() {
		return runTime;
	}

	public void setrunTime(int runTime) {
		this.runTime = runTime;
	}

	public int getcrashCounter() {
		return crashCounter;
	}

	public void setcrashCounter(int crashCounter) {
		this.crashCounter = crashCounter;
	}

	public void inccrashCounter() {
		this.crashCounter++;
	}
	
	public int getLanded() {
		return Landed;
	}

	public void setLanded(int Landed) {
		this.Landed = Landed;
	}
	
	public void incLanded() {
		this.Landed++;
	}
	
	public int gettookOff() {
		return tookOff;
	}

	public void settookOff(int tookOff) {
		this.tookOff = tookOff;
	}
	
	public void inctookOff() {
		this.tookOff++;
	}
	public double getlengthArrival() {
		return lengthArrival;
	}

	public void setlengthArrival(double lengthArrival) {
		this.lengthArrival = lengthArrival;
	}

	public double getlengthDeparture() {
		return lengthDeparture;
	}

	public void setlengthDeparture(double lengthDeparture) {
		this.lengthDeparture = lengthDeparture;
	}

	public double getelpasedArrival() {
		return elpasedArrival;
	}

	public void setelpasedArrival(double elpasedArrival) {
		this.elpasedArrival = elpasedArrival;
	}

	public double getelpasedDeparture() {
		return elpasedDeparture;
	}

	public void setelpasedDeparture(double elpasedDeparture) {
		this.elpasedDeparture = elpasedDeparture;
	}

}
