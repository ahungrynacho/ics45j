// Kimberly Chou 80176941
// Brian Huynh 57641580

import java.util.ArrayList;

public class Elevator implements Runnable {
	private int FLOORS = 5;
	private int simulatedTime = 100; //milliseconds
	private int loadUnloadTime = 10 * simulatedTime;
	private int travelTimePerFloor = 5 * simulatedTime;
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
		this.moveQueue = new ArrayList<ElevatorEvent>();
	}
	
	// TODO: Other methods that help perform Elevator behavior such as creating	
	// ElevatorEvents, managing the moveQueue, and updating its current state.
	
	public int totalTime(int dest) {
		return Math.abs((dest - this.currentFloor) * travelTimePerFloor) + loadUnloadTime;
	}
		
	public void terminate() {
		// Safely ends a thread by terminating it after it's done with its last execution cycle
		// which prevents deadlocks since all locks will be unlocked and released afterwards.
		this.running = false;
	}
	
	public int findFloor() {
		// Return the floor with passengers awaiting for an elevator.
		for (int src = 0; src < this.manager.getFLOORS(); src++) {
			for (int dest = 0; dest < this.manager.getFLOORS(); dest++) {
				if (this.manager.getFloor(src).getPassengerRequests(dest) != 0) {
					return src;
				}
			}
		}
		return 0;
	}
	
	// Generates ElevatorEvents for passengers who are about to enter the elevator from currentElevator.
	public ArrayList<ElevatorEvent> genElevatorEvents(int currentFloor) {
		ArrayList<ElevatorEvent> next = new ArrayList<ElevatorEvent>();

		for (int dest = 0; dest < this.manager.getFLOORS(); dest++) {
			if (this.manager.getFloor(currentFloor).getPassengerRequests(dest) != 0) {
				next.add(new ElevatorEvent(dest, totalTime(dest)));
				this.manager.getFloor(currentFloor).setPassengerRequests(dest, 0); // Set the number of passengers of this floor to 0 because they were loaded into the elevator.
			}
		}
		
		return next;
	
	}
	
	public void run() {
		// All elevators start at floor 0 so check this floor for passengers that want to go up.
		// If there are passengers that want to go up from floor 0, add them to the queue.
		// There is a race condition at when all the elevators start at floor 0 because the group of passengers
		// starting at floor 0 is not supposed to be split into multiple elevators.
		// Throughout the rest of the simulation, however, no two elevators will ever be on the same floor because 
		// this.manager.setApproachingElevator will mutex floors from other elevators.
		
		// This prevents the initial race condition from occuring.
		this.manager.setApproachingElevator(this.elevatorID, this.currentFloor); // initially lock floor 0
		this.moveQueue.addAll(genElevatorEvents(this.currentFloor));
		this.manager.setApproachingElevator(-1, this.currentFloor); // unlock floor 0
		
		while (this.running) {
			for (ElevatorEvent e : moveQueue) {
				this.manager.setApproachingElevator(this.elevatorID, e.getDestination()); // lock the floor this elevator is approaching
				this.manager.loadPassengers(this.currentFloor);
				
				try {
					Thread.sleep(e.getExpectedArrival()); // travel time + load/unload delay
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
					this.currentFloor = e.getDestination(); // the elevator arrived at its destination
					this.manager.unloadPassengers(this.currentFloor);
					moveQueue.remove(e);
				}
				this.manager.setApproachingElevator(-1, this.currentFloor); // unlock the current floor for other elevators
				if (moveQueue.isEmpty())
					this.moveQueue.addAll(genElevatorEvents(this.findFloor())); // scans floors for passengers waiting on this elevator
					
			}
		}
	}
}
