package org.elsys.robocode;

import robocode.HitByBulletEvent;
import robocode.HitWallEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

public class ChoCho extends Robot {

	@Override
	public void run() {

		
		while(true) {	
			ahead(500);	
			turnGunLeft(90);
			
			ahead(500);
			turnGunRight(90);
			
			ahead(500);	
			turnGunLeft(90);
			
			ahead(500);
			turnGunRight(90);
		}
	}
	
	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		fire(3);
		fire(0.5);
	}
	
	@Override
	public void onHitByBullet(HitByBulletEvent event) {
		turnLeft(45);
		ahead(100);
		
	}
	@Override
	public void onHitWall(HitWallEvent event) {
		turnLeft(90);
	}
	
	
}