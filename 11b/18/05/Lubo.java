package elsys.org.robocode;

import java.awt.geom.Point2D;
import java.util.Calendar;

import robocode.AdvancedRobot;
import robocode.BulletHitEvent;
import robocode.GunTurnCompleteCondition;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

public class Lubo extends AdvancedRobot {
	
	private boolean changeside = false;
	private double ahead = 1; 
	private double fire = 0;
	private boolean lowenergy = false;
	  
	
	@Override
	public void run() {
		while (true) {
			setAhead(ahead * 200);
			ahead += 5;
			if (changeside) {
				setTurnLeftRadians(Calendar.SECOND);
			} else {
				setTurnRightRadians(Calendar.SECOND);
			}
			waitFor(new GunTurnCompleteCondition(this));
		}
	}

	
	
	@Override
	public void onHitRobot(HitRobotEvent event) {
		turnRight(event.getBearing());
		fire(getEnergy() / 2);
	}
	@Override
	public void onBulletHit(BulletHitEvent event) {
		fire = getEnergy() / 3;
		lowenergy = true;
		ahead = ahead * 2;
	}
	
	@Override
	public void onHitWall(HitWallEvent event) {
		changeside = true;
		ahead = -ahead / 2;
	}
	@Override
	public void onHitByBullet(HitByBulletEvent event) {
		ahead = -ahead;
		changeside = false;
	}
	
	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		if (!lowenergy) {
			fire = getEnergy() / 2;
		}
		fire(fire);
	}
}