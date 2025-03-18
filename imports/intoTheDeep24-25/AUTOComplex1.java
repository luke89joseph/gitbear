package org.firstinspires.ftc.teamcode.FIRSTCode_2025FEB_luke;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Auto FEB 2025 (Complex) - Main")

public class AUTOComplex1 extends LinearOpMode {

    private DcMotor backleftmotor;
    private DcMotor frontrightmotor;
    private DcMotor frontleftmotor;
    private DcMotor backrightmotor;
   
    private DcMotor liftmotor;
    private DcMotor tiltmotor;
    
    private ElapsedTime clock = new ElapsedTime();
    
    //claw variables
    private Servo clawServo;
    
    //tilt motor variables
    private TouchSensor tilt_touchSensorMAX;
    private TouchSensor frontSensorL;
    private TouchSensor frontSensorR;
    private TouchSensor rightTouch;
    private int tiltTargetPos = 0; 
    private int liftTargetPos = 0;
    
    @Override
    public void runOpMode() {
        float y;
        float x;
        double rx = 0;
        int denominator;
        
       
        /*
        * Motor List:
        * - 4 Wheel Motors
        * - verticle lift
        * - tilt motor
        *
        * Servo List:
        *- Claw
        *
        */
        
        
        
        backrightmotor = hardwareMap.get(DcMotor.class, "back right motor");
        backleftmotor = hardwareMap.get(DcMotor.class, "back left motor");
        frontrightmotor = hardwareMap.get(DcMotor.class, "front right motor");
        frontleftmotor = hardwareMap.get(DcMotor.class, "front left motor");
        frontrightmotor.setDirection(DcMotor.Direction.REVERSE);
        frontleftmotor.setDirection(DcMotor.Direction.FORWARD);
        backleftmotor.setDirection(DcMotor.Direction.FORWARD);
        backrightmotor.setDirection(DcMotor.Direction.FORWARD);

    
        
    
        tiltmotor = hardwareMap.get(DcMotor.class, "tilt motor");
        tiltmotor.setDirection(DcMotor.Direction.REVERSE);
        tilt_touchSensorMAX = hardwareMap.get(TouchSensor.class, "tilt limit switch");
        clawServo = hardwareMap.get(Servo.class, "claw servo");
        liftmotor = hardwareMap.get(DcMotor.class, "lift motor");
        liftmotor.setDirection(DcMotor.Direction.REVERSE);
        
        frontSensorL = hardwareMap.get(TouchSensor.class, "front sensor left");
        frontSensorR = hardwareMap.get(TouchSensor.class, "front sensor right");
        rightTouch = hardwareMap.get(TouchSensor.class, "right touch sensor");
        
        waitForStart();
        if (opModeIsActive()) {
            calibrateTilt();
            tiltTargetPos = 1000;
            tiltmotor.setPower(1);
            clawServo.setPosition(0.34);
            
            tiltmotor.setTargetPosition(tiltTargetPos);
            tiltmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            liftmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            liftmotor.setPower(1);
            liftmotor.setTargetPosition(1000);
            liftmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            
            forward();
            while (frontSensorL.isPressed() == false||frontSensorR.isPressed() == false) {
                
            }
            stopM();
            backward();
            time(0.28);
            stopM();
            liftmotor.setTargetPosition(500);
            liftmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            stopM();
            time(2.0);
            clawServo.setPosition(0.5);
            
            liftmotor.setTargetPosition(1000);
            //liftmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            
            backward();
            liftmotor.setPower(0.4);
            liftmotor.setTargetPosition(400);
            liftmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            time(0.3);
            stopM();
            
            deg180();
            
            
            //strafeLeft();
            
            //while (rightTouch.isPressed() == false) {
                
            //}
            
            
            stopM();
            /*
            strafeLeft();
            time(0.3);
            stopM();
            
            forward();
            while (frontSensorL.isPressed() == false||frontSensorR.isPressed() == false) {
                
            }
            stopM();
            
            clawServo.setPosition(0.5);
            time(2.0);
            clawServo.setPosition(0.34);
    
            //claw 0.5, 0.34
            
            */
            stopM();
            tiltmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            tiltmotor.setPower(0);
            
            liftmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            liftmotor.setPower(0);
            clawServo.setPosition(0.34);
            stop();
            
        }
    }
    
    
    ///
    ///
    ///
    public void calibrateTilt() {
        while (tilt_touchSensorMAX.isPressed() == false) {
            telemetry.update();
            telemetry.addData("Calibrating...","");
            tiltmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);;
            tiltmotor.setPower(-1);
            telemetry.update();
        }
        tiltmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        tiltmotor.setPower(0);
        tiltTargetPos = 0;
      }
    public void strafeRight() {
        
        backrightmotor.setPower(-0.5);
        backleftmotor.setPower(-0.5);
        frontrightmotor.setPower(-0.5);
        frontleftmotor.setPower(-0.5);
    }
    public void forward() {
        
        backrightmotor.setPower(-0.2);
        backleftmotor.setPower(0.2);
        frontrightmotor.setPower(0.2);
        frontleftmotor.setPower(-0.2);
    }
    public void backward() {
        
        backrightmotor.setPower(0.2);
        backleftmotor.setPower(-0.2);
        frontrightmotor.setPower(-0.2);
        frontleftmotor.setPower(0.2);
    }
    
    public void stopM() {
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
    
    public void deg180() {
        backrightmotor.setPower(0.5);
        backleftmotor.setPower(0.5);
        frontrightmotor.setPower(-0.5);
        frontleftmotor.setPower(-0.5);
        ElapsedTime k = new ElapsedTime();
        while (k.seconds() < 1.59) {
            
        }
        ///time(1.59); // UPDATE
        stopM();
    }
    
    public void time(double t) {
        ElapsedTime clock = new ElapsedTime();
        while (clock.seconds() < t) {
            
        }
    }
}
