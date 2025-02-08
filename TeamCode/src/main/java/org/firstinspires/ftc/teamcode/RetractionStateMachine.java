package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "RetractionStateMachine", group = "MecanumHardware")
public class RetractionStateMachine extends LinearOpMode {

    public double cbarmpos;
    public double lbarmpos = 1;
    public double rbarmpos = 0;
    int count = 0;
    public double manual = 1;

    public enum RetractionStates {
        WAIT,
        RETRACT,
    };

    RetractionStates retractionState = RetractionStates.WAIT;

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
                    //slides
                    if (gamepad2.left_stick_y > 0){
                        robot.lspool.setPower(0.9);
                        robot.rspool.setPower(-0.9);
                    } else if (gamepad2.left_stick_y < 0){
                        robot.lspool.setPower(-0.5);
                        robot.rspool.setPower(0.5);
                    } else {
                        robot.lspool.setPower(0);
                        robot.rspool.setPower(0);
                    }

                    telemetry.addData("lspool", robot.lspool.getCurrentPosition());
                    telemetry.addData("rspool", robot.rspool.getCurrentPosition());

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

////            arm up vs down
//                    if (gamepad2.right_bumper && manual == 0) {
//                        if (count == 0) {
//                            robot.lbarm.setPosition(0.3);
//                            robot.rbarm.setPosition(0.7);
//                            sleep(1500);
//                            count = 1;
//                        } else if (count > 1) {
//                            robot.lbarm.setPosition(1);
//                            robot.rbarm.setPosition(0);
//                            sleep(1000);
//                            count = 0;
//                        } else if (count == 1) {
//                            robot.lbarm.setPosition(0);
//                            robot.rbarm.setPosition(1);
//                            sleep(1500);
//                            count = 2;
//                        }
//                    }

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
                        rbarmpos = lbarmpos - 0.2;
                        robot.lbarm.setPosition(lbarmpos); //check
                        robot.rbarm.setPosition(1-rbarmpos);
                    } else if (gamepad2.a && manual == 1){
                        lbarmpos -= 0.003;
                        rbarmpos = lbarmpos - 0.2;
                        robot.lbarm.setPosition(lbarmpos); //check
                        robot.rbarm.setPosition(1-rbarmpos);
                    }

                    if (lbarmpos > 1){
                        lbarmpos = 1;
                    } else if (lbarmpos < 0.4) {
                        lbarmpos = 0.4;
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
//                robot.spinclaw.setPower(-0.3);
                        robot.testspinclaw.setPosition(0.5);
                    } else if (gamepad2.dpad_right) {
//                robot.spinclaw.setPower(0.3);
                        robot.testspinclaw.setPosition(0);
                    } //else {
//                robot.spinclaw.setPower(0);
//            }

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

                    //switch states
                    if (gamepad2.right_bumper) {
                        retractionState = RetractionStates.RETRACT;
                        telemetry.addData("right bumper:", "retract");
                    } else {
                        telemetry.addData("right bumper:", "wait");
                    }

                    telemetry.update();

                    break;
//----------------------------------------------------------------------------------------------------------------
                case RETRACT:
                    robot.claw.setPosition(1);

                    telemetry.addData("case:", "retract");
                    //robot.cbarm.setPosition(1);

                    //set positions for the pivot
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
                    } else if (gamepad2.dpad_down){ //down for submersible
                        robot.lbarm.setPosition(1);
                        robot.rbarm.setPosition(0);
                        robot.claw.setPosition(0);
                        robot.lbarm.setPosition(0.5);
                        robot.rbarm.setPosition(0.7);
                        sleep(100);
                        robot.llinkage.setPower(0.9);
                        robot.rlinkage.setPower(0.9);
                        sleep(1500);
                        robot.llinkage.setPower(0);
                        robot.rlinkage.setPower(0);
                    } else if (gamepad2.dpad_right){ //specimen from human player area
                        robot.lbarm.setPosition(1);
                        robot.rbarm.setPosition(0);
                        sleep(100);
                        robot.llinkage.setPower(-0.9);
                        robot.rlinkage.setPower(-0.9);
                        sleep(900);
                        robot.llinkage.setPower(0);
                        robot.rlinkage.setPower(0);
                        robot.claw.setPosition(1);
                    } else if (gamepad2.dpad_left){//down for human player area
                        robot.lbarm.setPosition(1);
                        robot.rbarm.setPosition(0);
                        robot.claw.setPosition(0);
                        robot.lbarm.setPosition(0.5);
                        robot.rbarm.setPosition(0.7);
                        sleep(100);
                        robot.llinkage.setPower(0.9);
                        robot.rlinkage.setPower(0.9);
                        sleep(975);
                        robot.llinkage.setPower(0);
                        robot.rlinkage.setPower(0);
                    }

                    //slides
                    if (gamepad2.left_stick_y > 0){
                        robot.lspool.setPower(0.9);
                        robot.rspool.setPower(-0.9);
                    } else if (gamepad2.left_stick_y < 0){
                        robot.lspool.setPower(-0.5);
                        robot.rspool.setPower(0.5);
                    } else {
                        robot.lspool.setPower(0);
                        robot.rspool.setPower(0);
                    }

                    //arm up down
                    if (gamepad2.y && manual == 1){
                        lbarmpos += 0.003;
                        rbarmpos = lbarmpos - 0.2;
                        robot.lbarm.setPosition(lbarmpos); //check
                        robot.rbarm.setPosition(1-rbarmpos);
                    } else if (gamepad2.a && manual == 1){
                        lbarmpos -= 0.003;
                        rbarmpos = lbarmpos - 0.2;
                        robot.lbarm.setPosition(lbarmpos); //check
                        robot.rbarm.setPosition(1-rbarmpos);
                    }

                    //claw open close
                    if (gamepad2.x) { // Grab piece
                        robot.claw.setPosition(1);
                    } else if (gamepad2.b){
                        robot.claw.setPosition(0);
                    }

                    //claw spin
                    if (gamepad2.dpad_left) {
                        robot.testspinclaw.setPosition(0.5);
                    } else if (gamepad2.dpad_right) {
                        robot.testspinclaw.setPosition(0);
                    }

                    //switch states
                    if (gamepad2.right_bumper) {
                        retractionState = RetractionStates.WAIT;
                        telemetry.addData("right bumper:", "wait");
                    } else {
                        telemetry.addData("right bumper:", "retract");
                    }

                    telemetry.update();

                    break;
                default:
                    // Should never be reached, as liftState should never be null
                    retractionState = RetractionStates.WAIT;
            }
        }
    }
}