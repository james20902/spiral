package frc.lib.utility;

import java.util.HashMap;
import java.util.Map;

public class Heartbeat {
    //todo reimplementation of WPI's watchdog class

        private long m_startTime; // us
        private long timeout; // us
        private long m_expirationTime; // us
        private final Runnable m_callback;

        private final Map<String, Long> epochs = new HashMap<>();
        private boolean isExpired;

        boolean m_suppressTimeoutMessage;

        public Heartbeat(double timeout, Runnable callback) {
            timeout = (long) (timeout * 1.0e6);
            m_callback = callback;
        }

        public void addEpoch(String name){

        }

}
