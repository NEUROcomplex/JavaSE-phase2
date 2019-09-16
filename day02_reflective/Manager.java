package day02_reflective;

public class Manager extends Employee {
    private double bonus;

    public Manager() {
        bonus = 0;
    }

    public Manager(String id, String name, double salary, double bonus) {
        super(id, name, salary);
        this.bonus = bonus;
    }

    @Override
    public void printInfo() {
        System.out.println("Manager[" + getId() + ", " + getName() + ", " + getSalary() + bonus + "]");
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }
}
