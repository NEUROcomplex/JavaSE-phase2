package day02_reflective;

public class SonInheriteFatherPrivate_BreakPoint {
    public static void main(String[] args) {
        Father f = new Father();
        Son s = new Son();

        f.Fmethod2();
        s.Fmethod2();
    }

}

class Father {
    protected int f1 = 1;
    private int f2 = 2;
    private final int f3 = 3;

    private void Fmethod1() {}

    public void Fmethod2() {}

}

class Son extends Father {

}