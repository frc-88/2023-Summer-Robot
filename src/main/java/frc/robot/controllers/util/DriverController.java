package frc.robot.controllers.util;

import edu.wpi.first.wpilibj2.command.button.Trigger;

public interface DriverController {
    
    public double getTranslationX();

    public double getTranslationY();

    public double getRotation();

    public Trigger getScoreButton();

    public Trigger getPivotButton();
}