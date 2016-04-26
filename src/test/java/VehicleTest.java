import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for input validation checking of Vehicle class constructor & methods.
 * @author Graham Home
 *
 */
public class VehicleTest {

    private Logger logger = Logger.getLogger(this.getClass());
    
	//Test vehicles
	private Vehicle testCar;
	private Vehicle testPlane;
	private Vehicle testBoat1;
	private Vehicle testBoat2;
	private Vehicle testBoat3;
	private Vehicle testBoat4;
	
	/**
	 * Resets test vehicles
	 */
	private void reset() {
		testCar = new Vehicle("Car", 3, 3, 10, 0, 1, 2);
		testPlane = new Vehicle("Plane", 9, 5, 2, 0, 1, 2);
		testBoat1 = new Vehicle("Boat", 4, 3, 3, 0, 2, 3);
		testBoat2 = new Vehicle("Boat", 4, 3, 3, 1, 2, 3);
		testBoat3 = new Vehicle("Boat", 4, 3, 3, 50, 2, 3);
		testBoat4 = new Vehicle("Boat", 4, 3, 3, 99, 2, 3);
		
	}
	
	@Before
	public void setUp() {
	    logger.info("here i am at info about to reset");
		reset();
		logger.debug("i just reset");
	}

	@Test
	public void testVehicleName() {
		try {
			Vehicle v = new Vehicle("!@#$", 0, 0, 0, 0, 0, 0);
			fail("No exception thrown.");
		} catch (IllegalArgumentException e) {
			assertEquals("Vehicle name must contain alphanumeric characters only.\n", e.getMessage());
		}
	}

	@Test
	public void testVehicleSpeed1() {
		try {
			Vehicle v = new Vehicle("Car", 0, 0, 0, 0, 0, 0);
			fail("No exception thrown.");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			assertEquals("Vehicle must have a maximum speed greater than zero.\n", e.getMessage());
		}
	}
	
	@Test
	public void testVehicleMPG1() {
		try {
			Vehicle v = new Vehicle("Car", 10, 0, 0, 0, 0, 0);
			fail("No exception thrown.");
		} catch (IllegalArgumentException e) {
			assertEquals("Vehicle must have a MPG rating greater than zero.\n", e.getMessage());
		}
	}
	
	
	@Test
	public void testVehicleCapacity1() {
		try {
			Vehicle v = new Vehicle("Car", 10, 10, 0, 0, 0, 0);
			fail("No exception thrown.");
		} catch (IllegalArgumentException e) {
			assertEquals("Vehicle must have a fuel capacity greater than zero.\n", e.getMessage());
		}
	}
	
	@Test
	public void testVehicleEfficiency1() {
		try {
			Vehicle v = new Vehicle("Car", 10, 10, 10, -1, 0, 0);
			fail("No exception thrown.");
		} catch (IllegalArgumentException e) {
			assertEquals(
					"Vehicle must have a fuel efficiency rating between zero (inclusive) and one hundred (exclusive).\n", 
					e.getMessage());
		}
	}
	
	@Test
	public void testVehicleEfficiency2() {
		try {
			Vehicle v = new Vehicle("Car", 10, 10, 10, 100, 0, 0);
			fail("No exception thrown.");
		} catch (IllegalArgumentException e) {
			assertEquals(
					"Vehicle must have a fuel efficiency rating between zero (inclusive) and one hundred (exclusive).\n", 
					e.getMessage());
		}
	}
	
	@Test
	public void testVehicleLowSpeed1() {
		try {
			Vehicle v = new Vehicle("Car", 10, 10, 10, 10, -1, 0);
			fail("No exception thrown.");
		} catch (IllegalArgumentException e) {
			assertEquals(
					"High and low speed thresholds must be between zero (inclusive) and the maximum vehicle speed (exclusive).\n",
					e.getMessage());
		}
	}
	
	@Test
	public void testVehicleLowSpeed2() {
		try {
			Vehicle v = new Vehicle("Car", 10, 10, 10, 10, 10, 0);
			fail("No exception thrown.");
		} catch (IllegalArgumentException e) {
			assertEquals(
					"High and low speed thresholds must be between zero (inclusive) and the maximum vehicle speed (exclusive).\n", 
					e.getMessage());
		}
	}
	
