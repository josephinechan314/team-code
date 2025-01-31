
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import java.util.ArrayList;

@TeleOp(name = "MecanumHardware: Teleop!!", group = "MecanumHardware")
public class FtcTeleOp extends LinearOpMode {

    // Declare OpMode members.
    public static final double sensitivity_scalar = 0.15;
    public double driver_scalar = 0.95; //'public double' might need to be changed, not sure of syntax
    public static final double driver_rotation_scalar = 0.7;
    public double lbarmpos = 1;
    public double rbarmpos = 0;
    int count = 0;
    public double cbarmpos;
    public double manual = 0;
    Hardware robot = new Hardware();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting
        telemetry.addData("Hi", "I'm EVE");

//        robot.rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        robot.lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        robot.lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        robot.rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        robot.lspool.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        robot.rspool.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

//        robot.lspool.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
//        robot.rspool.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

//        robot.lspool.setPower(0);
//        robot.rspool.setPower(0);

//        robot.cbarm.setPosition(0.5);

        telemetry.addData("lspool: ", robot.lspool.getCurrentPosition());
        telemetry.addData("rspool: ", robot.rspool.getCurrentPosition());
        telemetry.update();

        robot.lbarm.setPosition(1);
        robot.rbarm.setPosition(0);

//        cbarmpos = robot.cbarm.getPosition();

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

            // motors
            driveByMatrix(robot.lf, robot.rf, robot.lb, robot.rb, x1, y1, yaw1 * driver_rotation_scalar, driver_scalar);

            // Gamepad 2: controls the linear slides and claw

            //slides
            if (gamepad2.left_stick_y > 0) { // Lifts slides
                robot.lspool.setPower(0.5);
                robot.rspool.setPower(-0.5);
            } else if (gamepad2.left_stick_y < 0) { // Lowers slides
                robot.lspool.setPower(-0.5);
                robot.rspool.setPower(0.5);
            } else {
                robot.lspool.setPower(0);
                robot.rspool.setPower(0);
            }

            //pivot
            if (gamepad2.left_trigger > 0) {
                robot.llinkage.setPower(0.75);
                robot.rlinkage.setPower(0.75);
            } else if (gamepad2.right_trigger > 0) {
                robot.llinkage.setPower(-0.75);
                robot.rlinkage.setPower(-0.75);
            } else {
                robot.llinkage.setPower(0);
                robot.rlinkage.setPower(0);
            }

            //claw
            if (gamepad2.x) { // Grab piece
                robot.claw.setPosition(1);
            } else if (gamepad2.b){
                robot.claw.setPosition(0);
            }

//            arm up vs down
            if (gamepad2.right_bumper && manual == 0) {
                if (count == 0) {
                    robot.lbarm.setPosition(0.3);
                    robot.rbarm.setPosition(0.7);
                    sleep(1500);
                    count = 1;
                } else if (count > 1) {
                    robot.lbarm.setPosition(1);
                    robot.rbarm.setPosition(0);
                    sleep(1000);
                    count = 0;
                } else if (count == 1) {
                    robot.lbarm.setPosition(0);
                    robot.rbarm.setPosition(1);
                    sleep(1500);
                    count = 2;
                }
            }

            if (gamepad2.left_bumper){
                if (manual == 0) {
                    manual = 1;
                } else {
                    manual = 0;
                }
            }

            telemetry.addData("manual: ", manual);

            if (gamepad2.y && manual == 1){
                lbarmpos += 0.003;
                rbarmpos -= 0.003;
                robot.lbarm.setPosition(lbarmpos);
                robot.rbarm.setPosition(rbarmpos);
            } else if (gamepad2.a && manual == 1){
                lbarmpos -= 0.003;
                rbarmpos += 0.003;
                robot.lbarm.setPosition(lbarmpos);
                robot.rbarm.setPosition(rbarmpos);
            }

//            if (gamepad2.left_bumper){
//                robot.lbarm.setPosition(lbarmpos);
//                robot.rbarm.setPosition(rbarmpos);
//                lbarmpos += 0.01;
//                rbarmpos += 0.01;
//            } else if (gamepad2.right_bumper) {
//                robot.lbarm.setPosition(lbarmpos);
//                robot.rbarm.setPosition(rbarmpos);
//                lbarmpos -= 0.01;
//                rbarmpos -= 0.01;
//            }
            if (lbarmpos > 1){
                lbarmpos = 1;
            } else if (lbarmpos < 0) {
                lbarmpos = 0;
            }
            if (rbarmpos > 1){
                rbarmpos = 1;
            } else if (rbarmpos < 0){
                rbarmpos = 0;
            }

            telemetry.addData("left arm pos: ", lbarmpos);
            telemetry.addData("left arm: ", robot.lbarm.getPosition());
            telemetry.addData("right arm pos: ", rbarmpos);
            telemetry.addData("right arm: ", robot.rbarm.getPosition());
;
            //claw spin
            if (gamepad2.dpad_left) {
                robot.spinclaw.setPower(-0.3);
            } else if (gamepad2.dpad_right) {
                robot.spinclaw.setPower(0.3);
            } else {
                robot.spinclaw.setPower(0);
            }

            //claw up vs down
            if (gamepad2.right_stick_y > 0) {
                cbarmpos -= 0.005;
                robot.cbarm.setPosition(cbarmpos);
            } else if (gamepad2.right_stick_y < 0) {
                cbarmpos += 0.005;
                robot.cbarm.setPosition(cbarmpos);
            }

            telemetry.addData("center arm: ", robot.cbarm.getPosition());

            if (cbarmpos < 0){
                cbarmpos = 0;
            } else if (cbarmpos > 1){
                cbarmpos = 1;
            }

//            robot.cbarm.setPosition(cbarmpos);
//            telemetry.addData("cbarmpos: ", cbarmpos);
//            telemetry.addData("cbarm: ", robot.cbarm.getPosition());

            //test drive motors direction
//            if (gamepad1.y){
//                robot.lf.setPower(1);
//            } else if (gamepad1.b){
//                robot.lb.setPower(1);
//            } else if (gamepad1.a){
//                robot.rb.setPower(1);
//            } else if (gamepad1.x){
//                robot.rf.setPower(1);
//            } else {
//                robot.lf.setPower(0);
//                robot.lb.setPower(0);
//                robot.rb.setPower(0);
//                robot.rf.setPower(0);
//            }


//            robot.rspool.setPower(0);
//            robot.lspool.setPower(0);
//            robot.llinkage.setPower(0);
//            robot.rlinkage.setPower(0);
//            robot.lbarm.setPower(0);
//            robot.rbarm.setPower(0);
//            robot.spinclaw.setPower(0);
            telemetry.update();
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

        if (gamepad1.right_bumper){
            front_l.setPower(-fl*0.4);
            front_r.setPower(-fr*0.4);
            back_l.setPower(-bl*0.4);
            back_r.setPower(-br*0.4);
        } else {// Set the power for the motors
            front_l.setPower(-fl);
            front_r.setPower(-fr);
            back_l.setPower(-bl);
            back_r.setPower(-br);
        }

        ArrayList<java.lang.Double> motor_power = new ArrayList<>();

        motor_power.add(front_l.getPower());
        motor_power.add(front_r.getPower());
        motor_power.add(back_l.getPower());
        motor_power.add(back_r.getPower());

        return motor_power;
    }
}