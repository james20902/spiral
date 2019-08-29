package frc.lib.control;
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2008-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.hal.AllianceStationID;
import edu.wpi.first.hal.ControlWord;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.hal.MatchInfoData;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.lib.utility.Console;

/**
 * Provide access to the network communication data to / from the Driver Station.
 */
public class DriverStation {
    /**
     * Number of Joystick Ports.
     */
    AtomicBoolean enabled = new AtomicBoolean(false);
    AtomicBoolean auto = new AtomicBoolean(false);
    AtomicBoolean test = new AtomicBoolean(false);
    AtomicBoolean DSAttached = new AtomicBoolean(false);
    AtomicBoolean FMSAttached = new AtomicBoolean(false);
    private static final int kJoystickPorts = 6;



    private static class HALJoystickButtons {
        int m_buttons;
        byte m_count;
    }

    private static class HALJoystickAxes {
        float[] m_axes;
        short m_count;

        HALJoystickAxes(int count) {
            m_axes = new float[count];
        }
    }

    private static class HALJoystickPOVs {
        short[] m_povs;
        short m_count;

        HALJoystickPOVs(int count) {
            m_povs = new short[count];
        }
    }

    /**
     * The robot alliance that the robot is a part of.
     */

    private static final double JOYSTICK_UNPLUGGED_MESSAGE_INTERVAL = 1.0;
    private double m_nextMessageTime;

    private static class DriverStationTask implements Runnable {
        private final DriverStation m_ds;

        DriverStationTask(DriverStation ds) {
            m_ds = ds;
        }

        @Override
        public void run() {
            m_ds.run();
        }
    } /* DriverStationTask */

    private static class MatchDataSender {
        NetworkTable table;
        NetworkTableEntry typeMetadata;
        NetworkTableEntry gameSpecificMessage;
        NetworkTableEntry eventName;
        NetworkTableEntry matchNumber;
        NetworkTableEntry replayNumber;
        NetworkTableEntry matchType;
        NetworkTableEntry alliance;
        NetworkTableEntry station;
        NetworkTableEntry controlWord;

        MatchDataSender() {
            table = NetworkTableInstance.getDefault().getTable("FMSInfo");
            typeMetadata = table.getEntry(".type");
            typeMetadata.forceSetString("FMSInfo");
            gameSpecificMessage = table.getEntry("GameSpecificMessage");
            gameSpecificMessage.forceSetString("");
            eventName = table.getEntry("EventName");
            eventName.forceSetString("");
            matchNumber = table.getEntry("MatchNumber");
            matchNumber.forceSetDouble(0);
            replayNumber = table.getEntry("ReplayNumber");
            replayNumber.forceSetDouble(0);
            matchType = table.getEntry("MatchType");
            matchType.forceSetDouble(0);
            alliance = table.getEntry("IsRedAlliance");
            alliance.forceSetBoolean(true);
            station = table.getEntry("StationNumber");
            station.forceSetDouble(1);
            controlWord = table.getEntry("FMSControlData");
            controlWord.forceSetDouble(0);
        }
    }

    private static DriverStation instance = new DriverStation();

    // Joystick User Data
    private HALJoystickAxes[] m_joystickAxes = new HALJoystickAxes[kJoystickPorts];
    private HALJoystickPOVs[] m_joystickPOVs = new HALJoystickPOVs[kJoystickPorts];
    private HALJoystickButtons[] m_joystickButtons = new HALJoystickButtons[kJoystickPorts];
    private MatchInfoData m_matchInfo = new MatchInfoData();

    // Joystick Cached Data
    private HALJoystickAxes[] m_joystickAxesCache = new HALJoystickAxes[kJoystickPorts];
    private HALJoystickPOVs[] m_joystickPOVsCache = new HALJoystickPOVs[kJoystickPorts];
    private HALJoystickButtons[] m_joystickButtonsCache = new HALJoystickButtons[kJoystickPorts];
    private MatchInfoData m_matchInfoCache = new MatchInfoData();

    // Joystick button rising/falling edge flags
    private int[] m_joystickButtonsPressed = new int[kJoystickPorts];
    private int[] m_joystickButtonsReleased = new int[kJoystickPorts];

    // preallocated byte buffer for button count
    private final ByteBuffer m_buttonCountBuffer = ByteBuffer.allocateDirect(1);

    private final MatchDataSender m_matchDataSender;

