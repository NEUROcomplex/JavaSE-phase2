package day_01;


public class SingletonDemo {
    public static void main(String[] args) {
        Singleton_01 s1 = Singleton_01.INSTANCE;
        s1.print();
        Singleton_01 s2 = Singleton_01.INSTANCE;
        s2.print();

        s1.setName("单例人01号加强版");
        s1.setAge(3);

        s1.print();
        s2.print();

        //Singleton_02.getInstance().print();

        //ObejectLoop ol = new ObejectLoop();
    }
}

/*
* 懒汉单例模式
* */
class Singleton_lazy {
    private static Singleton_lazy instance;//不在定义处赋值,不可以final修饰

    private String name;

    private Singleton_lazy() {
        name = "懒汉";
    }

    public Singleton_lazy getInstance() {
        if(instance == null) {//为空时赋值
            instance = new Singleton_lazy();
        }

        return instance;
    }
}

/*
* 饿汉单例模式
* */
//1. 导出引用本类唯一对象的 公有静态常量 的单例模式
class Singleton_01 {
    public static final Singleton_01 INSTANCE = new Singleton_01();

    private String name;
    private int age;

    {
        System.out.println("实例代码块被执行");
    }

    private Singleton_01() {
        name = "单例人01号";
        age = 1;
        //INSTANCE = new Singleton_01();
    }

    public void print() {
        System.out.println(age + "岁的" + name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

//2. 静态工厂方法返回 私有静态常量 的单例模式
class Singleton_02 {
    private static final Singleton_02 INSTANCE = new Singleton_02();

    private String name;
    private int age;

    private Singleton_02() {
        name = "单例人02号";
        age = 2;
    }

    public static Singleton_02 getInstance() {
        return INSTANCE;
    }

    public void print() {
        System.out.println(age + "岁的" + name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

//类中若包含值为本身对象的实例成员,则会无限循环创建对象;编译不会报错,运行出错;
class ObejectLoop {
    private ObejectLoop ol = new ObejectLoop();

    public ObejectLoop() {
        System.out.println("一个对象已被创建");
    }
}

//3. 利用 只有一个值的枚举类型 的单例模式
