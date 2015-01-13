package com.keertech.myandroid.test;

/**
 * *****************************************
 * Description ：SQL测试
 * in和exists
 * in 是把外表和内表作hash 连接，而exists是对外表作loop循环，每次loop循环再对内表进行查询。
 * <p/>
 * 如果两个表中一个较小，一个是大表，则子查询表大的用exists，子查询表小的用in：
 * 例如：表A（小表），表B（大表）1：select * from A where cc in (select cc from B)
 * 效率低，用到了A表上cc列的索引；select * from A where exists(select cc from B where cc=A.cc)
 * 效率高，用到了B表上cc列的索引。
 * 相反的2：select * from B where cc in (select cc from A)
 * 效率高，用到了B表上cc列的索引；select * from B where exists(select cc from A where cc=B.cc)
 * 效率低，用到了A表上cc列的索引。
 * not in 和not exists如果查询语句使用了not in 那么内外表都进行全表扫描，没有用到索引；而not extsts 的子查询依然能用到表上的索引。所以无论那个表大，用not exists都比not in要快。
 * <p/>
 * in 与 =的区别
 * select name from student where name in ('zhang','wang','li','zhao');与
 * select name from student where name='zhang' or name='li' or
 * name='wang' or name='zhao'
 * 的结果是相同的。
 * Created by cy on 2014/11/14.
 * *****************************************
 */
public class SQLTest {
    public static void main(String[] args) {
    }
}
