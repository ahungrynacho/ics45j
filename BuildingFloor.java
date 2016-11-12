// Kimberly Chou 80176941
// Brian Huynh 57641580

public class BuildingFloor {
	private int[] totalDestinationRequests; // total passengers that want to go to the ith floor
	private int[] arrivedPassengers; // passengers arrived from ith floor
	private int[] passengerRequests; // passengers who currently want to go to the ith floor
	private int	approachingElevator;
	// If an elevator starting from floor 0 assigned to pickup people on floor 4 is scheduled chronologically
	// ahead of a pickup request from floor 2 towards floor 4, then the elevator will skip floor 2.
	// A different elevator will pickup the people requesting to go from floor 2 to floor 4.
	// By default, an elevator will load all passengers requesting to go up first.
	
	public BuildingFloor() {
		totalDestinationRequests = new int[5];
		arrivedPassengers = new int[5];
		passengerRequests = new int[5];
		approachingElevator = -1;
	}
	
	public int getTotalDestinationRequests(int dest) {
		return this.totalDestinationRequests[dest];
	}
	
	// Get number of passengers that have arrived from an elevator to this floor
	public int getArrivedPassengers(int src) {
		return this.arrivedPassengers[src];
	}
	
	// Get number of passenger that want to travel to the ith floor
	public int getPassengerRequests(int dest) {
		return this.passengerRequests[dest];
	}
	
	// Get elevator ID of elevator approaching floor
	public int getApproachingElevator() {
		return approachingElevator;
	}
	
	// Set total number of requests to this floor
	public void setTotalDestinationRequests(int floor, int outgoingPassengers) {
		this.totalDestinationRequests[floor] += outgoingPassengers;
	}
	
	// Set number of passengers that have arrived from an elevator to this floor
	public void setArrivedPassengers(int floor, int incomingPassengers) {
		this.arrivedPassengers[floor] += incomingPassengers;
	}
	
	// Set number of passengers from this floor that want to travel to the ith floor
	public void setPassengerRequests(int floor, int passengerRequests) {
		this.passengerRequests[floor] = passengerRequests;
	}
	
	// Set elevator ID of elevator approaching floor
	public void setApproachingElevator(int approachingElevator) {
		this.approachingElevator = approachingElevator;
	}
	
}
