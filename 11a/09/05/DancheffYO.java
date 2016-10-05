package org.elsys.robocode;

import robocode.*;
import java.awt.Color;

public class DancheffYO extends Robot {
	int turnDirection = 1;
	
    @Override  
    public void run() {
        setBodyColor(Color.BLACK);
        setGunColor(Color.BLACK);
        setRadarColor(Color.GREEN);
        setScanColor(Color.BLACK);
        setBulletColor(Color.RED);
         
		while (true) {
			ahead(250);
			turnLeft(30);
			turnGunRight(360);
			back(250);
			turnGunLeft(360);
			turnLeft(30);
			turnRight(5 * turnDirection);
		}
    }
    
   @Override
   public void onHitRobot(HitRobotEvent e) {
	   if (e.getBearing() >= 0) {
            turnDirection = 1;
        } else {
            turnDirection = -1;
        }
        turnRight(e.getBearing());
        
        fire(2);
        if(getGunHeat() == 0) {
            if (e.getEnergy() > 16) {
                fire(3);
            } else if (e.getEnergy() > 10) {
                fire(2);
            } else if (e.getEnergy() > 4) {
                fire(1);
            } else if (e.getEnergy() > 2) {
                fire(.5);
            }
        }
        ahead(50);
    }
   
	@Override
	public void onScannedRobot(ScannedRobotEvent e) {
		fire(2);

		if (e.getDistance() > 20) {
			fire(0.5);
		} else {
			fire(3);
		}

	}
}