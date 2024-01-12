package Salvia.BaseCalculate;

import java.util.*;
// Iterable -> 迭代器,可以在 for item 中使用
public   class  MyArrayList<E> implements Iterable {
    private transient Object [] data; // 存储元素的数组
    private static final Object[] EMPTY_ARRAY = {}; // 空数组
    private static final int DEFAULT_CAPACITY = 10; // 默认初始容量
    public int size ; // 列表中元素的数量
    private int modCount = 0; // 修改次数

    public MyArrayList() {
        this.data =  EMPTY_ARRAY; // 初始化为空数组
    }


    //构造有指定容量的列表
    public MyArrayList(int initialCapacity) {
        if (initialCapacity == 0) {
            this.data =  EMPTY_ARRAY;
        } else if (initialCapacity > 0) {
            this.data =  new Object[initialCapacity];
        }
        else
            throw  new IllegalArgumentException("Illegal Capacity: "+
                    initialCapacity);

    }


//    构造一个包含指定集合的元素的列表，按照它们由集合的迭代器返回的顺序
    public MyArrayList(MyArrayList<?> myArrayList){
        Object[] elements = myArrayList.toArray();
        if ((size = elements.length) != 0){
            //检查data是否为 object 类型
            if (elements.getClass() != Object[].class){
                elements = Arrays.copyOf(elements ,size,Object[].class);
            }
        }
        //数组为空，使用 默认空数组
        else
            this.data = EMPTY_ARRAY;
    }

    //将指定的元素转换为一个固定大小的列表
    @SafeVarargs
    public static <E> MyArrayList<E> asList(E... elements){
        MyArrayList<E> list = new MyArrayList<>();
        list.addAll(elements);
        return list;
    }
    //用于 添加固定大小 list的列表
    private void addAll(E... elements) {
        for (E element : elements) {
            add(element);
        }
    }


    //检查是否需要扩容
    private void ensureCapacityInternal(int minCapacity) {
        if (data == EMPTY_ARRAY) {
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        ensureExplicitCapacity(minCapacity);
    }

    private void ensureExplicitCapacity(int minCapacity){
        modCount++;
        if (minCapacity > data.length){
            grow(minCapacity);
        }
    }

    private void grow(int minCapacity) {
        int oldCapacity = data.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0) {
            newCapacity = minCapacity;
        }
        if (newCapacity - Integer.MAX_VALUE > 0) {
            newCapacity = hugeCapacity(minCapacity);
        }
        data = Arrays.copyOf(data, newCapacity); // 扩容并复制元素到新数组
    }

    private int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) {
            throw new OutOfMemoryError();
        }
        return (minCapacity > Integer.MAX_VALUE) ? Integer.MAX_VALUE : minCapacity;
    }

    //add(e)
    public boolean add(E e) {
        ensureCapacityInternal(size + 1);
        data[size++] = e; // 将元素添加到数组末尾
        modCount++;
        return true;
    }


    // 迭代器 用于遍历元素
    @Override
    public Iterator<E> iterator() {
        return new MyIterator(); // 返回迭代器对象
    }
    private class MyIterator implements Iterator<E> {
        private int cursor = 0; // 当前迭代位置
        private final int expectedModCount = modCount; // 确保迭代器遍历过程中的数据一致性

        @Override
        public boolean hasNext() {
            return cursor != size; // 判断是否还有下一个元素
        }

        @Override
        public E next() {
            checkForComodification(); // 检查是否有并发修改
            int i = cursor;
            if (i >= size) {
                throw new NoSuchElementException();
            }
            cursor = i + 1;
            return (E) data[i]; // 返回下一个元素
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }


        //检查 modCount  expectedModCount 是否一致
        final void checkForComodification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }


    //add(index,e)
    public void add(int index , E e){
            checkIndex(index);
            //检查是否需要扩容
            ensureExplicitCapacity(size + 1);
            System.arraycopy(data,index,data,index + 1,size - index);
            data[index] = e;
            size ++;
        }
    //将集合转化为数组 toArray
    public Object[] toArray(){
        Object [] array = new Object[size];
        for (int i = 0; i < size; i++) {
            array[i] = data [i];
        }
        return array;
    }

    //将集合元素，添加到另外的数组中
    public boolean addAll(MyArrayList<?> myArrayList){
        Object[] elements = myArrayList.toArray();
        int newLength = elements.length;
        //检查是否需要扩容
        ensureCapacityInternal(size + newLength);
        System.arraycopy( elements,0,data,size,newLength);
        size = size + newLength;
        return newLength != 0;
    }

    //set方法

    public E set(int index , int element){
        rangCheck(index);
        //获取index 元素
        E oldValue = data(index);
        data[index] = element;
        return oldValue;
    }

    //get 方法
    public E get(int index){
        rangCheck(index);
        return data(index);
    }

    //获得下标处的索引
    E data(int index){
        return (E) data[index];
    }
    //判断索引是否符合规则，不能超过数组的长度
    private int rangCheck(int index){
        if (index >=0 && index <= size){
            return index;
        }
        else
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    //抛出具体索引异常
    public static String outOfBoundsMsg(int index) {
        return "Index: " + index;
    }

    //从指定位置开始
    public boolean addAll(int index,MyArrayList<?> myArrayList){
        Object[] elements = myArrayList.toArray();
        int newLength = elements.length;
        //移动个数
        int removeNum = size - index;
        if (removeNum > 0) {
            //将index后面所有元素移动
            System.arraycopy(data,index,data,index + newLength , removeNum);
            System.arraycopy(elements,0,data,index,newLength);
            size = size + newLength;
        }
        return newLength != 0;
    }
    //检查index是否符合规则,并抛出异常
    private void checkIndex(int index){
        if (index < 0 || index > data.length) throw new IndexOutOfBoundsException();
    }

    public boolean remove(E e){
        //要删除的元素为空
        if (e == null){
            //遍历数组，找到对应索引值
            for (int i = 0 ; i < size; i++){
                if (data[i] == null){
                    fastRemove(i);
                    return true;
                }
            }
        }
        else {
            for (int i = 0; i < size; i++) {
                if (e.equals(data[i])){
                    fastRemove(i);
                    return true;
                }
            }
        }
        return false;
    }
    //通过索引删除元素
    public E remove(int index){
        rangCheck(index);
        E removeElement = data(index);
        fastRemove(index);
        return removeElement;
    }
    // 删除集合元素
    private void fastRemove(int index){
        modCount++;
        //需要移动的元素个数
        int num = size - index - 1;
        if (num > 0) System.arraycopy(data,index + 1,data ,index ,num);
        //将数组的空位置设为null
        data[--size] = null;
    }

    public String toString() {
       MyStringBuffer sb = new MyStringBuffer();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    //清空集合
    public void clear(){
        modCount ++;//修改集合次数
        for (int i = 0; i < size; i++) {
            data[i] = null;//将每一个集合设为null
        }
        size = 0;//数组个数清空
    }


}
