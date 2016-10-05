package org.elsys.robocode;

import java.awt.Color;

import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

public class MalkiqMuk extends Robot {
	public MalkiqMuk() {
		
	}
	int forward = 1;
	@Override
	public void run() {
		setBodyColor(Color.RED);
		setBulletColor(Color.CYAN);
		while(true) {
			ahead(forward * 100);
			turnGunRight(180);
			back(100);
			turnGunLeft(360);
		}
	}
	@Override
	public void onHitRobot(HitRobotEvent event) {
		if(getEnergy() < 70) {
			fire(2);
		}else {
			fire(3);
		}
		
	}

	
	@Override
	public void onHitByBullet(HitByBulletEvent event) {
		ahead(150);
	}
	@Override
	public void onHitWall(HitWallEvent event) {
		forward = -1;
	}
	
	@Override
	public void onScannedRobot(ScannedRobotEvent e) {
		fire(1);
		if(e.getDistance() < 50) {
			fire(3);
		} else {
			fire(2);
		}
	}


}
