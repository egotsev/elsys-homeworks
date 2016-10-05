package org.elsys.robocode;

import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;

public class YassenAlexiev extends AdvancedRobot {
	public void run() {
		int moveAmount = (int) Math.max(getBattleFieldWidth(), getBattleFieldHeight());
		turnLeft(getHeading() % 90);
		ahead(moveAmount);
		turnGunRight(90);
		turnRight(90);

		while (true) {
			ahead(moveAmount);
			turnRight(90);
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		fire(3);
	}

	public void onHitRobot(HitRobotEvent e) {
		if (e.getBearing() > -10 && e.getBearing() < 10) {
			fire(3);
		}
		if (e.isMyFault()) {
			turnRight(10);
		}
	}
}
