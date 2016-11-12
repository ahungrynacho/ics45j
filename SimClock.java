// Kimberly Chou 80176941
// Brian Huynh 57641580

public class SimClock {
	private static int time; // Time
	
	// Constructor
	public SimClock() {
		time = 0; // initialize time to 0
	}
	
	public static void tick() {
		time++;
	}
	
	public static int getTime() {
		return time;
	}
}
