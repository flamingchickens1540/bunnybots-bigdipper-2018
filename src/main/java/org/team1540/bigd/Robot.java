package org.team1540.bigd;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team1540.bigd.subsystems.Arms;
import org.team1540.bigd.subsystems.Arms.ArmPosition;
import org.team1540.bigd.subsystems.BunnyBallBoi;
import org.team1540.bigd.subsystems.DriveTrain;
import org.team1540.bigd.subsystems.Grips;
import org.team1540.bigd.subsystems.Intake;
import org.team1540.bigd.subsystems.Shifter;
import org.team1540.rooster.power.PowerManager;
import org.team1540.rooster.preferencemanager.PreferenceManager;
import org.team1540.rooster.testers.motor.SimpleControllersTester;
import org.team1540.rooster.util.SimpleCommand;
import org.team1540.rooster.wrappers.ChickenTalon;

public class Robot extends IterativeRobot {

  public static final DriveTrain driveTrain = new DriveTrain();
  public static Arms arms = new Arms();
  public static final Shifter shifter = new Shifter();
  public static final Intake intake = new Intake();
  public static final Grips grips = new Grips();
  public static final BunnyBallBoi bunnyBoi = new BunnyBallBoi();

  @Override
  public void robotInit() {
    LiveWindow.disableAllTelemetry();

    PreferenceManager.getInstance().add(new Tuning());

    PowerManager.getInstance().registerPowerManageable(driveTrain);
    PowerManager.getInstance()
        .setGetTotalCurrent(PowerManager.getInstance().new GetPowerFromControllersDoubleSupplier());
    PowerManager.getInstance().setRunning(false);

    Command zeroHi = new SimpleCommand("Zero hi",
        () -> Robot.arms.setRealPosition(ArmPosition.HI.getPosition()));
    zeroHi.setRunWhenDisabled(true);
    SmartDashboard.putData(zeroHi);

    Command zeroLo = new SimpleCommand("Zero lo",
        () -> Robot.arms.setRealPosition(ArmPosition.LO.getPosition()));
    zeroLo.setRunWhenDisabled(true);
    SmartDashboard.putData(zeroLo);

    UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
    camera.setResolution(128, 73);
    camera.setFPS(30);
  }

  @Override
  public void disabledInit() {
    shifter.setHighGear(true);
  }

  @Override
  public void autonomousInit() {
    bunnyBoi.setBallActuator(true);
  }

  @Override
  public void teleopInit() {

    Robot.arms.stop();
    Robot.arms.setRealPosition(ArmPosition.HI.getPosition());

//    bunnyBoi.setBallActuator(false);
    bunnyBoi.setBunnyActuator(false);

//    new AutoShift().start();

    switch (Tuning.getSelectedTeleopMode()) {
      case DEFAULT:
        break;
      case MOTOR_TEST:
        (new SimpleCommand("Stop default commands", () -> {}, driveTrain, arms, shifter)).start();
        SimpleControllersTester tester = new SimpleControllersTester(
            new ChickenTalon(1),
            new ChickenTalon(2),
            new ChickenTalon(3),
            new ChickenTalon(4),
            new ChickenTalon(5),
            new ChickenTalon(6),
            new ChickenTalon(7),
            new ChickenTalon(8),
            new ChickenTalon(9),
            new ChickenTalon(10)
        );
        tester.addAllSendables();
        tester.start();
        break;
    }

    grips.setLeftArmIn(true);
    grips.setRightArmIn(true);
  }

  @Override
  public void testInit() {

  }

  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("lCurrent", arms.getLeftCurrent());
    SmartDashboard.putNumber("rCurrent", arms.getRightCurrent());
    SmartDashboard.putNumber("lArmPos", arms.getPositionLeft());
    SmartDashboard.putNumber("rArmPos", arms.getPositionRight());
    Scheduler.getInstance().run();
  }

  @Override
  public void disabledPeriodic() {

  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopPeriodic() {
  }
}
