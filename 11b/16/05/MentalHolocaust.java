import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.*;



import java.awt.*;


public class MentalHolocaust extends Robot {

	double moveAmount;

	
	public void run() {
		
		setBodyColor(Color.black);
		setGunColor(Color.white);
		setRadarColor(Color.red);
		setBulletColor(Color.white);
		setScanColor(Color.red);
		
		moveAmount = Math.max(getBattleFieldWidth(), getBattleFieldHeight());
	
		
		turnLeft(getHeading() % 90);
		turnGunRight(90);

		turnRight(90);

		while (true) {
			ahead(moveAmount);
			turnRight(90);
			turnGunRight(360);
		}
		
	}
	public void onScannedRobot(ScannedRobotEvent e) {
		double distance = e.getDistance();

		if(distance<200)
		{
		   fire(3.5);
		}
		else if(distance<500)
		{
		   fire(2.5);
		}
		else if(distance<800)
		{
		   fire(1.5);
		}
		else
		{
		   fire(0.5);
		}
		}
	

	public void onHitByBullet(HitByBulletEvent e) {
    
    back(10);
	}
}
