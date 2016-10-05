package org.elsys.robocode;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import robocode.util.Utils;
import robocode.AdvancedRobot;
import robocode.Rules;
import robocode.ScannedRobotEvent;

public class Martron2k extends AdvancedRobot {

	final Map<String, EnemyRobot> enemyMap;
	double previousEnergy = 100;
	int movementDirection = 1;
	double radarDirection = 1;
	EnemyRobot oldest;
	EnemyRobot bestTarget;
	
	public Martron2k() {
		enemyMap = new LinkedHashMap<String, EnemyRobot>(5, 2, true);
	}
	
	@Override
	public void run() {
		initialize();
		
		for(;;) {
			sweep();
			fireGun();
			scan();
		}
	}
	
	private void fireGun() {
		chooseTarget();
		
		if(bestTarget != null) {
			double targetBearing = Utils.normalRelativeAngle((Math.atan2((bestTarget.targetX - getX()), (bestTarget.targetY - getY()) ))
																										- getGunHeadingRadians());
			setTurnGunRightRadians(targetBearing / 2);
			
			double dist = Math.hypot(bestTarget.targetX - getX(), bestTarget.targetY - getY());
			double coverAngle = Math.atan(18 / dist);
			
			if(Math.abs(getGunTurnRemainingRadians()) < coverAngle) {
				setFire(3);
			}
		}
	}

	private void chooseTarget() {
		List<EnemyRobot> possibleTargets = new ArrayList<EnemyRobot>(enemyMap.values());
		bestTarget = null;
		
		double minDist = Double.POSITIVE_INFINITY;
		double dist;
		for(EnemyRobot robot : possibleTargets) {
			dist = Math.hypot(robot.targetX - getX(), robot.targetY - getY());
			if(dist < minDist) {
				minDist = dist;
				bestTarget = robot;
			}
		}
		
		/*if(bestTarget == null && possibleTargets.size() > 0) {
			bestTarget = possibleTargets.get(0);
		}*/
		
	}

	@Override
	public void onHitWall(robocode.HitWallEvent event) {
		if (movementDirection != 0) {
			setBack(100);
			movementDirection = 0;
		} else {
			setAhead(100);
			movementDirection = 1;
		}
	};
	
	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		if(!event.isSentryRobot()) {
			updateMap(event);
			updateScanDir(event);
			updateTargetPositions();
		}
		setTurnRight(event.getBearing()+90 - 30*movementDirection);
		double changeInEnergy = previousEnergy-event.getEnergy();
		if (changeInEnergy>0 && changeInEnergy<=3) {
			movementDirection = -movementDirection;
			setAhead((event.getDistance()/4 + 25));
		}
		
		previousEnergy = event.getEnergy();
	};
	
	private void updateTargetPositions() {
		// http://robowiki.net/wiki/Linear_Targeting
		for(EnemyRobot tmp : enemyMap.values()) {
			double bulletVelocity = Rules.getBulletSpeed(3);
			double enemyX = tmp.scannedX;
			double enemyY = tmp.scannedY;
			double enemyVelocity = tmp.scannedVelocity / 6;
			double enemyHeading = tmp.scannedHeading;
			
			double A = ((enemyX - getX()) / bulletVelocity );
			double B = ((enemyY - getY()) / bulletVelocity );
			double C = enemyVelocity / bulletVelocity * Math.sin(enemyHeading);
			double D = enemyVelocity / bulletVelocity * Math.cos(enemyHeading);
			
			double a = A * A + B * B;
			double b = 2 * (A * C + B * D);
			double c = (C * C + D * D - 1);
			
			double discr = b*b - 4*a*c;
			if (discr >= 0) {
				double t1 = (2*a) / ((-b) + Math.sqrt(discr));
				double t2 = (2*a) / ((-b) - Math.sqrt(discr));
				
				double t = Math.min(t1, t2) >= 0 ? Math.min(t1, t2) : Math.max(t1, t2);
				
				double targetX = enemyX + enemyVelocity * t * Math.sin(enemyHeading);
				double targetY = enemyY + enemyVelocity * t * Math.cos(enemyHeading);
				
				double minX = 18;
				double minY = 18;
				double maxX = getBattleFieldWidth() - minX;
				double maxY = getBattleFieldHeight() - minY;
				
				tmp.targetX = Math.min(maxX, (Math.max(targetX, minX)));
				tmp.targetY = Math.min(maxY, (Math.max(targetY, minY)));
			}
		}
	}

	private void updateMap(ScannedRobotEvent event) {
		final String scannedName = event.getName();
		
		EnemyRobot tmp = enemyMap.get(scannedName);
		if(tmp != null) {
			tmp.update(event);
		} else {
			tmp = new EnemyRobot(event);
			enemyMap.put(scannedName, tmp);
		}
				
	}

	private void updateScanDir(ScannedRobotEvent event) {
		final String robotName = event.getName();
		if((oldest == null) || robotName.equals(oldest.name) && enemyMap.size() == getOthers()) {
			EnemyRobot tmpOldest = enemyMap.values().iterator().next();
			double x = tmpOldest.scannedX;
			double y = tmpOldest.scannedY;
			
			double myHeading = getRadarHeadingRadians();
			double bearing = Utils.normalRelativeAngle((Math.atan2(x - getX(), y - getY())) - myHeading);
			
			radarDirection = bearing;
		}
		
	}

	private void sweep() {
		setTurnRadarRightRadians(radarDirection * Double.POSITIVE_INFINITY);
	}
	
	private void initialize() {
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		setBodyColor(Color.GREEN);
		setGunColor(Color.LIGHT_GRAY);
	}
	
	
	class EnemyRobot {
		final String name; 
		double scannedX; 
		double scannedY; 
		double scannedVelocity;
		double scannedHeading; 
		double targetX; 
		double targetY; 

		EnemyRobot(ScannedRobotEvent event) {
			name = event.getName();
			update(event);
			targetX = scannedX;
			targetY = scannedY;
		}

		void update(ScannedRobotEvent event) {
			Point2D.Double pos = getPosition(event);
			scannedX = pos.x;
			scannedY = pos.y;
			scannedVelocity = event.getVelocity();
			scannedHeading = event.getHeadingRadians();
		}

		Point2D.Double getPosition(ScannedRobotEvent event) {
			double distance = event.getDistance();
			double angle = getHeadingRadians() + event.getBearingRadians();

			double x = getX() + Math.sin(angle) * distance;
			double y = getY() + Math.cos(angle) * distance;

			return new Point2D.Double(x, y);
		}
	}
}

