package manager.model;

public class FullTimeEmployee extends Employee {
    private double hourlyRate;

    @Override
    public double calculateSalary() {
        return hourlyRate;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    @Override
    public String toString() {
        return "FullTimeEmployee{" +
                "hourlyRate=" + hourlyRate +
                "} " + super.toString();
    }
}
