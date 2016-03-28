import robocode.*;

import java.awt.Color;
public class WeakBomb extends AdvancedRobot {
  double previousEnergy = 100;
  int movementDirection = 1;
  int gunDirection = 1;
  int missCounter = 0;
  double enemyDistance = 0;
  double bearing;
  
  public void run() {
	 setColors(Color.RED, Color.DARK_GRAY, Color.GREEN, Color.red, Color.cyan);  
	 setTurnGunRight(99999);
	 if (getVelocity() <= 1){
	    	setAhead((enemyDistance/ 4 + 30)* movementDirection);
	    }
	 
  }
  public void onScannedRobot(ScannedRobotEvent e) {
	  bearing = e.getBearing();
	  // Stay at right angles to the opponent
      setTurnRight(bearing+90-30*movementDirection);
     // setTurnRight(e.getBearing()+90-30*movementDirection);
     // If the bot has small energy drop,
    // assume it fired
     
    double changeInEnergy = previousEnergy-e.getEnergy();
    if (changeInEnergy>0 && changeInEnergy<=30) {
         // Dodge!                           3
         movementDirection = -movementDirection;
         setAhead((enemyDistance/ 4 + 100)* movementDirection);
     }
    
    gunDirection = -gunDirection;
    setTurnGunRight(999999 * gunDirection);
    if(missCounter == 0){
    	 fire ( 1/enemyDistance * 10000);
    }

    fire ( (1/enemyDistance * 10000) / missCounter) ;
    
    previousEnergy = e.getEnergy();
  }
  @Override
	public void onBulletHit(BulletHitEvent event) {
		missCounter--;;
		//fireBullet(enemyDistance/0.5);
		super.onBulletHit(event);
	}
  @Override
	public void onBulletMissed(BulletMissedEvent event) {
		missCounter++;
		if(missCounter > 2){
			
			//setTurnRight(90-30 * -movementDirection);
			setAhead((enemyDistance/ 4 + 100)* movementDirection);
		}
		super.onBulletMissed(event);
	}
  
  	@Override
  		public void onHitByBullet(HitByBulletEvent event) {
  		setTurnLeft((enemyDistance/4+25)* movementDirection);
  			super.onHitByBullet(event);
  	}
  	@Override
  		public void onHitWall(HitWallEvent event) {
  			movementDirection= -movementDirection;
  			super.onHitWall(event);
  	}
}
