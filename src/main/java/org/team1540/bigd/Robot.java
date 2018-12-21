package org.team1540.bigd;

import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.StateMachineConfig;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team1540.bigd.commands.mechanism.Eject;
import org.team1540.bigd.commands.mechanism.Release;
import org.team1540.bigd.commands.mechanism.RunIntake;
import org.team1540.bigd.subsystems.Arms;
import org.team1540.bigd.subsystems.Arms.ArmPosition;
import org.team1540.bigd.subsystems.BunnyBoi;
import org.team1540.bigd.subsystems.DriveTrain;
import org.team1540.bigd.subsystems.Grips;
import org.team1540.bigd.subsystems.Intake;
import org.team1540.bigd.subsystems.Shifter;
import org.team1540.rooster.power.PowerManager;
import org.team1540.rooster.testers.motor.SimpleControllersTester;
import org.team1540.rooster.util.SimpleCommand;
import org.team1540.rooster.wrappers.ChickenTalon;

public class Robot extends IterativeRobot {

  public static final DriveTrain driveTrain = new DriveTrain();
  public static Arms arms;
  public static final Shifter shifter = new Shifter();
  public static final Intake intake = new Intake();
  public static final Grips grips = new Grips();
  public static final BunnyBoi bunnyBoi = new BunnyBoi();

//  public static final StateMachine<MechanismState, MechanismTransitions> mechanismStateMachine;
//
//  static {
//    StateMachineConfig<MechanismState, MechanismTransitions> mechanismStateMachineConfig =
//        new StateMachineConfig<>();
//    mechanismStateMachineConfig.configure(MechanismState.NO_CUBE_LO)
//        .permit(MechanismTransitions.MOVE_HI, MechanismState.NO_CUBE_HI)
//        .permit(MechanismTransitions.START_INTAKE, MechanismState.INTAKING)
//        .permit(MechanismTransitions.CUBE_POSSESSION_OVERRIDE, MechanismState.HAS_CUBE_LO)
//        .onEntryFrom(MechanismTransitions.STOP_INTAKE, new SimpleCommand("Stop Intake",
//            () -> intake.set(0))::start)
//        .onEntryFrom(MechanismTransitions.RELEASE, new Release()::start)
//        .onEntryFrom(MechanismTransitions.EJECT, new Eject()::start)
//        .onEntryFrom(MechanismTransitions.MOVE_LO, MechanismState.setPositionBuilder(MechanismState.NO_CUBE_LO)::start);
//    mechanismStateMachineConfig.configure(MechanismState.NO_CUBE_HI)
//        .permit(MechanismTransitions.MOVE_LO, MechanismState.NO_CUBE_LO)
//        .permit(MechanismTransitions.CUBE_POSSESSION_OVERRIDE, MechanismState.HAS_CUBE_HI)
//        .onEntryFrom(MechanismTransitions.RELEASE, new Release()::start)
//        .onEntryFrom(MechanismTransitions.EJECT, new Eject()::start)
//        .onEntryFrom(MechanismTransitions.MOVE_HI,
//            MechanismState.setPositionBuilder(MechanismState.NO_CUBE_HI)::start);
//    mechanismStateMachineConfig.configure(MechanismState.INTAKING)
//        .permit(MechanismTransitions.CUBE_ACQUIRED, MechanismState.HAS_CUBE_LO)
//        .permit(MechanismTransitions.STOP_INTAKE, MechanismState.NO_CUBE_LO)
//        .onEntry(new RunIntake()::start);
//    mechanismStateMachineConfig.configure(MechanismState.HAS_CUBE_LO)
//        .permit(MechanismTransitions.MOVE_HI, MechanismState.HAS_CUBE_HI)
//        .permit(MechanismTransitions.RELEASE, MechanismState.NO_CUBE_LO)
//        .permit(MechanismTransitions.EJECT, MechanismState.NO_CUBE_LO)
//        .permit(MechanismTransitions.CUBE_POSSESSION_OVERRIDE, MechanismState.NO_CUBE_LO)
//        .onEntryFrom(MechanismTransitions.MOVE_LO,
//            MechanismState.setPositionBuilder(MechanismState.HAS_CUBE_LO)::start)
//        .onEntryFrom(MechanismTransitions.CUBE_ACQUIRED, new SimpleCommand("Something", () -> {
//          intake.set(0);
//          grips.setRightArmIn(true);
//          grips.setLeftArmIn(true);
//        }, intake, grips)::start);
//    mechanismStateMachineConfig.configure(MechanismState.HAS_CUBE_HI)
//        .permit(MechanismTransitions.MOVE_LO, MechanismState.HAS_CUBE_LO)
//        .permit(MechanismTransitions.EJECT, MechanismState.NO_CUBE_HI)
//        .permit(MechanismTransitions.RELEASE, MechanismState.NO_CUBE_HI)
//        .permit(MechanismTransitions.CUBE_POSSESSION_OVERRIDE, MechanismState.NO_CUBE_HI)
//        .onEntryFrom(MechanismTransitions.MOVE_HI,
//            MechanismState.setPositionBuilder(MechanismState.HAS_CUBE_HI)::start)
//        .onEntry(new SimpleCommand("Stops in", () -> intake.setStopsOut(false), intake)::start);
//
//    mechanismStateMachine = new StateMachine<>(MechanismState.NO_CUBE_HI, mechanismStateMachineConfig);
//    mechanismStateMachine.setShouldLog(false);
//    // Ignore undefined behavior
//    mechanismStateMachine.onUnhandledTrigger((a, b) -> {});
//  }

