package org.team1540.bigd.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team1540.bigd.OI;
import org.team1540.bigd.RobotMap;
import org.team1540.rooster.ChickenSubsystem;
import org.team1540.rooster.drive.pipeline.AdvancedArcadeJoystickInput;
import org.team1540.rooster.drive.pipeline.CTREOutput;
import org.team1540.rooster.drive.pipeline.DriveData;
import org.team1540.rooster.drive.pipeline.Processor;
import org.team1540.rooster.drive.pipeline.TankDriveData;
import org.team1540.rooster.util.SimpleCommand;
import org.team1540.rooster.wrappers.ChickenTalon;

public class DriveTrain extends ChickenSubsystem {

  private ChickenTalon l1 = new ChickenTalon(RobotMap.leftTop);
  private ChickenTalon l2 = new ChickenTalon(RobotMap.leftForward);
  private ChickenTalon l3 = new ChickenTalon(RobotMap.leftBack);
  private ChickenTalon r1 = new ChickenTalon(RobotMap.rightTop);
  private ChickenTalon r2 = new ChickenTalon(RobotMap.rightForward);
  private ChickenTalon r3 = new ChickenTalon(RobotMap.rightBack);
  private CTREOutput ctreOutput = new CTREOutput(l1, r1, false);

  public DriveTrain() {
    // Slave the talons
    l2.set(ControlMode.Follower, this.l1.getDeviceID());
    l3.set(ControlMode.Follower, this.l1.getDeviceID());
    r2.set(ControlMode.Follower, this.r1.getDeviceID());
    r3.set(ControlMode.Follower, this.r1.getDeviceID());
    l1.setInverted(true);
    l2.setInverted(true);
    l3.setInverted(true);
    
    l1.setBrake(true);
    l2.setBrake(true);
    l3.setBrake(true);
    r1.setBrake(true);
    r2.setBrake(true);
    r3.setBrake(true);
  }

  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new SimpleCommand("Drive", new AdvancedArcadeJoystickInput(1, 0.64,
        OI::getThrottleInput, OI::getSoftTurnInput, OI::getHardTurnInput).then((Processor<TankDriveData, TankDriveData>) ((data)-> new TankDriveData(
        new DriveData(
            data.left.position,
            data.left.velocity,
            data.left.acceleration,
            data.left.velocity
        ),
        new DriveData(
            data.right.position,
            data.right.velocity,
            data.right.acceleration,
            data.right.velocity
        ), data.heading, data.turningRate
    ))).then(getCtreOutput()), this));

//    setDefaultCommand(new SimpleCommand("Drive", () -> {
//      l1.set(ControlMode.PercentOutput, OI.getThrottleInput());
//      r1.set(ControlMode.PercentOutput, OI.getThrottleInput());
//    }, this));

    // Button based shifting in OI
  }

  public CTREOutput getCtreOutput() {
    return ctreOutput;
  }

  public double getAverageVelocity() {
    return (l1.getQuadratureVelocity() + r1.getQuadratureVelocity()) / 2;
  }

}
