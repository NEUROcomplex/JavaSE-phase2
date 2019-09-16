package day_01;
/*
*   三种初始化对象方法
* */
public class ThreeWaysToInitialize_Demo {
    public static void main(String[] args) {
        Initial_03 i3 = new Initial_03.Builder(1,2).att3(3).att4(4).att5(5).att6(6).build();
        i3.printAtt();
    }
}

/*
*   重叠构造器模式
* */
class Initial_01 {
    private final int att1;
    private final int att2;
    private final int att3;
    private final int att4;
    private final int att5;
    private final int att6;

    public Initial_01() {
        this(0);
    }

    public Initial_01(int att1) {
        this(att1, 0);
    }

    public Initial_01(int att1, int att2) {
        this(att1, att2, 0);
    }

    public Initial_01(int att1, int att2, int att3) {
        this(att1, att2, att3, 0);
    }

    public Initial_01(int att1, int att2, int att3, int att4) {
        this(att1, att2, att3, att4, 0);
    }

    public Initial_01(int att1, int att2, int att3, int att4, int att5) {
        this(att1, att2, att3, att4, att5, 0);
    }

    public Initial_01(int att1, int att2, int att3, int att4, int att5, int att6) {
        this.att1 = att1;
        this.att2 = att2;
        this.att3 = att3;
        this.att4 = att4;
        this.att5 = att5;
        this.att6 = att6;
    }
}

/*
*   JavaBeans模式
* */
class Initial_02 {
    private int att1 = 0;//因需调用set设置初始值, 成员变量不可final
    private int att2 = 0;
    private int att3 = 0;
    private int att4 = 0;
    private int att5 = 0;
    private int att6 = 0;

    public Initial_02() {
    }

    public void setAtt1(int att1) {
        this.att1 = att1;
    }

    public void setAtt2(int att2) {
        this.att2 = att2;
    }

    public void setAtt3(int att3) {
        this.att3 = att3;
    }

    public void setAtt4(int att4) {
        this.att4 = att4;
    }

    public void setAtt5(int att5) {
        this.att5 = att5;
    }

    public void setAtt6(int att6) {
        this.att6 = att6;
    }
}

/*
*   Builder模式
* */
class Initial_03 {
    private final int att1;
    private final int att2;
    private final int att3;
    private final int att4;
    private final int att5;
    private final int att6;

    private Initial_03(Builder builder) {
        att1 = builder.att1;
        att2 = builder.att2;
        att3 = builder.att3;
        att4 = builder.att4;
        att5 = builder.att5;
        att6 = builder.att6;
    }

   /* public static class B {
        Builder b = new Builder(1,2);
        private int a = b.att1;
    }*/

    public static class Builder {
        private int att1;//不可设默认值的必要属性
        private int att2;//不可设默认值的必要属性
        private int att3 = 0;
        private int att4 = 0;
        private int att5 = 0;
        private int att6 = 0;

        public Builder(int att1, int att2) {
            this.att1 = att1;
            this.att2 = att2;
        }

        public Builder att3(int att3) {
            this.att3 = att3;
            return this;
        }

        public Builder att4(int att4) {
            this.att4 = att4;
            return this;
        }

        public Builder att5(int att5) {
            this.att5 = att5;
            return this;
        }

        public Builder att6(int att6) {
            this.att6 = att6;
            return this;
        }

        public Initial_03 build() {
            return new Initial_03(this);
        }
    }

    public void printAtt() {
        System.out.println(att1 + " " + att2 + " " + att3 + " " + att4 + " " + att5 + " " + att6);
    }
}