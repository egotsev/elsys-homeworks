package org.elsys.robocode;

import robocode.AdvancedRobot;
import robocode.HitByBulletEvent;
import robocode.ScannedRobotEvent;

public class ChuckNorris extends AdvancedRobot {
	private boolean has_seen_enemy = false;
	private double enemy_energy;
	private double enemy_direction;
	private double enemy_distance;
	public ChuckNorris() {
	}
	@Override
	public void run() {
		setTurnGunLeft(90);
		while(!has_seen_enemy) {
			setTurnRadarLeft(360);
			execute();
		}
		for(;;) {
			setTurnRadarLeft(360);
			
			double rot = enemy_direction + 90;
			rot -= getHeading();
			if(rot >= 360.0) rot -= 360.0;
			else if(rot < 0.0) rot += 360.0;
			if(rot > 180.0) setTurnLeft(360.0-rot);
			else setTurnRight(rot);
			System.out.println(enemy_direction + " " + getHeading() + " " + rot);

			if(Math.abs(getGunHeading() - enemy_direction) < 2.0) {
				if(enemy_distance < 100) fire(3.0);
				else if(enemy_distance < 200) fire(2.0);
				else if(enemy_distance < 500) fire(1.0);
			}
			execute();
		}
	}
	@Override
	public void onHitByBullet(HitByBulletEvent event) {
	}
	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		if(has_seen_enemy) {
			if(event.getEnergy() < enemy_energy) {
				double move = 50.0;
				double nx, ny;
				double angle = (2 * Math.PI) - (getHeadingRadians()+Math.PI/2);
				nx = Math.cos(angle)*move + getX();
				ny = Math.sin(angle)*move + getY();
				if(nx > 0 && ny > 0) {
					setAhead(move);
				} else {
					setBack(move);
				}
			}
		}
		enemy_energy = event.getEnergy();
		enemy_direction = getHeading() + event.getBearing();
		enemy_distance = event.getDistance();
		if(enemy_direction >= 360.0) enemy_direction -= 360.0;
		else if(enemy_direction < 0.0) enemy_direction += 360.0;
		has_seen_enemy = true;
	}
	
}
