//↓↓↓ AUTOS THAT WE WANT ↓↓↓

// balance
// mobility balance from middle
// 2 piece from clean
// a one piece mobility for bump
// a one piece mobility balance for middle
// and a 2 piece mobility for clean
// all cubes
// all mid
// and then the 2 piece is a a mid and a low

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.drive.AutoBalancePID;
import frc.robot.commands.drive.AutoBalanceSimple;
import frc.robot.commands.drive.FollowHolonomicTrajectory;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Roller;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.util.BotPoseProvider;
import frc.robot.util.TrajectoryHelper;


/** Add your docs here. */
public class Autonomous {

    // Center

    // public static ConditionalCommand center2HalfBalance(SwerveDrive drive, Arm arm, Roller roller) {
    //     return new ConditionalCommand(center2HalfBalance("Red", drive, arm, roller), 
    //         center2HalfBalance("Blue", drive, arm, roller),
    //         () -> {return DriverStation.getAlliance() == Alliance.Red;});
    // }

    // public static ConditionalCommand center3(SwerveDrive drive, Arm arm, Roller roller) {
    //     return new ConditionalCommand(center3("Red", drive, arm, roller), 
    //         center3("Blue", drive, arm, roller),
    //         () -> {return DriverStation.getAlliance() == Alliance.Red;});
    // }

    // public static ConditionalCommand center3Half(SwerveDrive drive, Arm arm, Roller roller) {
    //     return new ConditionalCommand(center3Half("Red", drive, arm, roller), 
    //         center3Half("Blue", drive, arm, roller),
    //         () -> {return DriverStation.getAlliance() == Alliance.Red;});
    // }

    // public static ConditionalCommand center3Balance(SwerveDrive drive, Arm arm, Roller roller) {
    //     return new ConditionalCommand(center3Balance("Red", drive, arm, roller), 
    //         center3Balance("Blue", drive, arm, roller),
    //         () -> {return DriverStation.getAlliance() == Alliance.Red;});
    // }

    // Charge Station

    public static ConditionalCommand engage(SwerveDrive drive, Arm arm, Roller roller) {
        return new ConditionalCommand(engage("Red", drive, arm, roller), 
            engage("Blue",drive,arm, roller),
            () -> {return DriverStation.getAlliance() == Alliance.Red;});
    }

    public static ConditionalCommand charge1MobilityBalance(SwerveDrive drive, Arm arm, Roller roller) {
        return new ConditionalCommand(charge1MobilityBalance("Red", drive, arm, roller), 
            charge1MobilityBalance("Blue", drive, arm, roller),
            () -> {return DriverStation.getAlliance() == Alliance.Red;});
    }

    // public static ConditionalCommand charge1HalfBalance(SwerveDrive drive, Arm arm, Roller roller) {
    //     return new ConditionalCommand(charge1HalfBalance("Red", drive, arm, roller), 
    //         charge1HalfBalance("Blue", drive, arm, roller),
    //         () -> {return DriverStation.getAlliance() == Alliance.Red;});
    // }

    // Wall

    // public static ConditionalCommand wall2(SwerveDrive drive, Arm arm, Roller roller) {
    //     return new ConditionalCommand(wall2("Red", drive, arm, roller), 
    //         wall2("Blue", drive, arm, roller),
    //         () -> {return DriverStation.getAlliance() == Alliance.Red;});
    // }

    // public static ConditionalCommand wall3(SwerveDrive drive, Arm arm, Roller roller) {
    //     return new ConditionalCommand(wall3("Red", drive, arm, roller), 
    //         wall3("Blue", drive, arm, roller),
    //         () -> {return DriverStation.getAlliance() == Alliance.Red;});
    // }   

    /////////////////////////////////////////////

