// Kimberly Chou 80176941
// Brian Huynh 57641580

import java.util.ArrayList;

public class Elevator implements Runnable {
	private int elevatorID;
	private int currentFloor;
	private int numPassengers;
	private int totalLoadedPassengers;
	private int totalUnloadedPassengers;
	private ArrayList<ElevatorEvent> moveQueue;
	private int[] passengerDestinations;
	private BuildingManager manager;
	
	// Constructor
	Elevator(int elevatorID, BuildingManager manager) {
		this.elevatorID = elevatorID;
		this.manager = manager;
	}
	
	// TODO: Other methods that help perform Elevator behavior such as creating	
	// ElevatorEvents, managing the moveQueue, and updating its current state.
	
	public void run() {
		
	}
}
