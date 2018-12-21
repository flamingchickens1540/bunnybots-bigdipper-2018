package org.team1540.bigd.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team1540.bigd.RobotMap;

public class BunnyBoi extends Subsystem {

  private Solenoid bunnyActuator = new Solenoid(RobotMap.bunnySolenoid);
  private Solenoid ballActuator = new Solenoid(RobotMap.ballSolenoid);

  @Override
  protected void initDefaultCommand() {

  }

  public void setBunnyActuator(boolean extended) {
    bunnyActuator.set(extended);
  }

  public void setBallActuator(boolean extended) {
    ballActuator.set(extended);
  }
}
