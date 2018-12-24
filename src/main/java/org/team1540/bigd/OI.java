package org.team1540.bigd;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.team1540.bigd.commands.mechanism.Eject;
import org.team1540.bigd.commands.mechanism.IntakeFurther;
import org.team1540.bigd.commands.mechanism.Release;
import org.team1540.bigd.commands.mechanism.RunIntake;
import org.team1540.bigd.commands.mechanism.Unstick;
import org.team1540.bigd.commands.mechanism.arms.JoystickArms;
import org.team1540.bigd.commands.mechanism.arms.MoveArms;
import org.team1540.bigd.subsystems.Arms.ArmPosition;
import org.team1540.rooster.Utilities;
import org.team1540.rooster.preferencemanager.Preference;
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

    new JoystickButton(copilotJoystick, 5).whileHeld(new RunIntake(false));
    new JoystickButton(copilotJoystick, 7).whenPressed(new SimpleCommand("Stop Intake",
        () -> Robot.intake.set(0), Robot.intake));
    new SimpleButton(() -> copilotJoystick.getPOV() == 0).whenPressed(new Unstick());
    new SimpleButton(() -> copilotJoystick.getPOV() == 180).whileHeld(new IntakeFurther());

    new JoystickButton(copilotJoystick, 8).whenPressed(new Release());
    new JoystickButton(copilotJoystick, 6).whenPressed(new Eject());

    new JoystickButton(copilotJoystick, 1).whenPressed(new MoveArms(ArmPosition.LO.getPosition()));
    new JoystickButton(copilotJoystick, 4).whenPressed(new MoveArms(ArmPosition.HI.getPosition()));
    new SimpleButton(() -> getWristAxis() != 0).whileHeld(new JoystickArms());

    new JoystickButton(copilotJoystick, 2).whenPressed(new SimpleCommand("Toggle Grips", () -> {
      if (Robot.grips.getLeftArmIn() != Robot.grips.getRightArmIn()) {
        Robot.grips.setLeftArmIn(false);
        Robot.grips.setRightArmIn(false);
      } else {
        Robot.grips.setLeftArmIn(!Robot.grips.getLeftArmIn());
        Robot.grips.setRightArmIn(!Robot.grips.getRightArmIn());
      }
    }));
    SimpleButton leftArmGrip =
        new SimpleButton(() -> copilotJoystick.getX(Hand.kLeft) < -0.5);
    leftArmGrip.whenPressed(new SimpleCommand("Open Left Grip",
        () -> Robot.grips.setLeftArmIn(false), Robot.grips));
    leftArmGrip.whenReleased(new SimpleCommand("Close Left Grip",
        () -> Robot.grips.setLeftArmIn(true), Robot.grips));
    SimpleButton rightArmGrip =
        new SimpleButton(() -> copilotJoystick.getX(Hand.kRight) > 0.5);
    rightArmGrip.whenPressed(new SimpleCommand("Open Right Grip",
        () -> Robot.grips.setRightArmIn(false)));
    rightArmGrip.whenReleased(new SimpleCommand("Close Right Grip",
        () -> Robot.grips.setRightArmIn(true)));

    new JoystickButton(copilotJoystick, 8).whenPressed(new SimpleCommand(
        "Toggle Bunny", () -> Robot.bunnyBoi.setBunnyActuator(!Robot.bunnyBoi.getBunnyActuator())));
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

  public static double getWristAxis() {
    return Tuning.armsJoystickScale * (
        Utilities.processDeadzone(copilotJoystick.getTriggerAxis(Hand.kLeft),
            0.5) - Utilities.processDeadzone(copilotJoystick.getTriggerAxis(Hand.kRight), 0.5));
//    return Utilities.processDeadzone(copilotJoystick.getY(Hand.kLeft), 0.2);
  }

  public static void setCopilotJoystickRumble(double rumble) {
    copilotJoystick.setRumble(RumbleType.kLeftRumble, rumble);
    copilotJoystick.setRumble(RumbleType.kRightRumble, rumble);
  }

}
