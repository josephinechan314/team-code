package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Hardware{
    // Declare OpMode members
    public DcMotorEx lf, lb, rf, rb = null;

    // HardwareMap, which connects motors in program to real ones on the robot
    HardwareMap hwMap = null;

    // Initialize standard Hardware interfaces
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and initialize everything
        lf = hwMap.get(DcMotorEx.class, "Front_left_drive");
        lb = hwMap.get(DcMotorEx.class, "Back_left_drive");
        rf = hwMap.get(DcMotorEx.class, "Front_right_drive");
        rb = hwMap.get(DcMotorEx.class, "Back_right_drive");
        //names the motors

        // Set direction of the motor. Set direction to REVERSE if using AndyMark motors
        lf.setDirection(DcMotorEx.Direction.REVERSE);
        lb.setDirection(DcMotorEx.Direction.REVERSE);
        rf.setDirection(DcMotorEx.Direction.FORWARD);
        rb.setDirection(DcMotorEx.Direction.REVERSE);
        // sets the front motors to reverse and the back motors forward

        // Set all motors to zero power so they start turned off
        lf.setPower(0);
        lb.setPower(0);
        rf.setPower(0);
        rb.setPower(0);
        //sets the power to 0

        // Turn off encoders
        lf.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        // tells the motors to run without the encoder

        // After stopping, the motor brakes, actively resisting any external attempts to move the motor
        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }
}