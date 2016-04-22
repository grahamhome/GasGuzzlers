import java.io.Console;
import java.util.HashMap;
import java.util.Map;

/**
 * Simulates range testing of custom vehicles.
 * @author Graham Home
 *
 */
public class GasGuzzlers {
	
	private static Map<String, Vehicle> vehicles = new HashMap<String, Vehicle>();
	
	private static Console console = System.console();
	
	private static String helpText = "Try \"make <name>\" or \"drive <name> <distance> <speed>\"%n";
	
	/**
	 * Creates and stores a new vehicle from the supplied values.
	 */
	private static void makeVehicle(String vehicleName) {
		boolean pass = false;
		while (!pass) {
			try {
				console.format("Please enter " + vehicleName + "'s information:%n");
				console.format("Maximum speed (mph): ");
				int maxSpeed = Integer.parseInt(console.readLine());
				console.format("Fuel tank size (gallons): ");
				int maxFuel = Integer.parseInt(console.readLine());
				console.format("Fuel economy (mpg): ");
				int mpg = Integer.parseInt(console.readLine());
				console.format("Percent by which fuel economy can vary due to vehicle speed (0-100): ");
				int efficiency = Integer.parseInt(console.readLine());
				int lowCutoff; int highCutoff;
				if (efficiency != 0) {
					console.format("Speed below which the vehicle is more efficient (mph): ");
					lowCutoff = Integer.parseInt(console.readLine());
					console.format("Speed above which the vehicle is less efficient (mph): ");
					highCutoff = Integer.parseInt(console.readLine());
				} else {
					lowCutoff = 0;
					highCutoff = maxSpeed;
				}
				vehicles.put(vehicleName, new Vehicle(vehicleName, maxSpeed, mpg, maxFuel, efficiency, lowCutoff, highCutoff));
				console.format(vehicleName + " has been created.%n");
				pass = true;
				
			} catch (NumberFormatException e) {
				console.format("Error: A number was not recognized. Please use integer values only.%n");	
			} catch (IllegalArgumentException e) {
				console.format("Error: " + e.getMessage());
			}
		}
	}
	
	/**
	 * Drives vehicle.
	 */
	private static void driveVehicle(Vehicle vehicle, int distance, int speed) {
		int result = vehicle.drive(distance, speed);
		console.format(Vehicle.returnCode(result));
		if ((result == 0)) {
			console.format("Remaining fuel: " + vehicle.fuelGauge() + " gallons.%n");
		}
		if ((result == 4) || vehicle.fuelGauge() == 0) {
			if (result == 4) { 
				console.format("Last trip: " + vehicle.finalLap() + "/" + distance + " miles travelled.%n");
			}
			console.format("Total distance travelled on this tank: " + vehicle.odometer() + " miles.\n");
			console.format("Good thing you packed that extra can of fuel. Fuel tank refilled!%n");
			vehicle.refuel();
		}
	}
	
	/**
	 * Allows user to make, drive, and refuel vehicles.
	 */
	public static void interpretCommands() {
		console.format("Please enter a command: ");
		String[] input = console.readLine().toLowerCase().split("\\W+");
		
		if (input[0].equals("make")) {
			if (input.length != 2) {
				console.format(helpText);
			} else {
				if (vehicles.containsKey(input[1])) {
					console.format(input[1] + " already exists.%n");
				} else {
					makeVehicle(input[1]);
				}
			}
			
		} else if (input[0].equals("drive")) {
			if (input.length != 4) {
				console.format(helpText);
			} else if (!vehicles.containsKey(input[1])) {
				console.format(input[1] + " does not exist.%n");
			} else {
				try {
					int distance = Integer.parseInt(input[2]);
					int speed = Integer.parseInt(input[3]);
					driveVehicle(vehicles.get(input[1]), distance, speed);
				} catch (NumberFormatException e) { console.format(helpText); }
			}
			
		} else { console.format(helpText); }
	}

	public static void main(String[] args) {
		while (true) {
			interpretCommands();
		}
	}
}
