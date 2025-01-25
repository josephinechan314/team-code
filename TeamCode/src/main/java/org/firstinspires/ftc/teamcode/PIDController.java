package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor; // DcMotorEx or DcMotor??
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

public class PIDController extends LinearOpMode {
    DcMotor test_motor;
    double integral = 0;
    double repetitions = 0;
    public static PIDCoefficients testPID = new PIDCoefficients(0,0,0);

    // Plots telemetry and field graphics
    // https://github.com/acmerobotics/ftc-dashboard
    FtcDashboard dashboard;
    public static double target = 100; // 100 is default value
    ElapsedTime PIDTimer = new ElapsedTime();

    @Override
    public void runOpMode() {
        test_motor = hardwareMap.dcMotor.get("testMotor");

        // Motor will actively resist external forces that try to move it ("locks" the motor)
        test_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        test_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        test_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        dashboard = FtcDashboard.getInstance();

        waitForStart();
        moveTestMotor(target); // This will be called for each of the four motors
    }
    void moveTestMotor(double target_pos) {
        double error = test_motor.getCurrentPosition();
        double last_error = 0;

        // Modify error and repetitions
        // Higher end motor tick count: higher value
        // Lower end motor tick count: lower value
        while (Math.abs(error) <= 9 && repetitions < 40) {
            error = test_motor.getCurrentPosition() - target_pos; // Checks how far motor is from desired position
            double delta_error = last_error - error;
            integral += delta_error * PIDTimer.time();
            double derivative = delta_error / PIDTimer.time();
            double P = testPID.p * error;
            double I = testPID.i * integral;
            double D = testPID.d * derivative;
            test_motor.setPower(P + I + D);
            error = last_error;
            PIDTimer.reset();
            repetitions ++;
        }
    }
}