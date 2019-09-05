package frc.lib.utility;

public enum Units {
    //SI units conversions
    GIGA(9),
    MEGA(6),
    KILO(3),
    BASE(0),
    CENTI(-2),
    MILLI(-3),
    MICRO(-6),
    NANO(-9),
    PICO(-12);

    private int multiplier;

    Units(int multiplier){
        this.multiplier = multiplier;
    }

    //todo the class becomes useless if we get too precise, at a certain point it should be outputting a long instead
    //of a double
    public double convert(double measure, Units unit){
        int difference = Math.abs(this.multiplier - unit.multiplier);
        if(this.multiplier > unit.multiplier){
            return measure * Math.pow(10, difference);
        }
        return measure / Math.pow(10, difference);
    }


}
