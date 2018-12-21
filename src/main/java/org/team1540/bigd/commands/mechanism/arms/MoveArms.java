package org.team1540.bigd.commands.mechanism.arms;

import edu.wpi.first.wpilibj.command.Command;
import org.team1540.bigd.Robot;
import org.team1540.bigd.Tuning;

public class MoveArms extends Command {

  private long spikeDuration;
  private long lastExecTime;
  private double target;

  public MoveArms(double target) {
    super("Move arms to " + target);
    this.target = target;
    requires(Robot.arms);
  }

  @Override
  protected void initialize() {
    spikeDuration = 0;
    lastExecTime = System.currentTimeMillis();
  }

  @Override
  protected void execute() {
    if (Robot.arms.getCurrent() > Tuning.armsCurrentLimit) {
      spikeDuration += System.currentTimeMillis() - lastExecTime;
    } else {
      spikeDuration = 0;
    }
    lastExecTime = System.currentTimeMillis();

    if (spikeDuration > Tuning.armsPeakDuration) {
      target = Robot.arms.getPosition();
    }
    Robot.arms.setPosition(target);
  }

  @Override
  protected boolean isFinished() {
    return Robot.arms.getError() < Tuning.armsTolerance
        && Math.abs(Robot.arms.getTrajectoryPosition() - target) < Tuning.armsTolerance;
  }
}
