package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Hardware{
    // Declare OpMode members
    public DcMotorEx lf, lb, rf, rb, llinkage, rlinkage, lspool, rspool = null;
    public Servo cbarm, claw = null;
    public CRServo lbarm, rbarm, spinclaw = null;

    // HardwareMap, which connects motors in program to real ones on the robot
    HardwareMap hwMap = null;

    // Initialize standard Hardware interfaces
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and initialize everything
        lf = hwMap.get(DcMotorEx.class, "front_left_drive");
        lb = hwMap.get(DcMotorEx.class, "back_left_drive");
        rf = hwMap.get(DcMotorEx.class, "front_right_drive");
        rb = hwMap.get(DcMotorEx.class, "back_right_drive");
        llinkage = hwMap.get(DcMotorEx.class, "linkage_left");
        rlinkage = hwMap.get(DcMotorEx.class, "linkage_right");
        lspool = hwMap.get(DcMotorEx.class,"spool_left");
        rspool = hwMap.get(DcMotorEx.class,"spool_right");
        lbarm = hwMap.get(CRServo.class,"left_arm_servo");
        rbarm = hwMap.get(CRServo.class,"right_arm_servo");
        cbarm = hwMap.get(Servo.class,"center_arm_servo");
        spinclaw = hwMap.get(CRServo.class,"spin_claw");
        claw = hwMap.get(Servo.class,"claw");

        // Set direction of the motor. Set direction to REVERSE if using AndyMark motors
        lf.setDirection(DcMotorEx.Direction.REVERSE);
        lb.setDirection(DcMotorEx.Direction.REVERSE);
        rf.setDirection(DcMotorEx.Direction.FORWARD);
        rb.setDirection(DcMotorEx.Direction.REVERSE);
        llinkage.setDirection(DcMotorEx.Direction.FORWARD);
        rlinkage.setDirection(DcMotorEx.Direction.FORWARD);
        lspool.setDirection(DcMotorEx.Direction.FORWARD);
        rspool.setDirection(DcMotorEx.Direction.FORWARD);
        lbarm.setDirection(CRServo.Direction.FORWARD);
        rbarm.setDirection(CRServo.Direction.FORWARD);
        cbarm.setDirection(Servo.Direction.FORWARD);
        spinclaw.setDirection(CRServo.Direction.FORWARD);
        claw.setDirection(Servo.Direction.FORWARD);

        // Set all motors to zero power so they start turned off
        lf.setPower(0);
        lb.setPower(0);
        rf.setPower(0);
        rb.setPower(0);
        llinkage.setPower(0);
        rlinkage.setPower(0);
        lspool.setPower(0);
        rspool.setPower(0);
        lbarm.setPower(0);
        rbarm.setPower(0);
        cbarm.setPosition(0);
        spinclaw.setPower(0);
        claw.setPosition(0);

        // Turn off encoders
        lf.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        llinkage.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rlinkage.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lspool.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rspool.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // After stopping, the motor brakes, actively resisting any external attempts to move the motor
        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        llinkage.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rlinkage.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lspool.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rspool.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
}