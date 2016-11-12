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
	
	public ArrayList<ElevatorEvent> genElevatorEvents(int currentFloor) {
		ArrayList<ElevatorEvent> next = new ArrayList<ElevatorEvent>();
		for (int dest = 0; dest < FLOORS; dest++) {
			if (this.manager.getFloor(currentFloor).getPassengerRequests(dest) != 0) {
				next.add(new ElevatorEvent(dest, totalTime(dest)));
			}
		}
		return next;
	
	}
	
	public void run() {
		// adding elements to the ArrayList of ElevatorEvents
		this.moveQueue.addAll(genElevatorEvents(this.currentFloor));
		
		while (this.running) {
			for (ElevatorEvent e : moveQueue) {
				this.manager.setApproachingElevator(this.elevatorID, e.getDestination());
				this.manager.loadPassengers(this.currentFloor);
				
				try {
					Thread.sleep(e.getExpectedArrival());
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
					this.currentFloor = e.getDestination();
					this.manager.unloadPassengers(this.currentFloor);
					moveQueue.remove(e);
				}
				
				this.moveQueue.addAll(genElevatorEvents(this.currentFloor));
				this.manager.setApproachingElevator(-1, this.currentFloor); // unlock the current floor for other elevators
				
			}
		}
	}
}
