package org.elsys.robocode;

import robocode.*;

public class Destroyer extends AdvancedRobot{
	boolean movingForward;
	
	public void run() {
		// Set robot colors
		setBodyColor(new Color.(139, 0, 0));//dark red
		setGunColor(new Color.(0, 0, 0);//black
		setRadarColor(new Color(128, 0, 0));//maroon
		setBulletColor(new Color(255, 255, 255));//white
		setScanColor(new Color(255, 99, 71));//tomato
		
		while (true) {
			// switch directions if we've stopped
			if (getVelocity() == 0)
			moveDirection *= -1;

			// circle our enemy
			setTurnRight(enemy.getBearing() + 90);
			setAhead(1000 * moveDirection);
		}
	}

	public void onScannedRobot() {
		//..
		turnGunTo(scannedAngle);
		fire(3);
	}
	 
	public void onHitByBullet(HitByBulletEvent e) {
		back(20);
	}
	
	public void onHitWall(HitWallEvent e) {
		back(20);
	}
	
	public void onHitRobot(HitRobotEvent e) {
		trackName = e.getName();
		gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
		turnGunRight(gunTurnAmt);
		fire(3);
		back(50);
	}
}
