package org.team1540.bigd.commands.mechanism;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.team1540.bigd.Robot;
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
    Robot.intake.setStopsOut(false);
    Robot.intake.set(0);
  }

  @Override
  protected boolean isFinished() {
    return (Robot.intake.isPeaking() && spikeTimer.get() > Tuning.intakeSpikingMinTime && timeSinceInitialized()
         > Tuning.intakeMinTime) || isTimedOut();
  }
}
