package org.team1540.bigd;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team1540.rooster.preferencemanager.TuningClass;

@TuningClass("bigd_")
public class Tuning {

  public static double autoShiftCurrent = 100;
  public static double autoShiftSpeed = 10;

  public static double intakeEjectTime = 0.75;
  public static double intakeEjectSpeed = 0.8;
  public static double intakePeakingCurrent = 10;
  public static double intakeMinTime = 0.5;
  public static double intakeMaxTime = 10;
  public static double intakeSuckSpeed = 0.4;
  public static double intakeSpikingMinTime = 0.4;
  public static double intakeFurtherSpeed = 0.2;
  public static double intakeUnstickSpeed = 0.25;
  public static double intakeUnstickTime = 0.1;

  public static double armsP = 10;
  public static double armsI = 0;
  public static double armsD = 10;
  public static double armsF = 3.41;
  public static int armsIzone = 0;
  public static double armsMaxIAccum = 0;
  public static double armsCurrentLimit = 30;
  public static double armsPeakDuration = 1.5;
  public static double armsMaxDeviation = 400;
  public static double armsTolerance = 20;
  public static int armsMaxVelocity = 250;
  public static int armsMaxAcceleration = 500;
  public static double armsCurrentZeroCutoff = 70;
  public static int armsRezeroMargin = 150;
  public static double armsJoystickScale = 0.6;

  private static SendableChooser<TeleopMode> teleopModeChooser = new SendableChooser<>();

  static {
    for (TeleopMode mode : TeleopMode.values()) {
      teleopModeChooser.addObject(mode.name(), mode);
    }
    teleopModeChooser.addDefault(TeleopMode.DEFAULT.name(), TeleopMode.DEFAULT);
    SmartDashboard.putData(teleopModeChooser);
  }

  public enum TeleopMode {
    DEFAULT, MOTOR_TEST
  }

  public static TeleopMode getSelectedTeleopMode() {
    return teleopModeChooser.getSelected();
  }

}

