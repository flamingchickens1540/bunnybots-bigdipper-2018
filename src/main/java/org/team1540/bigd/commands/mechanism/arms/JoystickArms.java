package org.team1540.bigd.commands.mechanism.arms;

import edu.wpi.first.wpilibj.command.Command;
import org.team1540.bigd.OI;
import org.team1540.bigd.Robot;

public class JoystickArms extends Command {

  public JoystickArms() {
    requires(Robot.arms);
  }

  @Override
  protected void execute() {
    Robot.arms.setPosition(OI.getWristAxis());
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
