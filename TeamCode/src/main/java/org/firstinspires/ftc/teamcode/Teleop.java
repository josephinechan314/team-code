package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import java.util.ArrayList;

@TeleOp(name = "Hardware: TeleOp", group = "Hardware")
public class Teleop extends LinearOpMode{

    // Declare OpMode members.
    public static final double sensitivity_scalar = 0.2;
    public static final double driver_scalar = 0.95;
    public static final double driver_rotation_scalar = 0.7;
    MecanumHardware robot = new MecanumHardware();

    @Override
    public void runOpMode(){
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting
        telemetry.addData("Hi", "I'm [name]");

        robot.FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.FL.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        robot.BL.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        robot.FR.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        robot.BR.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Run until the end of the match (driver press STOP)
        while (opModeIsActive()) {
            double x = gamepad1.right_stick_x;
            double y = -gamepad1.right_stick_y;
            double yaw = gamepad1.left_stick_x;

            if (Math.abs(yaw) < sensitivity_scalar){
                yaw = 0;
            }
            if (Math.abs(x) < sensitivity_scalar){
                x = 0;
            }
            if (Math.abs(y) < sensitivity_scalar){
                y = 0;
            }

            telemetry.addData("Wheel powers --> FL, FR, BL, BR: ", driveByMatrix(robot.FL, robot.FR,
                    robot.BL, robot.BR, x, y, yaw * driver_rotation_scalar, driver_scalar));
            telemetry.addData("FL: ", robot.FL.getCurrentPosition());
            telemetry.addData("BL: ", robot.BL.getCurrentPosition());
            telemetry.addData("FR: ", robot.FR.getCurrentPosition());
            telemetry.addData("BR: ", robot.BR.getCurrentPosition());
            telemetry.update();

            // Stop robot
            robot.FL.setPower(0);
            robot.BL.setPower(0);
            robot.FR.setPower(0);
            robot.BR.setPower(0);
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
        if (max > 1){
            fl /= max;
            fr /= max;
            bl /= max;
            br /= max;
        }
        if (scalar != 1.0){
            fl = fl * scalar;
            fr = fr * scalar;
            bl = bl * scalar;
            br = br * scalar;
        }

        // Set the power for the motors
        front_l.setPower(fl);
        front_r.setPower(fr);
        back_l.setPower(bl);
        back_r.setPower(br);

        ArrayList<java.lang.Double> motor_power = new ArrayList<>();

        motor_power.add(front_l.getPower());
        motor_power.add(front_r.getPower());
        motor_power.add(back_l.getPower());
        motor_power.add(back_r.getPower());

        return motor_power;
    }
}