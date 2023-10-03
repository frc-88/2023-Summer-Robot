// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// straight copied code
package frc.robot;

import frc.robot.util.controllers.ButtonBox;
import frc.robot.util.controllers.DriverController;
import frc.robot.util.controllers.FrskyDriverController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.AutoBalanceSimple;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Lights;
import frc.robot.subsystems.Roller;

public class RobotContainer {
  private CommandBase m_autoCommand = new WaitCommand(15);
  private String m_autoCommandName = "Wait";

  private final SwerveDrive m_drive = new SwerveDrive();
  private final DriverController m_driverController = new FrskyDriverController(Constants.DRIVER_CONTROLLER_ID);
  private final Roller m_Roller = new Roller();
  // private final Joystick m_Joystick = new Joystick(Constants.Joystick);
  private final Arm m_Arm = new Arm();
  private final ButtonBox m_buttonBox = new ButtonBox(Constants.BUTTON_BOX_ID);
  private final Lights m_candleSubsystem = new Lights(m_drive, m_Arm, () -> m_autoCommandName);
  AutoBalanceSimple autoBalanceCommand = new AutoBalanceSimple(m_drive);

  public RobotContainer() {
    configureControllers();
    configureDefaultCommands();
    configureSmartDashboardButtons();
  }

  public void teleopInit() {
    m_Arm.FreezeArm().schedule();
  }

  public void enableInit() {
    if (m_buttonBox.gamepieceSwitch.getAsBoolean()) {
      m_candleSubsystem.wantConeFactory().schedule();
    } else {
      m_candleSubsystem.wantCubeFactory().schedule();
    }
  }

  /*
   * /* private void configureControllers() {
   * new JoystickButton(m_Joystick, 10).and(new JoystickButton(m_Joystick,
   * 17).negate()).whileTrue(m_Arm.MoveCuberollers());
   * new JoystickButton(m_Joystick, 10).and(new JoystickButton(m_Joystick,
   * 17)).whileTrue(m_Arm.MoveConerollers());
   * new JoystickButton(m_Joystick, 11).whileTrue(m_Arm.MoveArmForward());
   * new JoystickButton(m_Joystick, 13).whileTrue(m_Arm.MoveArmBack());
   * new JoystickButton(m_Joystick, 18).whileTrue(m_Arm.Score());
   * new JoystickButton(m_Joystick,2).whileTrue(m_Arm.EndEffectorUp());
   * new JoystickButton(m_Joystick, 12).whileTrue(m_Arm.EndEffectorDown());
   * new JoystickButton(m_Joystick, 5).whileTrue(autoBalanceCommand);
   * }
   */

  private void configureDefaultCommands() {
    m_drive.setDefaultCommand(m_drive.grantDriveCommandFactory(m_drive, m_driverController));
    m_Arm.setDefaultCommand(m_Arm.stow());
    m_Roller.setDefaultCommand(m_Roller.Stoprollers());

  }

  private void configureSmartDashboardButtons() {
    SmartDashboard.putData("Reset Yaw", m_drive.resetYawCommandFactory());
    SmartDashboard.putData("Field Drive", m_drive.fieldOrientedDriveCommandFactory(m_drive, m_driverController));
    SmartDashboard.putData("Grant Drive", m_drive.grantDriveCommandFactory(m_drive, m_driverController));

    SmartDashboard.putData(m_drive);
  }

