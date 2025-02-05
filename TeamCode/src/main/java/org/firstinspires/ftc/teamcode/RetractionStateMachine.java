package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "RetractionStateMachine", group = "MecanumHardware")
public class RetractionStateMachine extends LinearOpMode {

    public double cbarmpos = 0.5;

    public enum RetractionStates {
        WAIT,
        RETRACT,
    };

    RetractionStates retractionState = RetractionStates.RETRACT;

    Hardware robot = new Hardware();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Run until the end of the match (driver press STOP)
        while (opModeIsActive()) {
            switch (retractionState) {
                case WAIT:
                    telemetry.addData("case:", "wait");
                    // Slides
                    if (gamepad2.left_stick_y > 0) { // Lifts slides
                        robot.lspool.setPower(0.8);
                        robot.rspool.setPower(0.8);
                    } else if (gamepad2.left_stick_y < 0) { // Lowers slides
                        robot.lspool.setPower(-0.8);
                        robot.rspool.setPower(-0.8);
                    }

                    // Pivot
                    if (gamepad2.left_trigger > 0) {
                        robot.llinkage.setPower(0.75);
                        robot.rlinkage.setPower(0.75);
                    } else if (gamepad2.right_trigger > 0) {
                        robot.llinkage.setPower(-0.75);
                        robot.rlinkage.setPower(-0.75);
                    }
                    /*
                     else {
                        robot.llinkage.setPower(0);
                        robot.rlinkage.setPower(0);
                    }
                     */

                    // Claw
                    if (gamepad2.x) { // Grab piece
                        robot.claw.setPosition(1);
                    } else if (gamepad2.b) {
                        robot.claw.setPosition(0);
                    }

                    // Claw spin
                    if (gamepad2.right_stick_x < 0) {
//                        robot.spinclaw.setPower(-0.2);
                        robot.testspinclaw.setPosition(0.5);
                    } else if (gamepad2.right_stick_x > 0) {
//                        robot.spinclaw.setPower(0.2);
                        robot.testspinclaw.setPosition(0);
                    }

                    // Claw up vs down
                    if (gamepad2.y) {
                        cbarmpos -= 0.001;
                    } else if (gamepad2.a) {
                        cbarmpos += 0.001;
                    }

                    // Reset arm positions
                    if (cbarmpos < 0) {
                        cbarmpos = 0;
                    } else if (cbarmpos > 1) {
                        cbarmpos = 1;
                    }
                    robot.cbarm.setPosition(cbarmpos);

                    robot.rspool.setPower(0);
                    robot.lspool.setPower(0);
                    robot.llinkage.setPower(0);
                    robot.rlinkage.setPower(0);
//                    robot.spinclaw.setPower(0);

                    telemetry.update();

                    if (gamepad1.right_trigger > 0) {
                        retractionState = RetractionStates.RETRACT;
                    }
                    break;
                case RETRACT:
                    robot.claw.setPosition(1);

                    telemetry.addData("case:", "retract");
                    //robot.cbarm.setPosition(1);

                    if (gamepad2.dpad_up) { //high bucket
                        robot.lbarm.setPosition(1);
                        robot.rbarm.setPosition(0);
                        sleep(100);
                        robot.llinkage.setPower(-0.9);
                        robot.rlinkage.setPower(-0.9);
                        sleep(1500);
                        robot.llinkage.setPower(0);
                        robot.rlinkage.setPower(0);
                        robot.claw.setPosition(1);
                    } else if (gamepad2.dpad_down){
                        robot.claw.setPosition(0);
                        robot.lbarm.setPosition(0.7);
                        robot.rbarm.setPosition(0.3);
                        sleep(100);
                        robot.llinkage.setPower(0.9);
                        robot.rlinkage.setPower(0.9);
                        sleep(1500);
                        robot.llinkage.setPower(0);
                        robot.rlinkage.setPower(0);
                    } else {
                        robot.llinkage.setPower(0);
                        robot.rlinkage.setPower(0);
                    }

                    telemetry.update();

                    if (gamepad1.right_trigger > 0){
                        retractionState = RetractionStates.WAIT;
                    }

                    break;
                default:
                    // Should never be reached, as liftState should never be null
                    retractionState = RetractionStates.RETRACT;
            }
        }
    }
}