    // private static SequentialCommandGroup center2HalfBalance(String alliance, SwerveDrive drive, Arm arm, Roller roller) {
    //     return new SequentialCommandGroup(
    //         centerBaseTo1Mid(alliance, drive, arm, roller),
    //         new FollowHolonomicTrajectory(drive, TrajectoryHelper.loadJSONTrajectory(alliance + "CenterGrid1ToPiece2.wpilib.json"), new Rotation2d(), Rotation2d.fromDegrees(alliance.equals("Blue") ? -35 : 35), false)
    //             .deadlineWith(intake.intakeFactory(), arm.stow(), grabber.holdConeFactory(), 
    //                 grabber.setPivotForwardsFactory().andThen(grabber.forcePivot()), aiming.noAimFactory()),
    //         new ParallelDeadlineGroup(
    //             new SequentialCommandGroup(
    //                 new FollowHolonomicTrajectory(drive, TrajectoryHelper.loadJSONTrajectory(alliance + "CenterPiece2ToEngage.wpilib.json"), false),
    //                 new AutoBalanceSimple(drive)
    //             ),
    //             new SequentialCommandGroup(
    //                 intake.stowFactory().alongWith(arm.stow(), grabber.holdConeFactory()).until(intake::isArmUp).withTimeout(0.5),
    //                 intake.stowFactory().alongWith(arm.stow(), grabber.holdConeFactory()).withTimeout(0.25),
    //                 arm.stow()
    //                     .alongWith(intake.stowFactory(), grabber.grabConeFactory().andThen(grabber.holdConeFactory()),
    //                         grabber.setPivotBackwardsFactory().andThen(grabber.forcePivot()))
    //             )
    //         )
    //     );
    // }

    // private static SequentialCommandGroup center3(String alliance, SwerveDrive drive, Arm arm, Roller roller) {
    //     return center3Base(alliance, drive, arm, roller,
    //         new FollowHolonomicTrajectory(drive, TrajectoryHelper.loadJSONTrajectory(alliance + "CenterGrid3ToMidfield.wpilib.json"), false)
    //             .deadlineWith(intake.stowFactory(), arm.stow(() -> 300), grabber.holdConeFactory(), aiming.noAimFactory(),
    //                 grabber.setPivotForwardsFactory().andThen(grabber.forcePivot()))
    //     );
    // }

    // private static SequentialCommandGroup center3Half(String alliance, SwerveDrive drive, Arm arm, Roller roller) {
    //     return center3Base(alliance, drive, arm, roller,
    //         new SequentialCommandGroup(
    //             new FollowHolonomicTrajectory(drive, TrajectoryHelper.loadJSONTrajectory(alliance + "CenterGrid3ToPiece3.wpilib.json"), new Rotation2d(), Rotation2d.fromDegrees(alliance.equals("Blue") ? -80 : 80), false)
    //                 .deadlineWith(intake.intakeFactory(), arm.stow(() -> 300), grabber.holdConeFactory(), aiming.noAimFactory(),
    //                     grabber.setPivotForwardsFactory().andThen(grabber.forcePivot()))
                // new ParallelDeadlineGroup(
                //     new FollowHolonomicTrajectory(drive, TrajectoryHelper.loadJSONTrajectory(alliance + "CenterPiece3ToGrid1.wpilib.json"), false),
                //     new SequentialCommandGroup(
                //         intake.stowFactory().alongWith(arm.stow(), grabber.holdConeFactory()).until(intake::isArmUp).withTimeout(0.5),
                //         intake.stowFactory().alongWith(arm.stow(), grabber.holdConeFactory()).withTimeout(0.25),
                //         new Handoff(intake, arm, grabber, true, true),
                //         arm.stow().alongWith(intake.stowFactory(), grabber.holdConeFactory())
                //     )
                // )
    //         )
    //     );
    // }

    // private static SequentialCommandGroup center3Balance(String alliance, SwerveDrive drive, Arm arm, Roller roller) {
    //     return center3Base(alliance, drive, arm, roller,
    //         new FollowHolonomicTrajectory(drive, TrajectoryHelper.loadJSONTrajectory(alliance + "CenterGrid3ToEngage.wpilib.json"), false, true)
    //             .deadlineWith(intake.downFactory(), arm.stow(() -> 300),
    //                 grabber.setPivotForwardsFactory().andThen(grabber.forcePivot()))
    //             .andThen(new AutoBalancePID(drive).alongWith(intake.downFactory(), arm.stow(), grabber.holdConeFactory()))
    //     );
    // }

    private static SequentialCommandGroup engage(String alliance, SwerveDrive drive, Arm arm, Roller roller) {
        return new SequentialCommandGroup(
            initialShootCubeMid(drive, arm, roller),
            new FollowHolonomicTrajectory(drive, TrajectoryHelper.loadJSONTrajectory(alliance + "Engage.wpilib.json"), false, true),
            new AutoBalancePID(drive)
        );
    }

