// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.util.preferenceconstants.DoublePreferenceConstant;
import frc.robot.util.preferenceconstants.IntPreferenceConstant;

import java.lang.reflect.Field;
import java.sql.Driver;
import java.util.function.Supplier;

import com.ctre.phoenix.led.*;
import com.ctre.phoenix.led.CANdle.LEDStripType;
import com.ctre.phoenix.led.CANdle.VBatOutputMode;
import com.ctre.phoenix.led.ColorFlowAnimation.Direction;
import com.ctre.phoenix.led.LarsonAnimation.BounceMode;
import com.ctre.phoenix.led.TwinkleAnimation.TwinklePercent;

/*
 *     everyone's talkin'
 * 'bout purple and yellow but
 *   tie dye rocks the house
 */

public class Lights extends SubsystemBase {
    private IntPreferenceConstant numLEDs = new IntPreferenceConstant("Number of LEDs", 60);
    private int m_state = 0;
    private int counter = 0;
    private final CANdle m_candle = new CANdle(Constants.CANDLE_ID);
    private boolean m_animDirection = false;
    private boolean m_setAnim = false;
    private DoublePreferenceConstant dangerAngle = new DoublePreferenceConstant("Danger Angle", 5.0);
    private double m_acceptableDifference = 0.25;

    private Animation m_toAnimate = null;
    private Animation m_lastAnimation = null;
    private Animation m_strobe = null;

    private SwerveDrive m_swerve;
    private Arm m_arm;
    private Roller m_Roller;
    private Supplier<String> m_autoName;

    public enum AnimationTypes {
        ColorFlow,
        Fire,
        Larson,
        Rainbow,
        RgbFade,
        SingleFade,
        Strobe,
        Twinkle,
        TwinkleOff,
        SetAll,
        Empty
    }

    public Lights(SwerveDrive swerve, Arm arm, Supplier<String> autoName) {
        m_swerve = swerve;
        m_arm = arm;
        m_autoName = autoName;
        CANdleConfiguration configAll = new CANdleConfiguration();
        configAll.statusLedOffWhenActive = false;
        configAll.disableWhenLOS = false;
        configAll.stripType = LEDStripType.BRG;
        configAll.brightnessScalar = 1.0;
        configAll.vBatOutputMode = VBatOutputMode.On;
        m_candle.configAllSettings(configAll, 100);
    }

    public void wantCone() {
        m_toAnimate = new StrobeAnimation(255, 200, 0, 0, 0.2, numLEDs.getValue(), 0);
        m_setAnim = true;
    }

    public void holdingCone() {
        m_toAnimate = new ColorFlowAnimation(255, 200, 0, 0, 0.2, numLEDs.getValue(), Direction.Forward);
        m_setAnim = true;
    }

    public void wantCube() {
        m_toAnimate = new StrobeAnimation(100, 0, 120, 0, 0.2, numLEDs.getValue(), 0);
        m_setAnim = true;
    }

    public void holdingCube() {
        m_toAnimate = new ColorFlowAnimation(100, 0, 120, 0, 0.2, numLEDs.getValue(), Direction.Forward);
        m_setAnim = true;
    }

    public void larsonColor(int r, int g, int b) {
        m_toAnimate = new LarsonAnimation(r, g, b, 0, 0.1, numLEDs.getValue(), BounceMode.Front, 5, 0);
        m_setAnim = true;
    }

    public void rainbow() {
        m_toAnimate = new RainbowAnimation(1, 0.7, numLEDs.getValue(), m_animDirection, 0);
        m_setAnim = true;
    }

    public void strobe(int r, int g, int b) {
        m_toAnimate = new StrobeAnimation(r, g, b, 0, 0.2, numLEDs.getValue(), 0);
        m_setAnim = true;
    }

    public void breathing(int r, int g, int b) {
        m_toAnimate = new SingleFadeAnimation(r, g, b, 0, 0.2, numLEDs.getValue(), 0);
        m_setAnim = true;
    }

    public void twinklyStars(int r, int g, int b) {
        m_toAnimate = new TwinkleAnimation(r, g, b, 0, 0.2, numLEDs.getValue(), TwinklePercent.Percent42, 0);
        m_setAnim = true;
    }

    public void fireThingIDK() {
        m_toAnimate = new FireAnimation(1, 0.2, numLEDs.getValue(), 0.2, 0.2, m_animDirection, 0);
        m_setAnim = true;
    }

    public void colorFlowThingIDK(int r, int g, int b) {
        m_toAnimate = new ColorFlowAnimation(r, g, b, 0, 0.2, numLEDs.getValue(), Direction.Forward, 0);
        m_setAnim = true;
    }

