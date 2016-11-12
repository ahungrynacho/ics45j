// Kimberly Chou 80176941
// Brian Huynh 57641580

public class BuildingFloor {
	private int[] totalDestinationRequests;
	private int[] arrivedPassengers;
	private int[] passengerRequests;
	private int	approachingElevator;
	
	public BuildingFloor() {
		totalDestinationRequests = new int[5];
		arrivedPassengers = new int[5];
		passengerRequests = new int[5];
		approachingElevator = -1;
	}
	
	// Get total number of requests to this floor
	public int[] getTotalDestinationRequests() {
		return totalDestinationRequests;
	}
	
	// Get number of passengers that have arrived from an elevator to this floor
	public int[] getArrivedPassengers() {
		return arrivedPassengers;
	}
	
	// Get number of passenger that want to travel to the ith floor
	public int[] getPassengerRequests() {
		return passengerRequests;
	}
	
	// Get elevator ID of elevator approaching floor
	public int getApproachingElevator() {
		return approachingElevator;
	}
	
	// Set total number of requests to this floor
	public void setTotalDestinationRequests(int floor, int totalRequests) {
		this.totalDestinationRequests[floor] = totalRequests;
	}
	
	// Set number of passengers that have arrived from an elevator to this floor
	public void setArrivedPassengers(int floor, int arrivedPassengers) {
		this.arrivedPassengers[floor] = arrivedPassengers;
	}
	
	// Set number of passenger that want to travel to the ith floor
	public void setPassengerRequests(int floor, int passengerRequests) {
		this.passengerRequests[floor] = passengerRequests;
	}
	
	// Set elevator ID of elevator approaching floor
	public void setApproachingElevator(int approachingElevator) {
		this.approachingElevator = approachingElevator;
	}
	
}
