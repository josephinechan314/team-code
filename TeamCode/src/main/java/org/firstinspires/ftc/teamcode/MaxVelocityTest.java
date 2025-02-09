package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
public class MaxVelocityTest extends LinearOpMode {
    public DcMotorEx lspool, rspool = null;
    double lcurrentVelocity, rcurrentVelocity;
    double lmaxVelocity = 0.0;
    double rmaxVelocity = 0.0;


    @Override
    public void runOpMode() {
        lspool = hardwareMap.get(DcMotorEx.class, "LeftSpoolMotor");
        rspool = hardwareMap.get(DcMotorEx.class, "RightSpoolMotor");
        waitForStart();


        while (opModeIsActive()) {
            lspool.setPower(1.0);
            rspool.setPower(1.0);

            lcurrentVelocity = lspool.getVelocity();
            rcurrentVelocity = rspool.getVelocity();

            if (lcurrentVelocity > lmaxVelocity) {
                lmaxVelocity = lcurrentVelocity;
            }

            if (rcurrentVelocity > rmaxVelocity) {
                rmaxVelocity = rcurrentVelocity;
            }

            telemetry.addData("current velocity left", lcurrentVelocity);
            telemetry.addData("maximum velocity left", lmaxVelocity);

            telemetry.addData("current velocity right", rcurrentVelocity);
            telemetry.addData("maximum velocity right", rmaxVelocity);
            telemetry.update();
        }
    }
}
