package org.team1540.bigd.commands.mechanism;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.team1540.bigd.Robot;
import org.team1540.bigd.Robot.MechanismState;
import org.team1540.bigd.Robot.MechanismTransitions;
import org.team1540.bigd.Tuning;

public class RunIntake extends Command {

  private Timer spikeTimer = new Timer();

  public RunIntake() {
    super(Tuning.intakeMaxTime);
    requires(Robot.intake);
  }

  @Override
  protected void initialize() {
    setTimeout(Tuning.intakeMaxTime);
    Robot.intake.setStopsOut(true);
    Robot.intake.set(-Tuning.intakeSuckSpeed);
    spikeTimer.reset();
    spikeTimer.start();
  }

  @Override
  protected void end() {
    spikeTimer.stop();
  }

  @Override
  protected boolean isFinished() {
    if (Robot.intake.isPeaking() && spikeTimer.get() > Tuning.intakeSpikingMinTime && timeSinceInitialized()
         > Tuning.intakeMinTime) {
      Robot.mechanismStateMachine.fire(MechanismTransitions.CUBE_ACQUIRED);
      return true;
    }
    // Return if it is timed out or was manually cancelled
    return isTimedOut() || Robot.mechanismStateMachine.isInState(MechanismState.NO_CUBE_LO);
  }
}
