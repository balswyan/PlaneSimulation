public class ArrivingPlane extends Airplane  implements Comparable<ArrivingPlane> {
	private int fuel;
	
	public ArrivingPlane(int flightID, int fuel) {

		this.setflightID(flightID);
		this.setFuel(fuel);
	}
	
	public int getFuel() {
		return fuel;
	}

	public void setFuel(int fuel) {
		this.fuel = fuel;
	}
	
	public void decFuel() {
		this.fuel--;
	}
	
	@Override
	public int compareTo(ArrivingPlane that) {
    	int thisFuel = this.getFuel();
    	int thatFuel = that.getFuel();

    	if (thisFuel - thatFuel == 0)
    	return that.gettimeWait() - this.gettimeWait();
    	return thisFuel - thatFuel;
	}
	
}
