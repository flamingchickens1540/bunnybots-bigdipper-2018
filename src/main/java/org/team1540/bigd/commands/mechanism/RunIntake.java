package org.team1540.bigd.commands.mechanism;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.team1540.bigd.Robot;
import org.team1540.bigd.Tuning;
import org.team1540.bigd.subsystems.Arms.ArmPosition;

public class RunIntake extends Command {

  private Timer spikeTimer = new Timer();
  private boolean useStops;

  public RunIntake(boolean useStops) {
    super(Tuning.intakeMaxTime);
    requires(Robot.intake);
    this.useStops = useStops;
  }

  @Override
  protected void initialize() {
    setTimeout(Tuning.intakeMaxTime);
    if (Robot.arms.getPosition() > ArmPosition.FURTHEST_BACK.getPosition()) {
      Robot.intake.setStopsOut(false);
      Robot.intake.set(Tuning.intakeSuckSpeed);
    } else {
      Robot.intake.setStopsOut(useStops);
      Robot.intake.set(-Tuning.intakeSuckSpeed);
    }
    spikeTimer.reset();
    spikeTimer.start();
  }

  @Override
  protected void end() {
    spikeTimer.stop();
    Robot.intake.setStopsOut(false);
    Robot.intake.set(0);
  }

  @Override
  protected boolean isFinished() {
    return useStops && ((Robot.intake.isPeaking() && spikeTimer.get() > Tuning.intakeSpikingMinTime
        && timeSinceInitialized()
        > Tuning.intakeMinTime) || isTimedOut());
  }
}
