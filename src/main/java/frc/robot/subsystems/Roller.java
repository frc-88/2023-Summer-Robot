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
    private final WPI_TalonFX m_ArmMainMain = new WPI_TalonFX(Constants.ARM_GEARBOX);
    private final WPI_TalonFX m_EndEffector = new WPI_TalonFX(Constants.END_EFFECTOR_PIVOT);

    public CommandBase MoveCuberollers() {
      return new RunCommand(() -> m_Cuberoller.set(0.5), this);
    }

    public CommandBase MoveConerollers() {
        return new RunCommand(() -> m_Coneroller.set(0.5), this);
      }

      public CommandBase Stoprollers() {
             return new RunCommand(() -> {m_Coneroller.set(0); m_Cuberoller.set(0);}, this);
      }

      public CommandBase Score() {
        return new RunCommand(() -> {m_Coneroller.set(-1); m_Cuberoller.set(-1);}, this);
      }

      public CommandBase EndEffectorUp() {
        return new RunCommand(() -> {m_EndEffector.set(0.5);});
      }

      public CommandBase EndEffectorDown() {
        return new RunCommand(() -> {m_EndEffector.set(-0.5);});
      }

      public CommandBase EndEffectorStop() {
        return new RunCommand(() -> {m_EndEffector.set(0);});
      }

}