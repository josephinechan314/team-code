package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Hardware{
    // Declare OpMode members
    public DcMotor lf, lb, rf, rb, lspool, rspool = null;
    public DcMotorEx llinkage, rlinkage = null;
    public Servo cbarm, lbarm, rbarm, claw = null;
    public CRServo spinclaw = null;

    // HardwareMap, which connects motors in program to real ones on the robot
    HardwareMap hwMap = null;

    // Initialize standard Hardware interfaces
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and initialize everything
        lf = hwMap.get(DcMotor.class, "leftFront");
        lb = hwMap.get(DcMotor.class, "leftBack");
        rf = hwMap.get(DcMotor.class, "rightFront");
        rb = hwMap.get(DcMotor.class, "rightBack");
        llinkage = hwMap.get(DcMotorEx.class, "LeftLinkageMotor");
        rlinkage = hwMap.get(DcMotorEx.class, "RightLinkageMotor");
        lspool = hwMap.get(DcMotor.class,"LeftSpoolMotor");
        rspool = hwMap.get(DcMotor.class,"RightSpoolMotor");
        lbarm = hwMap.get(Servo.class,"LeftArmServo");
        rbarm = hwMap.get(Servo.class,"RightArmServo");
        cbarm = hwMap.get(Servo.class,"CenterArmServo");
        spinclaw = hwMap.get(CRServo.class,"SpinServo");
        claw = hwMap.get(Servo.class,"ClawServo");

        // Set direction of the motor. Set direction to REVERSE if using AndyMark motors
        lf.setDirection(DcMotor.Direction.FORWARD);
        lb.setDirection(DcMotor.Direction.FORWARD);
        rf.setDirection(DcMotor.Direction.REVERSE);
        rb.setDirection(DcMotor.Direction.REVERSE);
        llinkage.setDirection(DcMotorEx.Direction.REVERSE);
        rlinkage.setDirection(DcMotorEx.Direction.FORWARD);
        lspool.setDirection(DcMotor.Direction.FORWARD);
        rspool.setDirection(DcMotor.Direction.FORWARD);
        lbarm.setDirection(Servo.Direction.FORWARD);
        rbarm.setDirection(Servo.Direction.FORWARD);
//        cbarm.setDirection(Servo.Direction.FORWARD);
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
//        lbarm.setPower(0);
//        rbarm.setPower(0);
        spinclaw.setPower(0);
        claw.setPosition(0);

        // Turn off encoders
        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        llinkage.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        rlinkage.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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