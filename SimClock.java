// Kimberly Chou 80176941
// Brian Huynh 57641580

public class SimClock {
	private static int time; // Time
	
	public static void SimClock() {
		time = 0; // initialize time to 0
	}
	
	public synchronized static void tick() {
		time++;
	}
	
	public synchronized static int getTime() {
		return time;
	}

}