    private volatile boolean m_threadKeepAlive = true;

    private final ReentrantLock m_cacheDataMutex = new ReentrantLock();

    private final Lock m_waitForDataMutex;
    private final Condition m_waitForDataCond;
    private int m_waitForDataCount;

    // Robot state status variables
    private boolean m_userInDisabled;
    private boolean m_userInAutonomous;
    private boolean m_userInTeleop;
    private boolean m_userInTest;

    // Control word variables
    private final Object m_controlWordMutex;
    private final ControlWord m_controlWordCache;
    private long m_lastControlWordUpdate;

    /**
     * Gets an instance of the DriverStation.
     *
     * @return The DriverStation.
     */
    public static DriverStation getInstance() {
        return DriverStation.instance;
    }

    /**
     * DriverStation constructor.
     *
     * <p>The single DriverStation instance is created statically with the instance static member
     * variable.
     */
    private DriverStation() {
        HAL.initialize(500, 0);
        m_waitForDataCount = 0;
        m_waitForDataMutex = new ReentrantLock();
        m_waitForDataCond = m_waitForDataMutex.newCondition();

        for (int i = 0; i < kJoystickPorts; i++) {
            m_joystickButtons[i] = new HALJoystickButtons();
            m_joystickAxes[i] = new HALJoystickAxes(HAL.kMaxJoystickAxes);
            m_joystickPOVs[i] = new HALJoystickPOVs(HAL.kMaxJoystickPOVs);

            m_joystickButtonsCache[i] = new HALJoystickButtons();
            m_joystickAxesCache[i] = new HALJoystickAxes(HAL.kMaxJoystickAxes);
            m_joystickPOVsCache[i] = new HALJoystickPOVs(HAL.kMaxJoystickPOVs);
        }

        m_controlWordMutex = new Object();
        m_controlWordCache = new ControlWord();
        m_lastControlWordUpdate = 0;

        m_matchDataSender = new MatchDataSender();

        // Internal Driver Station thread
        Thread m_thread = new Thread(new DriverStationTask(this), "FRCDriverStation");
        m_thread.setPriority((Thread.NORM_PRIORITY + Thread.MAX_PRIORITY) / 2);

        m_thread.start();
    }

    /**
     * Kill the thread.
     */
    public void release() {
        m_threadKeepAlive = false;
    }

    /**
     * Get the value of the axis on a joystick. This depends on the mapping of the joystick connected
     * to the specified port.
     *
     * @param stick The joystick to read.
     * @param axis  The analog axis value to read from the joystick.
     * @return The value of the axis on the joystick.
     */
    public double getStickAxis(int stick, int axis) {
        if (stick < 0 || stick >= kJoystickPorts) {
            throw new IllegalArgumentException("Joystick index is out of range, should be 0-5");
        }
        if (axis < 0 || axis >= HAL.kMaxJoystickAxes) {
            throw new IllegalArgumentException("Joystick axis is out of range");
        }

        m_cacheDataMutex.lock();
        try {
            if (axis >= m_joystickAxes[stick].m_count) {
                // Unlock early so error printing isn't locked.
                m_cacheDataMutex.unlock();
                reportJoystickUnpluggedWarning("Joystick axis " + axis + " on port " + stick
                        + " not available, check if controller is plugged in");
                return 0.0;
            }

            return m_joystickAxes[stick].m_axes[axis];
        } finally {
            if (m_cacheDataMutex.isHeldByCurrentThread()) {
                m_cacheDataMutex.unlock();
            }
        }
    }

    /**
     * Get the state of a POV on the joystick.
     *
     * @return the angle of the POV in degrees, or -1 if the POV is not pressed.
     */
    public int getStickPOV(int stick, int pov) {
        if (stick < 0 || stick >= kJoystickPorts) {
            throw new IllegalArgumentException("Joystick index is out of range, should be 0-5");
        }
        if (pov < 0 || pov >= HAL.kMaxJoystickPOVs) {
            throw new IllegalArgumentException("Joystick POV is out of range");
        }

        m_cacheDataMutex.lock();
        try {
            if (pov >= m_joystickPOVs[stick].m_count) {
                // Unlock early so error printing isn't locked.
                m_cacheDataMutex.unlock();
                reportJoystickUnpluggedWarning("Joystick POV " + pov + " on port " + stick
                        + " not available, check if controller is plugged in");
            }
        } finally {
            if (m_cacheDataMutex.isHeldByCurrentThread()) {
                m_cacheDataMutex.unlock();
            }
        }

        return m_joystickPOVs[stick].m_povs[pov];
    }

