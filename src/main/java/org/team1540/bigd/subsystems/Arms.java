package org.team1540.bigd.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import org.team1540.bigd.RobotMap;
import org.team1540.bigd.Tuning;
import org.team1540.rooster.ChickenSubsystem;
import org.team1540.rooster.wrappers.ChickenTalon;

public class Arms extends ChickenSubsystem {

  private ChickenTalon leftMotor = new ChickenTalon(RobotMap.armLeft);
  private ChickenTalon rightMotor = new ChickenTalon(RobotMap.armRight);

  public Arms() {
    leftMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    rightMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

    leftMotor.setInverted(true);
    leftMotor.setSensorPhase(true);
    rightMotor.setInverted(false);
    rightMotor.setSensorPhase(true);
    
    leftMotor.setBrake(true);
    leftMotor.config_kP(0, Tuning.armsP);
    leftMotor.config_kI(0, Tuning.armsI);
    leftMotor.config_kD(0, Tuning.armsD);
    leftMotor.config_kF(0, Tuning.armsF);
    leftMotor.config_IntegralZone(0, Tuning.armsIzone);
    leftMotor.configMaxIntegralAccumulator(0, Tuning.armsMaxIAccum);
    leftMotor.configMotionCruiseVelocity(Tuning.armsMaxVelocity);
    leftMotor.configMotionAcceleration(Tuning.armsMaxAcceleration);
    rightMotor.setBrake(true);
    rightMotor.config_kP(0, Tuning.armsP);
    rightMotor.config_kI(0, Tuning.armsI);
    rightMotor.config_kD(0, Tuning.armsD);
    rightMotor.config_kF(0, Tuning.armsF);
    rightMotor.config_IntegralZone(0, Tuning.armsIzone);
    rightMotor.configMaxIntegralAccumulator(0, Tuning.armsMaxIAccum);
    rightMotor.configMotionCruiseVelocity(Tuning.armsMaxVelocity);
    rightMotor.configMotionAcceleration(Tuning.armsMaxAcceleration);
  }

  @Override
  public void initDefaultCommand() {
    // Button based position setting in OI
  }

  public void setPosition(double position) {
    leftMotor.set(ControlMode.MotionMagic, position);
    rightMotor.set(ControlMode.MotionMagic, position);
  }

  public int getPositionLeft() {
    return (int) rightMotor.getSelectedSensorPosition();
  }

  public int getPositionRight() {
    return (int) rightMotor.getSelectedSensorPosition();
  }

  public int getPosition() {
    return (int) (leftMotor.getSelectedSensorPosition() + rightMotor.getSelectedSensorPosition()) / 2;
  }

  public double getCurrent() {
    return (leftMotor.getOutputCurrent() + rightMotor.getOutputCurrent()) / 2;
  }

  public double getError() {
    return ((float) leftMotor.getClosedLoopError() + (float) rightMotor.getClosedLoopError()) / 2;
  }

  public double getTrajectoryPosition() {
    // Left and right should be the same
    return leftMotor.getActiveTrajectoryPosition();
  }

  public void setRealPosition(int position) {
    leftMotor.setSelectedSensorPosition(position);
    rightMotor.setSelectedSensorPosition(position);
  }

  public void stop() {
    leftMotor.set(ControlMode.PercentOutput, 0);
    rightMotor.set(ControlMode.PercentOutput, 0);
  }

  public enum ArmPosition {

    LO(0), HI(726), FURTHEST_BACK(363);

    private int position;

    ArmPosition(int position) {
      this.position = position;
    }

    public int getPosition() {
      return position;
    }
  }
}
