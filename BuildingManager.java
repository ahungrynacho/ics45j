// Kimberly Chou 80176941
// Brian Huynh 57641580

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class BuildingManager {
	// critical section
	private BuildingFloor[]	floors;
	private Lock[] locks; // lock for floors array
	
	public BuildingManager() {
		floors = new BuildingFloor[5];
		locks = new Lock[5];
		for (int i = 0; i < 5; i++) {
			locks[i] = new ReentrantLock(); // each floor has their own lock
		}
	}
	
	// TODO: Any methods needed to manipulate the floors array
	// Remember to Lock :)
	
	public int loadPassengers(int floor) {
		
		locks[floor].lock();
		int passengers = this.floors[floor].passengersOnThisFloor();
		locks[floor].unlock();
		return passengers;
	}
	
	public void unloadPassengers(int floor) {
		
	}
	
	// If one thread is writing, then all other threads must wait.
	public void approachFloor(int approachingElevator, int floor) {
		locks[floor].lock();
		this.floors[floor].setApproachingElevator(approachingElevator);
		locks[floor].unlock();
	}
	
	public void leaveFloor(int floor) {
		locks[floor].lock();
		this.floors[floor].setApproachingElevator(-1);
		locks[floor].unlock();
	}
	
	// Get floors array
	public BuildingFloor[] getFloors() {
		return floors;
	}
	
	// Set floors array
	public void setFloor(int i, BuildingFloor floor) {
		this.floors[i] = floor;
	}
}
