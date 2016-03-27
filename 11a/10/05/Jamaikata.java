package org.elsys.robocode;

import java.awt.Color;

import robocode.AdvancedRobot;
import robocode.HitWallEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

public class Jamaikata extends AdvancedRobot {

	@Override
	public void run() {
		setBodyColor(Color.GREEN);
		setGunColor(Color.GREEN);
		setBulletColor(Color.BLACK);
		setRadarColor(Color.GREEN);
		while (true) {
			ahead(250);
			setTurnGunRight(360);
			back(250);
			setTurnGunLeft(360);
		}
	}
	
	@Override
	public void onHitWall(HitWallEvent event) {
	
		super.onHitWall(event);
		if(getX() < getWidth())
		{
			turnRight((90 - getHeading()));
		}
		else if((getX() + getWidth()) > getBattleFieldWidth())
		{
			turnLeft((90 + getHeading()));
		}
		else if((getY() + getHeight()) > getBattleFieldHeight())
		{
			turnRight((180 - getHeading()));
		}
		else if((getY() < getHeight()))
		{
			turnLeft(getHeading());
		}
		ahead(100);
				
			
	}
	
	@Override
	public void onScannedRobot(ScannedRobotEvent e)
	{
		System.out.println("Scanned robot event:");
		fire(3);
		
		if(e.getDistance() > 42)
		{
			fire(1);
		}
		else
		{
			fire(3);
		}
		
		
		
	}
}
