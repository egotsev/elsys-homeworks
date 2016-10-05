package org.elsys.robocode;
import java.awt.Color;
import java.lang.String;

import robocode.AdvancedRobot;
import robocode.BulletHitEvent;
import robocode.HitByBulletEvent;
import robocode.HitWallEvent;
import robocode.RadarTurnCompleteCondition;
import robocode.ScannedRobotEvent;

public class Frisk extends AdvancedRobot {
	
	private String targetName;
	private boolean searchingTarget;
	private double oldEnergy = 0;
	private int walkDir = 1;
	private int walkDirIncr = 1;	
	private int turns = 3;
	private static final int safeDistance = 250;
	private int emptyScanns = 0;
	
	private void Init(){
		setAllColors(Color.BLACK);
		setAdjustRadarForGunTurn(true);
		targetName = null;
		searchingTarget = false;
		turnGunRight(90);
	}
	
	@Override
	public void run() {
		Init();
		
		while(true)
		{
			if(targetName == null)
			{
				FindTarget();
			}
			else
			{
				setTurnRadarRight(360);
				
				if(getX() < 10 ||
				   getY() < 10 ||
				   getX() > getBattleFieldWidth() - 10 ||
				   getY() > getBattleFieldHeight() - 10){
					TurnAround();
				}
				
				waitFor(new RadarTurnCompleteCondition(this));
			}
			
			super.run();
		}
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		if(searchingTarget){
			searchingTarget = false;
			targetName = event.getName();
			oldEnergy = event.getEnergy();
			//Get to safety
			if(event.getDistance() > safeDistance || event.getDistance() < 50){
				turnRight(event.getBearing());
				back(safeDistance - event.getDistance());
				turnLeft(90);
			}else{
				turnRight(event.getBearing() - 90);
			}
		}else if(event.getName() == targetName){
			
			double dir = Math.abs(event.getBearing()) - 90;
			
			if(event.getBearing() > 0){
				setTurnRight(dir);
			}
			else{
				setTurnLeft(dir);
			}
			
			if(event.getEnergy() < oldEnergy)
			{
				ahead(60 * Math.signum(walkDir));
				
//				setTurnLeft(15 * Math.signum(walkDir));
				
				out.println("walkDir: " + walkDir);
				if(walkDir > turns){
					walkDirIncr = -1;
					walkDir = -1;
				}else if(walkDir < -turns){
					walkDirIncr = 1;
					walkDir = 1;
				}
				
				walkDir += walkDirIncr;
			}
			
			oldEnergy = event.getEnergy();
			
			if(event.getDistance() < safeDistance + 150){
				
				if(getEnergy() > 3 || oldEnergy <= 1){
					fire(3);					
				}
				emptyScanns = 0;
			}else{
				emptyScanns++;
				
				if(emptyScanns > 8){
					FindTarget();
				}
			}
		}
		super.onScannedRobot(event);
	}
	
	@Override
	public void onBulletHit(BulletHitEvent event) {
		if(event.getName() == targetName){
			oldEnergy = event.getEnergy();
		}
		super.onBulletHit(event);
	}
	
	@Override
	public void onHitWall(HitWallEvent event) {
		TurnAround();
		super.onHitWall(event);
	}
	
	@Override
	public void onHitByBullet(HitByBulletEvent event) {
		
		super.onHitByBullet(event);
	}	
	
	private void FindTarget(){
		searchingTarget = true;
		emptyScanns = 0;
		int step = 10;
		
		for(int i = 0; i < 360/step && searchingTarget; i++)
		{
			turnRadarRight(step);
		}
	}
	
	private void TurnAround(){
		walkDir = (int)Math.signum(walkDir) * (-1);
		walkDirIncr *= -1;
		ahead(60 * Math.signum(walkDir));
	}
}