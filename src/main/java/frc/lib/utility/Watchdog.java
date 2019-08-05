package frc.lib.utility;

import java.util.HashMap;
import java.util.Map;

public class Watchdog {

    //todo reimplementation of WPI's watchdog class

        private long m_startTime; // us
        private long m_timeout; // us
        private long m_expirationTime; // us
        private final Runnable m_callback;

        @SuppressWarnings("PMD.UseConcurrentHashMap")
        private final Map<String, Long> m_epochs = new HashMap<>();
        boolean m_isExpired;

        boolean m_suppressTimeoutMessage;

        public Watchdog(double timeout, Runnable callback) {
            m_timeout = (long) (timeout * 1.0e6);
            m_callback = callback;
        }

}
