// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    ///////////////////////////////////////////////////////
    // DRIVETRAIN
    ///////////////////////////////////////////////////////

    /**
     * The left-to-right distance between the drivetrain wheels
     *
     * Should be measured from center to center.
     */
    public static final double DRIVETRAIN_TRACKWIDTH_METERS = 0.4064; // 16 inches
    /**
     * The front-to-back distance between the drivetrain wheels.
     *
     * Should be measured from center to center.
     */
    public static final double DRIVETRAIN_WHEELBASE_METERS = 0.4064; // 16 inches

    public static final int FRONT_LEFT_MODULE_DRIVE_MOTOR = 19; 
    public static final int FRONT_LEFT_MODULE_STEER_MOTOR = 18; 
    public static final int FRONT_LEFT_MODULE_STEER_ENCODER = 18; 

    public static final int FRONT_RIGHT_MODULE_DRIVE_MOTOR = 8; 
    public static final int FRONT_RIGHT_MODULE_STEER_MOTOR = 11; 
    public static final int FRONT_RIGHT_MODULE_STEER_ENCODER = 11; 

    public static final int BACK_LEFT_MODULE_DRIVE_MOTOR = 1; 
    public static final int BACK_LEFT_MODULE_STEER_MOTOR = 0; 
    public static final int BACK_LEFT_MODULE_STEER_ENCODER = 0; 

    public static final int BACK_RIGHT_MODULE_DRIVE_MOTOR = 10; 
    public static final int BACK_RIGHT_MODULE_STEER_MOTOR = 6; 
    public static final int BACK_RIGHT_MODULE_STEER_ENCODER = 6;
	
	// Controllers
	public static final int DRIVER_CONTROLLER_ID = 0;
    public static final int BUTTON_BOX_ID = 1;
	
    // Arm Motors
    public static final int ARM_GEARBOX = 17;
    public static final int END_EFFECTOR_PIVOT = 16;
    public static final int END_EFFECTOR_PIVOT_ENCODER = 16;

    // End effector motors
    public static final int CUBE_ROLLER = 14;
    public static final int CONE_ROLLER = 15;

     // Drive Train
     public static final int CHARGE_STATION_LEVEL = 2;
     public static final int CANDLE_ID = 2;

     // Trajectory
     public static final double MAX_TRAJ_VELOCITY = 4;
     public static final double MAX_TRAJ_CENTRIP_ACC = 3;
     public static final double MAX_TRAJ_ACCELERATION = 3;
}
