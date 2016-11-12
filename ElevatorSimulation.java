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
		// (num_passengers, floor_destination, rate_of_occurrence)
		// At time = 0, all elevators start at floor 0 and all floors will begin requesting elevators
		/*	[
		  	[(2,4,100), (5,2,300)], 
			[(3,0,500), (1,4,200)],
			[(5,0,200), (2,1,500), (3,3,600)],
			[(4,0,200)],
			[(2,3,600), (6,2,100), (4,0,40)]
			]
		*/
		simSecond = 100; // in milliseconds
		totalSimTime = 1000 * simSecond;
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
	
	private void initBuildingManager() {
		int floor = 0;
		for (ArrayList<PassengerArrival> sublist : passengers) {
			BuildingFloor temp = new BuildingFloor();
			for (PassengerArrival p : sublist) {
				temp.setPassengerRequests(p.getDestinationFloor(), p.getNumPassengers());
			}
			this.manager.setFloor(floor, temp);
			floor++;
		}
	}
	
	public void start() {
		this.initBuildingManager();
		Elevator[] elevators = new Elevator[5]; 
		Thread[] threads = new Thread[5];// array of elevator threads
		
		for (int i = 0; i < 5; i++) {
			elevators[i] = new Elevator(i, this.manager);
		}
		for (int i = 0; i < 5; i++) {
			threads[i] = new Thread(elevators[i]);
		}
		
		// 1 millisecond = 10 ^ -3 seconds
		// 1 nanosecond = 10 ^ -9 seconds
		
		SimClock.SimClock();
		for (int i = 0; i < 5; i++){
			threads[i].start(); // do not put threads to sleep for this lab
		}
		
		while (SimClock.getTime() < this.totalSimTime) {

			SimClock.tick();
		}
		
		for (int i = 0; i < 5; i++) {
			elevators[i].terminate();
		}
		
	}
	
	public void printBuildingState() {
		
	}
	
	
}
