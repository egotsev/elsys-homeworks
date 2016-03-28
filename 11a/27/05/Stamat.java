package org.elsys.robocode;

import robocode.*;
import static robocode.util.Utils.normalRelativeAngleDegrees;
import java.awt.Color;

public class Stamat extends Robot {
	int turnDirection = 1;
	
    @Override  
    public void run() {
        setBodyColor(Color.WHITE);
        setGunColor(Color.BLACK);
        setRadarColor(Color.WHITE);
        setScanColor(Color.WHITE);
        setBulletColor(Color.WHITE);
         
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
            } else if (e.getEnergy() > 0.4) {
                fire(.1);
            }
        }
        ahead(100);
    }
   
	@Override
	public void onScannedRobot(ScannedRobotEvent e) {
		System.out.println("Scanned robot event:");
		fire(3);

		if (e.getDistance() > 30) {
			fire(0.5);
		} else {
			fire(3);
		}

	}
}
