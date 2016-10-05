package org.elsys.robocode;

import java.awt.Color;

import robocode.AdvancedRobot;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

public class Valio extends AdvancedRobot {
	private double fieldSize;
	private double enemyEnergy = 100;
	private int directionToMove = 1;

	private void changeDirectionRandomly() {
		double randNum = Math.random() + Math.random();
		if (randNum < 2.0) {
			setTurnLeft(randNum * 180);
			setAhead(fieldSize / randNum);
		} else {
			setTurnRight(randNum * 180);
			setBack(randNum * fieldSize / 3);
		}
	}

	@Override
	public void run() {
		fieldSize = getBattleFieldWidth();
		setBodyColor(Color.BLACK);
		setGunColor(Color.WHITE);
		setRadarColor(Color.BLUE);
		setBulletColor(Color.YELLOW);
		setScanColor(Color.CYAN);
		setAdjustRadarForRobotTurn(true);
		setAdjustGunForRobotTurn(true);
		setTurnRadarRight(Double.POSITIVE_INFINITY);
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		setTurnRight(event.getBearing() + 90 - 30 * directionToMove);
		double energy = enemyEnergy - event.getEnergy();

		if (energy > 0 && energy <= 3) {
			directionToMove = -directionToMove;
			setAhead((event.getDistance() / 4 + 25) * directionToMove);
		}

		double velo = event.getVelocity();
		double absoluteBearing = getHeadingRadians() + event.getBearingRadians();
		double radians = Utils.normalRelativeAngle(absoluteBearing - getGunHeadingRadians());

		setTurnGunRightRadians(radians);
		setTurnLeftRadians(radians);

		if (velo == 0) {
			setFire(2.5);
		} else {
			double distance = event.getDistance();
			if (distance < fieldSize / 2 && distance > fieldSize / 2.5) {
				setFire(0.8);
			} else {
				if (distance < fieldSize / 2.5 && distance > fieldSize / 3.2) {
					setFire(1.5);
				} else {
					if (distance < fieldSize / 3.2) {
						setFire(2.2);
					}
				}
			}
		}

		enemyEnergy = event.getEnergy();
	}

	@Override
	public void onHitByBullet(HitByBulletEvent event) {
		setBack(fieldSize / 5);
		setTurnRight(directionToMove * 180);
		setAhead(fieldSize / 4);
		setTurnRight(90);
		changeDirectionRandomly();
	}

	@Override
	public void onHitWall(HitWallEvent event) {
		setTurnRight(90);
		changeDirectionRandomly();
	}

	@Override
	public void onHitRobot(HitRobotEvent event) {
		setTurnRight(90);
		changeDirectionRandomly();
	}
}
