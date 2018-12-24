package org.team1540.bigd.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team1540.bigd.RobotMap;

public class Grips extends Subsystem {

  private Solenoid leftActuator = new Solenoid(RobotMap.leftArmSolenoid);
  private Solenoid rightActuator = new Solenoid(RobotMap.rightArmSolenoid);

  @Override
  protected void initDefaultCommand() {

  }

  public void setLeftArmIn(boolean in) {
    leftActuator.set(in);
  }

  public void setRightArmIn(boolean in) {
    rightActuator.set(in);
  }
  
  public boolean getLeftArmIn() {
    return leftActuator.get();
  }

  public boolean getRightArmIn() {
    return rightActuator.get();
  }

}
