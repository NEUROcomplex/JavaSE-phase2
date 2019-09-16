package day02_reflective;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Scanner;

/*
 *   分析用户输入的字符串代表的类
 * */
public class ReflectToAnalyseClass {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("输入需要分析的类名(eg:java.lang.Double):");
        String name = sc.nextLine();

        try {
            Class c1 = Class.forName(name);
            Class c1Super = c1.getSuperclass();
            String modifiers = Modifier.toString(c1.getModifiers());

            //打印修饰符,类名
            if (modifiers.length() > 0)
                System.out.print(modifiers + " ");
            System.out.print("class " + name);

            //打印可能的父类名
            if (c1Super != null && c1Super != Object.class)
                System.out.print(" extends " + c1Super.getName());
            System.out.println(" {");

            //打印成员变量
            printFields(c1);
            System.out.println();

            //打印构造器
            printConstructors(c1);
            System.out.println();

            //打印方法
            printMethods(c1);
            System.out.println("}");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void printFields(Class c) {
        Field[] fields = c.getDeclaredFields();

        for (Field f : fields) {
            System.out.print("  ");
            String name = f.getName();
            String type = f.getType().getName();
            String modifiers = Modifier.toString(f.getModifiers());
            //打印修饰符
            if (modifiers.length() > 0)
                System.out.print(modifiers + " ");
            //打印类型及变量名
            System.out.println(type + " " + name + ";");

        }
    }

    public static void printConstructors(Class c) {
        Constructor[] constructors = c.getConstructors();

        for (Constructor con : constructors) {
            System.out.print("  ");
            //String name = con.getName();
            String modifiers = Modifier.toString(con.getModifiers());
            //打印修饰符及构造器名
            if (modifiers.length() > 0)
                System.out.print(modifiers + " " + c.getName() + "(");
            //打印参数类型列表
            Class[] paramTypes = con.getParameterTypes();
            printParamTypes(paramTypes);
        }
    }

    public static void printMethods(Class c) {
        Method[] methods = c.getMethods();

        for (Method m : methods) {
            System.out.print("  ");
            String name = m.getName();
            String modifiers = Modifier.toString(m.getModifiers());
            //打印修饰符,方法名,返回类型
            if (modifiers.length() > 0)
                System.out.print(modifiers + " ");
            System.out.print(m.getReturnType().getName() + " ");
            System.out.print(name + "(");
            //打印参数类型列表
            Class[] paramTypes = m.getParameterTypes();
            printParamTypes(paramTypes);

        }
    }

    private static void printParamTypes (Class[] paramTypes) {
        if (paramTypes.length > 0) {
            for (int i = 0; i < paramTypes.length - 1; i++) {
                System.out.print(paramTypes[i].getName() + ", ");
            }
            System.out.println(paramTypes[paramTypes.length - 1].getName() + ");");
        }else {
            System.out.println(");");
        }
    }
}
