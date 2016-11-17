// Kimberly Chou 80176941
// Brian Huynh 57641580

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class BuildingManager {
	// critical section
	private int FLOORS;
	private BuildingFloor[]	floors;
	private Lock[] locks; // lock for floors array
	
	public BuildingManager() {
		floors = new BuildingFloor[FLOORS];
		locks = new Lock[FLOORS];
		for (int i = 0; i < FLOORS; i++) {
			locks[i] = new ReentrantLock(); // each floor has their own lock
		}
	}
	
	// TODO: Any methods needed to manipulate the floors array
	// Remember to Lock :)
	
	public int loadPassengers(int currentFloor) {
		
		locks[currentFloor].lock();
		int passengers = 0;
		// passengerRequests on current floor 0
		// 5 passengers want to go to floor 2
		// 2 passengers want to go to floor 4
		// floors[0].getPassengerRequests(i) = [0, 0, 5, 0, 2]
		
		// passengers going up only
		for (int dest = currentFloor; dest < FLOORS; dest++) {
			int req = this.floors[currentFloor].getPassengerRequests(dest);
			passengers += req;
			this.floors[currentFloor].setTotalDestinationRequests(dest, req);
			this.floors[currentFloor].setPassengerRequests(dest, 0); // transfer people from the floor into the elevator
		}
		
		// passengers going down only
		if (passengers == 0) {
			for (int dest = currentFloor; dest >= 0; dest--) {
				int req = this.floors[currentFloor].getPassengerRequests(dest);
				passengers += req;
				this.floors[currentFloor].setTotalDestinationRequests(dest, req);
				this.floors[currentFloor].setPassengerRequests(dest, 0);
			}
		}
		locks[currentFloor].unlock();
		return passengers;
	}
	
	public void unloadPassengers(int currentFloor) {
		locks[currentFloor].lock();
		// TODO: fill in unload passengers actions
		locks[currentFloor].unlock();
	}
	
	// If one thread is writing, then all other threads must wait.
	public void setApproachingElevator(int elevatorID, int floor) {
		locks[floor].lock();
		this.floors[floor].setApproachingElevator(elevatorID);
		locks[floor].unlock();
	}
	
	// Get floor
	public BuildingFloor getFloor(int i) {
		return this.floors[i];
	}
	
	// Set floor
	public void setFloor(int i, BuildingFloor floor) {
		this.floors[i] = floor;
	}

	public int getFLOORS() {
		return FLOORS;
	}

	public void setFLOORS(int fLOORS) {
		FLOORS = fLOORS;
	}
	
	public synchronized int findFloor() {
		// Check for request, WAIT if no requests available, Return when request found
		int floorFound = -1;
		do {
			for (int src = 0; src < getFLOORS(); src++) {
				for (int dest = 0; dest < getFLOORS(); dest++) {
					if (getFloor(src).getPassengerRequests(dest) != 0) {
						floorFound = src;
					}
				}
			}
			if (floorFound < 0) { // if no floor found, elevator wait
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} while (floorFound < 0);
		
		// if floor found exit while loop and return floor
		return floorFound;
	}
	
	
}
