package org.team1540.bigd.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Solenoid;
import org.team1540.bigd.RobotMap;
import org.team1540.bigd.Tuning;
import org.team1540.rooster.ChickenSubsystem;
import org.team1540.rooster.wrappers.ChickenTalon;

public class Intake extends ChickenSubsystem {

  private ChickenTalon left = new ChickenTalon(RobotMap.intakeLeft);
  private ChickenTalon right = new ChickenTalon(RobotMap.intakeRight);
  private Solenoid stops = new Solenoid(RobotMap.stopsSolenoid);

  public Intake() {
    left.setControlMode(ControlMode.PercentOutput);
    right.set(ControlMode.Follower, left.getDeviceID());
    right.setInverted(true);
  }

  @Override
  protected void initDefaultCommand() {

  }

  public void set(double percentOutput) {
    left.set(percentOutput);
  }

  public boolean isPeaking() {
    return left.getOutputCurrent() > Tuning.intakePeakingCurrent
        && right.getOutputCurrent() > Tuning.intakePeakingCurrent;
  }

  public boolean getStopsOut() {
    return stops.get();
  }

  public void setStopsOut(boolean out) {
    stops.set(out);
  }

  // TODO temporary, delete me
  public double getLeftCurrent() {
    return left.getOutputCurrent();
  }

  // TODO temporary, delete me
  public double getRightCurrent() {
    return right.getOutputCurrent();
  }

}
