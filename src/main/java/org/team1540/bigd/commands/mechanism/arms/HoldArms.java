package org.team1540.bigd.commands.mechanism.arms;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.team1540.bigd.Robot;
import org.team1540.bigd.Tuning;

public class HoldArms extends Command {
  private double setpoint;
  private Timer spikeTimer;
  private boolean timerIsRunning;

  public HoldArms() {
    requires(Robot.arms);
    spikeTimer = new Timer();
  }

  @Override
  protected void initialize() {
    setpoint = Robot.arms.getPosition();
    spikeTimer.stop();
    spikeTimer.reset();
  }

  @Override
  protected void execute() {
    if (Robot.arms.getCurrent() > Tuning.armsCurrentLimit) {
      if (spikeTimer.get() <= 0) {
        spikeTimer.start();
      }
    } else {
      spikeTimer.stop();
      spikeTimer.reset();
    }

    if (Math.abs(Robot.arms.getPosition() - setpoint) > Tuning.armsMaxDeviation
        || spikeTimer.hasPeriodPassed(Tuning.armsPeakDuration)) {
      setpoint = Robot.arms.getPosition();
    }

    Robot.arms.setPosition(setpoint);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
