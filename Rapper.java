package org.elsys.robocode;

import robocode.*;

public class Destroyer extends AdvancedRobot{
	boolean movingForward;
	
	public void run() {
		// Set robot colors
		setBodyColor(new Color.(0, 0, 0));
		setGunColor(new Color.(138, 0, 0);
		setRadarColor(new Color(100, 0, 0));
		setBulletColor(new Color(255, 0, 0));
		setScanColor(new Color(300, 0, 0));
		
		while (true) {
			ahead(100); // Move ahead 100
			back(100); // Move back 100
		
		}
	}

	public void onScannedRobot() {
		
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
