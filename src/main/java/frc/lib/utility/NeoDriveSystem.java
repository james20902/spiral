package frc.lib.utility;

import com.revrobotics.CANError;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import java.util.ArrayList;
import java.util.List;

public class NeoDriveSystem {
	private List<CANSparkMax> motors;
	private CANPIDController left, right;

	public NeoDriveSystem(){
		motors = new ArrayList<CANSparkMax>();
	}

	/*
	* Adds a motor to the drivetrain
	* @param Takes the CAN ID of the Spark MAX
	* @return whether or not the configuration succeeded
	*/
	public boolean addMotor (int ID) {
		CANSparkMax motor = new CANSparkMax(ID, MotorType.kBrushless);
		if (motor.restoreFactoryDefaults() != CANError.kOk) return false;
		if (motor.setIdleMode(CANSparkMax.IdleMode.kBrake) != CANError.kOk) return false;
		if (motor.setSmartCurrentLimit(50) != CANError.kOk) return false;
		return true;
	}

	/*
	* Initializes both sides of the drivetrain
	* @param boolean invertFollowing to determine whether or not to invert following
	* @return whether or not the configuration succeeded
	 */
	public boolean intitialize(boolean invertFollowing) {
		CANSparkMax leadLeft = motors.get(0);
		CANSparkMax leadRight = motors.get((motors.size()+1)/2 - 1);
		for(int i = 0; i < motors.size()/2; i++){
			if(!motors.get(i).follow(leadLeft, invertFollowing).equals(CANError.kOk)) return false;
		}
		for(int i = motors.size(); i > motors.size()/2; i--){
			if(!motors.get(i).follow(leadRight, invertFollowing).equals(CANError.kOk)) return false;
		}
		return true;
	}
}
