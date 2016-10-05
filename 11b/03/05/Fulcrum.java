package org.elsys.robots;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import robocode.AdvancedRobot;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

public class Fulcrum extends AdvancedRobot {
	final static double BULLET_POWER=3;
	final static double BULLET_SPEED=20-3*BULLET_POWER;
	
	static double turn=2;
	int turnDir=1;
	int moveDir=1;
	double oldEnemyHeading;
	double oldEnergy=100;
	
@Override
	public void run() {
		// TODO Auto-generated method stub
	 	setBodyColor(Color.black);
		setGunColor(Color.black);
		setRadarColor(Color.white);
		setScanColor(Color.pink);

		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		
		do{
			turnRadarRightRadians(Double.POSITIVE_INFINITY);
		}while(true);
	}
 
@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		// TODO Auto-generated method stub
		double absBearing=event.getBearingRadians() + getHeadingRadians();
		Graphics2D g = getGraphics();
	
		//turn speed varies between 4 and 8 with a step of 2
		turn = turn + (0.2 *Math.random());
		if(turn > 8){
			turn = 2;
		}
	
		//when the enemy fires, we randomly change turn direction and whether we go forwards or backwards
		if((oldEnergy - event.getEnergy()) <= 3 && (oldEnergy-event.getEnergy()) >= 0.1){
			if(Math.random() > .5){
				turnDir = -turnDir;
			}
			if(Math.random() > .8){
				moveDir = -moveDir;
			}
		}
		
		setMaxTurnRate(turn);
		setMaxVelocity(12-turn);
		setAhead(90*moveDir);
		setTurnLeft(90*turnDir);
		oldEnergy=event.getEnergy();
		
		double enemyHeading = event.getHeadingRadians();
		double enemyHeadingChange = enemyHeading - oldEnemyHeading;
		oldEnemyHeading = enemyHeading;
 
		//Circular Targeting
		double deltaTime = 0;
		double predictedX = getX()+event.getDistance()*Math.sin(absBearing);
		double predictedY = getY()+event.getDistance()*Math.cos(absBearing);
		while((++deltaTime) * BULLET_SPEED <  Point2D.Double.distance(getX(), getY(), predictedX, predictedY)){	
 
			// Adding calculated coordinates to the enemy's current ones.
			predictedX += Math.sin(enemyHeading) * event.getVelocity();
			predictedY += Math.cos(enemyHeading) * event.getVelocity();
 
			// Changes in heading
			enemyHeading += enemyHeadingChange;
 
			//If our predicted coordinates are outside the walls, put them 18 distance units away from the walls as we know 
			//that that is the closest they can get to the wall (Bots are non-rotating 36*36 squares).
			predictedX=Math.max(Math.min(predictedX,getBattleFieldWidth()-18),18);
			predictedY=Math.max(Math.min(predictedY,getBattleFieldHeight()-18),18);
 
		}
		//Find the bearing of our predicted coordinates from us.
		double aim = Utils.normalAbsoluteAngle(Math.atan2(  predictedX - getX(), predictedY - getY()));
 
		//Aim and fire.
		setTurnGunRightRadians(Utils.normalRelativeAngle(aim - getGunHeadingRadians()));
		if (getGunHeat() == 0) {
			setFire(BULLET_POWER);
		}
		setTurnRadarRightRadians(Utils.normalRelativeAngle(absBearing-getRadarHeadingRadians())*2);
	}
}
