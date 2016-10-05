package org.elsys;


import java.awt.Color;


import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

import static robocode.util.Utils.normalRelativeAngleDegrees;

public class KalinRobot extends Robot {
	
	int turnDirection = 1;
	int distination = 75;
	
	public void run() {
		// Set colors
		setBodyColor(Color.red);
		setGunColor(Color.blue);
		setRadarColor(Color.yellow);
		setBulletColor(Color.green);
		setScanColor(Color.green);
		//setAdjustRadarForRobotTurn(true); // independ radar movement

		while (true) {
			//turnRight(5 * turnDirection);
			//turnGunRight(5);
			//turnRight(10000);
			//setMaxVelocity(5);
			ahead(10000);
			//turnRadarRight(360);
			//ahead(100);
			turnGunRight(360);
			back(100);
			turnGunLeft(360);
		}
	}
	
//	private void setMaxVelocity(int i) {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public void onScannedRobot(ScannedRobotEvent e) {
		System.out.println("scannned robot event: " + e);
		if(e.getDistance() < 50 && getEnergy() > 50) {
			fire(10);
		} else {
			fire(5);
		}
		//fire(3);
		scan();
	}
	
	public void onHitRobot(HitRobotEvent e) {
		if (e.getBearing() > -10) {
				if(e.getBearing() < 20) {
					fire(10);
				}
		}
		if (e.isMyFault()) {
			turnRight(90);
		}
	}
	
	/**
	 * onHitByBullet:  Turn perpendicular to the bullet, and move a bit.
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		turnRight(normalRelativeAngleDegrees(90 - (getHeading() - e.getHeading())));

		ahead(distination);
		distination *= -1;
		scan();
	}
}