package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "autooooo", group = "mecanum hardware")
public class  Auto extends LinearOpMode {
    Hardware robot = new Hardware();

    @Override
    public void runOpMode(){
        robot.init(hardwareMap);
        telemetry.addData("eveeeeeeeee", "hey");
        telemetry.update();

        waitForStart();

        robot.claw.setPosition(1); //closes the claw

        robot.llinkage.setPower(-0.65); //rotates up
        robot.rlinkage.setPower(-0.65);

        sleep(550);

        robot.llinkage.setPower(0);
        robot.rlinkage.setPower(0);

//        robot.lspool.setPower(-0.9); //extends slides
//        robot.rspool.setPower(0.9);

        robot.lf.setPower(1); //drives forward
        robot.lb.setPower(1);
        robot.rf.setPower(1);
        robot.rb.setPower(1);

        sleep(500); //timer for slides

        robot.lspool.setPower(0); //stops slides
        robot.rspool.setPower(0);

        sleep(800); //timer for driving up to (and slamming into) the submersible

        robot.lf.setPower(0);
        robot.lb.setPower(0);
        robot.rf.setPower(0);
        robot.rb.setPower(0);

        robot.lbarm.setPosition(0); //arm
        robot.rbarm.setPosition(1);

        sleep(750);

//        robot.lspool.setPower(-0.75); //extends slides
//        robot.rspool.setPower(0.75);

        robot.cbarm.setPosition(1);

        sleep(300);

        robot.claw.setPosition(0);

        robot.lspool.setPower(0);
        robot.rspool.setPower(0);
        robot.cbarm.setPosition(0.5);

        sleep(50);

        robot.lspool.setPower(0.9);
        robot.rspool.setPower(-0.9);

        robot.lf.setPower(0);
        robot.lb.setPower(0);
        robot.rf.setPower(0);
        robot.rb.setPower(0);

        robot.lbarm.setPosition(0);
        robot.rbarm.setPosition(1);

        sleep(750);

        robot.lspool.setPower(0); //(assuming the specimen is lined up) lowers slides
        robot.rspool.setPower(0);
////        robot.cbarm.setPosition(1); //pulls the claw down
//
//        sleep(600);
//
////        robot.cbarm.setPosition(0.5); //resets claw up/down
//
//        robot.lspool.setPower(0); //stops lowering the slides
//        robot.rspool.setPower(0);
//
//        robot.claw.setPosition(0); //opens the claw

        robot.lb.setPower(-1); //drives back into the wall
        robot.lf.setPower(-1);
        robot.rb.setPower(-1);
        robot.rf.setPower(-1);

        //------------------------------------------------------------------------------------
//        robot.lspool.setPower(-0.75); //(assuming the specimen is lined up) lowers slides
//        robot.rspool.setPower(0.75);
//        robot.cbarm.setPosition(1); //pulls the claw down

//        sleep(600);

//        robot.cbarm.setPosition(0.5); //resets claw up/down

        robot.lspool.setPower(0); //stops lowering the slides
        robot.rspool.setPower(0);

        //-------------------------------------------------------------------------------------

        sleep(1300);

        telemetry.addData("done", "backward");

        robot.lf.setPower(0); //stops the robot
        robot.lb.setPower(0);
        robot.rf.setPower(0);
        robot.rb.setPower(0);

        sleep(10);

//        robot.lf.setPower(1); //turns so the robot is facing the observation deck (and slams into the wall)
//        robot.lb.setPower(1);
//        robot.rf.setPower(-1);
//        robot.rb.setPower(-1);
//        telemetry.addData("done", "turned");
//
//        sleep(1500);
//
        robot.lf.setPower(1);
        robot.lb.setPower(1);
        robot.rf.setPower(1);
        robot.rb.setPower(1);

        sleep(100);

        robot.lf.setPower(1); //slam into the corner
        robot.lb.setPower(-1);
        robot.rf.setPower(-1);
        robot.rb.setPower(1);

        telemetry.addData("done", "slammed");

        sleep(1500); //slams into the corner of the observation deck

        robot.lf.setPower(0);
        robot.lb.setPower(0);
        robot.rf.setPower(0);
        robot.rb.setPower(0);

        robot.claw.setPosition(1); //closes the claw
        sleep(5);
    }
}
