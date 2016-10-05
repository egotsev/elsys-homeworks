import java.awt.Color;
import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

public class EpileptikRobot extends AdvancedRobot {

	public void run() {
		
		while (true) {
			
			setAllColors(Color.green);
			ahead(100);
			turnLeft(90);
			setAllColors(Color.yellow);
			ahead(100);
			turnLeft(90);
			setAllColors(Color.red);
			ahead(100);
			turnLeft(90);
			setAllColors(Color.black);
			ahead(100);
			turnLeft(90);
			setAllColors(Color.cyan);
			ahead(100);
			turnLeft(90);
			setAllColors(Color.gray);
			ahead(100);
			turnLeft(90);
			setAllColors(Color.DARK_GRAY);
			ahead(100);
			turnLeft(90);
			setAllColors(Color.magenta);
			ahead(100);
			turnLeft(90);
			setAllColors(Color.orange);
			ahead(100);
			turnLeft(90);
			setAllColors(Color.white);
			ahead(100);
			turnLeft(90);
			setAllColors(Color.pink);
			ahead(100);
			turnLeft(90);
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent event) {
		
		if (getEnergy() < 10.0) {
			fire(0.1);
		} 
		
		if (getEnergy() < 20.0) {
			fire(0.2);
		} 
		
		if (getEnergy() < 25.0) {
			fire(0.3);
		} 
		
		if (getEnergy() < 30.0) {
			fire(0.5);
		} 
		
		if (getEnergy() < 35.0) {
			fire(1);
		} 
		
		if (getEnergy() < 40.0) {
			fire(2);
		} 
		
		if (getEnergy() < 50.0) {
			fire(3);
		} 
		
		fire(5);
	}

}