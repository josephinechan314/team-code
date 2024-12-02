package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import java.util.ArrayList;

@TeleOp(name = "MecanumHardware: Teleop!!", group = "MecanumHardware")
public class FtcTeleOp extends LinearOpMode {

    // Declare OpMode members.
    public static final double sensitivity_scalar = 0.2;
    public double driver_scalar = 0.95; //'public double' might need to be changed, not sure of syntax
    public static final double driver_rotation_scalar = 0.7;
    MecanumHardware robot = new MecanumHardware();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting
        telemetry.addData("Hi", "I'm EVE");

        robot.FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.FR.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        robot.FL.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        robot.BL.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        robot.BR.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        robot.left_out.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        robot.right_out.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        robot.right_out.setPower(0);
        robot.left_out.setPower(0);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        if (gamepad1.left_bumper) { // Makes the wheels slow down
            driver_scalar = 0.35;
        }

        // Run until the end of the match (driver press STOP)
        while (opModeIsActive()) {
            // Gamepad 1: drives the robot
            double x1 = gamepad1.right_stick_x;
            double y1 = -gamepad1.right_stick_y;
            double yaw1 = gamepad1.left_stick_x;

            if (Math.abs(yaw1) < sensitivity_scalar) {
                yaw1 = 0;
            }
            if (Math.abs(x1) < sensitivity_scalar) {
                x1 = 0;
            }
            if (Math.abs(y1) < sensitivity_scalar) {
                y1 = 0;
            }

            telemetry.addData("Wheel powers --> FL, FR, BL, BR: ", driveByMatrix(robot.FL, robot.FR,
                    robot.BL, robot.BR, x1, y1, yaw1 * driver_rotation_scalar, driver_scalar));
            telemetry.update();

            // Gamepad 1: controls the Gobilda slides for outtake
            if (gamepad1.left_stick_y > 0) { // Lifts slides
                robot.left_out.setPower(0.5);
                robot.right_out.setPower(0.5);
            } else if (gamepad1.left_stick_y < 0) { // Lowers slides
                robot.left_out.setPower(-0.5);
                robot.right_out.setPower(-0.5);
            }

//            // Gamepad 1: controls the Gobilda slides for suspension
//            if (gamepad1.a) { // Locks slides
//                robot.left_out.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
//                robot.right_out.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
//
//                robot.left_out.setPower(-0.5);
//                robot.right_out.setPower(-0.5);
//
//                robot.left_out.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
//                robot.right_out.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
//            }

//            // Gamepad 2: controls outtake/suspension for Gobilda slides
//            if (gamepad2.left_stick_y > 0) {
//                robot.right_out.setPower(0.5);
//                robot.left_out.setPower(0.5);
//            } else if (gamepad2.left_stick_y < 0) {
//                robot.right_out.setPower(-0.5);
//                robot.left_out.setPower(-0.5);
//            }

            // Gamepad 2: controls spinning servo for intake
            if (gamepad2.right_trigger > 0) { // Grab piece
                robot.spin_in_servo.setPower(1);
            }
            else if (gamepad2.left_trigger > 0) { // Deposit piece
                robot.spin_in_servo.setPower(-1);
            }

            // Gamepad 2: flip the intake up or down
            if (gamepad2.right_bumper) { // Flip intake back towards robot
                robot.total_in_servo_left.setPosition(0.1);
                robot.total_in_servo_right.setPosition(0.1);
            }
            else if (gamepad2.left_bumper) { // Flip intake forward away from robot
                robot.total_in_servo_left.setPosition(0.9);
                robot.total_in_servo_right.setPosition(0.9);
            }

            // Gamepad 2: controls outtake servo
            if (gamepad2.y) { // Push piece into bucket
                robot.servo_out.setPosition(1);
            }
            else if (gamepad2.a) { // Return servo to original position
                robot.servo_out.setPosition(-1);
            }

            // Gamepad 2: controls Misumi slides
            if (gamepad2.b){
                robot.misumi.setPower(0.6);
            } else if (gamepad2.x){
                robot.misumi.setPower(-0.6);
            }

            // Stop robot
            robot.FL.setPower(0);
            robot.BL.setPower(0);
            robot.FR.setPower(0);
            robot.BR.setPower(0);
            robot.right_out.setPower(0.5);
            robot.left_out.setPower(0.5);
            robot.total_in_servo_left.setPosition(0.5);
            robot.total_in_servo_right.setPosition(0.5);
            robot.spin_in_servo.setPower(0);
            robot.misumi.setPower(0);
        }
    }

    public ArrayList<java.lang.Double> driveByMatrix(DcMotor front_l, DcMotor front_r, DcMotor back_l, DcMotor back_r,
                                                     double x, double y, double yaw, double scalar) {

        double fl = x + y + yaw;
        double fr = -x + y - yaw;
        double bl = -x + y + yaw;
        double br = x + y - yaw;

        // Find the maximum absolute power that needs to be applied to any motor
        double max = Math.max(Math.abs(fl), Math.max(Math.abs(fr), Math.max(Math.abs(bl), Math.abs(br))));

        // Scale the powers so that the maximum power is set to 1
        if (max > 1) {
            fl /= max;
            fr /= max;
            bl /= max;
            br /= max;
        }
        if (scalar != 1.0) {
            fl = fl * scalar;
            fr = fr * scalar;
            br = br * scalar;
            bl = bl * scalar;
        }

        // Set the power for the motors
        front_l.setPower(-fl);
        front_r.setPower(-fr);
        back_l.setPower(-bl);
        back_r.setPower(-br);

        ArrayList<java.lang.Double> motor_power = new ArrayList<>();

        motor_power.add(front_l.getPower());
        motor_power.add(front_r.getPower());
        motor_power.add(back_l.getPower());
        motor_power.add(back_r.getPower());

        return motor_power;
    }
}