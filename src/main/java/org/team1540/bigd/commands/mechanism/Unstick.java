package org.team1540.bigd.commands.mechanism;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;
import org.team1540.bigd.Robot;
import org.team1540.bigd.Tuning;

public class Unstick extends CommandGroup {

  public Unstick() {
    addSequential(new TimedCommand(Tuning.intakeUnstickTime) {
      @Override
      protected void initialize() {
        Robot.intake.set(Tuning.intakeUnstickSpeed);
      }

      @Override
      protected void end() {
        Robot.intake.set(0);
      }
    });
    addSequential(new RunIntake(false));
  }

}
