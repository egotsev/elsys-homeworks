import robocode.*;
import robocode.util.*;

/**
 * SuperWalls - a sample robot by CrazyBassoonist based on the sample robot Walls by Mathew Nelson and maintained by Flemming N. Larsen
 * Moves around the outer edge with two targeting systems
 */
public class Billy extends AdvancedRobot {
	
	@Override
	public void run() {
		while (true) {
			turnGunRight(180);
		}
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		// mirror opponent movement
		final double headOnBearing = event.getBearingRadians() + getHeadingRadians();
		final double destinationDistanceX = getBattleFieldWidth() - Math.sin(headOnBearing) * event.getDistance() - getX() * 2;
		final double destinationDistanceY = getBattleFieldHeight() - Math.cos(headOnBearing) * event.getDistance() - getY() * 2;
		double destinationDistance = Math.sqrt(destinationDistanceX * destinationDistanceX + destinationDistanceY * destinationDistanceY);
		destinationDistance = destinationDistance > 0.001 ? destinationDistance : 0;
		double turn = (destinationDistance > 0 ? Math.atan2(destinationDistanceX, destinationDistanceY) : event.getHeadingRadians()) - getHeadingRadians();
		double ahead = Double.POSITIVE_INFINITY;
		
		if (Math.cos(turn) < 0) {
		    turn -= Math.PI;
		    ahead = Double.NEGATIVE_INFINITY;
		}
		
		setTurnRightRadians(Utils.normalRelativeAngle(turn));
		setAhead(ahead);
		setMaxVelocity(destinationDistance);
		
		double firePower = Math.min(500 / event.getDistance(), 3);

		fire(firePower);
	}

	@Override
	public void onHitByBullet(HitByBulletEvent e) {
		back(100);
	}
}