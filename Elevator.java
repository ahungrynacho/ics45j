// Kimberly Chou 80176941
// Brian Huynh 57641580

import java.util.ArrayList;

public class Elevator implements Runnable {
	private long simulatedTime = 100; //milliseconds
	private long loadUnloadTime = 10 * simulatedTime;
	private long travelTimePerFloor = 5 * simulatedTime;
	private int elevatorID;
	private int currentFloor;
	private int numPassengers; // current number of passengers in the elevator
	private int totalLoadedPassengers; // total passengers this elevator loaded throughout the simulation
	private int totalUnloadedPassengers; // total passengers this elevator unloaded throughout the simulation
	private ArrayList<ElevatorEvent> moveQueue;
	private int[] passengerDestinations;
	private BuildingManager manager;
	private volatile boolean running;
	
	// Constructor
	Elevator(int elevatorID, BuildingManager manager) {
		this.running = true;
		this.elevatorID = elevatorID;
		this.currentFloor = 0;
		this.numPassengers = 0;
		this.totalLoadedPassengers = 0;
		this.totalUnloadedPassengers = 0;
		this.passengerDestinations = new int[5];
		this.manager = manager;
	}
	
	// TODO: Other methods that help perform Elevator behavior such as creating	
	// ElevatorEvents, managing the moveQueue, and updating its current state.
	public long totalTime(int destFloor) {
		return Math.abs((destFloor - this.currentFloor) * travelTimePerFloor) + (2 * loadUnloadTime);
	}
	
	public void moveElevator(int destFloor) {
		// insert a delay while traversing each floor
	}
	
	public void setPassengerDestinations(int passengers, int destFloor) {
		this.passengerDestinations[destFloor] += passengers;
	}
	
	public int nextFloor() {
		return ++this.currentFloor;
	}
	
	public void loadPassengers(int passengers, int destFloor) {
		this.numPassengers += passengers;
		this.totalLoadedPassengers += passengers;
		this.moveQueue.add(new ElevatorEvent(destFloor, totalTime(destFloor)));
	}
	
	public void unloadPassengers(int passengers) {
		this.numPassengers -= passengers;
		this.totalUnloadedPassengers += passengers;
		for (ElevatorEvent e : moveQueue) {
			if (e.getDestination() == this.currentFloor)
				moveQueue.remove(e);
		}
	}
	
	public void terminate() {
		// Safely ends a thread by terminating it after it's done with its last execution cycle
		// which prevents deadlocks since all locks will be unlocked and released afterwards.
		this.running = false;
	}
	
	public void run() {
		while (this.running) {
			for (int i = 0; i < 5; i++) {
				int currentPassengers = this.manager.loadPassengers(this.currentFloor);
				this.totalLoadedPassengers += currentPassengers;
				this.numPassengers = currentPassengers;
				this.manager.leaveFloor(this.currentFloor);
				this.manager.approachFloor(this.elevatorID, this.passengerDestinations[i]);
			}
		}
	}
}
