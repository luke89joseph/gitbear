package org.firstinspires.ftc.teamcode.FIRSTCode_2025FEB_luke;
 
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

 
@TeleOp(name = "Acompt FEB 2025")
 
public class Acompt_FEB2025 extends LinearOpMode {
 
    //drive motor variables
    private DcMotor backleftmotor;
    private DcMotor frontrightmotor;
    private DcMotor frontleftmotor;
    private DcMotor backrightmotor;
   
    //lift motor variables 
    private DcMotor liftmotor;
    private int liftTargetPos = 0;
    private double liftMax = 0.0;
    private ElapsedTime liftInputTime = new ElapsedTime();
    private ElapsedTime calibrateTime =  new ElapsedTime();
    
    
    
    //siren variables
    private DcMotor siren;
    private ElapsedTime sirenWindDownTime = new ElapsedTime();
    private double sirenPower = 0;
    
    //claw variables
    private Servo clawServo;
    private ElapsedTime clawInputTime = new ElapsedTime(); 
    private boolean clawClosed = false; 
    
    //tilt motor variables
    private DcMotor tiltmotor;
    private TouchSensor tilt_touchSensorMAX;
    private int tiltTargetPos = 0; 
    private double tiltMax = 3000.0;
    private ElapsedTime tiltInputTime = new ElapsedTime();
    private ElapsedTime limitSwtichActivationTime = new ElapsedTime();

