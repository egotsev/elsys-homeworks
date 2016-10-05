import java.awt.Color;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

public class Idiot extends Robot {
	
	public void run() {
		
		setAllColors(Color.green);
		
		while(true) {

			ahead(150);
			turnLeft(110);
			ahead(135);
			turnLeft(90);
			turnRadarLeft(360);
			ahead(135);
			turnRight(110);
			ahead(150);
			turnRight(90);
			turnRadarLeft(360);
			
		}
		
	}
	
	public void onScannedRobot(ScannedRobotEvent event) {
		
		if (event.getDistance() < 200.0) {
			
			if (getEnergy() > 50.0) {
				fire(2);
			} else {
				fire(1.5);
			}
		
		}
		
	}
	
	public void onHitWall(HitWallEvent event) {
		
		turnLeft(180);
		turnRight(50);
		ahead(100);
		
	}
	
	public void onHitRobot(HitRobotEvent event) {
		
		if (event.isMyFault()) {
			
			fire(3);
			turnRight(160);
			ahead(100);
	
		} else {
		
			fire(3);
			turnRight(180);
			ahead(100);
		
		}
		
	}	
}