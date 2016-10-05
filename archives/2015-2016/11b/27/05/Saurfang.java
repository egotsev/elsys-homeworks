package org.elsys.robots;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import robocode.AdvancedRobot;

import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;

enum Direction {
	Clockwise,
	CounterClockwise
}

public class Saurfang extends AdvancedRobot {
	private EnemyBot enemy = new EnemyBot();
	private double previousEnergy = 100;
	private Direction movementDirection = Direction.Clockwise;
	private int radarDirection = 1;
	private double firePower;
	private Rectangle2D fieldRect;
	//it takes 8 ticks for the radar to turn around
	private long radarTurnTime = 8;
	
	public void run() {
		
		setColors(Color.RED, Color.BLACK, Color.RED, Color.RED, Color.RED);
		fieldRect = new Rectangle2D.Double(18, 18, getBattleFieldWidth()-36,
			    getBattleFieldHeight()-36);
		setTurnRadarRight(99999);
		
		
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);

		while (true) {
			//if the gun is cool and we're pointed at the target, shoot!
			if (getGunHeat() == 0 && Math.abs(getGunTurnRemaining()) < 10) {
				setFire(firePower);
			}
			//sweep();
			execute();
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		// track if we have no enemy, the one we found is significantly
		// closer, or we scanned the one we've been tracking.
		if ( enemy.none() || e.getDistance() < enemy.getDistance() - 70 ||
				e.getName().equals(enemy.getName())) {
			enemy.update(e, this);
		}
		
		firePower = Math.min(400 / e.getDistance(), 3);
		//from robocode faq
		double bulletSpeed = 20 - firePower * 3;
		long bulletTime = (long)(enemy.getDistance() / bulletSpeed);
		
		double absDeg = getFutureAbsoluteDegrees(enemy, bulletTime);
		// turn the gun to the predicted x,y location
		setTurnGunRight(normalizeBearing(absDeg - getGunHeading()));
		
		dodge(absDeg);
	}
	
	double getFutureAbsoluteDegrees(EnemyBot currentEnemy, long time) {
		// calculate gun turn to predicted x,y location
		double futureX = currentEnemy.getFutureX(time);
		double futureY = currentEnemy.getFutureY(time);
		
		return absoluteBearing(getX(), getY(), futureX, futureY);
	}
	
	void dodge(double absDeg) {
		int movementDirMultiplier = 1;
		
		if (movementDirection != Direction.Clockwise) {
			movementDirMultiplier = -1;
		}
		
	    // Stay at right angles to the opponent
	    setTurnRight(enemy.getBearing()+ 90 - 30 * movementDirMultiplier);
	       
	    // If the bot has small energy drop, assume it fired
		double changeInEnergy = previousEnergy-enemy.getEnergy();
		//dodge
		if (changeInEnergy>0 && changeInEnergy<=3) {
	       toggleMovementDirection();
	       setAhead((enemy.getDistance()/4+25) * movementDirMultiplier);
		} else {
			dodgeWalls(movementDirMultiplier, absDeg);
		}
 
		previousEnergy = enemy.getEnergy();
	}
	
	void dodgeWalls(int movementDirMultiplier, double absDeg) {
		double goalDirection = absDeg - Math.PI / 2 * movementDirMultiplier;
		
		while (!fieldRect.contains(getX()+Math.sin(goalDirection)*120, getY()+
		        Math.cos(goalDirection)*120))
		{
			goalDirection += movementDirMultiplier*.1;	//turn a little toward enemy and try again
		}
		
		double turn =
			    robocode.util.Utils.normalRelativeAngle(goalDirection-getHeadingRadians());
			if (Math.abs(turn) > Math.PI/2)
			{
				turn = robocode.util.Utils.normalRelativeAngle(turn + Math.PI);
				setBack(100);
			}
			else
				setAhead(100);
			setTurnRightRadians(turn);
	}
	
	@Override
	public void onRobotDeath(RobotDeathEvent e) {
		if (e.getName() == enemy.getName()) {
			enemy.reset();
		}
	}
	
	void toggleMovementDirection() {
		if (movementDirection == Direction.Clockwise) {
			movementDirection = Direction.CounterClockwise;
		} else {
			movementDirection = Direction.Clockwise;
		}
	}
	
	// computes the absolute bearing between two points
	double absoluteBearing(double x1, double y1, double x2, double y2) {
		double xo = x2-x1;
		double yo = y2-y1;
		double hyp = Point2D.distance(x1, y1, x2, y2);
		double arcSin = Math.toDegrees(Math.asin(xo / hyp));
		double bearing = 0;

		if (xo > 0 && yo > 0) { // both pos: lower-Left
			bearing = arcSin;
		} else if (xo < 0 && yo > 0) { // x neg, y pos: lower-right
			bearing = 360 + arcSin; // arcsin is negative here, actuall 360 - ang
		} else if (xo > 0 && yo < 0) { // x pos, y neg: upper-left
			bearing = 180 - arcSin;
		} else if (xo < 0 && yo < 0) { // both neg: upper-right
			bearing = 180 - arcSin; // arcsin is negative here, actually 180 + ang
		}

		return bearing;
	}
	
	double normalizeBearing(double angle) {
		while (angle >  180) angle -= 360;
		while (angle < -180) angle += 360;
		return angle;
	}
}