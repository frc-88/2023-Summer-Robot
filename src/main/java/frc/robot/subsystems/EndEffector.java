package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.math.filter.Debouncer.DebounceType;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants;
import frc.robot.util.preferenceconstants.DoublePreferenceConstant;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class EndEffector extends SubsystemBase {

    private final WPI_TalonFX m_Cuberoller = new WPI_TalonFX(Constants.CUBE_ROLLER);
    private final WPI_TalonFX m_Coneroller = new WPI_TalonFX(Constants.CONE_ROLLER);
    private final WPI_TalonFX m_ArmMainMain = new WPI_TalonFX(Constants.ARM_GEARBOX);
    //private final EndEffector m_EndEffector = new EndEffector();

    // Cube
  private DoublePreferenceConstant innerRollerCubeIntakeSpeed = 
  new DoublePreferenceConstant("Intake/Speeds/Intake/Inner Cube", -0.5);
private DoublePreferenceConstant outerRollerCubeIntakeSpeed =
  new DoublePreferenceConstant("Intake/Speeds/Intake/Outer Cube", 0.5);
// Cone
private DoublePreferenceConstant innerRollerConeIntakeSpeed =
  new DoublePreferenceConstant("Intake/Speeds/Intake/Inner Cone", 0.5);
private DoublePreferenceConstant outerRollerConeIntakeSpeed =
  new DoublePreferenceConstant("Intake/Speeds/Intake/Outer Cone", 0.5);
// Outgest
private DoublePreferenceConstant innerRollerOutgestConeIntakeSpeed =
  new DoublePreferenceConstant("Intake/Speeds/Outgest/Inner Cone", -1);
private DoublePreferenceConstant outerRollerOutgestConeIntakeSpeed =
  new DoublePreferenceConstant("Intake/Speeds/Outgest/Outer Cone", -0.1);
  private DoublePreferenceConstant innerRollerOutgestCubeIntakeSpeed =
  new DoublePreferenceConstant("Intake/Speeds/Outgest/Inner Cube", -1);
private DoublePreferenceConstant outerRollerOutgestCubeIntakeSpeed =
  new DoublePreferenceConstant("Intake/Speeds/Outgest/Outer Cube", -1);
// Hold
private DoublePreferenceConstant innerRollerHoldCubeIntakeSpeed =
new DoublePreferenceConstant("Intake/Speeds/Hold/Inner Cube", 0.025);
private DoublePreferenceConstant outerRollerHoldCubeIntakeSpeed =
new DoublePreferenceConstant("Intake/Speeds/Hold/Outer Cube", 0.1);
private DoublePreferenceConstant innerRollerHoldConeIntakeSpeed =
new DoublePreferenceConstant("Intake/Speeds/Hold/Inner Cone", 0.07);
private DoublePreferenceConstant outerRollerHoldConeIntakeSpeed =
new DoublePreferenceConstant("Intake/Speeds/Hold/Outer Cone", 0.1);

private DoublePreferenceConstant armUpStallIntakeSpeed =
      new DoublePreferenceConstant("Intake/Arm/Up Stall Speed", -0.03);
  private DoublePreferenceConstant armDownStallIntakeSpeed =
      new DoublePreferenceConstant("Intake/Arm/Down Stall Speed", 0.03);
  private DoublePreferenceConstant armUpMoveIntakeSpeed =
      new DoublePreferenceConstant("Intake/Arm/Up Move Speed", -0.35);  
  private DoublePreferenceConstant armDownMoveIntakeSpeed =
      new DoublePreferenceConstant("Intake/Arm/Down Move Speed", 0.35); 

private final Debouncer m_hasGamePieceDebounce = new Debouncer(0.25, DebounceType.kBoth);
//IR sensor
private DoublePreferenceConstant irSensorConeMin =
      new DoublePreferenceConstant("Intake/IR/Cone Min", 0.491);
  private DoublePreferenceConstant irSensorConeMax =
      new DoublePreferenceConstant("Intake/IR/Cone Max", 0.698);
  private DoublePreferenceConstant irSensorCubeMin =
      new DoublePreferenceConstant("Intake/IR/Cube Min", 0.491);
  private DoublePreferenceConstant irSensorCubeMax =
      new DoublePreferenceConstant("Intake/IR/Cube Max", 0.698);
  private DoublePreferenceConstant irSensorHasPiece =
      new DoublePreferenceConstant("Intake/IR/Has Piece", 0.25);

private final Debouncer m_hasGamePieceCenteredDebounce = new Debouncer(0.25, DebounceType.kBoth);

private double m_irValue = 0;

private boolean m_coneMode = false;
  private boolean m_isIntaking = false;


  public EndEffector() {
    m_ArmMainMain.setInverted(true);
    m_ArmMainMain.overrideLimitSwitchesEnable(false);

    StatorCurrentLimitConfiguration sclc = new StatorCurrentLimitConfiguration(true, 60, 60, .1);

    m_Coneroller.configStatorCurrentLimit(sclc);
    m_Cuberoller.configStatorCurrentLimit(sclc);

  }

  public void armUp() {
    if (isArmUp()) {
      m_ArmMainMain.set(armUpStallIntakeSpeed.getValue());
    } else {
      m_ArmMainMain.set(armUpMoveIntakeSpeed.getValue()); 
    }
   }

   public void armDown() {
    if (isArmDown()) {
      m_ArmMainMain.set(armDownStallIntakeSpeed.getValue());
    } else {
     m_ArmMainMain.set(armDownMoveIntakeSpeed.getValue());
   }
    
  }

  public boolean isArmUp() {
    return m_ArmMainMain.isRevLimitSwitchClosed() > 0;
  }

  public boolean isArmDown() {
    return m_ArmMainMain.isFwdLimitSwitchClosed() > 0;
  }

public void intake() {
    if (!hasGamePieceCentered() || !m_coneMode) {
      if (!m_coneMode) {
        m_Cuberoller.set(innerRollerCubeIntakeSpeed.getValue());
      } else {
        m_Coneroller.set(innerRollerConeIntakeSpeed.getValue());
      }
    } else {
      hold();
    }
  }

  public void outgest() {
    if (!m_coneMode) {
      m_Cuberoller.set(innerRollerOutgestCubeIntakeSpeed.getValue());
    } else {
      m_Coneroller.set(innerRollerOutgestConeIntakeSpeed.getValue());
    }
  }

  public void stopRollers() {
    m_Coneroller.set(0.);
    m_Cuberoller.set(0.);
  }

  public void hold() {
    if (hasGamePiece()) {
      if (!m_coneMode) {
        m_Cuberoller.set(innerRollerHoldCubeIntakeSpeed.getValue());
      } else {
        m_Coneroller.set(innerRollerHoldConeIntakeSpeed.getValue());
      }
    } else {
      stopRollers();
    }
  }

  private boolean hasGamePieceCentered() {
    return m_hasGamePieceCenteredDebounce.calculate(
      m_irValue > (m_coneMode ? irSensorConeMin.getValue() : irSensorCubeMin.getValue()) 
      && m_irValue < (m_coneMode ? irSensorConeMax.getValue() : irSensorCubeMax.getValue()));
  }

  public boolean hasGamePiece() {
    return m_hasGamePieceDebounce.calculate(m_irValue > irSensorHasPiece.getValue());
  }

  public CommandBase intakeFactory() {
    CommandBase coneCommand = new RunCommand(() -> {intake(); armDown();}, this)
      .until(this::hasGamePiece)
      .andThen(new RunCommand(() -> {intake(); armDown();}, this)
        .withTimeout(0.4)
        .andThen(new RunCommand(() -> {hold(); armUp();}, this)
          .until(this::hasGamePieceCentered)));
    CommandBase cubeCommand = new RunCommand(() -> {intake(); armDown();}, this)
      .until(this::hasGamePieceCentered)
      .andThen(new RunCommand(() -> {intake(); armDown();}, this)
        .withTimeout(0.5));
    return new ConditionalCommand(coneCommand, cubeCommand, () -> m_coneMode).alongWith(new InstantCommand(() -> m_isIntaking = true)).andThen(new InstantCommand(() -> m_isIntaking = false))
        .withName("intake");
  }

  public CommandBase outgestFactory() {
    return new RunCommand(() -> {outgest(); armDown();}, this).withName("outgest").alongWith(new InstantCommand(() -> m_isIntaking = false)); 
  }



  
}