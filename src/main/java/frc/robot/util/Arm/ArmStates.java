package frc.robot.util.Arm;

public class ArmStates {
    public static final ArmState stowGeneric = new ArmState("Unused State", 80, 70, 200).makeStow();
  //  public static final ArmState stowForHP = new ArmState("Stow For HP", 80, 70, 600).makeStow();
    //public static final ArmState stowFlat = new ArmState("Stow Flat", 10, 10, 600).makeStow();
    public static final ArmState stowForHandoff = new ArmState("Stow For Handoff", 10, 10, 200).makeStow();
   // public static final ArmState flat = new ArmState("Flat", 10, 10, 400).makeStow();
   // public static final ArmState stowWithPiece = new ArmState("Stow With Piece", 10, 10, 600).makeStow();
   // public static final ArmState getConeFromIntake1 = new ArmState("Get Cone From Intake 1", 40,  0, 600);
    //public static final ArmState getConeFromIntake2 = new ArmState("Get Cone From Intake 2", 65.6, 0, 600);
    //public static final ArmState getCubeFromIntake1 = new ArmState("Get Cube From Intake 1", 53.2, 0, 600);
  //  public static final ArmState getCubeFromIntake2 = new ArmState("Get Cube From Intake 2", 53.2, 0, 600);
    public static final ArmState getConeFromShelf = new ArmState("Get Cone From Shelf", 85.3, 34, 100);
    public static final ArmState getCubeFromShelf = new ArmState("Get Cube From Shelf", 77.6, 31.7, 100);
    public static final ArmState getConeFromChute = new ArmState("Get Cone From Chute", 85.3, 34, 100).addDeployIntermediaries(1).addRetractIntermediaries(1);
    public static final ArmState getCubeFromChute = new ArmState("Get Cube From Chute", 85.3, 34, 100).addDeployIntermediaries(1).addRetractIntermediaries(1);
    public static final ArmState scoreConeHigh = new ArmState("Score Cone High", 132.2,  149.2, 250);
    public static final ArmState scoreConeMiddle = new ArmState("Score Cone Middle", 54.4, 165.9, 400);
    public static final ArmState scoreConeLow = new ArmState("Score Cone Low", 56.3, 156.8, 600);
    public static final ArmState scoreConeMiddleFront = new ArmState("Score Cone Middle Front", 80, 70, 300);
    public static final ArmState scoreConeLowFront = new ArmState("Score Cone Low Front", 80, 70, 300);
    public static final ArmState scoreCubeHigh = new ArmState("Score Cube High", 131, 135.9, 250).addDeployIntermediaries(1);
    public static final ArmState scoreCubeMiddle = new ArmState("Score Cube Middle", 78.2, 149.5, 400);
    public static final ArmState scoreCubeLow = new ArmState("Score Cube Low", 64.3, 160.7, 600);
    public static final ArmState scoreCubeMiddleFront = new ArmState("Score Cube Middle Front", 80, 70, 300);
    public static final ArmState scoreCubeLowFront = new ArmState("Score Cube Low Front", 80, 70, 300);
    //public static final ArmState autoPrepScoreCone = new ArmState("Auto Prep Score Cone", 80, 70, 300);
}
