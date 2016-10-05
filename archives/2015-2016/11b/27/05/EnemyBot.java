package org.elsys.robots;

import robocode.Robot;
import robocode.ScannedRobotEvent;

public class EnemyBot {
	private double  bearing, 
					distance, 
					energy, 
					heading, 
					velocity,
					x,
					y;
	
	private String name;
	
	public EnemyBot() {
		this.reset();
	}
	
	public void update(ScannedRobotEvent e, Robot robot) {
		this.bearing = e.getBearing();
		this.distance = e.getDistance();
		this.energy = e.getEnergy();
		this.heading = e.getHeading();
		this.name = e.getName();
		this.velocity = e.getVelocity();
		
		double absBearingDeg = (robot.getHeading() + e.getBearing());
		if (absBearingDeg < 0) absBearingDeg += 360;
		
		// yes, you use the _sine_ to get the X value because 0 deg is North
		x = robot.getX() + Math.sin(Math.toRadians(absBearingDeg)) * e.getDistance();
		
		// yes, you use the _cosine_ to get the Y value because 0 deg is North
		y = robot.getY() + Math.cos(Math.toRadians(absBearingDeg)) * e.getDistance();
	}
	
	public void reset()
	{
		this.bearing = 0;
		this.distance =0;
		this.energy = 0;
		this.heading = 0;
		this.name = "";
		this.velocity = 0;
		this.x = 0;
		this.y = 0;
	}
	
	public double getFutureX(long when) {
		return x + Math.sin(Math.toRadians(getHeading())) * getVelocity() * when;
	}
	
	public double getFutureY(long when) {
		return y + Math.cos(Math.toRadians(getHeading())) * getVelocity() * when;
	}
	
	public boolean none() {
		return name.equals("");
	}
	
	public double getBearing() {
		return this.bearing;
	}
	public double getDistance() {
		return this.distance;
	}
	public double getEnergy() {
		return this.energy;
	}
	
	public double getHeading() {
		return this.heading;
	}
	
	public String getName() {
		return this.name;
	}
	
	public double getVelocity() {
		return this.velocity;
	}
	
	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}
}
