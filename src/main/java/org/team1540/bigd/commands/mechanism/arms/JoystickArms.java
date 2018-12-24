package org.team1540.bigd.commands.mechanism.arms;

import edu.wpi.first.wpilibj.command.Command;
import org.team1540.bigd.OI;
import org.team1540.bigd.Robot;
import org.team1540.bigd.Tuning;
import org.team1540.bigd.subsystems.Arms.ArmPosition;

public class JoystickArms extends Command {

  public JoystickArms() {
    requires(Robot.arms);
  }

  @Override
  protected void execute() {
    Robot.arms.setPercentOutput(OI.getWristAxis());
    if (Robot.arms.getCurrent() > Tuning.armsCurrentZeroCutoff) {
      OI.setCopilotJoystickRumble(Robot.arms.getCurrent() / Tuning.armsCurrentLimit - 1);
      if (Math.abs(Robot.arms.getPosition() - ArmPosition.HI.getPosition())
          < Tuning.armsRezeroMargin) {
        Robot.arms.setRealPosition(ArmPosition.HI.getPosition());
      } else if (Math.abs(Robot.arms.getPosition() - ArmPosition.LO.getPosition())
          < Tuning.armsRezeroMargin) {
        Robot.arms.setRealPosition(ArmPosition.LO.getPosition());
      }
    } else {
      OI.setCopilotJoystickRumble(0);
    }
  }

  @Override
  protected void end() {
    Robot.arms.setPercentOutput(0);
    OI.setCopilotJoystickRumble(0);
  }


  @Override
  protected boolean isFinished() {
    return false;
  }
}
