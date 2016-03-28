package com.elsys.robotics;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

public class FordyDu extends AdvancedRobot {
	boolean firing = false;
	boolean foundRobot = false;
	
	@Override
	public void run() {
		while (!firing) {
			turnGunLeft(90);
		}
	}
	
	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		System.out.println("Shooting.");
		double fire = (getBattleFieldWidth() / event.getDistance()) * (getEnergy() / 10);
		firing = true;
		turnGunLeft(-5);
		fire(fire);
		firing = false;
		System.out.println(event.getDistance());
	}
}
