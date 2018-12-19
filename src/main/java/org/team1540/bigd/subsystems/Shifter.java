package org.team1540.bigd.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team1540.bigd.RobotMap;

public class Shifter extends Subsystem {

  private Solenoid shifter = new Solenoid(RobotMap.shiftingSolenoid);

  @Override
  protected void initDefaultCommand() {

  }

  public void setHighGear(boolean isHighGear) {
    shifter.set(isHighGear);
  }
}
