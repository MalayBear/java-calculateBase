package Salvia.BaseCalculate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Locale;

//Serializable,用于对象转化为字节流 Comparable<String>泛型接口用于自然排序
// CharSequence 抽象类接口 可以使用length、charAt
public class MyString implements Serializable ,Comparable<String>,CharSequence {

    @Serial
    //serialVersionUID 用来表明类的不同版本间的兼容性，数字表明这个序列化的类的唯一标识
    private static final long serialVersionUID = -204188373852348874L;
    //重写，value,由于 value属性为 private
    private char[] value;

    @Override
    public String toString() {//不可改为 MyString 会出现不兼容问题
        return new String(this.value);
    }

    public MyString() {
    }

    public MyString(char[] value) {
        this.value = value;
    }
    public static String valueOf(Object obj) {
        return (obj == null) ? "null" : obj.toString();
    }
    public static MyString valueOf(char[] data) {
        return new MyString(data);
    }
    public void MyString(char[] value) {
        this.value = MyString.copyOf(value, value.length);
    }


    //将字节数组转化为字符数组
    public MyString(Byte[] bytes) {
        value = toCharArray(bytes);
    }

    //重写toCharArray
    public  <T> char[] toCharArray(T[] array) {
        //创建一个字符数组与 输入数组 长度相同
        char[] result = new char[array.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = (char) array[i];
        }
        return result;
    }
    public char [] toCharArray(){
        char [] result = new char[value.length];
        System.arraycopy(value,0,result,0,value.length);
        return result;
    }

    //offer 为偏移量，count 数组元素的个数
    public MyString(char[] value, int offset, int count) {
        if (offset < 0)
            throw new StringIndexOutOfBoundsException();
        if (count <= 0) {
            if (count < 0) {
                throw new StringIndexOutOfBoundsException();
            }
            if (offset < value.length)
                this.value = new char[0];
        } else if (offset > value.length - count)
            throw new StringIndexOutOfBoundsException();
        else
            this.value = MyString.copyOfRange(value, offset, count);
    }

    //getChars () 方法将字符从字符串复制到目标字符数组 复制形式
    public void getChars (int start ,int end , char[] str , int strStart){
        if(start < 0 || end > value.length || strStart > end){
            throw new IndexOutOfBoundsException();
        }
        System.arraycopy(value,start,str,strStart,end - start);
    }


    //重写 copyOfRange,from包含结束下标，to 不包含结束下标
    //不能用 T，因为无法实例化
  private static char[] copyOfRange(char [] original, int from, int to) {
        int copyLength = to - from;
        if (copyLength < 0) throw new IllegalArgumentException(from + ">" + to);
        char[] copy = new char[original.length];
        System.arraycopy(original, from, copy, 0,
                    Math.min(original.length - from, copyLength));
            return copy;

    }
    //获取字符串长度
    public final int length() {
        return value.length;
    }

    //返回某索引处的字符
    public char charAt(int index){
        if (index < 0 || index > value.length){
            throw new StringIndexOutOfBoundsException();
        }

        return value[index];
    }


    //从指定区域摘出 字符串
    //charSequence是一个接口，表示char值的一个可读序列
    @Override
    public CharSequence subSequence(int start, int end) {
        if (start < 0){
            throw new StringIndexOutOfBoundsException(start);
        }
        if (end > value.length){
            throw new StringIndexOutOfBoundsException(end);
        }
        if (start > end){
            throw new StringIndexOutOfBoundsException("start index is bigger than end index.");
        }

        return new MyString(copyOfRange(value,start,end));

    }

    //比较字符串大小
    @Override
    public int compareTo(String o) {
        return 0;
    }

    //重写compareTo
    public int compareTo(MyString myString){
        int len1 = value.length;
        int len2 = myString.value.length;
        //取最小值
        int minLen = Math.min(len1,len2);
       //把 value放入数组
        char [] v1 = value;
        char [] v2 = myString.value;
        int i = 0;
        while (i < minLen){
            char c1 = v1[i];
            char c2 = v2[i];
            if (c1 != c2){
                return c1 - c2;//返回ASCII差值；
            }
            i++;
        }
        return len1 - len2;
    }

    //重写 equal
    public boolean myEquals(Object object){
        //比较所在地址相同
        if (this == object)
            return true;
       //判断类型是否相关
        if (object instanceof MyString){
           MyString anotherObject = (MyString) object;
            //迭代器
            int n = value.length;
            if (n == anotherObject.value.length){
               char [] value1 = value;
               char [] value2 = anotherObject.value;
               //遍历数组，有一个元素不相等即为 false
                for (int i = 0; i <n ; i++) {
                    if (value1[i] != value2[i])
                        return false;
                }
            }
        }
        return true;
    }

