package day02_reflective;

public class Employee {
    private String id;
    private String name;
    private double salary;

    public Employee() {
        this("", "", 0);
    }

    public Employee(String id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public void printInfo() {
        System.out.println("Employee[" + id + ", " + name + ", " + salary + "]");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
