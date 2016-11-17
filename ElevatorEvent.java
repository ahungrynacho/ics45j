// Kimberly Chou 80176941
// Brian Huynh 57641580

public class ElevatorEvent {

	private int destination; // floor elevator needs to move to
	private int expectedArrival; // expected simulated time that the elevator will spend on that floor (including loading+unloading people)
	private int source;
	
	public ElevatorEvent(int src, int dest) {
		this.source = src;
		this.destination = dest;
		this.expectedArrival = -1;
	}
	// Get destination
	
	public int getDestination() {
		return destination;
	}
	
	// Get expected time spent on floor including loading and unloading people
	public int getExpectedArrival() {
		return expectedArrival;
	}
	
	// Set destination 
	public void setDestination(int destination) {
		this.destination = destination;
	}
	
	// Set expected time spent on floor including loading and unloading people
	public void setExpectedArrival(int floorTravelTime, int loadUnloadTime) {
		this.expectedArrival = (Math.abs(destination - source) * floorTravelTime) + loadUnloadTime;
	}
}
