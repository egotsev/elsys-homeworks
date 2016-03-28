import java.awt.Color;

import robocode.AdvancedRobot;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;

public class Nessy extends AdvancedRobot {
	

		boolean peek; 		// Don't turn if there's a robot there
		double moveAmount; // How much to move

		public void run() {

			setBodyColor(Color.MAGENTA);
			
			setBulletColor(Color.CYAN);
			
			setGunColor(Color.BLACK);

			setScanColor(Color.GREEN);;

			moveAmount = 4000; //100 * Math.max(getBattleFieldWidth(), getBattleFieldHeight());
 			peek = false;

			turnLeft(getHeading() % 90);
			
			ahead(moveAmount);
			// Turn the gun to turn right 90 degrees.
			peek = true;
			
			turnGunRight(90);
			
			turnRight(90);

			while (true) {

				peek = true;
				// Move up the wall
				ahead(moveAmount);
				peek = false;
				// Turn to the next wall
				turnRight(90);
			}
		}

		//public void onHitRobot(HitRobotEvent e) {
			// If he's in front of us, set back up a bit.
		//	if (e.getBearing() > -80 && e.getBearing() < 90) {
				//back(50);
			//}else {
			//	ahead(4000);
		//	}
		//}

		public void onScannedRobot(ScannedRobotEvent e) {
			fire(6);

			if (peek) {
				scan();
			}
		}
		
		public void onHitRobot(HitRobotEvent e) {
			// If we're moving the other robot, reverse!
			if (e.isMyFault()) {
				turnRight(180);
			}
	}
}
