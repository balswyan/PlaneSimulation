public class Airplane {
	private int flightID = 0;
    private int timeWait = 0;
    
    public int getflightID() {
		return flightID;
	}

	public void setflightID(int flightID) {
		this.flightID = flightID;
	}

	public int gettimeWait() {
		return timeWait;
	}

	public void settimeWait(int timeWait) {
		this.timeWait = timeWait;
	}
	
	public void timeWaiti() {
		this.timeWait++;
	}
	
}
