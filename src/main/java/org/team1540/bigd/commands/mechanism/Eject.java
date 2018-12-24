package org.team1540.bigd.commands.mechanism;

import edu.wpi.first.wpilibj.command.Command;
import org.team1540.bigd.Robot;
import org.team1540.bigd.Tuning;
import org.team1540.bigd.subsystems.Arms.ArmPosition;

public class Eject extends Command {


  public Eject() {
    super(Tuning.intakeEjectTime);
    requires(Robot.intake);
  }

  @Override
  protected void initialize() {
    setTimeout(Tuning.intakeEjectTime);
    // Set it based on if the arms are up or down
    Robot.intake.set(Robot.arms.getPosition() > ArmPosition.FURTHEST_BACK.getPosition() ?
        -Tuning.intakeEjectSpeed : Tuning.intakeEjectSpeed);
  }

  @Override
  protected void end() {
    Robot.intake.set(0);
  }

  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }
}
