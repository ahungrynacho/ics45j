// Kimberly Chou 80176941
// Brian Huynh 57641580

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ElevatorSimulation {
	private BuildingManager manager;
	private Scanner scanner;
	private int totalSimTime; // total simulation time
	private int simSecond; // rate of each simulated second
	private ArrayList<ArrayList<PassengerArrival>> passengers;
	
	// Constructor
	public ElevatorSimulation() {
		manager = new BuildingManager();
		passengers = new ArrayList<ArrayList<PassengerArrival>>();
	}
	
	
	// Read ElevatorConfig.txt file
	public ArrayList<String> readConfigFile(String file) {
		ArrayList<String> lines = new ArrayList<String>();
		try {
			scanner = new Scanner(new File(file)); // Open file
					String line = scanner.nextLine(); // Read next line
					lines.add(line); // add to ArrayList
		} catch (FileNotFoundException e) {
			// Exception: file not found
		} finally {
			// Always close scanner
			scanner.close();
		}
		return lines;
	}
	
	// Extract Simulation and Floor Information
	public void extractInformation(ArrayList<String> lines) {
		for (int i = 0; i < lines.size(); i++) {
			if (i == 0) { // 1st line is duration of Simulation
				totalSimTime = Integer.parseInt(lines.get(i));
			} else if (i == 1) { // 2nd line is each simulated second in real time
				simSecond = Integer.parseInt(lines.get(i));
			} else {
				String[] passengerInfo = lines.get(i).split(";"); // Separate by ";"
				for (int j = 0; j < passengerInfo.length; j++) {
					String[] info = passengerInfo[j].split(" "); // Separate by " "
					// Create PassengerArrival Object; at time=0, timePeriod = expectedArrivalTime
					PassengerArrival passenger = new PassengerArrival(Integer.parseInt(info[0]), Integer.parseInt(info[1]), 
													Integer.parseInt(info[2]), Integer.parseInt(info[2]));
					// Add passenger information to passengers
					passengers.get(i - 2).add(passenger); // TODO: Feel free to change indexing
					
					// Set Floor i - 2(b/c i = 2 here) passenger requests [Floor (2nd #), # of people (1st #)] 
					// manager.getFloors()[i - 2].setPassengerRequests(Integer.parseInt(info[1]), Integer.parseInt(info[0]));
					// manager.getFloors()[i - 2].setTotalDestinationRequests(Integer.parseInt(info[1]), Integer.parseInt(info[0]));
				}
			}
		}
	}
	
	
}
