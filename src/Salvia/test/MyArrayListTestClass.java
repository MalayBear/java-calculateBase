package Salvia.test;

import Salvia.BaseCalculate.MyArrayList;
import Salvia.BaseCalculate.MyString;
import Salvia.pojo.User;
import org.junit.Test;

public class MyArrayListTestClass {
    //测试 add  add(index , string) addAll(index , list) addAll(list) remove remove(index)
    @Test
    public void test1(){
        //测试 add
      MyArrayList<MyString> myArrayList = new MyArrayList<>();
      MyString str1 = new MyString("zhangsan".toCharArray());
      MyString str2 = new MyString("lisi".toCharArray());
      MyString str3 = new MyString("wangwu".toCharArray());
      MyString str4 = new MyString("salvia".toCharArray());
        myArrayList.add(str1);
        myArrayList.add(str2);
        myArrayList.add(2,str3);
        myArrayList.remove(str4);
        System.out.println("myArrayList = " + myArrayList);
//        for (Object myString : myArrayList) {
//            System.out.println("string = " + myString);
//        }
//      arrayList.add("zhangsan");
//      arrayList.add("lisi");
//        for (String s:arrayList) {
//            System.out.println(s);
//        }

        //测试 addAll
        MyArrayList<MyString> myArrayList1 = new MyArrayList<>();
        MyString string1 = new MyString("laoliu".toCharArray());
        MyString string2 = new MyString("liqi".toCharArray());
        myArrayList1.add(string1);
        myArrayList1.add(string2);
       //myArrayList.addAll(myArrayList1);
        myArrayList.addAll(2,myArrayList1);
        myArrayList.remove(3);
        for (Object myString : myArrayList) {
            System.out.println("string = " + myString);
        }

    }

    //测试 构造列表 get set 方法
    @Test
    public void test2(){
        //有容量的列表
        MyArrayList<MyString> myArrayList = new MyArrayList<>(10);
        MyString string1 = new MyString("laoliu".toCharArray());
        MyString string2 = new MyString("liqi".toCharArray());
        myArrayList.add(string1);
        myArrayList.add(string2);
        //创建一个包含指定集合的列表
        MyArrayList<Integer> list =  new MyArrayList<>( MyArrayList.asList(1, 2, 3, 4, 5));
        myArrayList.set(0,8);
        System.out.println( myArrayList.get(1));
        System.out.println(myArrayList);

    }

    //测试 clear
    @Test
    public void test3(){
        MyArrayList<MyString> myArrayList = new MyArrayList<>();
        MyString str1 = new MyString("zhangsan".toCharArray());
        MyString str2 = new MyString("lisi".toCharArray());
        myArrayList.add(str1);
        myArrayList.add(str2);
        System.out.println( "清理前"+ myArrayList.toString());
        myArrayList.clear();
        System.out.println("清理后" + myArrayList.toString());
    }




}
