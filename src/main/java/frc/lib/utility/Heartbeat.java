package frc.lib.utility;

public class Heartbeat {

        private double benchmark;
        private double checkpoint;
        private double difference;

        private Runnable callback;

        public Heartbeat(Runnable callback){
                this(callback, 20);
        }

        public Heartbeat(Runnable callback, double benchmark){
                this.callback = callback;
                this.benchmark = benchmark;
        }

        public void start(){
                checkpoint = SystemClock.getSystemTime();
        }
        public void check(){
                difference = SystemClock.getSystemTime() - checkpoint;
                if(difference > benchmark){
                        callback.run();
                }
        }
        public double getDifference(){
                return difference;
        }
}
