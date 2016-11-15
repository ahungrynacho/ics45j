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
			while (scanner.hasNextLine()) {
					String line = scanner.nextLine(); // Read next line
					lines.add(line); // add to ArrayList
			}
		} catch (FileNotFoundException e) {
			// Exception: file not found
		} finally {
			// Always close scanner
			scanner.close();
		}
		return lines;
	}
	
	public void getFloorPassengers(ArrayList<String> floorPassengers) {
		// Takes the last five lines of ElevatorConfig.txt
		for (int i = 0; i < floorPassengers.size(); i++) { // for each line (floor)
			String[] passengerInfo = floorPassengers.get(i).split(";"); // Split by ";"
			ArrayList<PassengerArrival> temp = new ArrayList<PassengerArrival>();
			for (int j = 0; j < passengerInfo.length; j++) {
				// Separate by " " to get the three pieces of information about the passengers
				String[] info = passengerInfo[j].split(" ");
				// Create PassengerArrival Objects for each item in passengerInfo
				PassengerArrival passenger = new PassengerArrival(Integer.parseInt(info[0]), 
						Integer.parseInt(info[1]), Integer.parseInt(info[2]), Integer.parseInt(info[2]));
				temp.add(passenger);
			}
			// Add PassengerArrival Object to corresponding position in passengers array
			passengers.add(temp);
		}
		
	}
	
	// Extract Simulation and Floor Information
	public void extractInformation(ArrayList<String> lines) {
		// 1st line is duration of Simulation
		totalSimTime = Integer.parseInt(lines.get(0));
		// 2nd line is each simulated second in real time
		simSecond = Integer.parseInt(lines.get(1));
		
		// Save PassengerArrival Objects for each floor in Config file (line 3 to 7)
		ArrayList<String> floorLines = new ArrayList<String>(lines.subList(2, 6));
		getFloorPassengers(floorLines);
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
		// Continuously populate floors with passengers at the rate and quantity specified by
		// ArrayList<ArrayList<PassengerArrival>> passengers by accessing each floor through this.manager.		
		// Have each thread continuously scan through floors by accessing this.manager.
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
			threads[i].start();
		}
		
		while (SimClock.getTime() < this.totalSimTime) {
			SimClock.tick();
			
			// Each time the clock ticks, print out: SimClock time
			// Number of passengers entering a specific floor and requesting to go to ith floor
			// Elevator Actions including
			// elevator heading to specific floor to pickup passengers
			// elevator heading to specific floor to unload passengers
			// elevator reaching a specific floor to pickup passengers (includes number of passengers and destination)
			// elevator reaching a specific floor to unload passengers (includes number of passengers exiting)
			System.out.print(SimClock.getTime() * simSecond);
		}
		
		for (int i = 0; i < 5; i++) {
			elevators[i].terminate();
		}
		
	}
	
	public void printBuildingState() {
		// For each Floor: total number of requests, total number of passengers exit,
		// current number in elevator, which elevator currently heading towards floor
		
		// For each Elevator: total number of passengers that entered throughout simulation,
		// total number of passengers that exited the elevator on a specific floor,
		// current passengers heading to any floor
	}
	
	
}
