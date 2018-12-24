package org.team1540.bigd.commands.mechanism;

import edu.wpi.first.wpilibj.command.Command;
import org.team1540.bigd.Robot;
import org.team1540.bigd.Tuning;

public class IntakeFurther extends Command {

  @Override
  protected void initialize() {
    Robot.intake.set(-Tuning.intakeFurtherSpeed);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.intake.set(0);
  }
}
