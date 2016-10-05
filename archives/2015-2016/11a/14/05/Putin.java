package org.elsys.robocode;

import java.awt.Color;

import robocode.Robot;
import robocode.ScannedRobotEvent;

public class Putin extends Robot {
	public Putin() {

	}

	@Override
	public void run() {
		setBodyColor(Color.YELLOW);
		setGunColor(Color.YELLOW);
		setBulletColor(Color.BLACK);
		setRadarColor(Color.BLACK);
		while (true) {
			ahead(250);
			turnLeft(30);
			turnGunRight(360);
			back(250);
			turnGunLeft(360);
			turnLeft(30);
		}
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent e) {
		System.out.println("Scanned robot event:");
		fire(3);

		if (e.getDistance() > 30) {
			fire(1);
		} else {
			fire(3);
		}

	}

}
