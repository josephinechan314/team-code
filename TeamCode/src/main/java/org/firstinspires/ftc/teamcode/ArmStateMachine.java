package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "ArmStateMachine", group = "MecanumHardware")
public class ArmStateMachine extends LinearOpMode {

    public enum ArmState {
        PICK_UP,
        PLACE,
        STOP,
    };

    ArmState armState = ArmState.PICK_UP; // Check later if this should be STOP

    Hardware robot = new Hardware();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Run until the end of the match (driver press STOP)
        while (opModeIsActive()) {
            switch (armState) {
                case PICK_UP:
                    // Waiting for some input
                    if (gamepad1.y) {
                        robot.lbarm.setPosition(0);
                        robot.rbarm.setPosition(0);

                        armState = ArmState.PLACE;
                    }
                    break;
                case PLACE:
                    if (gamepad1.a) {
                        robot.lbarm.setPosition(1);
                        robot.rbarm.setPosition(1);

                        armState = ArmState.STOP;
                    }
                    break;
                case STOP:
                    robot.lbarm.setPosition(0.5);
                    robot.rbarm.setPosition(0.5);
                    break;
                default:
                    // Should never be reached, as liftState should never be null
                    armState = ArmState.PICK_UP;
            }

            /*
            // small optimization, instead of repeating ourselves in each
            // lift state case besides LIFT_START for the cancel action,
            // it's just handled here
            if (gamepad1.y && liftState != LiftState.LIFT_START) {
                liftState = LiftState.LIFT_START;
            }

            // Mecanum drive code goes here, but since none of the code in the switch case stops the robot, this will always run!
            updateDrive(gamepad1, gamepad2);
             */
        }
    }
}