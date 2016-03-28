package org.elsys.robocode.homework;

import java.awt.Color;

import robocode.AdvancedRobot;
import robocode.Event;
import robocode.GunTurnCompleteCondition;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.MoveCompleteCondition;
import robocode.ScannedRobotEvent;
import robocode.TurnCompleteCondition;

public class ThePulverizer extends AdvancedRobot {
	
	int direction = 2;
	int turn = 1;
	
	public boolean isHeadingRight() {
		return getHeading() > 45 && getHeading() < 135;
	}
	
	public boolean isHeadingLeft() {
		return getHeading() > 225 && getHeading() < 315;
	}
	
	public boolean isHeadingUp() {
		return (getHeading() > 315 && getHeading() <= 0) || (getHeading() >= 0 && getHeading() < 45);
	}
	
	public boolean isHeadingDown() {
		return getHeading() > 135 && getHeading() < 225;
	}
	
	public double calculateDistanceToWall() {
		double robotAngle = 0;
		double robotHeading = getHeading();
		if(90 > robotHeading) {
			robotAngle = 90 - robotHeading;
		}
		else if(180 > getHeading()) {
			robotAngle = 180 - robotHeading;
		}
		else if(270 > robotHeading) {
			robotAngle = 270 - robotHeading;
		}
		else if(360 > robotHeading) {
			robotAngle = 360 - robotHeading;
		}
		double firstLeg = 0;
		if(robotHeading > 315 && robotHeading < 360) {
			firstLeg = Math.sin(Math.toRadians(robotAngle));
		}
		else {
			firstLeg = Math.cos(Math.toRadians(robotAngle));
		}
		
		double secondLeg = 0;
		if(isHeadingLeft()) {
			secondLeg = getX();
		}
		else if(isHeadingRight()) {
			secondLeg = getBattleFieldWidth() - getX();
		}
		else if(isHeadingDown()) {
			secondLeg = getY();
		}
		else if(isHeadingUp()) {
			secondLeg = getBattleFieldWidth() - getY();
		}
		double directionLength = 0;
		directionLength = Math.sqrt(firstLeg*firstLeg + secondLeg*secondLeg);
		return directionLength;
	}
	
	@Override
	public void run() {
		setBodyColor(Color.BLACK);
		setBulletColor(Color.ORANGE);
		setGunColor(Color.DARK_GRAY);
		setAdjustGunForRobotTurn(true);
		setScanColor(Color.RED);
		//boolean initialScan = true;
		double dir = 0;
		while(true) {
			dir = calculateDistanceToWall();
			setAhead(direction*100);
			if(getDistanceRemaining() >= dir/2) {
				setTurnLeft(60*turn);
			}
			//if(initialScan) {
				setTurnGunLeft(180);
				//initialScan = false;
			//}
			waitFor(new GunTurnCompleteCondition(this));
		}
		
	}
	
	@Override
	public void onHitWall(HitWallEvent event) {
		setBack(20);
		setTurnGunLeft(180);
		if(event.getBearing() < 0) {
			setTurnRight(190 - (-event.getBearing()));
		}
		else {
			setTurnLeft(190 - event.getBearing());
		}
		waitFor(new TurnCompleteCondition(this));
	}
	
	public void changeTurn() {
		turn = -turn;
	}
	
	@Override
	public void onHitByBullet(HitByBulletEvent event) {
//		if(event.getBearing() < 0) {
//			setTurnRight(60);
//		}
//		else {
//			setTurnLeft(60);
//		}
//		setAhead(100);
		changeTurn();
		//waitFor(new MoveCompleteCondition(this));
	}
	
	public boolean enemyOnTheLeft(ScannedRobotEvent e) {
		return e.getBearing() < 0;
	}
	
	public boolean enemyTooClose(ScannedRobotEvent e) {
		return e.getDistance() < 150;
	}
	
	public boolean enemyIsHeadingRight(ScannedRobotEvent e) {
		return e.getHeading() > 45 && e.getHeading() < 135;
	}
	
	public boolean enemyIsHeadingLeft(ScannedRobotEvent e) {
		return e.getHeading() > 225 && e.getHeading() < 315;
	}
	
	public boolean enemyIsHeadingUp(ScannedRobotEvent e) {
		return (e.getHeading() > 315 && e.getHeading() <= 0) || (e.getHeading() >= 0 && e.getHeading() < 45);
	}
	
	public boolean enemyIsHeadingDown(ScannedRobotEvent e) {
		return e.getHeading() > 135 && e.getHeading() < 225;
	}
	
	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
//		if(enemyOnTheLeft(event)) {
//			setTurnGunLeft(180);
//			setTurnLeft(-event.getBearing()+event.getHeading());
//		}
//		else {
//			setTurnGunLeft(180);
//			setTurnRight(event.getBearing()+event.getHeading());
//		}
		System.out.println(event.getBearing());
		if(enemyTooClose(event)) {
			fire(3);
			if(enemyOnTheLeft(event)) {
				setTurnRight(180 - (-event.getBearing()));
			}
			else {
				setTurnLeft(180 - event.getBearing());
			}
			
			setAhead(direction*100);
			waitFor(new MoveCompleteCondition(this));
		}
		else if(event.getDistance() < 300) {
			fire(2);
			
		}
//		if(enemyOnTheLeft(event)) {
//			setTurnLeft(-event.getBearing());
//		}
//		else {
//			setTurnRight(event.getBearing());
//		}
	}

	@Override
	public void onHitRobot(HitRobotEvent event) {
		setTurnGunRight(getHeading() - getGunHeading() + event.getBearing());
		setBack(direction*100);
		if(event.getBearing() < 0) {
			setTurnRight(60);
		}
		else {
			setTurnLeft(60);
		}
		waitFor(new TurnCompleteCondition(this));
	}
}
