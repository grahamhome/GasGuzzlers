/**
 * Defines a basic vehicle for range testing
 * @author Graham Home
 *
 */
public class Vehicle {
	private String name;
	private float maxSpeed;	// Max speed in miles/hour
	private float mpg;		// Fuel economy in miles/gallon
	private float fuel;		// Current fuel level in gallons
	private float maxFuel;	// Max fuel capacity in gallons
	private float efficiency;	// The fuel efficiency percentage as a decimal (0-1)
	private float lowCutoff;	// Speed below which the MPG increases by the efficiency percentage
	private float highCutoff;	// Speed above which the MPG decreases by the efficiency percentage
	private float finalLap = 0;	// Records the distance traveled if a trip is not completed
	private float odometer = 0;	//Records the distance traveled for each tank of fuel consumed
	
	public Vehicle(String name, int maxSpeed, int mpg, int maxFuel, int efficiencyPct, int lowCutoff, int highCutoff) throws IllegalArgumentException {
		this.name = name.trim();
		if (!this.name.matches("^[a-zA-Z0-9]*$")) {
			throw new IllegalArgumentException("Vehicle name must contain alphanumeric characters only.\n");
		}
		this.maxSpeed = maxSpeed;
		if (this.maxSpeed <= 0) {
			throw new IllegalArgumentException("Vehicle must have a maximum speed greater than zero.\n");
		}
		this.mpg = mpg;
		if (this.mpg <= 0) { 
			throw new IllegalArgumentException("Vehicle must have a MPG rating greater than zero.\n");
		}
		this.fuel = maxFuel;
		this.maxFuel = maxFuel;
		if (this.maxFuel <= 0) { 
			throw new IllegalArgumentException("Vehicle must have a fuel capacity greater than zero.\n");
		}
		if ((efficiencyPct < 0) || (efficiencyPct >= 100)) { 
			throw new IllegalArgumentException(
					"Vehicle must have a fuel efficiency rating between zero (inclusive) and one hundred (exclusive).\n"
					);
		}
		this.efficiency = ((float)efficiencyPct)/100;
		this.lowCutoff = lowCutoff;
		this.highCutoff = highCutoff;
		if (efficiency != 0) {
			if ((this.lowCutoff < 0) 
					|| (this.lowCutoff >= this.maxSpeed) 
					|| (this.highCutoff < 0) 
					|| (this.highCutoff >= this.maxSpeed)) {
				throw new IllegalArgumentException(
						"High and low speed thresholds must be between zero (inclusive) and the maximum vehicle speed (exclusive).\n"
						); 
			}
			if (this.lowCutoff >= this.highCutoff) {
				throw new IllegalArgumentException("High speed threshold must be greater than low speed threshold.\n"); 
			}
		}
	}
	
	/**
	 * Drives the vehicle a specified distance.
	 * @param distance : The distance in miles to drive
	 * @return : success/error code integer.
	 */
	public int drive(int distance, int speed) {
		if (speed > maxSpeed) { 
			return 1;
		} else if (speed <= 0) { 
			return 2;
		} else if (distance <= 0) {
			return 3;
		} else {
			float effectiveMPG = adjustMPG(speed);
			float consumption = ((float)distance)/effectiveMPG;
			if (consumption > fuel) { 
				finalLap = (fuel * effectiveMPG);
				fuel = 0;
				odometer += finalLap;
				return 4;
			}
			fuel -= consumption;
			odometer += distance;
			return 0;
		}
	}
	
	/**
	 * Returns the vehicle's efficiency-adjusted MPG rating for a given speed.
	 * @param speed : an integer representing vehicle speed.
	 * @return : a float representing miles per gallon.
	 */
	private float adjustMPG(int speed) {
		float newMPG = ((speed < lowCutoff) ? (mpg * (1+efficiency)) : 
			(speed > highCutoff) ? (mpg*(1-efficiency)) : 
				mpg);
		return (newMPG < 1) ? 1 : newMPG;
	}
	
	/**
	 * Method used for testing purposes only.
	 * @param speed : an integer representing vehicle speed.
	 * @return : a float representing miles per gallon.
	 */
	int getAdjustedMPG(int speed) {
		return (int) Math.round(adjustMPG(speed));
	}
	
	/**
	 * Returns the amount of fuel left in the vehicle, in gallons.
	 * @return : an integer representing the gallons of fuel left.
	 */
	public int fuelGauge() {
		return Math.round(fuel);
	}
	
	/**
	 * Refills the vehicle's fuel tank.
	 */
	public void refuel() {
		fuel = maxFuel;
		finalLap = 0;
		odometer = 0;
	}
	
	/**
	 * Returns the distance traveled if a trip is not completed, otherwise -1
	 * @return : an integer representing distance in miles.
	 */
	public int finalLap() {
		return Math.round(finalLap);
	}
	
	/**
	 * Returns the distance traveled on the current tank of fuel.
	 * @return : an integer representing distance in miles.
	 */
	public int odometer() {
		return Math.round(odometer);
	}
	
	/**
	 * Success/Error messages.
	 */
	public static String returnCode(int code) { 
		switch (code) {
		case 0: return "You have arrived at your destination.\n";
		case 1: return "Error: Requested speed exceeds maximum speed.\n";
		case 2: return "Error: Requested speed must be greater than zero.\n";
		case 3: return "Error: Requested distance must be greater than 0.\n";
		case 4: return "Vehicle ran out of fuel before reaching its destination.\n";
		default: return "Invalid error code.\n";
		}
	}
}