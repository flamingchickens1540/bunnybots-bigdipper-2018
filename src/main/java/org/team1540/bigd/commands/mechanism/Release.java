package org.team1540.bigd.commands.mechanism;

import org.team1540.bigd.Robot;
import org.team1540.rooster.util.SimpleCommand;

public class Release extends SimpleCommand {

  public Release() {
    super("Release", () -> {
      Robot.grips.setLeftArmIn(false);
      Robot.grips.setRightArmIn(false);
    }, Robot.intake);
  }

}
