package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.hardware.bosch.BHI260IMU;

public class MecanumHardware {
    // Declare OpMode members.
    public DcMotorEx FL = null;
    public DcMotorEx BL = null;
    public DcMotorEx FR = null;
    public DcMotorEx BR = null;

    public DcMotor left_out = null;
    public DcMotor right_out = null;
    public Servo servo_out = null;

    public DcMotorEx misumi = null;
    public CRServo spin_in_servo = null;
    public Servo total_in_servo_left = null;
    public Servo total_in_servo_right = null;

    // public BHI260IMU imu = null;

    // HardwareMap, which connects motors in program to real ones on the robot.
    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    // Constructor:
    public MecanumHardware() {
    }

    // Initialize standard Hardware interfaces.
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map.
        hwMap = ahwMap;

        // Define and initialize everything.
        // Driving
        FL = hwMap.get(DcMotorEx.class, "FL");
        BL = hwMap.get(DcMotorEx.class, "BL");
        FR = hwMap.get(DcMotorEx.class, "FR");
        BR = hwMap.get(DcMotorEx.class, "BR");

        // Outtake
        left_out = hwMap.get(DcMotor.class, "lOuttakeSlide");
        right_out = hwMap.get(DcMotor.class, "rOuttakeSlide");
        servo_out = hwMap.get(Servo.class, "outtakeServo");

        // Intake
        misumi = hwMap.get(DcMotorEx.class, "misumiSlides");
        spin_in_servo = hwMap.get(CRServo.class, "intakeTotal");
        total_in_servo_left = hwMap.get(Servo.class, "intakeLeft");
        total_in_servo_right = hwMap.get(Servo.class, "intakeRight");

        // imu = hwMap.get(BHI260IMU.class, "imu");

        // Set direction of the motor. Set direction to REVERSE if using AndyMark motors.
        FL.setDirection(DcMotorEx.Direction.FORWARD);
        BL.setDirection(DcMotorEx.Direction.FORWARD);
        FR.setDirection(DcMotorEx.Direction.REVERSE);
        BR.setDirection(DcMotorEx.Direction.REVERSE);

        left_out.setDirection(DcMotor.Direction.REVERSE);
        right_out.setDirection(DcMotor.Direction.REVERSE);
        servo_out.setDirection(Servo.Direction.FORWARD); //CHECK

        misumi.setDirection(DcMotorEx.Direction.FORWARD); //CHECK
        spin_in_servo.setDirection(CRServo.Direction.FORWARD); //CHECK
        total_in_servo_left.setDirection(Servo.Direction.FORWARD); //CHECK
        total_in_servo_right.setDirection(Servo.Direction.FORWARD); //CHECK

        // Set all motors to zero power so they start turned off.
        FL.setPower(0);
        BL.setPower(0);
        FR.setPower(0);
        BR.setPower(0);

        left_out.setPower(0);
        right_out.setPower(0);
        servo_out.setPosition(0);

        misumi.setPower(0);
        spin_in_servo.setPower(0);
        total_in_servo_left.setPosition(0.5);
        total_in_servo_right.setPosition(0.5);

        // Turn off encoders.
        FL.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        BL.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        FR.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        BR.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        left_out.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        right_out.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        misumi.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}