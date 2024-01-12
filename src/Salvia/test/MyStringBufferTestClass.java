package Salvia.test;

import Salvia.BaseCalculate.MyString;
import Salvia.BaseCalculate.MyStringBuffer;
import org.junit.Test;

public class MyStringBufferTestClass {
    @Test
    //测试 StringBuffer 中的  append , reverse ,replace, delete
    public void test1(){
        MyString myString = new MyString("hello".toCharArray());
        MyString myString1 = new MyString("Hello".toCharArray());
        MyString myString2 = new MyString("world".toCharArray());
        MyString myString3 = new MyString("World".toCharArray());
        MyStringBuffer buffer = new MyStringBuffer();
        buffer.append(myString);
        //反转
        buffer.reverse();
        System.out.println(buffer.toString().trim());
        MyStringBuffer buffer1 = new MyStringBuffer();
        buffer1.append(myString1);
        buffer1.append(myString2);
        buffer1.replace(0,5,myString3);
        System.out.println(buffer1);
        //删除
        buffer1.delete(0,5);
        System.out.println(buffer1);
    }
}
