package org.elsys;

import robocode.*;
import static robocode.util.Utils.normalRelativeAngleDegrees;
import java.awt.*;
 
public class VAC_BAN extends AdvancedRobot {
	boolean movingForward; // Is set to true when setAhead is called, set to false on setBack
	boolean inWall; // Is true when robot is near the wall.
 
	public void run() {
		// Set colors
		setBodyColor(new Color(221, 175, 19));
		setGunColor(new Color(11,77,113));
		setRadarColor(new Color(99,228,199));
		setBulletColor(new Color(255,238,0));
		setScanColor(new Color(255,241,46));
 
		// Every part of the robot moves freely from the others.
		setAdjustRadarForRobotTurn(true);
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
 
		// Check if the robot is closer than 50px from the wall.
		if (getX() <= 50 || getY() <= 50 || getBattleFieldWidth() - getX() <= 50 || getBattleFieldHeight() - getY() <= 50) {
				inWall = true;
			} else {
			inWall = false;
		}
 
		setAhead(40000); // go ahead until you get commanded to do differently
		setTurnRadarRight(360); // scan until you find your first enemy
		movingForward = true; // we called setAhead, so movingForward is true
 
		while (true) {
			/**
 			* Check if we are near the wall, and check if we have noticed (inWall boolean) yet.
			* If we have noticed yet, do nothing
			* If we have not noticed yet, reverseDirection and set inWall to true
 			* If we are out of the wall, reset inWall
 			*/
			if (getX() > 50 && getY() > 50 && getBattleFieldWidth() - getX() > 50 && getBattleFieldHeight() - getY() > 50 && inWall == true) {
				inWall = false;
			}
			if (getX() <= 50 || getY() <= 50 || getBattleFieldWidth() - getX() <= 50 || getBattleFieldHeight() - getY() <= 50 ) {
				if ( inWall == false){
					reverseDirection();
					inWall = true;
				}
			}
 
			// If the radar stopped turning, take a scan of the whole field until we find a new enemy
			if (getRadarTurnRemaining() == 0.0){
			setTurnRadarRight(360);
			}
 
			execute(); // execute all actions set.
 
		}
	}
 
 
 
	/**
	 * onHitWall:  There is a small chance the robot will still hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Bounce off!
		reverseDirection();
	}
 
	/**
	 * reverseDirection:  Switch from ahead to back & vice versa
	 */
	public void reverseDirection() {
		if (movingForward) {
			setBack(40000);
			movingForward = false;
		} else {
			setAhead(40000);
			movingForward = true;
		}
	}
 
 
	public void onScannedRobot(ScannedRobotEvent e) {
		// Calculate exact location of the robot
		double absoluteBearing = getHeading() + e.getBearing();
		double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());
		double bearingFromRadar = normalRelativeAngleDegrees(absoluteBearing - getRadarHeading());
 
		//Spiral around our enemy. 90 degrees would be circling it (parallel at all times)
		// 80 and 100 make that we move a bit closer every turn.
		if (movingForward){
			setTurnRight(normalRelativeAngleDegrees(e.getBearing() + 80));
		} else {
			setTurnRight(normalRelativeAngleDegrees(e.getBearing() + 100));
		}
 
 
		// If it's close enough, fire!
		if (Math.abs(bearingFromGun) <= 4) {
			setTurnGunRight(bearingFromGun); 
			setTurnRadarRight(bearingFromRadar); // keep the radar focussed on the enemy
			// We check gun heat here, because calling fire()
			// uses a turn, which could cause us to lose track
			// of the other robot.
 
			// The close the enmy robot, the bigger the bullet. 
			// The more precisely aimed, the bigger the bullet.
			// Don't fire us into disability, always save .1
			if (getGunHeat() == 0 && getEnergy() > .2) {
				fire(Math.min(4.5 - Math.abs(bearingFromGun) / 2 - e.getDistance() / 250, getEnergy() - .1));
			} 
		} // otherwise just set the gun to turn.
		// 
		else {
			setTurnGunRight(bearingFromGun);
			setTurnRadarRight(bearingFromRadar);
		}
		// Generates another scan event if we see a robot.
		// We only need to call this if the radar
		// is not turning.  Otherwise, scan is called automatically.
		if (bearingFromGun == 0) {
			scan();
		}
	}		
 
	/**
	 * onHitRobot:  Back up!
	 */
	public void onHitRobot(HitRobotEvent e) {
		// If we're moving the other robot, reverse!
		if (e.isMyFault()) {
			reverseDirection();
		}
	}
}