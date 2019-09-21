### core Java 泛型



#### 类型变量 

* 泛型类可以有多个类型变量( 类型参数 ); 如: class Pair<T, U> {...}

* 泛型类与泛型方法可以共存, 各自定义的泛型不相关; 如:

  * ```java
    class GenericTest<T> {
        private T tt;
        public static <S>void go(S ss) {
        }
    }
    ```

* 数组有协变特性: 不同数组各自的元素类型有继承关系, 则数组类型也有继承关系; 但泛型类型没有 

* 常用泛型变量 

  * E-集合元素类型
  * T(或U, S)-任意类型 
  * K, V-键值对的类型



#### 定义及使用泛型

*类及方法可以定义类型变量( 泛型 ), 方法及变量可使用泛型*  ; 若作用域内未定义泛型, 则不可使用泛型;

* 静态变量无法使用泛型, 实例变量可以在泛型类中使用泛型;

* 静态方法可以定义并使用泛型, 但不能只使用泛型;
* 实例方法可以只使用或定义并使用泛型;



* 类型变量具有继承关系的不同泛型类型毫无关系,
  * ==Pair< Employee > p1 = new Pair< Manager >();报错==



#### 限定列表(泛型上限)

* < T extends 父类A **&** 接口B **&** 接口C...  > ,也称为类型变量的上限;

* 类型限定无下限,诸如< T super A... >不存在; 但通配符限定上下限均有:

  * ```java
    <? extends A>
    <? super B>
    ```


* 在泛型擦除后,  运行时类型参数会被替换为父类类型或限定列表的第一个类型, 在必要时再强制转型回来; 



#### 类型擦除

==在编译时, 泛型类及泛型方法的类型变量会被擦除, 替换为原始类型: 限定列表的首个类型或Object;==

如: class Pair< T > {...public void setSecond( T second )...}会被擦除为:

​      class Pair {...public void setSecond( Object second )...}

且:

* JVM中没有泛型, 只有普通的类和方法, 泛型在编译阶段会被泛型擦除;
* JVM用**方法签名和返回类型**确定一个方法;



* ==由于泛型擦除, 在JVM中会出现  子类同时拥有自己的重写的方法及因被泛型擦除而未被子类覆盖的父类方法== ; 

  * 如: StringPair extends Pair< String >中有:
  * setSecond(String second) 和未被覆盖的setSecond(Object second);

* 运行时Pair< String > 中的String get( ) 与 Object get( )方法, JVM能正常区分;

* 为了在上述情况中保持多态, JVM会生成桥方法: 

  * setSecond(Object second) {...setSecond( ( String ) second)...};

* 必要时, 编译时会插入强制转型指令让JVM执行, 将因类型擦除而被替换的类型转换为运行时的实际类型;



#### 类型擦除带来的限制

* 不能用基本类型实例化类型变量,即< 基本类型 > 非法, 必要时使用其包装器类;
* 运行时类型检查只会检查擦除后得到的原始类型, 即==同一泛型类型的不同参数化类型会有同样的Class实例==, 例如: 即所有Pair在虚拟机中的类型都一样: 
  * Pair< String > stringPair = new ...;  Pair< Employee > employeePair = new ...;
  * ==stringPair == employeePair 为true;==

* 理由同上, 不能创建泛型类型的数组: 如 Pair< String >[ ]; 因为StringPair 与 employeePair均可存入该数组; 但可使用Arraylist<>安全地创建: 

  * ```java
    ArrayList<Pair<String>> pairList = new ArrayList<>();
    ```

* 不能实例化类型变量, 例如 new T( )...; 因为假若可以, 会被擦除为new Object( ), 而非运行时T的实际类型 ;  若要创建T的实例: 

  * 可使用函数式接口传入构造器表达式;( 暂未接触 )
  * 使用反射: makeT( Class c1 ) { ...return ( T )c1.newInstance( )... }**( 已过时 )**

* 不能构造泛型( 类型变量 )数组, 如 T[] = {......} ; 但可将T[] 作为方法的参数, 在运行时传入实际类型数组;  可声明 private T[] arr;

  * 那么,如何用其他方式构建 T[] ?

