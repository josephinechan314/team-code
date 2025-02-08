package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.checkerframework.checker.units.qual.A;

@TeleOp(name = "ArmStateMachine", group = "Hardware")
public class ArmStateMachine extends OpMode {
//cambria is pushing
    public enum ArmState {
        PICK_UP,
        MIDDLE,
        PLACE,
//        STOP,
    };
    ArmState armState = ArmState.PICK_UP; // Check later if this should be STOP

    Hardware robot = new Hardware();

    ElapsedTime timer = new ElapsedTime();

    public void init(){
        timer.reset();

        robot.lbarm.setPosition(0);
        robot.rbarm.setPosition(0);
    }

    public void loop(){

        switch (armState){
            case PICK_UP:
                if (gamepad2.right_bumper){
                    robot.claw.setPosition(1);
                    robot.lbarm.setPosition(0.5);
                    robot.rbarm.setPosition(0.5);
                    armState = ArmState.MIDDLE;
                }
                break;

            case MIDDLE:
                if (gamepad2.right_bumper){
                    timer.reset();
                    robot.lbarm.setPosition(1);
                    robot.rbarm.setPosition(1);
                    while (timer.seconds() < 0.5){
                        robot.cbarm.setPosition(1);
                    }
                    robot.claw.setPosition(0);
                    robot.cbarm.setPosition(0.5);
                    armState = ArmState.PLACE;
                }
                break;


            case PLACE:
                if (gamepad2.right_bumper){
                    robot.lbarm.setPosition(0);
                    robot.rbarm.setPosition(0);
                    armState = ArmState.PICK_UP;
                }
                break;

            default:
                armState = ArmState.PICK_UP;
        }

        if (gamepad2.left_bumper && armState != ArmState.PICK_UP){
            armState = ArmState.PICK_UP;
        }
    }

//    @Override
//    public void runOpMode() {
//        robot.init(hardwareMap);
//
//        // Wait for the game to start (driver presses PLAY)
//        waitForStart();
//
//        // Run until the end of the match (driver press STOP)
//        while (opModeIsActive()) {
//            switch (armState) {
//                case PICK_UP:
//                    if (gamepad1.y) {
//                        robot.lbarm.setPosition(1);
//                        robot.rbarm.setPosition(1);
//                    }
//                    armState = ArmState.MIDDLE;
//                    break;
//                case MIDDLE:
//                    if (gamepad1.y) {
//                        robot.lbarm.setPosition(0.5);
//                        robot.rbarm.setPosition(0.5);
//                    }
//                    armState = ArmState.PLACE;
//                    break;
//                case PLACE:
//                    if (gamepad1.y) {
//                        robot.lbarm.setPosition(0);
//                        robot.rbarm.setPosition(0);
//                    }
//                    armState = ArmState.STOP;
//                    break;
//                case STOP:
//                    telemetry.addData("Left arm position: ", robot.lbarm.getPosition());
//                    telemetry.addData("Right arm position: ", robot.rbarm.getPosition());
//                    telemetry.update();
//                    armState = ArmState.PICK_UP;
//                    break;
//                default:
//                    // Should never be reached, as liftState should never be null
//                    armState = ArmState.PICK_UP;
//            }
//
//            /*
//            // small optimization, instead of repeating ourselves in each
//            // lift state case besides LIFT_START for the cancel action,
//            // it's just handled here
//            if (gamepad1.y && liftState != LiftState.LIFT_START) {
//                liftState = LiftState.LIFT_START;
//            }
//
//            // Mecanum drive code goes here, but since none of the code in the switch case stops the robot, this will always run!
//            updateDrive(gamepad1, gamepad2);
//             */
//        }
//    }
}