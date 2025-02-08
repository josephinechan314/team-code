package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@Config // Allows us to modify variables in real time (hence why we have public static variables")
@TeleOp(name = "PIDF Arm Test", group = "Hardware")
public class PIDF_Arm2 extends OpMode { // https://gm0.org/en/latest/docs/software/getting-started/linear-opmode-vs-opmode.html
    public PID_Controller controller;

    public static double p_left, i_left, d_left; // Feedback components
    public static double p_right, i_right, d_right; // Feedback components

    public static double f_left = 0; // Feedforward component
    public static double f_right = 0;

    public static int target_left = 0; // Target motor positions
    public static int target_right = 0;

    // THIS IS IN THE SPECS FOR THE MOTORS, DON'T KNOW OFF THE TOP OF MY HEAD
    private final double ticks_in_degrees = 700 / 180.0;

    Hardware robot = new Hardware();

    @Override
    public void init() {
        robot.init(hardwareMap);

        // THIS IS THROWING AN ERROR BECAUSE THE VARS DON'T EXIST, IDK HOW TO FIX FOR NOW OR IF WE NEED IT
        //controller = new PIDController(p, i, d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    @Override
    public void loop() {
        PID_Controller(robot.lspool, p_left, i_left, d_left, f_left, target_left);
        PID_Controller(robot.rspool, p_right, i_right, d_right, f_right, target_right);
    }
//changed this
    public void PID_Controller(DcMotor motor, double P, double I, double D, double F, double target) {
        controller.setPID(P, I, D);

        int curr_pos = robot.rspool.getCurrentPosition();
        double pid = controller.calculate(curr_pos, target);
        double ff = Math.cos(Math.toRadians(target / ticks_in_degrees)) * F;

        double power = pid + ff;
        motor.setPower(power);

        telemetry.addData("pos: ", curr_pos);
        telemetry.addData("target: ", target);
        telemetry.update();
    }
}