* 不能捕获或抛出泛型的实例: 如 < T extends Throwable >, 不能抛出或捕获T的实例: 

  * 如catch(T e)

* 擦除后的方法冲突: 如: 若Pair< T >中定义boolean equals( T t )

  * 擦除后会是equals( Object t ), 与继承自Object的equals( Object o )冲突; 
  * 编译会报错, 解决方法: 改名; 

* 一个类或类型变量不能同时成为两个接口的子类( 包括间接子类 ), 这两个接口为同一接口的不同参数化; 例如

  * ```java
    class Employee implements Comparable<Employee>
    class Manager extends Employee implements Comparable<Manager>//报错
    ```

  * ```java
    class Employee implements Comparable
    class Manager extends Employee implements Comparable
    //ok;接口未声明类型参数则可以通过
    ```



#### 通配符类型

通配符限定与类型变量限定十分相似, 但有一些区别; 考虑T 与 ?

1. T 可用于变量, 但 ? 是一个类型 , 而虚拟机不知道它的具体类型, 因此诸如: 

   ```
   set(? a)//不可调用, 虚拟机无法确定?的类型
   set(T a)//可以调用, T在运行时因泛型擦除是个确定的类型
   ? get()//可行;返回一个值不需要知道其类型;
   T get()//可行;
   ? var;//不可行;
   T var;//可行
   ```


2. T 类型限定有上限但无下限; ? 通配符限定上下限均有;

3. 考虑到1. ,必要时可使用类型参数T 替代? 参与运算, 但也有通配符类型不可被替代的使用场景;



#### 原始类型与通配符类型数组

* 参见上文, 无法创建参数化类型的数组, 如: new T[...]; 或是 new List< T >[...]或是 new List< String >[...]; ( 报错Generic Array Creation )

* 但可通过原始类型和和通配符类型< ? >创建数组, 如下:

```java
List[] lists = new List[2];//可行
List<?>[] lists2 = new List<?>[2];//可行
List<T>[] list3 = new List<T>[2];//报错Generic Array Creation
List<String>[] list4 = new List<String>[2];//报错Generic Array Creation
```

​	==即使能创建成功, lists与lists2可以加入任何list的参数化类型, 是不安全的;==

* 另外, 诸如以下创建也合法: 

```java
List<String>[] list4 = new List[3];//创建的是原始类型的数组, 被赋予给List<String>类型的数组变量
```

​	在向其中加入元素时,会检查加入的元素类型是否为List< String >
​	但若在创建时静态初始化, 则会按List而非List< String >来检查类型;



### Effective Java 注意事项

* 避免使用原始类型

  * 由于类型擦除带来的限制, 在使用原始类型时, 其任意参数化类型均可通过类型检查, 有类型错误的风险;

* 使用泛型方法, 及定义受限定泛型的PECS规则

  * 泛型方法可为方法的参数及返回值提供很大的灵活性, 但在涉及类型参数的上下限时应遵守PECS规则( Producer-extend, Consumer-super ); 如: 

  ```java
  <T extends Comparable<? super T>> Pair<T> minmaxList(List<? extends T> list){...}
  
  Iterator<?> itr = /*(Iterator<T>)*/ list.listIterator();//也可以将Iterator<?>强制转型为Iterator<T>
  
  T min = (T)itr.next();//若在获取itr时将迭代器转型为了Iterator<T>, 则无需在此处转型为T
  ```

  ​	如此使用泛型, 需注意 通配符类型 **?** 表示的子类与泛型 **T** 之间的转换; 

  ​	可使用注解消除未受检查的类型转换警告;

  ```
  @SuppressWarnings("unchecked")
  ```

  ​	

* 使用类型安全的异构容器

  * 泛型容器, 若对容器本身使用泛型, 则容器内所有的元素均会受到相同的类型限制; 

  ```java
  Map<K, V> map = new HashMap<>();//类或方法中定义泛型容器
  Map<String, Pair<?>>;只可
  Map<String, Integer>;
  ```




