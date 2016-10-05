package org.elsys.robots;

import java.awt.Color;
import java.awt.geom.Point2D;

import robocode.*;
import robocode.util.Utils;

public class Mitko extends AdvancedRobot {

	final static double BULLET_POWER=3;
	final static double BULLET_DAMAGE=BULLET_POWER*4;
	final static double BULLET_SPEED=20-3*BULLET_POWER;

	static double dir=1;
	static double oldEnemyHeading;
	static double enemyEnergy;


	public void run(){
		setBodyColor(Color.pink);
		setGunColor(Color.white);
		setRadarColor(Color.green);

		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
	}
	public void onScannedRobot(ScannedRobotEvent e){
		double absBearing=e.getBearingRadians()+getHeadingRadians();

		double turn=absBearing+Math.PI/2;

		turn-=Math.max(0.5,(1/e.getDistance())*100)*dir;

		setTurnRightRadians(Utils.normalRelativeAngle(turn-getHeadingRadians()));

		if(enemyEnergy>(enemyEnergy=e.getEnergy())){
			if(Math.random()>200/e.getDistance()){
				dir=-dir;
			}
		}

		setMaxVelocity(400/getTurnRemaining());

		setAhead(100*dir);

		double enemyHeading = e.getHeadingRadians();
		double enemyHeadingChange = enemyHeading - oldEnemyHeading;
		oldEnemyHeading = enemyHeading;

		double deltaTime = 0;
		double predictedX = getX()+e.getDistance()*Math.sin(absBearing);
		double predictedY = getY()+e.getDistance()*Math.cos(absBearing);
		while((++deltaTime) * BULLET_SPEED <  Point2D.Double.distance(getX(), getY(), predictedX, predictedY)){
			predictedX += Math.sin(enemyHeading) * e.getVelocity();
			predictedY += Math.cos(enemyHeading) * e.getVelocity();
			enemyHeading += enemyHeadingChange;
			predictedX=Math.max(Math.min(predictedX,getBattleFieldWidth()-18),18);
			predictedY=Math.max(Math.min(predictedY,getBattleFieldHeight()-18),18);

		}
		double aim = Utils.normalAbsoluteAngle(Math.atan2(  predictedX - getX(), predictedY - getY()));
		setTurnGunRightRadians(Utils.normalRelativeAngle(aim - getGunHeadingRadians()));
		setFire(BULLET_POWER);
		setTurnRadarRightRadians(Utils.normalRelativeAngle(absBearing-getRadarHeadingRadians())*2);
	}
	public void onBulletHit(BulletHitEvent e){
		enemyEnergy-=BULLET_DAMAGE;
	}
	public void onHitWall(HitWallEvent e){
		dir=-dir;
	}
}
