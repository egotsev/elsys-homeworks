package whataFlegend;
import robocode.*;
import java.awt.*;
public class WhatAFingLegend extends AdvancedRobot
{
	public byte scanDirection = 1;
	public void run() {
	
		while(true) {
			setTurnRight(400);
			setTurnLeft(400);
			setMaxVelocity(8);
			ahead(1000);
		}
	}


	 public void onScannedRobot(ScannedRobotEvent e) {
		// Lock on to our target
		scanDirection *= -1; // changes value from 1 to -1
		setTurnRadarRight(360 * scanDirection);
		setTurnRadarRight(getHeading() - getRadarHeading() + e.getBearing());
		fire(5);
		}
	public void onHitByBullet(HitByBulletEvent e) {
		setTurnLeft(200);
	}
	

	public void onHitWall(HitWallEvent e) {
		
		back(50);
	}	
}