    /**
     * The state of the buttons on the joystick.
     *
     * @param stick The joystick to read.
     * @return The state of the buttons on the joystick.
     */
    public int getStickButtons(final int stick) {
        if (stick < 0 || stick >= kJoystickPorts) {
            throw new IllegalArgumentException("Joystick index is out of range, should be 0-3");
        }

        m_cacheDataMutex.lock();
        try {
            return m_joystickButtons[stick].m_buttons;
        } finally {
            m_cacheDataMutex.unlock();
        }
    }

    /**
     * Returns the number of axes on a given joystick port.
     *
     * @param stick The joystick port number
     * @return The number of axes on the indicated joystick
     */
    public int getStickAxisCount(int stick) {
        if (stick < 0 || stick >= kJoystickPorts) {
            throw new IllegalArgumentException("Joystick index is out of range, should be 0-5");
        }

        m_cacheDataMutex.lock();
        try {
            return m_joystickAxes[stick].m_count;
        } finally {
            m_cacheDataMutex.unlock();
        }
    }


    /**
     * Gets the value of isXbox on a joystick.
     *
     * @param stick The joystick port number
     * @return A boolean that returns the value of isXbox
     */
    public boolean getJoystickIsXbox(int stick) {
        if (stick < 0 || stick >= kJoystickPorts) {
            throw new IllegalArgumentException("Joystick index is out of range, should be 0-5");
        }

        return HAL.getJoystickIsXbox((byte) stick) == 1;
    }

    /**
     * Gets the value of type on a joystick.
     *
     * @param stick The joystick port number
     * @return The value of type
     */
    public int getJoystickType(int stick) {
        if (stick < 0 || stick >= kJoystickPorts) {
            throw new IllegalArgumentException("Joystick index is out of range, should be 0-5");
        }

        return HAL.getJoystickType((byte) stick);
    }

    /**
     * Gets the name of the joystick at a port.
     *
     * @param stick The joystick port number
     * @return The value of name
     */
    public String getJoystickName(int stick) {
        if (stick < 0 || stick >= kJoystickPorts) {
            throw new IllegalArgumentException("Joystick index is out of range, should be 0-5");
        }

        return HAL.getJoystickName((byte) stick);
    }

    /**
     * Returns the types of Axes on a given joystick port.
     *
     * @param stick The joystick port number
     * @param axis The target axis
     * @return What type of axis the axis is reporting to be
     */
    public int getJoystickAxisType(int stick, int axis) {
        if (stick < 0 || stick >= kJoystickPorts) {
            throw new IllegalArgumentException("Joystick index is out of range, should be 0-5");
        }

        return HAL.getJoystickAxisType((byte) stick, (byte) axis);
    }


    private void sendMatchData() {
        AllianceStationID alliance = HAL.getAllianceStation();
        boolean isRedAlliance = false;
        int stationNumber = 1;
        switch (alliance) {
            case Blue1:
                break;
            case Blue2:
                stationNumber = 2;
                break;
            case Blue3:
                stationNumber = 3;
                break;
            case Red1:
                isRedAlliance = true;
                break;
            case Red2:
                isRedAlliance = true;
                stationNumber = 2;
                break;
            default:
                isRedAlliance = true;
                stationNumber = 3;
                break;
        }


        String eventName;
        String gameSpecificMessage;
        int matchNumber;
        int replayNumber;
        int matchType;
        synchronized (m_cacheDataMutex) {
            eventName = m_matchInfo.eventName;
            gameSpecificMessage = m_matchInfo.gameSpecificMessage;
            matchNumber = m_matchInfo.matchNumber;
            replayNumber = m_matchInfo.replayNumber;
            matchType = m_matchInfo.matchType;
        }

        m_matchDataSender.alliance.setBoolean(isRedAlliance);
        m_matchDataSender.station.setDouble(stationNumber);
        m_matchDataSender.eventName.setString(eventName);
        m_matchDataSender.gameSpecificMessage.setString(gameSpecificMessage);
        m_matchDataSender.matchNumber.setDouble(matchNumber);
        m_matchDataSender.replayNumber.setDouble(replayNumber);
        m_matchDataSender.matchType.setDouble(matchType);
        m_matchDataSender.controlWord.setDouble(HAL.nativeGetControlWord());
    }

