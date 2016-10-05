package org.elsys.robocode;

import java.awt.Color;

import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

public class BB9 extends AdvancedRobot {

	private boolean movingForward = true;
	double previousEnergy = 100;
	int movementDirection = 1;
	int gunDirection = 1;
	int direction = 1;
	
	final double firePower = 3;
	final double ramMovement = 40;
	final double criticalFirePower = 0.5;
	final double bigNumber = 99999;
	final double bounceFromWall = 90;
	final double lowEnergy = 8;
	
	public void run() {
		setColors(Color.black, Color.white, Color.blue, Color.red, Color.green);
		
		setTurnGunRight(bigNumber);
	}
	
	@Override
	public void onHitWall(HitWallEvent event) {
		reverseDirection();
	}
	
	@Override
	public void onHitRobot(HitRobotEvent event) {
		fire(firePower);
		setAhead(ramMovement);
	}
	
	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		
		setTurnRight(event.getBearing() + 90 - 30 * movementDirection);
	   
		double changeInEnergy = previousEnergy - event.getEnergy();
		dodgeBullet(changeInEnergy, event);
		
		gunDirection = -gunDirection;
		setTurnRadarRight(bigNumber * gunDirection);
		
		double absoluteBearing = getHeadingRadians() + event.getBearingRadians();
		
		setTurnGunRightRadians(Utils.normalRelativeAngle(absoluteBearing - 
			getGunHeadingRadians() + (event.getVelocity() * Math.sin(event.getHeadingRadians() - 
			absoluteBearing) / 15.0)));
		
		
		fireAtPredictedTargetPosition(event);
		
		previousEnergy = event.getEnergy();
	}
	
	void dodgeBullet(double changeInEnergy, ScannedRobotEvent event) {
		if (changeInEnergy > 0 && changeInEnergy <= 3) {
		     
		    movementDirection = -movementDirection;
		    setAhead((event.getDistance()/4 + 25) * movementDirection);
		}
	}
	
	void fireAtPredictedTargetPosition(ScannedRobotEvent event) {
		if(getGunHeat() == 0 && Math.abs(getGunTurnRemaining()) < 1) {
			
			if(getEnergy() < lowEnergy) {
				setFire(criticalFirePower);
			} else {
				setFire(Math.min(400 / event.getDistance(), firePower));
			}
		}
	}
	
	private void reverseDirection() {
		if (movingForward) {
			movingForward = false;
			
			setBack(bounceFromWall);
		} else {
			movingForward = true;
			
			setAhead(bounceFromWall);
		}
	}

}