	@Test
	public void testVehicleHighSpeed1() {
		try {
			Vehicle v = new Vehicle("Car", 10, 10, 10, 10, 5, -1);
			fail("No exception thrown.");
		} catch (IllegalArgumentException e) {
			assertEquals(
					"High and low speed thresholds must be between zero (inclusive) and the maximum vehicle speed (exclusive).\n", 
					e.getMessage());
		}
	}
	
	@Test
	public void testVehicleHighSpeed2() {
		try {
			Vehicle v = new Vehicle("Car", 10, 10, 10, 10, 5, 10);
			fail("No exception thrown.");
		} catch (IllegalArgumentException e) {
			assertEquals(
					"High and low speed thresholds must be between zero (inclusive) and the maximum vehicle speed (exclusive).\n", 
					e.getMessage());
		}
	}
	
	@Test
	public void testVehicleHighLowSpeed1() {
		try {
			Vehicle v = new Vehicle("Car", 10, 10, 10, 10, 5, 4);
			fail("No exception thrown.");
		} catch (IllegalArgumentException e) {
			assertEquals("High speed threshold must be greater than low speed threshold.\n", e.getMessage());
		}
	}
	
	
	@Test
	public void testVehicleValid() {
		try {
			Vehicle v = new Vehicle("Car", 3, 3, 3, 0, 1, 2);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testDrive1() {
			assertEquals(4, testPlane.drive(11, 4));
	}
	
	@Test
	public void testDrive2() {
		assertEquals(3, testPlane.drive(-1, 4));
	}
	
	@Test
	public void testDrive3() {
		assertEquals(3, testPlane.drive(0, 4));
	}
	
	@Test
	public void testDrive4() {
		assertEquals(0, testPlane.drive(10, 4));
	}
	
	@Test
	public void testDrive5() {
		assertEquals(1, testPlane.drive(10, 10));
	}
	
	@Test
	public void testDrive6() {
		assertEquals(2, testPlane.drive(10, -1));
	}
	
	@Test
	public void testDrive7() {
		assertEquals(2, testPlane.drive(10, 0));
	
	}
	
	@Test
	public void testDrive8() {
		assertEquals(0, testPlane.drive(10, 9));
	}
	
	@Test
	public void testMPG0() {
		assertEquals(3, testBoat1.getAdjustedMPG(4));
		
	}
	
	@Test
	public void testMPG2() {
		assertEquals(5, testBoat3.getAdjustedMPG(1));
		
	}
	@Test
	public void testMPG3() {
	    assertEquals(3, testBoat3.getAdjustedMPG(2));
		
	}
	
	@Test
	public void testMPG4() {
	    assertEquals(1, testBoat4.getAdjustedMPG(4));
		
	}
	
	@Test
	public void testMPG5() {
	    assertEquals(3, testBoat4.getAdjustedMPG(3));
		
	}
	
	@Test
	public void testFuel1() {
		testCar.drive(15, 3);
		assertEquals(5, testCar.fuelGauge());
	}
	
	@Test
	public void testFuel2() {
		testCar.drive(30, 3);
		assertEquals(0, testCar.fuelGauge());
	}
	
	@Test
	public void testOdometer1() {
	    assertEquals(0, testCar.odometer());
	}
	
	@Test
	public void testOdometer2() {
		testCar.drive(10,3);
		assertEquals(10, testCar.odometer());
	}
	
	@Test
	public void testFinalLap() {
		testCar.drive(40,3);
		assertEquals(30, testCar.finalLap());
	}
	
	@Test
	public void testRefuel1() {
		testCar.drive(30, 3);
		testCar.refuel();
		assertEquals(10, testCar.fuelGauge());
	}
	
	@Test
	public void testRefuel2() {
		testCar.drive(30, 3);
		testCar.refuel();
		assertEquals(0, testCar.finalLap());
	}
	
	@Test
	public void testRefuel3() {
		testCar.drive(30, 3);
		testCar.refuel();
		assertEquals(0, testCar.odometer());
	}
	
}