    /**
     * Copy data from the DS task for the user. If no new data exists, it will just be returned,
     * otherwise the data will be copied from the DS polling loop.
     */
    protected void getData() {
        // Get the status of all of the joysticks
        for (byte stick = 0; stick < kJoystickPorts; stick++) {
            m_joystickAxesCache[stick].m_count =
                    HAL.getJoystickAxes(stick, m_joystickAxesCache[stick].m_axes);
            m_joystickPOVsCache[stick].m_count =
                    HAL.getJoystickPOVs(stick, m_joystickPOVsCache[stick].m_povs);
            m_joystickButtonsCache[stick].m_buttons = HAL.getJoystickButtons(stick, m_buttonCountBuffer);
            m_joystickButtonsCache[stick].m_count = m_buttonCountBuffer.get(0);
        }
        // lock joystick mutex to swap cache data
        m_cacheDataMutex.lock();
        try {
            for (int i = 0; i < kJoystickPorts; i++) {
                // If buttons weren't pressed and are now, set flags in m_buttonsPressed
                m_joystickButtonsPressed[i] |=
                        ~m_joystickButtons[i].m_buttons & m_joystickButtonsCache[i].m_buttons;

                // If buttons were pressed and aren't now, set flags in m_buttonsReleased
                m_joystickButtonsReleased[i] |=
                        m_joystickButtons[i].m_buttons & ~m_joystickButtonsCache[i].m_buttons;
            }

            // move cache to actual data
            HALJoystickAxes[] currentAxes = m_joystickAxes;
            m_joystickAxes = m_joystickAxesCache;
            m_joystickAxesCache = currentAxes;

            HALJoystickButtons[] currentButtons = m_joystickButtons;
            m_joystickButtons = m_joystickButtonsCache;
            m_joystickButtonsCache = currentButtons;

            HALJoystickPOVs[] currentPOVs = m_joystickPOVs;
            m_joystickPOVs = m_joystickPOVsCache;
            m_joystickPOVsCache = currentPOVs;

            MatchInfoData currentInfo = m_matchInfo;
            m_matchInfo = m_matchInfoCache;
            m_matchInfoCache = currentInfo;
        } finally {
            m_cacheDataMutex.unlock();
        }

        m_waitForDataMutex.lock();
        m_waitForDataCount++;
        m_waitForDataCond.signalAll();
        m_waitForDataMutex.unlock();

        sendMatchData();
    }

    /**
     * Reports errors related to unplugged joysticks Throttles the errors so that they don't overwhelm
     * the DS.
     */
    private void reportJoystickUnpluggedError(String message) {
        double currentTime = Timer.getFPGATimestamp();
        if (currentTime > m_nextMessageTime) {
            Console.reportError(message);
            m_nextMessageTime = currentTime + JOYSTICK_UNPLUGGED_MESSAGE_INTERVAL;
        }
    }

    /**
     * Reports errors related to unplugged joysticks Throttles the errors so that they don't overwhelm
     * the DS.
     */
    private void reportJoystickUnpluggedWarning(String message) {
        double currentTime = Timer.getFPGATimestamp();
        if (currentTime > m_nextMessageTime) {
            Console.reportWarning(message);
            m_nextMessageTime = currentTime + JOYSTICK_UNPLUGGED_MESSAGE_INTERVAL;
        }
    }

    /**
     * Provides the service routine for the DS polling m_thread.
     */
    private void run() {
        int safetyCounter = 0;
        while (m_threadKeepAlive) {
            HAL.waitForDSData();
            getData();

            synchronized (m_cacheDataMutex) {
                enabled.set(m_controlWordCache.getEnabled() && m_controlWordCache.getDSAttached());
                auto.set(m_controlWordCache.getAutonomous());
                test.set(m_controlWordCache.getTest());
                DSAttached.set(m_controlWordCache.getDSAttached());
                FMSAttached.set(m_controlWordCache.getFMSAttached());//todo won't need to put this in synchronized once all joystick access is relegated to Atomics
            }

            if (enabled.get()) {
                safetyCounter = 0;
            }

            safetyCounter++;
            if (safetyCounter >= 4) {
                MotorSafety.checkMotors();
                safetyCounter = 0;
            }
        }
    }

}
