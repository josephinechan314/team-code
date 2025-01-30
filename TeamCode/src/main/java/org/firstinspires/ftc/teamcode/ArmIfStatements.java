package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "Hardware: ArmIfStatements!", group = "Hardware")
public class ArmIfStatements extends LinearOpMode {

    Hardware robot = new Hardware();
    int count = 0;

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        telemetry.addLine("Testing arm!");

        telemetry.addData("left arm position: ", robot.lbarm.getPosition());
        telemetry.addData("right arm position: ", robot.rbarm.getPosition());

        robot.lbarm.setPosition(1);
        robot.rbarm.setPosition(0);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Run until the end of the match (driver press STOP)
        while (opModeIsActive()) {
            telemetry.addData("left arm position before: ", robot.lbarm.getPosition());
            telemetry.addData("right arm position before: ", robot.rbarm.getPosition());

            if (gamepad1.right_bumper) {
                if (count == 0) { // Pick up

                    telemetry.addData("left arm position before: ", robot.lbarm.getPosition());
                    telemetry.addData("right arm position before: ", robot.rbarm.getPosition());

                    robot.lbarm.setPosition(0.3);
                    robot.rbarm.setPosition(0.7);
                    sleep(1500);

                    telemetry.addData("count: ", count);

                    telemetry.addData("left arm position after: ", robot.lbarm.getPosition());
                    telemetry.addData("right arm position after: ", robot.rbarm.getPosition());

                    telemetry.update();

                    count = 1;
                }
                else if (count > 1) { // Middle
                    telemetry.addData("left arm position before: ", robot.lbarm.getPosition());
                    telemetry.addData("right arm position before: ", robot.rbarm.getPosition());

                    robot.lbarm.setPosition(1);
                    robot.rbarm.setPosition(0);
                    sleep(1000);

                    telemetry.addData("count: ", count);

                    telemetry.addData("left arm position: ", robot.lbarm.getPosition());
                    telemetry.addData("right arm position: ", robot.rbarm.getPosition());
                    telemetry.update();

                    count = 0;
                }
                else if (count == 1) { // Place
                    telemetry.addData("left arm position before: ", robot.lbarm.getPosition());
                    telemetry.addData("right arm position before: ", robot.rbarm.getPosition());

                     // Resets at 2
                    robot.lbarm.setPosition(0);
                    robot.rbarm.setPosition(1);
                    sleep(1500);

                    telemetry.addData("count: ", count);

                    telemetry.addData("left arm position: ", robot.lbarm.getPosition());
                    telemetry.addData("right arm position: ", robot.rbarm.getPosition());
                    telemetry.update();

                    count = 2;
                }
            }
            telemetry.addData("left arm position after: ", robot.lbarm.getPosition());
            telemetry.addData("right arm position after: ", robot.rbarm.getPosition());
            telemetry.addData("count: ", count);
            telemetry.update();
        }
    }
}