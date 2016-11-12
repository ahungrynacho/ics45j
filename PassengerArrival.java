// Kimberly Chou 80176941
// Brian Huynh 57641580

public class PassengerArrival {
	private int numPassengers; // number of passengers for this specific behavior
	private int destinationFloor; // desired destination of these passengers
	private int timePeriod; // periodic time passengers request this behavior
	private int expectedTimeOfArrival; // simulated time when next batch enters simulation
	
	// Constructors
	public PassengerArrival() { // Default
	}
	
	public PassengerArrival(int numPassengers, int destinationFloor, int timePeriod, int expectedTimeOfArrival) {
		this.numPassengers = numPassengers;
		this.destinationFloor = destinationFloor;
		this.timePeriod = timePeriod;
		this.expectedTimeOfArrival = expectedTimeOfArrival;
	}
	
	// Get number of passengers
	public int getNumPassengers() {
		return numPassengers;
	}
	
	// Get desired destination
	public int getDestinationFloor() {
		return destinationFloor;
	}
	
	// Get periodic time passengers request this behavior
	public int getTimePeriod() {
		return timePeriod;
	}
	
	// Get expected time when next batch enter simulation
	public int getExpectedTimeOfArrival() {
		return expectedTimeOfArrival;
	}
	
	// Set number of passengers
	public void setNumPassengers(int numPassengers) {
		this.numPassengers = numPassengers;
	}
	
	// Set desired destination
	public void setDestinationFloor(int destinationFloor) {
		this.destinationFloor = destinationFloor;
	}

	// Set periodic time passengers request this behavior
	public void setTimePeriod(int timePeriod) {
		this.timePeriod = timePeriod;
	}
	
	// Set expected time when next batch enter simulation
	public void setExpectedTimeOfArrival(int expectedTimeOfArrival) {
		this.expectedTimeOfArrival = expectedTimeOfArrival;
	}
	
}
