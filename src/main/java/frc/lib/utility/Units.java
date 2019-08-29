package frc.lib.utility;

public enum Units {
    //SI units conversions
    GIGA(1000000000),
    MEGA(1000000),
    KILO(1000),
    CENTI(0.01),
    MILLI(0.001),
    MICRO(0.000001),
    NANO(0.000000001),
    PICO(0.000000000001);

    private double multipler;

    private Units(double multiplier){
        this.multipler = multiplier;
    }

    
}
