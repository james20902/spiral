package frc.lib.utility;

public enum Units {
    //SI units conversions
    GIGA(9, 3),
    MEGA(6, 2),
    KILO(3, 1),
    CENTI(-2, -1),
    BASE(1, 0),
    MILLI(-3, -2),
    MICRO(-6, -3),
    NANO(-9, -4),
    PICO(-12, -5);

    private int multipler;
    private int scale;

    private Units(int multiplier, int scale){
        this.multipler = multiplier;
    }

    public double convert(double measure, Units unit){
        double multiplier = this.scale > unit.scale ? 0 : 1;
        return measure * unit.multipler;
    }


}
