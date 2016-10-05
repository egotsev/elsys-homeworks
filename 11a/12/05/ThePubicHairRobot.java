package org.homework;

import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;

public class ThePubicHairRobot extends AdvancedRobot {

	private boolean isMovingForwards;
	private boolean isCloseToWall;

	@Override
	public void run() {

		// Setting Radar and Gun to move independently
		setAdjustRadarForRobotTurn(true);
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);

		if (checkWalls(true)) {
			isCloseToWall = true;
		} else {
			isCloseToWall = false;
		}

		setAhead(50000);
		setTurnRadarRight(360);
		isMovingForwards = true;

		while (true) {

			if (checkWalls(false) && isCloseToWall == true) {
				isCloseToWall = false;
			}
			if (checkWalls(true) && isCloseToWall == false) {
				reverseDirection();
				isCloseToWall = true;
			}

			if (getRadarTurnRemaining() == 0.0) {
				setTurnRadarRight(360);
			}

			execute();
		}
	}


	public boolean checkWalls(boolean check) {
		if (check) {
			return getX() <= 50 || getY() <= 50 || getBattleFieldWidth() - getX() <= 50
					|| getBattleFieldHeight() - getY() <= 50;
		} else {
			return getX() <= 50 && getY() <= 50 && getBattleFieldWidth() - getX() <= 50
					&& getBattleFieldHeight() - getY() <= 50;
		}
	}
	
	@Override
	public void onHitWall(HitWallEvent event) {
		reverseDirection();
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent event) {

		double headingBearing = getHeading() + event.getBearing();
		double bearingFromGun = headingBearing - getGunHeading();
		double bearingFromRadar = headingBearing - getRadarHeading();

		setTurnRight(event.getBearing() + 90);

		if (bearingFromGun <= 4) {
			setTurnGunRight(bearingFromGun);
			setTurnRadarRight(bearingFromRadar);

			if (getEnergy() > 0.2) {
				fire(event.getDistance() / 250);
			}
		} else {
			setTurnGunRight(bearingFromGun);
			setTurnRadarRight(bearingFromRadar);
		}

		if (bearingFromGun == 0) {
			scan();
		}
	}

	@Override
	public void onHitRobot(HitRobotEvent event) {
		if (event.isMyFault()) {
			reverseDirection();
		}
	}

	public void reverseDirection() {
		if (isMovingForwards) {
			setBack(50000);
			isMovingForwards = false;
		} else {
			setAhead(50000);
			isMovingForwards = true;
		}
	}
}
