import robocode.AdvancedRobot;
import robocode.BulletHitEvent;
import robocode.BulletMissedEvent;
import robocode.HitByBulletEvent;
import robocode.HitWallEvent;
import robocode.Rules;
import robocode.ScannedRobotEvent;

public class Velioo extends AdvancedRobot {
	double prevEnergy = 100;
	int movementDirection = 1;
	int gunDirection = 1;
	int count = 0;

	public void run() {
		setTurnGunRight(50000);
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		setTurnRight(e.getBearing() + 90 - 30 * movementDirection);

		double changeInEnergy = prevEnergy - e.getEnergy();
		if (changeInEnergy > 0 && changeInEnergy <= 3) {
			movementDirection = -movementDirection;
			setAhead((e.getDistance() / 4 + 30) * movementDirection);
		}
		gunDirection = -gunDirection;
		setTurnGunRight(50000 * gunDirection);

		if (e.getDistance() < 50) {
			setTurnLeft(120);
			setBack(100);
		}
		
		if (getEnergy() > 15 && e.getDistance() <= 50)
			fire(2.5);
		else if (getEnergy() > 15 && e.getDistance() > 100)
			fire(1);
		if (e.getEnergy() == 0)
			fire(Rules.MAX_BULLET_POWER);
		if(e.getDistance() < 10)
			fire(Rules.MAX_BULLET_POWER);

		prevEnergy = e.getEnergy();
	}

	@Override
	public void onBulletMissed(BulletMissedEvent event) {
		count++;
		if (count > 20 && getEnergy() > 30)
			setAhead(100);
	}

	@Override
	public void onHitWall(HitWallEvent event) {
		setBack(150);
		turnLeft(100);
	}
	
	@Override
	public void onBulletHit(BulletHitEvent event) {
		count-=10;
	}
	
	@Override
	public void onHitByBullet(HitByBulletEvent event) {
		movementDirection = -movementDirection;
	}
}