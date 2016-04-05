package Megatron;

import java.awt.Color;
import java.awt.geom.*;
import robocode.AdvancedRobot;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

public class GalvatronMain extends AdvancedRobot {
	
	private static boolean forward = true;
	
	@Override
	public void run()
	{
		setBodyColor(Color.DARK_GRAY);
		setRadarColor(Color.GRAY);
		setAhead(Double.POSITIVE_INFINITY);
		setAdjustRadarForRobotTurn(true);
	
		setTurnRadarRight(Double.POSITIVE_INFINITY);
		while(true)
		{
			//setTurnRight(wallEvade(getX(), getY(), getBattleFieldWidth(), getBattleFieldHeight(), getHeading()));
			execute();
		}
	}
	
	void reverseDir()
	{
		if(forward)
		{
			forward = false;
			setBack(Double.POSITIVE_INFINITY);
		}
		else
		{
			forward = true;
			setAhead(Double.POSITIVE_INFINITY);
		}
	}
	
	@Override
	public void onHitWall(HitWallEvent event)
	{
		if(getGunTurnRemainingRadians() == 0)
		{
			fire(10);
		}
		reverseDir();
		fire(10);
	}
	
	@Override
	public void onScannedRobot(ScannedRobotEvent e)
	{
		double bulletPower = Math.min(3.0,getEnergy());
		double myX = getX();
		double myY = getY();
		double absoluteBearing = getHeadingRadians() + e.getBearingRadians();
		double enemyX = getX() + e.getDistance() * Math.sin(absoluteBearing);
		double enemyY = getY() + e.getDistance() * Math.cos(absoluteBearing);
		double enemyHeading = e.getHeadingRadians();
		double enemyVelocity = e.getVelocity();
		 
		 
		double deltaTime = 0;
		double battleFieldHeight = getBattleFieldHeight(), 
		       battleFieldWidth = getBattleFieldWidth();
		double predictedX = enemyX, predictedY = enemyY;
		while((++deltaTime) * (20.0 - 3.0 * bulletPower) < 
		      Point2D.Double.distance(myX, myY, predictedX, predictedY)){		
			predictedX += Math.sin(enemyHeading) * enemyVelocity;	
			predictedY += Math.cos(enemyHeading) * enemyVelocity;
			if(	predictedX < 18.0 
				|| predictedY < 18.0
				|| predictedX > battleFieldWidth - 18.0
				|| predictedY > battleFieldHeight - 18.0){
				predictedX = Math.min(Math.max(18.0, predictedX), 
		                    battleFieldWidth - 18.0);	
				predictedY = Math.min(Math.max(18.0, predictedY), 
		                    battleFieldHeight - 18.0);
				break;
			}
		}
		double theta = Utils.normalAbsoluteAngle(Math.atan2(
		    predictedX - getX(), predictedY - getY()));
		 
		setTurnRadarRightRadians(
		    Utils.normalRelativeAngle(absoluteBearing - getRadarHeadingRadians()));
		setTurnGunRightRadians(Utils.normalRelativeAngle(theta - getGunHeadingRadians()));
		fire(bulletPower);
		setTurnRadarRight(Double.POSITIVE_INFINITY);
	}
	
	@Override
	public void onHitByBullet(HitByBulletEvent e)
	{
		setTurnRight(60);
		reverseDir();
		fire(10);
	}
	
	@Override
	public void onHitRobot(HitRobotEvent e)
	{
		fire(10);
		setTurnLeft(60);
		reverseDir();
		fire(10);
	}
	
}