     boolean turboOn = true;
     boolean turboToggle = true;
  
   
    @Override
    public void runOpMode() {
      //variables declared here will only exist when teleop is initalized 
        float y;
        float x;
        double rx = 0;
        int denominator;
       
        
       
        
   
    /*
    * Motor List:
    * - 4 Wheel Motors
    * - Airhorn
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
        //liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        
        siren = hardwareMap.get(DcMotor.class, "siren motor");
        
        
        waitForStart();
        //tiltmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        
        //auto calibrate lift
        
            liftmotor.setPower(1);
            liftmotor.setTargetPosition(0);
            liftmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            liftmotor.setPower(1);
            while(liftmotor.isBusy()&&calibrateTime.seconds() < 3)
            {
              
            }
        
        // Auto calibrate tilt
        tiltmotor.setPower(-1);
        while (tilt_touchSensorMAX.isPressed() == false) {
          telemetry.addData("Calibrating...","");
          telemetry.update();
        }
        tiltmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        tiltmotor.setPower(0);
        tiltTargetPos = 0;
        
        
        
        
        
        
        // this loop runs continuously when teleop mode is run 
        while(opModeIsActive()) {
          
          
          //--------------------------------------------------------------------
            // Driving contols
            
              //strafing 
            y = -gamepad1.left_stick_x;
            x = gamepad1.left_stick_y;
            
            if(gamepad1.left_stick_y == 0 && gamepad1.left_stick_y == 0)
            {
              if(gamepad2.dpad_right)//right
              {
                y = -1;
              }
              else if(gamepad2.dpad_left)//left
              {
                y = 1;
              }
              if(gamepad2.dpad_up)//forward
              {
                x = -1;
              }
              else if(gamepad2.dpad_down)//backward
              {
                x = 1;
              }
              else if(gamepad2.left_stick_x > 0.2 || gamepad2.left_stick_x < -0.2)//dead zones to prevent accidental activation 
              {
                if(turboOn)
                {
                   y = -gamepad2.left_stick_x;
                }else{
                   y = -gamepad2.left_stick_x;
                }
              }
              if (turboOn)//keeps the speed non turbo 
              {
                x /=2;
                y /=2;
              }
            }
              
            
              //rotating
            //Priority of inputs: GP1 right stick, GP1 triggers, GP2 triggers
            if (gamepad1.right_stick_x != 0)
            {
              rx = gamepad1.right_stick_x * -1.1;
            }else if (gamepad1.left_trigger > 0.1 || gamepad1.right_trigger > 0.1) {//control rotation with gamepad 1 triggers
              rx = (gamepad1.left_trigger - gamepad1.right_trigger) * 1.1; //the aggrigate input between triggers determines speed
            } else if (gamepad2.left_trigger > 0.1 || gamepad2.right_trigger > 0.1) {//control rotation with gamepad 2 triggers
              rx = (gamepad2.left_trigger - gamepad2.right_trigger) * 1.1; //the aggrigate input between triggers determines speed
            }else if(gamepad2.right_stick_x > 0.2 || gamepad2.right_stick_x < -0.2)
            {
              rx = -gamepad2.right_stick_x/2;
            }else{
              rx = 0;
            }
              //controling motors
            denominator = 2;
            if(turboOn == false) {
              frontleftmotor.setPower(((y + x) + rx) / denominator);
              backleftmotor.setPower(((y - x) - rx) / denominator);
              frontrightmotor.setPower(((y - x) + rx) / denominator);
              backrightmotor.setPower(((y + x) - rx) / denominator);
            }
            else {
              frontleftmotor.setPower((2*(y + x) + rx) / denominator);
              backleftmotor.setPower((2*(y - x) - rx) / denominator);
              frontrightmotor.setPower((2*(y - x) + rx) / denominator);
              backrightmotor.setPower((2*(y + x) - rx) / denominator);
            }
           //turbo drive  
           telemetry.addData("TurboOn", turboOn);
            if (gamepad1.a) {
              if (turboToggle) {
                turboToggle = false;
                if (turboOn) {
                  turboOn = false;
                }
                else {
                  turboOn = true;
                }
              }
            }
            else {
              turboToggle = true;
            }
            //end of driving contols 
            //-------------------------------------------------------------------------------------------------------------------------
           // keep all nondriving contol below line
           
           //Claw servo 
           //both controlers have equal control over claw
           //claw can only be accutuatted every 500 milliseconds 
           if (clawInputTime.milliseconds() > 1500)
           {
             clawInputTime.reset();
           }
            if((gamepad2.right_bumper||gamepad1.right_bumper)&& clawInputTime.milliseconds() > 500)
            {
              clawInputTime.reset();
              if(clawClosed)
              {
                clawServo.setPosition(0.5);
                clawClosed = false;
                 telemetry.addData("claw open", clawServo.getPosition()); //displays claw servo positon for debugging purpoues 
                
              }else{
                clawServo.setPosition(0.34);
                clawClosed = true;
                 telemetry.addData("claw closed", clawServo.getPosition()); //displays claw servo positon for debugging purpoues 
                
              }
            }
            
             //preset positions 
            if(gamepad2.y)//bucket
            {
              liftTargetPos = 1800;
              tiltTargetPos = 1000;
            }else if(gamepad2.b)//intake
            {
              liftTargetPos = 200;
              tiltTargetPos = 2700;
            }else if(gamepad2.x)//normal pos
            {
              liftTargetPos = 0;
              tiltTargetPos = 0;
            }

            
          
            //lift motor contol
            double cosineInputMultiplier = 3.14159265/5000;
            double cosine = Math.cos((3500-tiltTargetPos)*cosineInputMultiplier);
            int liftHorizontalMax = 800;
            liftMax=liftHorizontalMax/cosine;
            //liftMax = 1000/(Math.cos((3500.0 - tiltTargetPos)*3.14)/5000);
            telemetry.addData("Angle",Math.cos((3500.0 - tiltTargetPos)*3.14)/5000);
            if (liftMax > 1900||liftMax < liftHorizontalMax)
            {
              liftMax = 1900;
            }
            telemetry.addData("Lift Max",liftMax);
            if((liftInputTime.milliseconds() > 1))
            {
             liftInputTime.reset();
              if (gamepad2.left_stick_y != 0)
              {
                liftTargetPos -= gamepad2.left_stick_y*3;
                //down on stick is positive, up is negative 
                 
              }
              else if(gamepad1.dpad_up)//up
              {
                liftTargetPos += 3;
              }
              else if(gamepad1.dpad_down)//down
              {
                liftTargetPos -= 3;
              }
            }
             
            
            if(liftTargetPos > liftMax)
            {
              liftTargetPos = (int)Math.round(liftMax);
            }
            if (liftTargetPos < 0)
            {
              liftTargetPos = 0;
            }
            
            
            
            
            
            liftmotor.setTargetPosition(liftTargetPos);
            telemetry.addData("liftTargetPos",liftTargetPos);
            //telemetry.addData("Lift input Timer",liftInputTime.milliseconds());
            liftmotor.setPower(1);
            liftmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            liftmotor.setPower(1);
           
            
            //siren controller
            if(Math.floor(sirenWindDownTime.milliseconds())>8)
            {
              sirenWindDownTime.reset();
               if ((gamepad1.left_bumper||gamepad2.left_bumper))
              {
                sirenPower += 5;
              }else if (sirenPower > 0){
              if(gamepad1.left_stick_button||gamepad2.left_stick_button)
                {
                  sirenPower-=10;
                }else{
                  sirenPower -= 1;
                }
                
                  
              }
            }
            
            
            //makes sure the siren power is set within 0.0-1.0;
            if(sirenPower > 1000)
            {
              sirenPower = 1000;
            }
            if(sirenPower < 0)
            {
              sirenPower = 0;
            }
             
            siren.setPower(sirenPower/1000);
            telemetry.addData("Siren Power",sirenPower);
            
            
            
            
            //tilt control 
            if (tiltInputTime.milliseconds() > 10000){
              tiltInputTime.reset();
            }
            double approxLen = liftTargetPos;
            if (approxLen < 250){
              approxLen = 300.0;
            }
            double hAngle = (Math.atan(15.0/(approxLen)));
            tiltMax =  3300; //3300 - (1500.0 * hAngle);
            
            telemetry.addData("h angle",hAngle);
            telemetry.addData("Tilt Max",tiltMax);
            //up
            if(-gamepad2.right_stick_y < -0.1 && tiltTargetPos > 0 && !tilt_touchSensorMAX.isPressed()&&tiltInputTime.milliseconds() > 100 )
            {
              tiltInputTime.reset();
              tiltTargetPos -= gamepad2.right_stick_y*60;
            } 
            //down
            else if (-gamepad2.right_stick_y > 0.1 && tiltTargetPos < tiltMax && tiltInputTime.milliseconds() > 100 && !(liftmotor.isBusy()&& liftMax<1900))
            
            {
              tiltInputTime.reset();
              tiltTargetPos -= gamepad2.right_stick_y*60;
            
            }
            //up
            else if(gamepad1.dpad_right && !tilt_touchSensorMAX.isPressed()&&tiltInputTime.milliseconds() > 100 )
            {
              tiltInputTime.reset();
              tiltTargetPos -= 50;
            }
            //down
            else if (gamepad1.dpad_left && tiltTargetPos < tiltMax&&tiltInputTime.milliseconds() > 100 && !(liftmotor.isBusy()&&gamepad2.left_stick_y == 0))
            {
              tiltInputTime.reset();
              tiltTargetPos += 50;
            }
           if(tiltTargetPos > tiltMax)
           {
             tiltTargetPos = (int)tiltMax;
           }
            
            
            tiltmotor.setTargetPosition(tiltTargetPos);
            telemetry.addData("tiltTargetPos",tiltTargetPos);
            //telemetry.addData("Tilt input Timer",tiltInputTime.milliseconds());
            tiltmotor.setPower(1);
            tiltmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            tiltmotor.setPower(1);
            
            //recalibate function
            if((gamepad1.right_stick_button && gamepad1.left_stick_button)||(gamepad2.right_stick_button && gamepad2.left_stick_button))
            {
              calibrate();
            }
                
            //limit switch function
            if (tilt_touchSensorMAX.isPressed() && limitSwtichActivationTime.milliseconds() < 1)
            {
              tiltTargetPos = 0;
              tiltmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
              
            }
            
            telemetry.addData("maxLimit:", tilt_touchSensorMAX.isPressed());
            telemetry.update();
          }// run loop end 
     
    }//op mode end
    
   public void calibrate()
   {
     liftmotor.setPower(1);
            liftmotor.setTargetPosition(0);
            liftmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            liftmotor.setPower(1);
            while(liftmotor.isBusy()&&calibrateTime.seconds() < 3)
            {
              
            }
            calibrateTime.reset();
     while (tilt_touchSensorMAX.isPressed() == false) {
                telemetry.update();
                telemetry.addData("Calibrating...","");
                tiltmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                tiltmotor.setPower(-1);
                telemetry.update();
              }
              tiltmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
              tiltmotor.setPower(0);
              tiltTargetPos = 0;
   }
   
     
   
   
}//class end
