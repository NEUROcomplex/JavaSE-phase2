package day02_reflective;

public class RefletiveDemo {
    public static void main(String[] args) {
        Employee e1 = new Employee("001", "Jack", 8000);
        Manager m1 = new Manager("002", "Mary", 9000, 1000);
        Employee m2 = m1;
        //getClass()方法
        System.out.println(e1.getClass().getName());
        System.out.println(m1.getClass());
        System.out.println(m2.getClass());
    }
}
