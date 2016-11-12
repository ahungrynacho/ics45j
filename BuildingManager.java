// Kimberly Chou 80176941
// Brian Huynh 57641580

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class BuildingManager {
	private BuildingFloor[]	floors;
	private Lock floorsLock; // lock for floors array
	
	public BuildingManager() {
		floors = new BuildingFloor[5];
		floorsLock = new ReentrantLock(); // initialize lock
	}
	
	// TODO: Any methods needed to manipulate the floors array
	// Remember to Lock :)
	
	// Get floors array
	public BuildingFloor[] getFloors() {
		return floors;
	}
	
	// Set floors array
	public void setFloors(BuildingFloor[] floors) {
		this.floors = floors;
	}
}
