package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Arm extends SubsystemBase {

  private final WPI_TalonFX m_ArmMainMain = new WPI_TalonFX(Constants.ARM_GEARBOX);
  private final WPI_TalonFX m_EndEffector = new WPI_TalonFX(Constants.END_EFFECTOR_PIVOT);
  private final DigitalInput m_coastMode = new DigitalInput(0);

  public Arm(){
    m_ArmMainMain.configOpenloopRamp(1);
    m_EndEffector.configOpenloopRamp(0.5);
    m_EndEffector.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    m_EndEffector.setSelectedSensorPosition(0);
    m_ArmMainMain.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    m_ArmMainMain.setSelectedSensorPosition(0);
    m_EndEffector.configMotionCruiseVelocity(9600);
    m_EndEffector.configMotionAcceleration(9600);
    m_ArmMainMain.configMotionAcceleration(9600);
    m_ArmMainMain.configMotionCruiseVelocity(9600);
    m_EndEffector.config_kD(0, 0.5);
    m_EndEffector.config_kP(0, 1);
    m_EndEffector.config_kF(0, 0.057);
    m_ArmMainMain.config_kD(0, 0.5);
    m_ArmMainMain.config_kP(0, 1);
    m_ArmMainMain.config_kF(0, 0.057);
    m_ArmMainMain.setNeutralMode(NeutralMode.Brake);
    m_EndEffector.setNeutralMode(NeutralMode.Brake);
  }



  public CommandBase MoveArmForward() {
    return new RunCommand(() -> m_ArmMainMain.set(-0.5), this);
  }

  public CommandBase MoveArmBack() {
    return new RunCommand(() -> m_ArmMainMain.set(0.5), this);
  }

  public CommandBase StopArm() {
    return new RunCommand(() -> m_ArmMainMain.set(0), this);
  }

  public CommandBase EndEffectorUp() {
    return new RunCommand(() -> {
      m_EndEffector.set(0.5);
    }, this);
  }

  public CommandBase EndEffectorDown() {
    return new RunCommand(() -> {
      m_EndEffector.set(-0.5);
    }, this);
  }

  public CommandBase EndEffectorStop() {
    return new RunCommand(() -> {
      m_EndEffector.set(0);
    });
  }

  public void BrakeMode() {
    m_ArmMainMain.setNeutralMode(NeutralMode.Brake);
    m_EndEffector.setNeutralMode(NeutralMode.Brake);
  }

  public void CoastMode() {
    m_ArmMainMain.setNeutralMode(NeutralMode.Coast);
    m_EndEffector.setNeutralMode(NeutralMode.Coast);
  }

  public CommandBase FreezeArm() {
    return new InstantCommand(() -> {
      m_EndEffector.set(0);
      m_ArmMainMain.set(0);
  }, this);}

  public CommandBase stow() {
    return new RunCommand(() -> {
      m_EndEffector.set(ControlMode.MotionMagic, 49489);
      m_ArmMainMain.set(ControlMode.MotionMagic, 0);
    }, this);
  }

  public CommandBase GrabCone() {
    return new RunCommand(() -> {
      m_EndEffector.set(ControlMode.MotionMagic, 135345);
      m_ArmMainMain.set(ControlMode.MotionMagic, 11669);
    }, this);
  }

  public boolean isShoulderReady() {
    if (-1000 <= m_ArmMainMain.getSelectedSensorPosition() && 
    m_ArmMainMain.getSelectedSensorPosition() <= 1000) {
      return true;
    }
    else {
      return false;
    }
  }

  public boolean isEffectorReady() {
    if (48489 <= m_EndEffector.getSelectedSensorPosition() 
    && m_EndEffector.getSelectedSensorPosition() <= 50489) {
      return true;
    }
    else {
      return false;
    }
  }

public void periodic() {
  SmartDashboard.putNumber("End Effector Encoder", m_EndEffector.getSelectedSensorPosition());
  SmartDashboard.putNumber("Arm Positon", m_ArmMainMain.getSelectedSensorPosition());
  SmartDashboard.putBoolean("Coast Mode", m_coastMode.get());
  if (m_coastMode.get()) {
    BrakeMode();
  } else {
    CoastMode();
  }
}
}