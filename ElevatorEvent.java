// Kimberly Chou 80176941
// Brian Huynh 57641580

public class ElevatorEvent {

	private int destination; // floor elevator needs to move to
	private int expectedArrival; // expected simulated time that the elevator will spend on that floor (including loading+unloading people)

	
	public ElevatorEvent(int dest, int expArr) {
		this.destination = dest;
		this.expectedArrival = expArr;
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
	public void setExpectedArrival(int expectedArrival) {
		this.expectedArrival = expectedArrival;
	}
}