    private static SequentialCommandGroup charge1MobilityBalance(String alliance, SwerveDrive drive, Arm arm, Roller roller) {
        return new SequentialCommandGroup(
            initialShootCubeMid(drive, arm, roller),
            new FollowHolonomicTrajectory(drive, TrajectoryHelper.loadJSONTrajectory(alliance + "ChargeGrid5ToMobility.wpilib.json"), true),
            new FollowHolonomicTrajectory(drive, TrajectoryHelper.loadJSONTrajectory(alliance + "ChargeMobilityToEngage.wpilib.json"), true, true),
            new AutoBalancePID(drive)
        );
    }

    // private static SequentialCommandGroup charge1HalfBalance(String alliance, SwerveDrive drive, Arm arm, Roller roller) {
    //     return new SequentialCommandGroup(
    //         initialShootCubeMid(drive, arm, roller),
    //         new FollowHolonomicTrajectory(drive, TrajectoryHelper.loadJSONTrajectory(alliance + "ChargeGrid5ToPiece3.wpilib.json"), false)
    //             .deadlineWith(intake.intakeFactory(), arm.stowFrom(ArmStates.scoreCubeMiddle), grabber.holdConeFactory(), 
    //                 grabber.setPivotForwardsFactory().andThen(grabber.forcePivot())),
    //         new ParallelDeadlineGroup(
    //             new FollowHolonomicTrajectory(drive, TrajectoryHelper.loadJSONTrajectory(alliance + "ChargePiece3ToEngage.wpilib.json"), false, true),
    //             new SequentialCommandGroup(
    //                 intake.stowFactory().alongWith(arm.stow(), grabber.holdConeFactory()).until(intake::isArmUp).withTimeout(0.5),
    //                 intake.stowFactory().alongWith(arm.stow(), grabber.holdConeFactory()).withTimeout(0.25),
    //                 new Handoff(intake, arm, grabber, true, true),
    //                 arm.stow()
    //                     .alongWith(intake.stowFactory(), grabber.grabConeFactory().andThen(grabber.holdConeFactory()),
    //                         grabber.setPivotBackwardsFactory().andThen(grabber.forcePivot()))
    //             )
    //         ),
    //         new AutoBalancePID(drive).alongWith(intake.stowFactory(), grabber.holdConeFactory())
    //     );
    // }

    // private static SequentialCommandGroup wall2(String alliance, SwerveDrive drive, Arm arm, Roller roller) {
    //     return new SequentialCommandGroup(
    //         initialShootCubeMid(drive, arm, roller).deadlineWith(new RepeatCommand(aiming.setRetroPipelineFactory(true))),
    //         new FollowHolonomicTrajectory(drive, TrajectoryHelper.loadJSONTrajectory(alliance + "WallGrid8ToPiece4.wpilib.json"), false)
    //             .deadlineWith(intake.intakeFactory(), arm.stowFrom(ArmStates.scoreCubeMiddle), grabber.holdConeFactory(), 
    //                 grabber.setPivotForwardsFactory().andThen(grabber.forcePivot())),
    //         new ParallelDeadlineGroup(
    //             new FollowHolonomicTrajectory(drive, TrajectoryHelper.loadJSONTrajectory(alliance + "WallPiece4ToGrid9.wpilib.json"), false),
    //             new SequentialCommandGroup(
    //                 intake.stowFactory().alongWith(arm.stow(), grabber.holdConeFactory()).until(intake::isArmUp).withTimeout(0.5),
    //                 intake.stowFactory().alongWith(arm.stow(), grabber.holdConeFactory()).withTimeout(0.1),
    //                 new Handoff(intake, arm, grabber, true, false),
    //                 arm.sendArmToState(Arm.ScoreConeMid, 400).alongWith(
    //                     intake.handoffFactory().withTimeout(0.3).andThen(intake.downFactory()), 
    //                     grabber.grabConeFactory(), 
    //                     grabber.setPivotBackwardsFactory().andThen(grabber.forcePivot(),
    //                     aiming.noAimFactory()))
    //             )
    //         ),
    //         arm.sendArmToState(Arm.scoreConeMid).alongWith(
    //             intake.downFactory(), grabber.grabConeFactory(),
    //             aiming.aimFactory(Constants.AIM_MIDDLE_OUTREACH, true)
    //             ).withTimeout(.5),
    //         arm.sendArmToState(ArmStates.scoreConeMiddle).alongWith(
    //             intake.downFactory(), grabber.dropConeFactory(),
    //             aiming.aimFactory(Constants.AIM_MIDDLE_OUTREACH, true).withTimeout(0.05).andThen(aiming.noAimFactory()),
    //             printAiming(arm, grabber)
    //             ).withTimeout(0.1),
    //         arm.stowFrom(ArmStates.scoreConeMiddle, () -> 300).withTimeout(0.25).deadlineWith(
    //             intake.downFactory(),
    //             grabber.dropConeFactory(),
    //             aiming.noAimFactory())
    //     );
    // }