  private void configureControllers() {



    // LOW
    m_buttonBox.setLow.and(m_buttonBox.gamepieceSwitch)
        .whileTrue(m_Arm.ScoreConeLow());
    m_buttonBox.setLow.and(m_buttonBox.gamepieceSwitch.negate())
        .whileTrue(m_Arm.ScoreCubeLow());

    // MID
    m_buttonBox.setMiddle.and(m_buttonBox.gamepieceSwitch)
        .whileTrue(m_Arm.ScoreConeMid());
    m_buttonBox.setMiddle.and(m_buttonBox.gamepieceSwitch.negate())
        .whileTrue(m_Arm.ScoreCubeMid());

    // HIGH
    m_buttonBox.setHigh.and(m_buttonBox.gamepieceSwitch)
        .whileTrue(m_Arm.ScoreConeHigh());
    m_buttonBox.setHigh.and(m_buttonBox.gamepieceSwitch.negate())
        .whileTrue(m_Arm.ScoreCubeHigh());

    // GRAB FROM CHUTE
    m_buttonBox.setHigh.and(m_buttonBox.gamepieceSwitch)
        .whileTrue(m_Arm.GrabChuteCone()).whileTrue(m_Arm.GrabCone());
    m_buttonBox.setHigh.and(m_buttonBox.gamepieceSwitch.negate())
        .whileTrue(m_Arm.GrabChuteCube()).whileTrue(m_Arm.GrabCube());

    // SCORE
    m_buttonBox.scoreButton.or(m_driverController.getScoreButton()).and(m_buttonBox.gamepieceSwitch)
        .whileTrue(m_Roller.ScoreCone());
    m_buttonBox.scoreButton.or(m_driverController.getScoreButton()).and(m_buttonBox.gamepieceSwitch.negate())
        .whileTrue(m_Roller.ScoreCube());

    // END EFFECTOR ROTATE
    m_buttonBox.indicateMid.whileTrue(m_Arm.EndEffectorUp());
    m_buttonBox.indicateHigh.whileTrue(m_Arm.EndEffectorDown());

    // INTAKE
    m_buttonBox.intakeButton.or(m_driverController.getScoreButton()).and(m_buttonBox.gamepieceSwitch)
        .whileTrue(m_Roller.MoveConerollers()).whileTrue(m_Arm.GrabCone());
    m_buttonBox.intakeButton.or(m_driverController.getScoreButton()).and(m_buttonBox.gamepieceSwitch.negate())
        .whileTrue(m_Roller.MoveCuberollers()).whileTrue(m_Arm.GrabCube());

    // lights
    m_buttonBox.gamepieceSwitch.and(m_Roller.havePieceTrigger().negate())
        .whileTrue(m_candleSubsystem.wantConeFactory());
    m_buttonBox.gamepieceSwitch.and(m_Roller.havePieceTrigger())
        .whileTrue(m_candleSubsystem.holdingConeFactory());
    m_buttonBox.gamepieceSwitch.negate().and(m_Roller.havePieceTrigger().negate()
        .whileTrue(m_candleSubsystem.wantCubeFactory()));
    m_buttonBox.gamepieceSwitch.negate().and(m_Roller.havePieceTrigger())
        .whileTrue(m_candleSubsystem.holdingCubeFactory());
  }

  // public void disabledPeriodic() {
  // if (m_buttonBox.intakeButton.getAsBoolean() &&
  // !m_autoCommandName.equals("Wait")) {
  // m_autoCommand = new WaitCommand(15);
  // m_autoCommandName = "Wait";
  // }

  // if (m_buttonBox.setHigh.getAsBoolean() &&
  // !m_autoCommandName.equals("Engage")) {
  // m_autoCommand = Autonomous.engage(m_drive, m_intake, m_arm, m_grabber,
  // m_coprocessor);
  // m_autoCommandName = "Engage";
  // }

  // if (m_buttonBox.handoffButton.getAsBoolean() &&
  // !m_autoCommandName.equals("Center2Balance")) {
  // m_autoCommand = Autonomous.center2HalfBalance(m_drive, m_intake, m_arm,
  // m_grabber, m_candleSubsystem, m_aiming, m_coprocessor);
  // m_autoCommandName = "Center2Balance";
  // }

  // if (m_buttonBox.setLow.getAsBoolean() &&
  // !m_autoCommandName.equals("Center3")) {
  // m_autoCommand = Autonomous.center3(m_drive, m_intake, m_arm, m_grabber,
  // m_candleSubsystem, m_aiming, m_coprocessor);
  // m_autoCommandName = "Center3";
  // }

  // if (m_buttonBox.getFromShelfButton.getAsBoolean() &&
  // !m_autoCommandName.equals("Center3Half")) {
  // m_autoCommand = Autonomous.center3Half(m_drive, m_intake, m_arm, m_grabber,
  // m_candleSubsystem, m_aiming, m_coprocessor);
  // m_autoCommandName = "Center3Half";
  // }

  // if (m_buttonBox.scoreButton.getAsBoolean() &&
  // !m_autoCommandName.equals("Center3Balance")) {
  // m_autoCommand = Autonomous.center3Balance(m_drive, m_intake, m_arm,
  // m_grabber, m_candleSubsystem, m_aiming, m_coprocessor);
  // m_autoCommandName = "Center3Balance";
  // }

  // if (m_buttonBox.setMiddle.getAsBoolean() &&
  // !m_autoCommandName.equals("Charge1Half")) {
  // m_autoCommand = Autonomous.charge1HalfBalance(m_drive, m_intake, m_arm,
  // m_grabber, m_candleSubsystem, m_coprocessor);
  // m_autoCommandName = "Charge1Half";
  // }

  // if (m_buttonBox.getFromChuteButton.getAsBoolean() &&
  // !m_autoCommandName.equals("Charge1Mobility")) {
  // m_autoCommand = Autonomous.charge1MobilityBalance(m_drive, m_intake, m_arm,
  // m_grabber, m_candleSubsystem, m_coprocessor);
  // m_autoCommandName = "Charge1Mobility";
  // }

  // if (m_buttonBox.setFlat.getAsBoolean() && !m_autoCommandName.equals("Wall3"))
  // {
  // m_autoCommand = Autonomous.wall3(m_drive, m_intake, m_arm, m_grabber,
  // m_candleSubsystem, m_aiming, m_coprocessor);
  // m_autoCommandName = "Wall3";
  // }

  // SmartDashboard.putString("Auto", m_autoCommandName);
  // }

  public Command getAutonomousCommand() {
    return m_autoCommand;
  }

}