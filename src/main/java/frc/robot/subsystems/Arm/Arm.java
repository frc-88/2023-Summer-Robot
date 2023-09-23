package frc.robot.subsystems.Arm;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Arm extends SubsystemBase {
    
    private final WPI_TalonFX m_Cuberoller = new WPI_TalonFX(Constants.CUBE_ROLLER);
    private final WPI_TalonFX m_Coneroller = new WPI_TalonFX(Constants.CONE_ROLLER);

   public void MoveCubeRollers() {
    m_Cuberoller.set(0.5);
    }
public void MoveConeRollers() {
    
}

    public void StopRollers() {
        m_Cuberoller.set(0);
        m_Coneroller.set(0);
    }

    public CommandBase MoveCuberollers() {
      return new InstantCommand(() -> {MoveCubeRollers();});
    }

    public CommandBase Stoprollers() {
        return new InstantCommand(() -> {StopRollers();});
    }
}