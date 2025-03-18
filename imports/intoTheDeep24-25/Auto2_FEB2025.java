package org.firstinspires.ftc.teamcode.FIRSTCode_2025FEB_luke;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;


@Autonomous(name="Auto FEB 2025 (Simple) - Turn Right")

public class Auto2_FEB2025 extends LinearOpMode {

    // todo: write your code here
    private DcMotor backleftmotor;
    private DcMotor frontrightmotor;
    private DcMotor frontleftmotor;
    private DcMotor backrightmotor;
    private ElapsedTime runtime = new ElapsedTime();
    
    @Override
    public void runOpMode() {
    
        backrightmotor = hardwareMap.get(DcMotor.class, "back right motor");
        backleftmotor = hardwareMap.get(DcMotor.class, "back left motor");
        frontrightmotor = hardwareMap.get(DcMotor.class, "front right motor");
        frontleftmotor = hardwareMap.get(DcMotor.class, "front left motor");
        
        frontrightmotor.setDirection(DcMotor.Direction.REVERSE); // front right motor is inately reversed
        frontleftmotor.setDirection(DcMotor.Direction.FORWARD);
        backleftmotor.setDirection(DcMotor.Direction.FORWARD);
        backrightmotor.setDirection(DcMotor.Direction.FORWARD);
        
        waitForStart();
        runtime.reset();
        runAllMotors();
        while (opModeIsActive() && runtime.seconds() <= 2.2) {
            
        }
        stopAllMotors();
        runtime.reset();
        strafeRight();
        while (opModeIsActive() && runtime.seconds() <= 1.0) {
            
        }
        stopAllMotors();

      
    }
    
    public void strafeRight() {
        
        backrightmotor.setPower(-0.5);
        backleftmotor.setPower(-0.5);
        frontrightmotor.setPower(-0.5);
        frontleftmotor.setPower(-0.5);
    }
    public void runAllMotors() {
        
        backrightmotor.setPower(-0.5);
        backleftmotor.setPower(0.5);
        frontrightmotor.setPower(0.5);
        frontleftmotor.setPower(-0.5);
    }
    
    public void stopAllMotors() {
        backrightmotor.setPower(0);
        backleftmotor.setPower(0);
        frontrightmotor.setPower(0);
        frontleftmotor.setPower(0);
    }
    
    public void strafeLeft() {
        
        backrightmotor.setPower(0.5);
        backleftmotor.setPower(0.5);
        frontrightmotor.setPower(0.5);
        frontleftmotor.setPower(0.5);
    }
     
     
}
