package Salvia.test;

import Salvia.BaseCalculate.MyString;
import org.junit.Test;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class MyStringTestClass {
    MyString myString = new MyString();
    //equal
    @Test
    public void test1(){
        MyString myString = new MyString("hello".toCharArray());
        MyString myString1 = new MyString("hello".toCharArray());
        System.out.println(myString1.myEquals(myString));//true
        System.out.println(myString1 == myString);//false

    }

    //连接字符串
    @Test
    public void test2(){
        MyString str1 = new MyString("hello".toCharArray());
        MyString str2 = new MyString("world".toCharArray());
        System.out.println(str1.length());
        System.out.println(str1.concat(str2).toString());

    }

    //摘取字符串
    @Test
    public void test3() {
        MyString string = new MyString("helloWorld".toCharArray());
        CharSequence sub = string.subSequence(1,3);
        System.out.println(sub.toString().trim());

    }
//测试 copyOfRange
//    @Test
//    public void test4(){
//      char [] original = new char[5];
//      original [0] = 'h';
//      original [1] = 'e';
//      original [2] = 'l';
//      original [3] = 'l';
//      original [4] = 'o';
//        System.out.println(myString.copyOfRange(original,1,2));
//
//
//    }

    //比较字符串大小,输出ASCII的值
    @Test
    public void test4(){
        MyString str1 = new MyString("hello".toCharArray());
        MyString str2 = new MyString("World".toCharArray());
        System.out.println(str1.compareTo(str2));

    }


    //判断字符串是否包含子串
    @Test
    public void test5(){
        MyString str1 = new MyString("hello".toCharArray());
        MyString str2 = new MyString("hello World".toCharArray());
        System.out.println(str2.contains(str1));


    }


    //连接字符串
    @Test
    public void test6(){
        MyString str1 = new MyString("hello".toCharArray());
        MyString str2 = new MyString("hello World".toCharArray());
        System.out.println(str2.concat(str1));
    }

    //判断字符串是否以前缀开头
    @Test
    public void test7(){
        MyString str1 = new MyString("hello".toCharArray());
        MyString str2 = new MyString("hello World".toCharArray());
        System.out.println(str2.startsWith(str1));
        MyString str3 = new MyString("World".toCharArray());
        MyString str4 = new MyString("W".toCharArray());
        System.out.println(str3.startsWith(str4,0));
    }

    //大写转小写
    @Test
    public void test8(){
        MyString str1 = new MyString("HELLO".toCharArray());
        MyString str2 = new MyString("hello World".toCharArray());
        System.out.println(str1.toLowerCase());
        System.out.println(str2.toUpperCase());
    }

    //截取字符串,返回一个新字符串对象
    @Test
    public void test9(){
        MyString str1 = new MyString("HELLO".toCharArray());
        MyString str2 = new MyString("hello World".toCharArray());
        MyString sb = str2.subString(1,3);
        MyString sb1 = str1.subString(2);
        System.out.println(sb.toString().trim());
        System.out.println(sb1.toString().trim());

    }

}
