package org.elsys.robot;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;
import java.awt.Color;

public class Bosilka extends AdvancedRobot {
	
	private int moveDirection = 1;
	double previousEnergy = 100;
	int gunDirection = 1;
	int wallMargin = 60;
    
	public void setColors()
	{
		setBodyColor(Color.BLACK);
		setGunColor(Color.RED);
		setRadarColor(Color.WHITE);
		setBulletColor(Color.RED);
		setScanColor(Color.BLACK);
	}
	
	public void run() {
		setColors();
	     do {
	         if (getRadarTurnRemaining() == 0)
	             setTurnRadarRight(Double.POSITIVE_INFINITY);
	         execute();
	         
	     } while (true);
	 }
	 static final double FACTOR = 2.1;
	 public void onScannedRobot(ScannedRobotEvent e) {
	     double absBearing = e.getBearingRadians() + getHeadingRadians();
	     setTurnRadarRightRadians( FACTOR * robocode.util.Utils.normalRelativeAngle(absBearing - getRadarHeadingRadians()) );
	     aceInTheHole(e);
	     wallSmoothing(absBearing);
	     
	     if(getOthers() > 10)
	     {
	    	 
	    	 doCircling(e);
	    	 
	     }
	     else if(getOthers() < 10 && getOthers() > 1){
	    	 doStraffing(e);
	     }
	     else if(getOthers() == 1)
	     {
	    	 seekAndDestroy(e);
	     }
	    	
	     
	 }   
	 
	 public void wallSmoothing(double absBearing)
	 {
		 double goalDirection = absBearing-Math.PI/2*moveDirection;
	     Rectangle2D fieldRect = new Rectangle2D.Double(18, 18, getBattleFieldWidth()-36,
	         getBattleFieldHeight()-36);
	     while (!fieldRect.contains(getX()+Math.sin(goalDirection)*120, getY() + Math.cos(goalDirection)*120))
	     {
	     	goalDirection += moveDirection*.1;
	     }
	     double turn =  robocode.util.Utils.normalRelativeAngle(goalDirection-getHeadingRadians());
	     if (Math.abs(turn) > Math.PI/2)
	     {
	     	turn = robocode.util.Utils.normalRelativeAngle(turn + Math.PI);
	     	setBack(100);
	     }
	     else
	     	setAhead(100);
	     setTurnRightRadians(turn);
	     
	 }
	 
	 public void aceInTheHole(ScannedRobotEvent e)
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
		    double battleFieldHeight = getBattleFieldHeight(), battleFieldWidth = getBattleFieldWidth();
		    double predictedX = enemyX, predictedY = enemyY;
		    while((++deltaTime) * (20.0 - 3.0 * bulletPower) <   Point2D.Double.distance(myX, myY, predictedX, predictedY)){		
		    	predictedX += Math.sin(enemyHeading) * enemyVelocity;	
		    	predictedY += Math.cos(enemyHeading) * enemyVelocity;
		    	if(	predictedX < 18.0 || predictedY < 18.0 || predictedX > battleFieldWidth - 18.0 || predictedY > battleFieldHeight - 18.0){
		    		predictedX = Math.min(Math.max(18.0, predictedX), battleFieldWidth - 18.0);	
		    		predictedY = Math.min(Math.max(18.0, predictedY), battleFieldHeight - 18.0);
		    		break;
		    	}
		    }
		    double theta = Utils.normalAbsoluteAngle(Math.atan2(predictedX - getX(), predictedY - getY()));
		     
		    setTurnRadarRightRadians(Utils.normalRelativeAngle(absoluteBearing - getRadarHeadingRadians()));
		    setTurnGunRightRadians(Utils.normalRelativeAngle(theta - getGunHeadingRadians()));
		    fire(bulletPower);
	 }
	 public void seekAndDestroy(ScannedRobotEvent e) {
			    setTurnRight(e.getBearing()+90 - 30*moveDirection);
			    double changeInEnergy = previousEnergy - e.getEnergy();
			    if (changeInEnergy > 0 && changeInEnergy <= 3) {
			         moveDirection = - moveDirection;
			         setAhead((e.getDistance()/4+25) * moveDirection);
			     }
			    gunDirection = -gunDirection;
			    setTurnGunRight(99999*gunDirection);
			    
			  }
	 
	 public void doCircling(ScannedRobotEvent enemy) {
			if (getVelocity() == 0)
				moveDirection *= -1;
			setTurnRight(enemy.getBearing() + 90);
			setAhead(1000 * moveDirection);
		}

	 public void doStraffing(ScannedRobotEvent enemy) {
		 setTurnRight((enemy.getBearing() + 90 - (15 * moveDirection)));

			if (getTime() % 20 == 0) {
				moveDirection *= -1;
				setAhead(150 * moveDirection);
			}
		}
	 
	 public void onHitRobot(HitRobotEvent event) {
	       if (event.getBearing() > -90 && event.getBearing() <= 90) {
	           setBack(100);
	       } else {
	           setAhead(100);
	       }
	   }
	 
	 
	 
	
	 
}
