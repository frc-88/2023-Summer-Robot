package frc.robot.subsystems.Arm;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants;

public class Arm {
    
    private final WPI_TalonFX m_Cuberoller = new WPI_TalonFX(Constants.CUBE_ROLLER);
    private final WPI_TalonFX m_Coneroller = new WPI_TalonFX(Constants.CONE_ROLLER);

   public void MoveRollers() {
    m_Cuberoller.set(0.5);
    }

    public CommandBase Moverollers() {
      return new InstantCommand(() -> {MoveRollers();});

    }
}