    // private static SequentialCommandGroup wall3(String alliance, SwerveDrive drive, Arm arm, Roller roller) {
    //     return new SequentialCommandGroup(
    //         wall2(alliance, drive, arm, roller),
    //         new FollowHolonomicTrajectory(drive, TrajectoryHelper.loadJSONTrajectory(alliance + "WallGrid9ToPiece3.wpilib.json"), new Rotation2d(), Rotation2d.fromDegrees(alliance.equals("Blue") ? 35 : -35), false)
    //             .deadlineWith(intake.intakeFactory(), arm.stowFrom(ArmStates.scoreCubeMiddle), grabber.holdConeFactory(), 
    //                 grabber.setPivotForwardsFactory().andThen(grabber.forcePivot())),
    //             new ParallelDeadlineGroup(
    //                 new FollowHolonomicTrajectory(drive, TrajectoryHelper.loadJSONTrajectory(alliance + "WallPiece3ToGrid7.wpilib.json"), false),
    //                 new SequentialCommandGroup(
    //                     intake.stowFactory().alongWith(arm.stow(), grabber.holdConeFactory()).until(intake::isArmUp).withTimeout(0.5),
    //                     intake.stowFactory().alongWith(arm.stow(), grabber.holdConeFactory()).withTimeout(0.25),
    //                     new Handoff(intake, arm, grabber, true, false),
    //                     arm.sendArmToState(ArmStates.scoreConeMiddle, 400).alongWith(
    //                         intake.handoffFactory().withTimeout(0.3).andThen(intake.downFactory()), 
    //                         grabber.grabConeFactory(), 
    //                         grabber.setPivotBackwardsFactory().andThen(grabber.forcePivot(),
    //                         aiming.noAimFactory()))
    //                 )
    //             ),
    //             arm.sendArmToState(ArmStates.scoreConeMiddle).alongWith(
    //                 intake.downFactory(), grabber.grabConeFactory(),
    //                 aiming.aimFactory(Constants.AIM_MIDDLE_OUTREACH, true)
    //                 ).withTimeout(.5),
    //             arm.sendArmToState(ArmStates.scoreConeMiddle).alongWith(
    //                 intake.downFactory(), grabber.dropConeFactory(),
    //                 aiming.aimFactory(Constants.AIM_MIDDLE_OUTREACH, true).withTimeout(0.05).andThen(aiming.noAimFactory()),
    //                 printAiming(arm, grabber)
    //                 ).withTimeout(0.1),
    //         new FollowHolonomicTrajectory(drive, TrajectoryHelper.loadJSONTrajectory(alliance + "WallGrid7ToMidfield.wpilib.json"), false)
    //             .deadlineWith(intake.stowFactory(), arm.stow(() -> 300), grabber.dropConeFactory(), aiming.noAimFactory(),
    //                 grabber.setPivotForwardsFactory().andThen(grabber.forcePivot()))
    //     );
    // }

