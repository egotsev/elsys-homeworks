package org.elsys.robocode;

import robocode.*;

public class MyRobot extends AdvancedRobot{
	boolean movingForward;
	
	public void run() {
		// Set robot colors
		setBodyColor(new Color.(139, 0, 0));//dark red
		setGunColor(new Color.(0, 0, 0);//black
		setRadarColor(new Color(128, 0, 0));//maroon
		setBulletColor(new Color(255, 255, 255));//white
		setScanColor(new Color(255, 99, 71));//tomato
		
		while (true) {
			ahead(100); // Move ahead 100
			turnGunRight(360); // Spin gun around
			back(100); // Move back 100
			turnGunRight(360); // Spin gun around
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