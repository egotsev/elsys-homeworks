import robocode.*;
import robocode.util.Utils;

import java.awt.Color;
import java.util.Hashtable;
import java.util.Enumeration;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D;

public class BlackPirate extends AdvancedRobot {
	private static double myEnergy;
	private static Point2D.Double futurePos;
	private static Point2D.Double myPos;
	private BadBoy objetivo;
	private static Point2D.Double prevPos;
	private static Hashtable<String, BadBoy> enemies = new Hashtable<String, BadBoy>();

	public class BadBoy {
		public Point2D.Double pos;
		public double energy;
		public boolean hp;
	}

	public void run() {

		setColors(Color.PINK, Color.CYAN, Color.WHITE);
		setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
		setAdjustRadarForGunTurn(true);
		setAdjustGunForRobotTurn(true);

		futurePos = prevPos = myPos = new Point2D.Double(getX(), getY());
		objetivo = new BadBoy();

		while (true) {
			myPos = new Point2D.Double(getX(), getY());
			myEnergy = getEnergy();

			if (objetivo.hp && getTime() > 7) {
				this.trackAndFire();
			}

			execute();
			scan();
		}
	}

	private static Point2D.Double hallarPunto(Point2D.Double p, double dist,
			double ang) {
		return new Point2D.Double(p.x + dist * Math.sin(ang), p.y + dist
				* Math.cos(ang));
	}

	private static double angulo(Point2D.Double p2, Point2D.Double p1) {

		return Math.atan2(p2.x - p1.x, p2.y - p1.y);
	}

	public void trackAndFire() {
		double theta;
		double distanciaObjetivo = myPos.distance(objetivo.pos);
		Rectangle2D.Double perimetro = new Rectangle2D.Double(30, 30,
				getBattleFieldWidth() - 60, getBattleFieldHeight() - 60);

		if (getGunTurnRemaining() == 0 && myEnergy > 1) {
			setFire(Math.min(
					Math.min(myEnergy / 6.0, 1000 / distanciaObjetivo),
					objetivo.energy / 3.0));
		}

		setTurnGunRightRadians(Utils.normalRelativeAngle(angulo(objetivo.pos,
				myPos) - getGunHeadingRadians()));

		double distNextPunto = myPos.distance(futurePos);

		if (distNextPunto > 20) {

			theta = angulo(futurePos, myPos) - getHeadingRadians();

			double sentido = 1;

			if (Math.cos(theta) < 0) {
				theta += Math.PI;
				sentido = -1;
			}

			setAhead(distNextPunto * sentido);
			theta = Utils.normalRelativeAngle(theta);
			setTurnRightRadians(theta);

			if (theta > 1)
				setMaxVelocity(0.0);
			else
				setMaxVelocity(8.0); // max

		} else {

			int iterNum = 1000;
			Point2D.Double cand;
			for (int i = 0; i < iterNum; i++) {

				cand = hallarPunto(
						myPos,
						Math.min(distanciaObjetivo * 0.8,
								100 + iterNum * Math.random()), 2 * Math.PI
								* Math.random());
				if (perimetro.contains(cand)
						&& evalHeuristic(cand) < evalHeuristic(futurePos)) {
					futurePos = cand;
				}

			}

			prevPos = myPos;

		}
	}

	public static double evalHeuristic(Point2D.Double p) {

		double eval = 0.1 / p.distanceSq(prevPos);

		Enumeration<BadBoy> enumVar = enemies.elements();
		while (enumVar.hasMoreElements()) {
			BadBoy enemy = enumVar.nextElement();

			if (enemy.hp) {
				eval += Math.min(enemy.energy / myEnergy, 2)
						*

						(1 + Math.abs(Math.cos(angulo(p, myPos)
								- angulo(enemy.pos, p))))
						/ p.distanceSq(enemy.pos);
			}
		}
		return eval;
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		BadBoy en = (BadBoy) enemies.get(e.getName());

		if (en == null) {
			en = new BadBoy();
			enemies.put(e.getName(), en);
		}

		en.hp = true;
		en.energy = e.getEnergy();
		en.pos = hallarPunto(myPos, e.getDistance(),
				getHeadingRadians() + e.getBearingRadians());

		if (!objetivo.hp || e.getDistance() < myPos.distance(objetivo.pos)) {
			objetivo = en;
		}

		double radarTurn = getHeadingRadians() + e.getBearingRadians() - getRadarHeadingRadians();
		setTurnRadarRightRadians(2.0 * Utils.normalRelativeAngle(radarTurn));

	}

	public void onRobotDeath(RobotDeathEvent e) {
		((BadBoy) enemies.get(e.getName())).hp = false;
	}

}