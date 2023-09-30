package frc.robot.util.Arm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.DoubleSupplier;

import frc.robot.util.preferenceconstants.DoublePreferenceConstant;

public class ArmState {
    private final String m_name;
    private boolean m_isStow = false;
    private final DoublePreferenceConstant m_ArmMainAngle;
    private final DoublePreferenceConstant m_effectorAngle;
    private final DoublePreferenceConstant m_acceleration;
    private List<ArmState> m_deployIntermediaries = new ArrayList<>();
    private List<ArmState> m_retractIntermediaries = new ArrayList<>();
    private DoublePreferenceConstant p_deployIntermediaryTolerance;
    private DoublePreferenceConstant p_retractIntermediaryTolerance;

    public ArmState(String name, double arm, double effector, double acceleration) {
        m_name = name;

        m_ArmMainAngle = new DoublePreferenceConstant("Arm/States/" + name + "/Arm Angle", arm);
        m_effectorAngle = new DoublePreferenceConstant("Arm/States/" + name + "/Effector Angle", effector);
        m_acceleration = new DoublePreferenceConstant("Arm/States/" + name + "/Acceleration", acceleration);
    }

    public ArmState addDeployIntermediaries(int numIntermediaries) {
        for (int n = 1; n <= numIntermediaries; n++) {
            m_deployIntermediaries.add(new ArmState(m_name + "/Deploy " + n, m_ArmMainAngle.getValue(), m_effectorAngle.getValue(), m_acceleration.getValue()));
        }
        p_deployIntermediaryTolerance = new DoublePreferenceConstant("Arm/States/" + m_name + "/Deploy Tolerance", 2);
        return this;
    }

    public ArmState addRetractIntermediaries(int numIntermediaries) {
        for (int n = 1; n <= numIntermediaries; n++) {
            m_retractIntermediaries.add(new ArmState(m_name + "/Retract " + n, m_ArmMainAngle.getValue(), m_effectorAngle.getValue(), m_acceleration.getValue()));
        }
        p_retractIntermediaryTolerance = new DoublePreferenceConstant("Arm/States/" + m_name + "/Retract Tolerance", 2);
        return this;
    }

    public String getName() {
        return m_name;
    }

    public ArmState makeStow() {
        m_isStow = true;
        return this;
    }

    public boolean isStow() {
        return m_isStow;
    }

    public double getShoulderAngle() {
        return m_ArmMainAngle.getValue();
    }

    public double getWristAngle() {
        return m_effectorAngle.getValue();
    }

    public double getAcceleration() {
        return m_acceleration.getValue();
    }

    public List<ArmState> getDeployIntermediaries() {
        return m_deployIntermediaries;
    }

    public List<ArmState> getRetractIntermediaries() {
        return m_retractIntermediaries;
    }
    
    public DoubleSupplier getDeployIntermediaryTolerance() {
        return Objects.nonNull(p_deployIntermediaryTolerance) ? p_deployIntermediaryTolerance::getValue : () -> 9;
    }

    public DoubleSupplier getRetractIntermediaryTolerance() {
        return Objects.nonNull(p_retractIntermediaryTolerance) ? p_retractIntermediaryTolerance::getValue : () -> 0;
    }

    @Override
    public String toString() {
        return m_name + " - Arm: " + m_ArmMainAngle.getValue() + " - End Effector: " + m_effectorAngle.getValue();
    }
}
