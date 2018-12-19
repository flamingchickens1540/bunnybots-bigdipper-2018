package org.team1540.bigd;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team1540.rooster.preferencemanager.TuningClass;

@TuningClass("bigd")
public class Tuning {

  public static double ejectTime = 0.75;
  public static double autoShiftCurrent = 100;
  public static double autoShiftSpeed = 10;
  public static double intakePeakingCurrent = 7.5;
  public static double intakeMinTime = 0.5;
  public static double intakeMaxTime = 10;
  public static double intakeSuckSpeed = 0.3;
  public static double intakeSpikingMinTime = 0.75;
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

