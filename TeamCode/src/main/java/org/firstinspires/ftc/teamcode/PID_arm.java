package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


@Config // Allows us to modify variables in real time (hence why we have public static variables")
@TeleOp(name = "PID arm test 2", group = "Hardware")
public class PID_arm extends LinearOpMode { // https://gm0.org/en/latest/docs/software/getting-started/linear-opmode-vs-opmode.html
    ElapsedTime timer = new ElapsedTime();

    // Feedback components for left motor
    public static double pl = 0;
    public static double il = 0;
    public static double dl = 0;
    public static double fl = 0; // Tune first!! Then p, then i, then d

    // Feedback components for right motor
    public static double pr = 0;
    public static double ir = 0;
    public static double dr = 0;
    public static double fr = 0;

    public double integral_sum = 0;

    Hardware robot = new Hardware();
    private double prev_error = 0;

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        // Calls the controller for right and left
        if (gamepad2.left_stick_y > 0) { // Goes to max height
            int l_height = PID_Controller(robot.lspool, pl, il, dl, fl, 200);
            robot.lspool.setTargetPosition(l_height);

            int r_height = PID_Controller(robot.rspool, pr, ir, dr, fr, 200);
            robot.rspool.setTargetPosition(r_height);
        } else if (gamepad2.left_stick_y < 0) { // Goes to low height
            int l_height = PID_Controller(robot.lspool, pl, il, dl, fl, 0);
            robot.lspool.setTargetPosition(l_height);

            int r_height = PID_Controller(robot.rspool, pr, ir, dr, fr, 0);
            robot.rspool.setTargetPosition(r_height);
        }
    }

    public int PID_Controller(DcMotor motor, double p, double i, double d, double f, double target) {
        double curr_pos = motor.getCurrentPosition();
        double error = target - curr_pos;

        // timer.second() = curr_time - prev_time AKA change in time
        integral_sum += error * timer.seconds(); // Area
        double derivative = (error - prev_error) / timer.seconds(); // Slope
        prev_error = error; // Output of last loop becomes input of new loop

        telemetry.addData("pos: ", curr_pos);
        telemetry.addData("target: ", target);
        telemetry.update();

        timer.reset();

        double output = (error * p) + (derivative * d) + (integral_sum * i) + (target * f);
        return (int)output; // WTF this is weird syntax
    }
}