    // private static SequentialCommandGroup center3Base(String alliance, SwerveDrive drive, Arm arm, Roller roller, CommandBase ending) {
    //     return new SequentialCommandGroup(
    //         centerBaseTo1Mid(alliance, drive, arm, roller),
    //         new FollowHolonomicTrajectory(drive, TrajectoryHelper.loadJSONTrajectory(alliance + "CenterGrid1ToPiece2.wpilib.json"), new Rotation2d(), Rotation2d.fromDegrees(alliance.equals("Blue") ? -35 : 35), false)
    //             .deadlineWith(intake.intakeFactory(), arm.stow(() -> 300), grabber.holdConeFactory(), aiming.noAimFactory(),
    //                 grabber.setPivotForwardsFactory().andThen(grabber.forcePivot())),
    //         new ParallelDeadlineGroup(
    //             new FollowHolonomicTrajectory(drive, TrajectoryHelper.loadJSONTrajectory(alliance + "CenterPiece2ToGrid3.wpilib.json"), false),
    //             new SequentialCommandGroup(
    //                     intake.stowFactory().alongWith(arm.stow(), grabber.holdConeFactory()).until(intake::isArmUp).withTimeout(0.5),
    //                     intake.stowFactory().alongWith(arm.stow(), grabber.holdConeFactory()).withTimeout(0.25),
    //                     new Handoff(intake, arm, grabber, true, false),
    //                     arm.sendArmToState(ArmStates.scoreConeMiddle, 400).alongWith(
    //                         intake.handoffFactory().withTimeout(0.3).andThen(intake.downFactory()), 
    //                         grabber.grabConeFactory(), 
    //                         grabber.setPivotBackwardsFactory().andThen(grabber.forcePivot(),
    //                         aiming.noAimFactory()))
    //                 )
    //         ),
    //         arm.sendArmToState(ArmStates.scoreConeMiddle).alongWith(
    //             intake.downFactory(), grabber.grabConeFactory(),
    //             aiming.aimFactory(Constants.AIM_MIDDLE_OUTREACH, true)
    //             ).withTimeout(.6),
    //         arm.sendArmToState(ArmStates.scoreConeMiddle).alongWith(
    //             intake.downFactory(), grabber.dropConeFactory(),
    //             aiming.aimFactory(Constants.AIM_MIDDLE_OUTREACH, true).withTimeout(0.05).andThen(aiming.noAimFactory()),
    //             printAiming(arm, grabber)
    //             ).withTimeout(0.1),
    //         ending
    //     );
    // }

    // private static SequentialCommandGroup centerBaseTo1Mid(String alliance, SwerveDrive drive, Arm arm, Roller roller) {
    //     return new SequentialCommandGroup(
    //         initialShootCubeMid(drive, arm, roller)
    //             .deadlineWith(intake.setConeFactory(), 
    //                 candle.wantConeFactory(), 
    //                 aiming.noAimFactory(), 
    //                 new RepeatCommand(aiming.setRetroPipelineFactory(true))),
    //         new FollowHolonomicTrajectory(drive, TrajectoryHelper.loadJSONTrajectory(alliance + "CenterGrid2ToPiece1.wpilib.json"), false)
    //             .deadlineWith(intake.intakeFactory(), arm.stow(), grabber.holdConeFactory(), 
    //                 grabber.setPivotForwardsFactory().andThen(grabber.forcePivot())),
    //         new ParallelDeadlineGroup(
    //             new FollowHolonomicTrajectory(drive, TrajectoryHelper.loadJSONTrajectory(alliance + "CenterPiece1ToGrid1.wpilib.json"), false),
    //             new SequentialCommandGroup(
    //                     intake.stowFactory().alongWith(arm.stow(), grabber.holdConeFactory()).until(intake::isArmUp).withTimeout(0.5),
    //                     new Handoff(intake, arm, grabber, true, false),
    //                     arm.sendArmToState(ArmStates.scoreConeMiddle).alongWith(
    //                         intake.handoffFactory().withTimeout(0.3).andThen(intake.downFactory()), 
    //                         grabber.grabConeFactory(), 
    //                         grabber.setPivotBackwardsFactory().andThen(grabber.forcePivot(),
    //                         aiming.noAimFactory()))
    //                 )
    //         ),
    //         arm.sendArmToState(ArmStates.scoreConeMiddle).alongWith(
    //             intake.downFactory(), grabber.grabConeFactory(),
    //             grabber.setPivotBackwardsFactory().andThen(grabber.forcePivot()),
    //             aiming.aimFactory(Constants.AIM_MIDDLE_OUTREACH, true)
    //             ).withTimeout(.9),
    //         arm.sendArmToState(ArmStates.scoreConeMiddle).alongWith(
    //             intake.downFactory(), grabber.dropConeFactory(),
    //             aiming.aimFactory(Constants.AIM_MIDDLE_OUTREACH, true).withTimeout(0.05).andThen(aiming.noAimFactory()),
    //             printAiming(arm, grabber)
    //             ).withTimeout(0.1)
    //     );
    // }

    private static SequentialCommandGroup initialShootCubeMid(SwerveDrive drive, Arm arm, Roller roller) {
        return new SequentialCommandGroup(       
            arm.stow().withTimeout(1),
            roller.ScoreCube().withTimeout(0.2)
            //arm.stow().withTimeout(0.1)
        );
    }
}
