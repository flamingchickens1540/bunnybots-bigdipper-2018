package org.team1540.bigd;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.team1540.bigd.Robot.MechanismState;
import org.team1540.bigd.Robot.MechanismTransitions;
import org.team1540.rooster.Utilities;
import org.team1540.rooster.preferencemanager.Preference;
import org.team1540.rooster.triggers.AxisButton;
import org.team1540.rooster.triggers.SimpleButton;
import org.team1540.rooster.util.SimpleCommand;

public class OI {

  private static final XboxController driveJoystick = new XboxController(0);
  private static final XboxController copilotJoystick = new XboxController(1);
  @Preference
  public static double defaultDeadzone = 0.1;
  @Preference
  public static double hardTurnPower = 3;

  static {
    new JoystickButton(driveJoystick, 5).whenPressed(new SimpleCommand("Set Low Gear",
        () -> Robot.shifter.setHighGear(false), Robot.shifter));
    new JoystickButton(driveJoystick, 6).whenPressed(new SimpleCommand("Set High Gear",
        () -> Robot.shifter.setHighGear(true), Robot.shifter));

    new JoystickButton(copilotJoystick, 5).whenPressed(new SimpleCommand("Fire",
        () -> Robot.mechanismStateMachine.fire(MechanismTransitions.START_INTAKE)));
    new JoystickButton(copilotJoystick, 7).whenPressed(new SimpleCommand("Fire",
        () -> Robot.mechanismStateMachine.fire(MechanismTransitions.STOP_INTAKE)));
    new JoystickButton(copilotJoystick, 8).whenPressed(new SimpleCommand("Fire",
        () -> Robot.mechanismStateMachine.fire(MechanismTransitions.RELEASE)));
    new JoystickButton(copilotJoystick, 6).whenPressed(new SimpleCommand("Fire",
        () -> Robot.mechanismStateMachine.fire(MechanismTransitions.EJECT)));
    new JoystickButton(copilotJoystick, 1).whenPressed(new SimpleCommand("Fire",
        () -> Robot.mechanismStateMachine.fire(MechanismTransitions.MOVE_LO)));
    new JoystickButton(copilotJoystick, 4).whenPressed(new SimpleCommand("Fire",
        () -> Robot.mechanismStateMachine.fire(MechanismTransitions.MOVE_HI)));
    new JoystickButton(copilotJoystick, 10).whenPressed(new SimpleCommand("Fire",
        () -> Robot.mechanismStateMachine.fire(MechanismTransitions.CUBE_POSSESSION_OVERRIDE)));
    SimpleButton leftArmGrip =
        new SimpleButton(() -> copilotJoystick.getX(Hand.kLeft) > 0.5);
    leftArmGrip.whenPressed(new SimpleCommand("Close Left", () -> {
      if (!Robot.hasCube()) {
        Robot.grips.setLeftArmIn(true);
      }
    }, Robot.grips));
    leftArmGrip.whenReleased(new SimpleCommand("Open Left", () -> {
      if (!Robot.hasCube()) {
        Robot.grips.setLeftArmIn(false);
      }
    }, Robot.grips));
    SimpleButton rightArmGrip =
        new SimpleButton(() -> copilotJoystick.getX(Hand.kRight) < -0.5);
    rightArmGrip.whenPressed(new SimpleCommand("Close Right", () -> {
      if (!Robot.hasCube()) {
        Robot.grips.setRightArmIn(true);
      }
    }, Robot.grips));
    rightArmGrip.whenReleased(new SimpleCommand("Open Right", () -> {
      if (!Robot.hasCube()) {
        Robot.grips.setRightArmIn(false);
      }
    }, Robot.grips));
//    new JoystickButton(copilotJoystick, 7).whenPressed(new SimpleCommand("Toggle Stops",
//        () -> {
//          boolean state = Robot.intake.getArmsIn();
//          if (state) {
//            Robot.mechanismStateMachine.fire(MechanismTransitions.RELEASE);
//          }
//          Robot.intake.setArmsIn(!state);
//        }));

  }

  public static double getThrottleInput() {
    return Utilities.processDeadzone(driveJoystick.getY(Hand.kLeft), defaultDeadzone);
  }

  public static double getSoftTurnInput() {
    return Utilities.processDeadzone(driveJoystick.getX(Hand.kRight), defaultDeadzone);
  }

  public static double getHardTurnInput() {
    return Utilities
        .scale((Utilities.processDeadzone(driveJoystick.getTriggerAxis(Hand.kLeft), defaultDeadzone)
        - Utilities.processDeadzone(driveJoystick.getTriggerAxis(Hand.kRight), defaultDeadzone)),
            hardTurnPower);
  }

  public static double getEjectSpeed() {
    return Utilities.processDeadzone(copilotJoystick.getTriggerAxis(Hand.kLeft), defaultDeadzone);
  }

}
