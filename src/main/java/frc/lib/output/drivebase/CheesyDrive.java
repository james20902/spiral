package frc.lib.output.drivebase;

import frc.lib.input.Controller;
import frc.lib.output.Motors;
import frc.lib.utility.Input;

/*
* This is Cheesy Drive taken from team 254, the Cheesy Poofs
* it is a curvature based drive that follows a directory
* the only changes were spelling fixes and return statement, it fit right in
* https://github.com/Team254/FRC-2018-Public/blob/master/src/main/java/com/team254/lib/util/CheesyDriveHelper.java
 */
public class CheesyDrive extends DriveSystem {

    private static final double kThrottleDeadband = 0.02;
    private static final double kWheelDeadband = 0.02;

    // These factor determine how fast the wheel traverses the "non linear" sine curve.
    private static final double kHighWheelNonLinearity = 0.65;
    private static final double kLowWheelNonLinearity = 0.5;

    private static final double kHighNegInertiaScalar = 4.0;

    private static final double kLowNegInertiaThreshold = 0.65;
    private static final double kLowNegInertiaTurnScalar = 3.5;
    private static final double kLowNegInertiaCloseScalar = 4.0;
    private static final double kLowNegInertiaFarScalar = 5.0;

    private static final double kSensitivity = 0.65;

    private static final double kQuickStopDeadband = 0.5;
    private static final double kQuickStopWeight = 0.1;
    private static final double kQuickStopScalar = 5.0;

    private static double mOldWheel = 0.0;
    private static double mQuickStopAccumulator = 0.0;
    private static double mNegInertiaAccumulator = 0.0;

    public CheesyDrive(Controller instance) {
        super(instance);
    }

    public DriveSignal math() {
        double throttle = this.instance.getAxis(Input.getInstance().lAxis);
        double wheel = this.instance.getAxis(Input.getInstance().rAxis);
        boolean isQuickTurn = this.instance.getButtonState(Input.getInstance().button1) == Controller.ButtonState.HELD;

        wheel = handleDeadzone(wheel, kWheelDeadband);
        throttle = handleDeadzone(throttle, kThrottleDeadband);

        double negInertia = wheel - mOldWheel;
        mOldWheel = wheel;

        double wheelNonLinearity;
        wheelNonLinearity = kLowWheelNonLinearity;
        final double denominator = Math.sin(Math.PI / 2.0 * wheelNonLinearity);
        // Apply a sine function that's scaled to make it feel better.
        wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) / denominator;
        wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) / denominator;
        wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) / denominator;

        double leftPwm, rightPwm, overPower;
        double sensitivity;

        double angularPower;
        double linearPower;

        // Negative inertia!
        double negInertiaScalar;
        if (wheel * negInertia > 0) {
            // If we are moving away from 0.0, aka, trying to get more wheel.
            negInertiaScalar = kLowNegInertiaTurnScalar;
        } else {
            // Otherwise, we are attempting to go back to 0.0.
            if (Math.abs(wheel) > kLowNegInertiaThreshold) {
                negInertiaScalar = kLowNegInertiaFarScalar;
            } else {
                negInertiaScalar = kLowNegInertiaCloseScalar;
            }
        }
        sensitivity = kSensitivity;
        double negInertiaPower = negInertia * negInertiaScalar;
        mNegInertiaAccumulator += negInertiaPower;

        wheel = wheel + mNegInertiaAccumulator;
        if (mNegInertiaAccumulator > 1) {
            mNegInertiaAccumulator -= 1;
        } else if (mNegInertiaAccumulator < -1) {
            mNegInertiaAccumulator += 1;
        } else {
            mNegInertiaAccumulator = 0;
        }
        linearPower = throttle;

        // Quickturn!
        if (isQuickTurn) {
            if (Math.abs(linearPower) < kQuickStopDeadband) {
                double alpha = kQuickStopWeight;
                mQuickStopAccumulator = (1 - alpha) * mQuickStopAccumulator
                        + alpha * Math.min(1, Math.max(-1, wheel)) * kQuickStopScalar;
            }
            overPower = 1.0;
            angularPower = wheel;
        } else {
            overPower = 0.0;
            angularPower = Math.abs(throttle) * wheel * sensitivity - mQuickStopAccumulator;
            if (mQuickStopAccumulator > 1) {
                mQuickStopAccumulator -= 1;
            } else if (mQuickStopAccumulator < -1) {
                mQuickStopAccumulator += 1;
            } else {
                mQuickStopAccumulator = 0.0;
            }
        }

        rightPwm = leftPwm = linearPower;
        leftPwm += angularPower;
        rightPwm -= angularPower;

        if (leftPwm > 1.0) {
            rightPwm -= overPower * (leftPwm - 1.0);
            leftPwm = 1.0;
        } else if (rightPwm > 1.0) {
            leftPwm -= overPower * (rightPwm - 1.0);
            rightPwm = 1.0;
        } else if (leftPwm < -1.0) {
            rightPwm += overPower * (-1.0 - leftPwm);
            leftPwm = -1.0;
        } else if (rightPwm < -1.0) {
            leftPwm += overPower * (-1.0 - rightPwm);
            rightPwm = -1.0;
        }

        return new DriveSignal(leftPwm, rightPwm);
    }

    public void init() {

    }

    public void logSlowdown() {

    }
}
