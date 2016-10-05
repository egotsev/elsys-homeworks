package robocop;

import robocode.*;

public class robocop extends AdvancedRobot {
  double previousEnergy = 100;
  int movementDirection = 1;
  int gunDirection = 1;
  public void run() {
    setTurnGunRight(99999);
  }
  public void onScannedRobot(
    ScannedRobotEvent e) {
      
      setTurnRight(e.getBearing()+90-
         30*movementDirection);
         
     
    double changeInEnergy =
      previousEnergy-e.getEnergy();
    if (changeInEnergy>0 &&
        changeInEnergy<=3) {
         
         movementDirection =
          -movementDirection;
         setAhead((e.getDistance()/4+25));
     }
    
    gunDirection = -gunDirection;
    setTurnGunRight(99999*gunDirection);
    
    
    fire ( 2 ) ;
    
    
    previousEnergy = e.getEnergy();
  }
}
