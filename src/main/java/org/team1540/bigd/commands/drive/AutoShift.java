package org.team1540.bigd.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import org.team1540.bigd.Robot;
import org.team1540.bigd.Tuning;

public class AutoShift extends Command {

  @Override
  protected void execute() {
    if (Robot.driveTrain.getPowerTelemetry().get().getCurrent() > Tuning.autoShiftCurrent || Math.abs(Robot.driveTrain.getAverageVelocity()) < Tuning.autoShiftSpeed) {
      Robot.shifter.setHighGear(false);
    }
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
