package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants;

public class Roller extends SubsystemBase {

  private final WPI_TalonFX m_Cuberoller = new WPI_TalonFX(Constants.CUBE_ROLLER);
  private final WPI_TalonFX m_Coneroller = new WPI_TalonFX(Constants.CONE_ROLLER);

  public CommandBase MoveCuberollers() {
    return new RunCommand(() -> {
      m_Coneroller.set(0.75);
      m_Cuberoller.set(0.75);
    }, this);
  }

  public CommandBase MoveConerollers() {
    return new RunCommand(() -> {
      m_Coneroller.set(0.75);
      m_Cuberoller.set(-0.75);
    }, this);
  }

  public CommandBase Stoprollers() {
    return new RunCommand(() -> {
      m_Coneroller.set(0);
      m_Cuberoller.set(0);
    }, this);
  }

  public CommandBase ScoreCube() {
    return new RunCommand(() -> {
      m_Coneroller.set(-1);
      m_Cuberoller.set(-1);
    }, this);
  }

  public CommandBase ScoreCone() {
    return new RunCommand(() -> {
      m_Coneroller.set(-0.55);
      m_Cuberoller.set(.55);
    }, this);
  }

  // this is hopefully gonna be a makeshift sensor for the gamepieces
  // using stall current

  public boolean doWeHaveGamePiece() {
    return false;
  }

  public Trigger havePieceTrigger() {
    return new Trigger(this::doWeHaveGamePiece);
  }

  public boolean areAllCANDevicesPresent() {
    return m_Coneroller.getBusVoltage() > 6 && m_Cuberoller.getBusVoltage() > 6;
  }
}