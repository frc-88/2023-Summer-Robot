package frc.robot.subsystems.Arm;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Arm extends SubsystemBase {
    
    private final WPI_TalonFX m_Cuberoller = new WPI_TalonFX(Constants.CUBE_ROLLER);
    private final WPI_TalonFX m_Coneroller = new WPI_TalonFX(Constants.CONE_ROLLER);
    private final WPI_TalonFX m_ArmMain = new WPI_TalonFX(Constants.ARM_GEARBOX);
    
    public CommandBase MoveArmForward() {
       return new RunCommand(() -> m_ArmMain.set(0.5), this);
       }
    
       public CommandBase MoveArmBack() {
        return new RunCommand(() -> m_ArmMain.set(0.5), this);
       }

       public CommandBase StopArm() {
        return new RunCommand(() -> m_ArmMain.set(0), this);
       }

    public CommandBase MoveCuberollers() {
      return new RunCommand(() -> m_Cuberoller.set(0.5), this);
    }

    public CommandBase MoveConerollers() {
        return new RunCommand(() -> m_Coneroller.set(0.5), this);
      }

      public CommandBase Stoprollers() {
             return new RunCommand(() -> {m_Coneroller.set(0); m_Cuberoller.set(0);}, this);
      }



}