package org.elsys.robocode;

import java.awt.geom.Rectangle2D;
import robocode.*;
import robocode.util.Utils;

public class ExT extends AdvancedRobot {
	public static int direction=1;
	public double bulletspeed=11;
	public static boolean randomMode;
	public double lastenergy;

	private static final StringBuffer pattern = new StringBuffer();
	
	@Override
	public void run() {
        setAdjustRadarForGunTurn(true);
        setAdjustGunForRobotTurn(true);
        while(true){
        	pattern.insert(0,"\u8000");
        	turnRadarRightRadians(Double.POSITIVE_INFINITY);
        }
	}
	
	@Override
	public void onHitByBullet(HitByBulletEvent e) {
		bulletspeed = e.getVelocity();
	}
	
	@Override
	public void onDeath(DeathEvent e) {
		if (getRoundNum() <= 2)
			randomMode = true;
	}
	
	@Override
	public void onScannedRobot(ScannedRobotEvent e) {
		double absb;
		double tmpvalue;
		double enemyDist = e.getDistance();
		if (randomMode && (Math.random() < (-2.3366658461737655E-01 * Math.exp(-1.6310550343898985E+01 / (enemyDist/bulletspeed + -9.7811055805086689E-01)) + 2.3437149121234324E-01)))
			direction = -direction;

		absb = getHeadingRadians() + e.getBearingRadians();
		double offset;
		do {
			offset = Math.PI/1.75;
			while (!new Rectangle2D.Double(18, 18, 764, 564).contains(
					getX()+Math.sin(tmpvalue = absb+direction*(offset -= 0.05))*130,
					getY()+Math.cos(tmpvalue)*130));
			if (offset < Math.PI/3) {
				direction = -direction;
				continue;
			}
		} while(false);
		tmpvalue -= getHeadingRadians();
		
		setTurnRightRadians(Math.tan(tmpvalue));
		
		if (randomMode || lastenergy > e.getEnergy())
			setAhead((Math.abs(tmpvalue) > Math.PI/2)?-60:60);
		lastenergy = e.getEnergy();

		double power = Math.min(3, Math.max(0.1, Math.min(Math.min(900 / enemyDist, getEnergy()/10), lastenergy/4)));

		tmpvalue = e.getHeadingRadians() - absb;
		pattern.insert(0,(char)(
				(byte)(enemyDist/600)|
				(32*(byte)(e.getVelocity()*Math.cos(tmpvalue)+8))|
				(1024*(byte)(e.getVelocity()*Math.sin(tmpvalue)+8))
		));
		
		int bins[] = new int[31+30];
		
		int keyLength = Math.min(pattern.length(), Math.min(50, pattern.indexOf("\u8000") - 1));
		int index1 = keyLength;
		int index2;
		char char1;
		int patternsFound=0;
		int patternsPassed=0;
		int bestgf=15;
		do {
			index1 = pattern.indexOf(pattern.substring(0, keyLength), index1+1);
			if (index1 < 0) {
				index1 = --keyLength;
			} else {
				index2 = index1;
				double bulletdistance=0;
				double angd=0;
				tmpvalue=enemyDist;
				do {
					char1 = pattern.charAt(index2--);
					if (char1 == 0x8000)
						break;
					angd += Math.atan((char1/1024 - 8) / tmpvalue);
					tmpvalue += (char1&0x1F)/32 - 8;
				} while ((bulletdistance+=(offset = Rules.getBulletSpeed(power))) < tmpvalue && index2 > 0);
				tmpvalue = Math.asin(8.0 / offset)/15.5;
				if(index2 != 0 && char1 != 0x8000) {
					index2 = (int)Math.round(angd/tmpvalue)+15;
					bins[index2]+= keyLength;
					if (bins[index2] > bins[bestgf])
						bestgf = index2;
					patternsFound++;
					setFire(power);
				}
				patternsPassed++;
			}
		} while(keyLength > 0 && patternsFound < 200 && patternsPassed < 1000);

		setTurnGunRightRadians(Utils.normalRelativeAngle(absb + (bestgf-15)*tmpvalue - getGunHeadingRadians()));
		setTurnRadarRightRadians(Math.sin(absb - getRadarHeadingRadians())*2);
	}
}