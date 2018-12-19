package org.team1540.bigd.commands.mechanism;

import edu.wpi.first.wpilibj.command.Command;
import org.team1540.bigd.OI;
import org.team1540.bigd.Robot;
import org.team1540.bigd.Robot.MechanismState;
import org.team1540.bigd.Tuning;

public class Eject extends Command {


  public Eject() {
    super(Tuning.ejectTime);
    requires(Robot.intake);
  }

  @Override
  protected void initialize() {
    setTimeout(Tuning.ejectTime);
    // Set it based on if the arms are up or down
    Robot.intake.set(Robot.mechanismStateMachine.isInState(MechanismState.HAS_CUBE_LO) ?
        1 : -1);
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
