package org.team1540.bigd.subsystems;

import org.team1540.bigd.RobotMap;
import org.team1540.rooster.ChickenSubsystem;
import org.team1540.rooster.wrappers.ChickenTalon;

public class Arms extends ChickenSubsystem {

  private ChickenTalon leftMotor = new ChickenTalon(RobotMap.armLeft);
  private ChickenTalon rightMotor = new ChickenTalon(RobotMap.armRight);

  public Arms() {
    leftMotor.setBrake(true);
    rightMotor.setBrake(true);
  }

  public void zero() {
    leftMotor.setQuadraturePosition(0);
    rightMotor.setQuadraturePosition(0);
  }

  @Override
  public void initDefaultCommand() {
    // Button based position setting in OI
  }

  public void setPosition(int quadPosition) {
//    leftMotor.set(ControlMode.MotionMagic, quadPosition);
//    rightMotor.set(ControlMode.MotionMagic, quadPosition);
  }
}