  @Override
  public void robotInit() {
    arms = new Arms();

    LiveWindow.disableAllTelemetry();

    PowerManager.getInstance().registerPowerManageable(driveTrain);
    PowerManager.getInstance()
        .setGetTotalCurrent(PowerManager.getInstance().new GetPowerFromControllersDoubleSupplier());
    PowerManager.getInstance().setRunning(false);

    Command zero = new SimpleCommand("Zero arms",
        () -> Robot.arms.setRealPosition(ArmPosition.HI.getPosition()));
    zero.setRunWhenDisabled(true);
    SmartDashboard.putData(zero);
  }

  @Override
  public void disabledInit() {
    shifter.setHighGear(true);
  }

  @Override
  public void autonomousInit() {
    bunnyBoi.setBallActuator(true);
    bunnyBoi.setBunnyActuator(true);
  }

  @Override
  public void teleopInit() {

    Robot.arms.stop();
    Robot.arms.setRealPosition(ArmPosition.HI.getPosition());

    bunnyBoi.setBallActuator(false);
    bunnyBoi.setBunnyActuator(false);

//    new AutoShift().start();

    switch (Tuning.getSelectedTeleopMode()) {
      case DEFAULT:
        break;
      case MOTOR_TEST:
        (new SimpleCommand("Stop default commands", () -> {}, driveTrain, arms, shifter)).start();
        SimpleControllersTester tester = new SimpleControllersTester(
            new ChickenTalon(1),
            new ChickenTalon(2),
            new ChickenTalon(3),
            new ChickenTalon(4),
            new ChickenTalon(5),
            new ChickenTalon(6),
            new ChickenTalon(7),
            new ChickenTalon(8),
            new ChickenTalon(9),
            new ChickenTalon(10)
        );
        tester.addAllSendables();
        tester.start();
        break;
    }

    grips.setLeftArmIn(true);
    grips.setRightArmIn(true);
  }

  @Override
  public void testInit() {

  }

  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("lCurrent", intake.getLeftCurrent());
    SmartDashboard.putNumber("rCurrent", intake.getRightCurrent());
    System.out.println(arms.getPositionLeft() + "," + arms.getPositionRight());
  }

  @Override
  public void disabledPeriodic() {

  }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }
}
