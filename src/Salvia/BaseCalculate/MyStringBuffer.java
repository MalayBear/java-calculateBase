package Salvia.BaseCalculate;

import java.io.Serializable;

public final class MyStringBuffer  {
   private char[] value;
   private int count;
   MyString myString = new MyString();

   //空参
   public MyStringBuffer(){
       value = new char[16];//初始值为16
       count = 0;//跟踪字符串长度 更新字符定义到下一个可用的位置
   }
   public MyStringBuffer(int capacity){
       value = new char[capacity];
       count = 0;
   }

   //传字符串
    public MyStringBuffer(MyString str){
       value = new char[str.length() + 16];//给出一段额外的空间 用于 append
       count = str.length();
       //从 0 开始 复制 count 个元素
       str.getChars(0,count ,value,0);
    }

    //检查是否需要 扩容
    private void ensureCapacity(int minCapacity){
       if (minCapacity > value.length){
            expandCapacity(minCapacity);
       }
    }
    //扩容
    private void expandCapacity(int minCapacity){
       int newCapacity = value.length * 2 + 4;// + 4 避免容量为零的情况
        if (minCapacity > newCapacity)
            newCapacity = minCapacity;
        char [] newValue = new char[newCapacity];
        System.arraycopy(value,0,newValue,0,count);
        value = newValue;
    }
    //避免无限调用
   private boolean appended = false;
    //添加字符序列
    public MyStringBuffer append(CharSequence ch){

       if (ch == null){
           return append("null");
       }
       if (!appended){
           appended = true;
           //转化为字符串，储存到缓冲区
           return append(ch.toString());
       }
       else
           return this;
    }

    public MyStringBuffer append(MyString myString, int start, int end) {
        if (myString == null) {
            myString = new MyString("null".toCharArray());
        }
        //toString是一个 Object的一个继承 会被所有类继承
        return append(myString.toString().substring(start, end));
    }

    //添加字符串，添加末尾
    public MyStringBuffer append(MyString str){
       int len = str.length();
       //检查是否需要扩容
       ensureCapacity(len + count);
       str.getChars(0,len,value,count);
       count += len;
       return this;//返回当前 MyStringBuffer 的对象
    }

    //传入一个对象
    public MyStringBuffer append(Object o){
       if (o instanceof MyString){
           append((MyString) o);
       }
       return append(o.toString());
    }

    //字符串反转
    public MyStringBuffer reverse(){
       for (int i = 0 , j  = count - 1 ; i < j ; i ++ , j--){
           char temp = value[i];
           value[i] = value[j];
           value[j] = temp;
       }
       return this;
    }

    public void replace(int start , int end ,MyString string){
        checkRange(start,end);
        //减去要删除的长度，再加上 字符串 的长度
        int newLength = count - (end - start) + string.length();
        char [] newValue = new char[newLength];
        System.arraycopy(value, 0, newValue, 0, start);
        System.arraycopy(string.toCharArray(), 0, newValue, start, string.length());
        System.arraycopy(value, end, newValue, start + string.length(), count - end);
        value = newValue;
        count = newLength;
    }

    //start包括，end 不包括
    public void delete(int start , int end){
       checkRange(start,end);
       int newLength = count  - (end - start);
       char [] newValue = new char[newLength];
       System.arraycopy(value, 0, newValue, 0, start);
       System.arraycopy(value, end, newValue, start, count - end);
       value = newValue;
       count = newLength;
    }

    //校验 索引
    private void checkRange(int start , int end ){
       if (start < 0 || end > count || start >= end)
           throw new IndexOutOfBoundsException();
    }
    public String toString(){
       return new String(value);
    }


}