    public boolean approximatelyEqual(Pose2d a, Pose2d b) {
        return a.getTranslation().getDistance(b.getTranslation()) < m_acceptableDifference;
    }

    public boolean didSomethingBreak() {
        return m_isFaulted;
    }

    public boolean areWeRed() {
        if (DriverStation.getAlliance() == Alliance.Red) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isMatchOver() {
        if (DriverStation.getMatchTime() == 0 && !DriverStation.isAutonomous()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean areWeBlue() {
        if (DriverStation.getAlliance() == Alliance.Blue) {
            return true;
        } else {
            return false;
        }
    }

    public boolean IDKWHATWEAREOHNO() {
        // Theoretically, this shouldn't ever happpen.
        // If it does, then something is wrong.
        if (DriverStation.getAlliance() == Alliance.Invalid) {
            return true;
        } else {
            return false;
        }
    }

    double m_previousMatchTime = 0;

    boolean m_isFaulted = false;

    public void checkForFault() {
        double m_currentMatchTime = DriverStation.getMatchTime();
        if (!DriverStation.isDisabled()) {
            m_previousMatchTime = m_currentMatchTime;
        } else {
            if (m_previousMatchTime != 0 && DriverStation.isDisabled()) {
                m_isFaulted = true;
            } else {
                m_isFaulted = false;
            }
        }
    }

    @Override
    public void periodic() {
        if (DriverStation.isDisabled()) {
            switch (m_state) {
                case 0:
                    larsonColor(255, 0, 0);
                    if (m_swerve.areAllCANDevicesPresent() && counter++ > 75) {
                        m_state++;
                        counter = 0;
                    }
                    break;
                case 1:
                    larsonColor(255, 128, 0);
                    if (m_arm.isShoulderReady() && m_arm.areAllCANDevicesPresent() && counter++ > 75) {
                        m_state++;
                        counter = 0;
                    }
                    break;
                case 2:
                    larsonColor(255, 255, 0);
                    if (m_arm.isEffectorReady() && m_Roller.areAllCANDevicesPresent() && counter++ > 75) {
                        m_state++;
                        counter = 0;
                    }
                    break;
                case 3:
                    larsonColor(0, 0, 255);
                    if (DriverStation.isDSAttached()) {
                        m_state++;
                        counter = 0;
                    }
                    System.out.println("waiting for DriverStation");
                    break;
                case 4:
                    larsonColor(255, 255, 255);
                    if (!m_autoName.get().equals("Wait") && counter++ > 75) {
                        m_state++;
                        counter = 0;
                    }
                    break;
                case 5:
                    rainbow();
                    if (counter++ > 100) {
                        m_state++;
                        counter = 0;
                    }
                    break;
                default:
                    // rainbow();
                    break;
            }
        }

        // if (isMatchOver()) {
        //     if (areWeBlue()) {
        //         twinklyStars(0, 0, 128);
        //     }
        //     if (areWeRed()) {
        //         twinklyStars(128, 0, 0);
        //     }
        //     if (IDKWHATWEAREOHNO()) {
        //         twinklyStars(128, 128, 128);
        //     }

        // }

        // if (didSomethingBreak()) {
        //     fireThingIDK();
        // }

        // if (Math.abs(SmartDashboard.getNumber("NavX.pitch", 0.0)) > dangerAngle.getValue()
        //         || Math.abs(SmartDashboard.getNumber("NavX.roll", 0.0)) > dangerAngle.getValue()) {
        //     // if (m_lastAnimation == null) {
        //     // m_lastAnimation = m_toAnimate;
        //     // }
        //     // strobe(255, 0, 0);
        //     // m_strobe = m_toAnimate;
        // } else if (m_toAnimate.equals(m_strobe) && m_lastAnimation != null) {
        //     m_toAnimate = m_lastAnimation;
        //     m_setAnim = true;
        //     m_lastAnimation = null;
        // }

        if (m_setAnim) {
            m_candle.clearAnimation(0);
            m_setAnim = false;
        }
        m_candle.animate(m_toAnimate, 0);

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }

    public InstantCommand wantConeFactory() {
        return new InstantCommand(() -> {
            wantCone();
        });
    }

    public InstantCommand holdingConeFactory() {
        return new InstantCommand(() -> {
            holdingCone();
        });
    }

    public InstantCommand wantCubeFactory() {
        return new InstantCommand(() -> {
            wantCube();
        });
    }

    public InstantCommand holdingCubeFactory() {
        return new InstantCommand(() -> {
            holdingCube();
        });
    }
}
