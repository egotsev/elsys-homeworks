import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;

public class Osama extends AdvancedRobot {
	private Map<String, Double> energyMap = new HashMap<String, Double>();
	private int forwardMove = 1;
	private int forwardGun = 1;

	@Override
	public void run() {
		setColors(Color.RED, Color.WHITE, Color.BLACK); 
		setTurnGunRight(99999);
	}
  
	@Override
	public void onScannedRobot(ScannedRobotEvent e) {
		String name = e.getName();
		if (!energyMap.containsKey(name)) {
			energyMap.put(name, 100.0);
		}
		
		setTurnRight(e.getBearing() + 90 - 30 * forwardMove);
         
		double changeInEnergy = energyMap.get(name) - e.getEnergy();
		if (changeInEnergy > 0 && changeInEnergy <= 3) {
			changeMoveDirection();
			setAhead((e.getDistance() / 3) * forwardMove);
		}

		changeGunDirection();
		setTurnGunRight(99999 * forwardGun);
		setFire(Math.min(400 / e.getDistance(), 3));
		
		energyMap.put(name, e.getEnergy());
	}

	@Override
	public void onHitWall(HitWallEvent e) {
		changeMoveDirection();
	}

	@Override
	public void onHitRobot(HitRobotEvent e) {
		changeMoveDirection();
	}

	private void changeMoveDirection() {
		forwardMove = -forwardMove;
	}
	
	private void changeGunDirection() {
		forwardGun = -forwardGun;
	}
}