    //当且仅当此字符串包含指定的char值序列时返回true
    public boolean contains(MyString s){
        return indexOf(s) > -1;
    }


    //查找单个字符在字符串中的位置
    public int indexOf(int ch , int fromIndex){
        if (fromIndex > value.length){
            throw new StringIndexOutOfBoundsException(fromIndex);
        }
        for (int i = fromIndex; i < value.length ; i++) {
            if (value[i] == ch)
                return i;
        }
        return -1;
    }

    //查找一个字符串在另一个字符串中的位置
    public int indexOf(MyString s ,int fromIndex){
       char [] source = value;
       char [] target = s.value;//在此寻找 字符串
        for (int i = fromIndex; i < source.length; i++) {
            //字符串第一次出现的位置
            if (source[i] == target[0]){
                int index = i;
                int samLen = 1;//统计出现的次数
                for (int j = 1; j < target.length && i + j < source.length; j++) {
                    if (target[j] != source [i + j]){
                       break;
                    }
                    samLen ++;
                }

                if (samLen == target.length)
                    return index;
            }

        }
        return -1;
    }
//找指定字符串第一次出现的位置
    public int indexOf(MyString s ){
        return indexOf(s,0);
    }
    //返回指定字符串在字符串中第一次出现的位置
    public int indexOf(int ch){
        return indexOf(ch,0);
    }


    //连接字符串
    public MyString concat(MyString myString){
        if (myString.length() == 0)
            return this;
        int len = value.length;
        int newLen = myString.length();
        //定义一个新数组用于拼接
        char[] buf = copyOf(value,len + newLen);
        System.arraycopy(myString.value,0,buf,len,newLen);
        return new MyString(buf);
    }

    //Arrays.copyOf,更新数组长度
    private static char [] copyOf(char [] original,int newLength){
       char [] a = new char[newLength];
        if (original.length > newLength){
            for (int i = 0; i < a.length; i++) {
                a[i] = original[i];
            }
        }
        else {
            int j = 0;
            for ( j = 0; j < original.length; j++) {
                a[j] = original[j];
            }
            while (j < newLength){
                //将数组剩余位置，放入0元素
                a[j] = 0;
                j++;
            }

        }
        return a;
    }



    //判断从fromIndex开始，子串是否以指定前缀开头
    public boolean startsWith(MyString s , int fromIndex){
        char [] source = value;
        char [] target = s.value;
        int newLength = 0;
        for (int i = 0; i < target.length && i + fromIndex < source.length; i++) {
            if (target [i] != source[i + fromIndex]){
                break;
            }
            newLength ++ ;
        }
        return newLength == target.length;
    }

    public boolean startsWith(MyString s){
        return this.startsWith(s,0);
    }


    //大写转小写
    public MyString toLowerCase(){
        //调用默认的locale
        return this.toLowerCase(Locale.getDefault());
    }
    //Locale 是用来国际化数据的
    public MyString toLowerCase(Locale locale){
        if (locale == null)
            throw new NullPointerException();
        char [] newLocale = new char[value.length];
        for (int i = 0; i < newLocale.length; i++) {
            if (value [i] >= 'A' && value [i] <= 'Z'){
                newLocale[i] = (char) (value[i] + 32);
            }
            else
                newLocale[i] = value[i];
        }
        return new MyString(newLocale);

    }
    //小写转大写
    public MyString toUpperCase(){
        return this.toUpperCase(Locale.getDefault());
    }
    public MyString toUpperCase(Locale locale){
        if (locale == null)
            throw new NullPointerException();
        char [] newLocale = new  char[value.length];
        for (int i = 0; i < value.length; i++) {
            if (value[i] >= 'a' && value[i] <= 'z')
                newLocale[i] = (char) (value[i] - 32);
            else
                newLocale[i] = value[i];
        }
        return new MyString(newLocale);
    }

    //截取子串
    public MyString subString(int start,int end){
        if (start < 0)
            throw new StringIndexOutOfBoundsException(start);
        if (end > value.length)
            throw new StringIndexOutOfBoundsException(end);
        return (start == 0 && end == value.length ? this : new MyString(value,start,end));

    }
    public MyString subString(int start){
        if (start < 0)
            throw new StringIndexOutOfBoundsException(start);
        return (start == 0 ? this : new MyString(value,start,value.length-start));
    }
}
