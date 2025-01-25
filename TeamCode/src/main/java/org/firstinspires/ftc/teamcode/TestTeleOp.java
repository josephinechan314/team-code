package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "MecanumHardware: TestTeleOp!", group = "MecanumHardware")
public class TestTeleOp extends LinearOpMode {

    Hardware robot = new Hardware();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        robot.llinkage.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        robot.rlinkage.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        robot.llinkage.setPower(0);
        robot.rlinkage.setPower(0);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Run until the end of the match (driver press STOP)
        while (opModeIsActive()) {

            if (gamepad1.dpad_up) {
                robot.llinkage.setPower(0.75);
                robot.rlinkage.setPower(0.75);
            }
            if (gamepad1.dpad_down) {
                robot.llinkage.setPower(-0.75);
                robot.rlinkage.setPower(-0.75);
            }

            telemetry.addData("Encoder position --> Left: ", robot.llinkage.getCurrentPosition());
            telemetry.addData("Encoder position --> Right: ", robot.rlinkage.getCurrentPosition());
            telemetry.update();

            robot.llinkage.setPower(0);
            robot.rlinkage.setPower(0);
        }
    }
}