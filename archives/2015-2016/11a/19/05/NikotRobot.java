package com.rangelov.nikolay.nikot;

import java.awt.Color;

import robocode.*;
import robocode.util.Utils;

public class NikotRobot extends Robot {
	public void run() {
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForRobotTurn(true);
		setColors(null, Color.RED, Color.GREEN, Color.BLUE, Color.GRAY);
		while(true) {
			ahead(100);
			turnGunLeft(360);
			back(100);
			turnGunRight(360);
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		double distance = e.getDistance();
		double absoluteBearing = getHeading() + e.getBearing();
		turnGunRight(Utils.normalRelativeAngle(absoluteBearing - getGunHeading() + (e.getVelocity() * Math.sin(e.getHeadingRadians() - absoluteBearing) / 13.0)));
		if(distance<200){
		   fire(3.5);
		}
		else if(distance<500){
		   fire(2.5);
		}
		else if(distance<800){
		   fire(1.5);
		}
		else{
		   fire(0.5);
		}
	}
	public void onHitByBullet(HitByBulletEvent e) {
	    back(10);
	}
	public void onHitWall(HitWallEvent e) {
	    back(20);
	}   
	public void OnHitRobot(HitRobotEvent event) {
	    if (event.getBearing() > -90 && event.getBearing() <= 90){
	        back(100);
	    }
	    else{
	        ahead(100);
	    }
	}
}
