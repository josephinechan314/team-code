package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "MecanumHardware: TestTeleOp!", group = "MecanumHardware")
public class TestTeleOp extends LinearOpMode {

    Hardware robot = new Hardware();
    double lencoder;
    double rencoder;

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        telemetry.addData("Hi", "EVE");

        robot.llinkage.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        robot.rlinkage.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

//        robot.llinkage.setPower(0);
//        robot.rlinkage.setPower(0);

        lencoder = robot.llinkage.getCurrentPosition();
        rencoder = robot.rlinkage.getCurrentPosition();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Run until the end of the match (driver press STOP)
        while (opModeIsActive()) {

            if (gamepad1.left_trigger > 0) {
                robot.llinkage.setPower(0.5);
                robot.rlinkage.setPower(0.5);
                lencoder = robot.llinkage.getCurrentPosition();
                rencoder = robot.rlinkage.getCurrentPosition();
            } else if (gamepad1.right_trigger > 0) {
                robot.llinkage.setPower(-0.5);
                robot.rlinkage.setPower(-0.5);
                lencoder = robot.llinkage.getCurrentPosition();
                rencoder = robot.rlinkage.getCurrentPosition();
            }
            lencoder = robot.llinkage.getCurrentPosition();
            rencoder = robot.rlinkage.getCurrentPosition();

            telemetry.addData("Encoder position --> Left: ", lencoder);
            telemetry.addData("Encoder position --> Right: ", rencoder);
            telemetry.update();

            robot.llinkage.setPower(0);
            robot.rlinkage.setPower(0);
        }
    }
}