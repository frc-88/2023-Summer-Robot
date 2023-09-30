package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Roller extends SubsystemBase {
    
    private final WPI_TalonFX m_Cuberoller = new WPI_TalonFX(Constants.CUBE_ROLLER);
    private final WPI_TalonFX m_Coneroller = new WPI_TalonFX(Constants.CONE_ROLLER);

    public CommandBase MoveCuberollers() {
      return new RunCommand(() -> {
        m_Coneroller.set(0.5);
        m_Cuberoller.set(0.5);
      }, this);
    }
  
    public CommandBase MoveConerollers() {
      return new RunCommand(() -> {
        m_Coneroller.set(0.5);
        m_Cuberoller.set(-0.5);
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
        System.out.println("Cube Cube Cube!");
      }, this);
    }
  
    public CommandBase ScoreCone() {
      return new RunCommand(() -> {
        m_Coneroller.set(-1);
        m_Cuberoller.set(1);
        System.out.println("Cone Cone Cone!");
      }, this);
    }
  }  