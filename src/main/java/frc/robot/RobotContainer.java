// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// straight copied code
package frc.robot;
import frc.robot.util.controllers.DriverController;
import frc.robot.util.controllers.FrskyDriverController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AutoBalanceSimple;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.Arm.Arm;


public class RobotContainer {
  private final SwerveDrive m_drive = new SwerveDrive();
  private final DriverController m_driverController = new FrskyDriverController(Constants.DRIVER_CONTROLLER_ID);
  private final Joystick m_Joystick = new Joystick(Constants.Joystick);
  private final Arm m_Arm = new Arm();
  private final AutoBalanceSimple autoBalanceCommand = new AutoBalanceSimple(m_drive);

  public RobotContainer() {
    configureControllers();
    configureDefaultCommands();
    configureSmartDashboardButtons();
  }

  private void configureControllers() {
    new JoystickButton(m_Joystick, 10).and(new JoystickButton(m_Joystick, 17).negate()).whileTrue(m_Arm.MoveCuberollers());
    new JoystickButton(m_Joystick, 10).and(new JoystickButton(m_Joystick, 17)).whileTrue(m_Arm.MoveConerollers());
    new JoystickButton(m_Joystick, 11).whileTrue(m_Arm.MoveArmForward());
    new JoystickButton(m_Joystick, 13).whileTrue(m_Arm.MoveArmBack());
    new JoystickButton(m_Joystick, 18).whileTrue(m_Arm.Score());
    new JoystickButton(m_Joystick,2).whileTrue(m_Arm.EndEffectorUp());
    new JoystickButton(m_Joystick, 12).whileTrue(m_Arm.EndEffectorDown());
    new JoystickButton(m_Joystick, 5).whileTrue(autoBalanceCommand);
  }

  private void configureDefaultCommands() {
    m_drive.setDefaultCommand(m_drive.grantDriveCommandFactory(m_drive, m_driverController));
    m_Arm.setDefaultCommand(m_Arm.Stoprollers());
  }


  private void configureSmartDashboardButtons() {
    SmartDashboard.putData("Reset Yaw", m_drive.resetYawCommandFactory());
    SmartDashboard.putData("Field Drive", m_drive.fieldOrientedDriveCommandFactory(m_drive, m_driverController));
    SmartDashboard.putData("Grant Drive", m_drive.grantDriveCommandFactory(m_drive, m_driverController));
    
    SmartDashboard.putData(m_drive);
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
  
}