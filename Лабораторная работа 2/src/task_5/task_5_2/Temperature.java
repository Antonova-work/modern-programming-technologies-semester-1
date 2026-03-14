package task_5.task_5_2;

public record Temperature(double value, Unit unit) {

    public enum Unit {
        CELSIUS, FAHRENHEIT, KELVIN
    }

    public Temperature {
        double kelvin = switch(unit) {
            case CELSIUS -> value + 273.15;
            case FAHRENHEIT -> (value - 32) * 5/9 + 273.15;
            case KELVIN -> value;
        };
        if (kelvin < 0) {
            throw new IllegalArgumentException("Temperature below absolute zero");
        }
    }

    public Temperature convertTo(Unit targetUnit) {
        double kelvin = switch(unit) {
            case CELSIUS -> value + 273.15;
            case FAHRENHEIT -> (value - 32) * 5/9 + 273.15;
            case KELVIN -> value;
        };

        double converted = switch(targetUnit) {
            case CELSIUS -> kelvin - 273.15;
            case FAHRENHEIT -> (kelvin - 273.15) * 9/5 + 32;
            case KELVIN -> kelvin;
        };

        return new Temperature(converted, targetUnit);
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", value, unit == Unit.KELVIN ? "K" : "о" + unit.name().charAt(0));
    }
}
