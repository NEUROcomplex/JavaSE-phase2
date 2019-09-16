### 反射



#### 1. 获得Class对象的三种方法

* Class c1 = 对象.getClass();
* Class c1 = Class.forName(String) String代表类名 ,若String不为类名/接口则报错, 因此需抛出异常;
* Class c1 = 类.class; 如 Class c1 = int.class/Random.class ...;



#### 2. 利用反射分析类

*利用Class.forName(String)得到的Class 对象 x, 可以将该类对象的成员打印出来*

**import java.lang.reflect.*;**

* 获得类名
  * x.getName() / getSimpleName()
* 获得单个成员或一类成员的数组
  * Field类的对象
    * x.getField(String) / getFields(); 获得公共域
    * x.getDeclaredField() / getDeclaredFields(); 返回所有的域
  * Constructor类的对象
    * x.getConstructor(String)...同上
  * Method类的对象
    * x.getMethod(String)...同上
* 获得修饰符
  * String modifiers = Modifier.toString(成员类对象.getModifiers());



#### 3.利用反射访问运行中的对象的域的值

*可以绕过访问权限访问对象的成员变量, 获取或修改变量的值;*

**import java.lang.reflect.*;**

* 获取对象obj的类对象
  * Class x = obj.getClass();
* 获取域, 利用父类AccessibleObject的setAccessible()方法抑制访问权限检查;
  * Field f = x.getField(String);
  * f.setAccessible(true); 
    * 也可AccessibleObject.setAccessible(域对象数组, true)抑制整个数组的元素的检查;
  * Object val = f.get(obj) ; 获得f所代表的域在obj对象内的值;
  * set(Object  obj, Object  value); 修改值



#### 4. 利用反射调用方法

* 获得Method类的对象m;
* 调用invoke(Object obj, Object实参列表);  obj为本类对象;
  * m.invoke(obj, ......); 若m为静态方法, obj置为null;
  * 返回值与参数类型均为Object, 因此需要进行类型转换;



#### 5. 利用反射编写泛型数组

若需复制给定数组 a 内容并拓展长度, 诸如

Object[] newArray = new Obejct[newLength]; 

System.arraycopy(...a...newArray...);的代码返回的是定义为Object类型的数组, 无法向下转型为a的类型; 因此可利用反射得到定义的类型为 a 的类型的复制后的数组: 

* 可利用java.util.Arrays.copyOf(旧数组, 新长度 )实现;
* 利用java.lang.reflect.Array.newInstance (Class 元素类型的类对象, 新长度 )实现;
  * Array.newInstance(Class componentType, int...dimensions)也可创建多维数组;





