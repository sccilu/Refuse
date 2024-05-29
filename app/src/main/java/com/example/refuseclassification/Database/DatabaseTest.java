package com.example.refuseclassification.Database;

import org.litepal.LitePal;
import org.litepal.crud.callback.FindMultiCallback;
import org.litepal.tablemanager.Connector;

import java.util.List;

/**
 * 测试数据库连接的类
 */
public class DatabaseTest {

    public static void main(String[] args) {
        // 初始化数据库连接
        Connector.getDatabase();

        // 插入测试数据
        insertTestData();

        // 查询并打印所有数据
        queryAndPrintAllData();
    }

    /**
     * 插入测试数据到数据库
     */
    private static void insertTestData() {
        Knowledge knowledge1 = new Knowledge(1, "塑料瓶", "可回收物", "可以回收利用，扔到可回收物垃圾桶");
        Knowledge knowledge2 = new Knowledge(2, "香蕉皮", "厨余垃圾", "属于有机垃圾，扔到厨余垃圾桶");
        Knowledge knowledge3 = new Knowledge(3, "电池", "有害垃圾", "含有重金属污染物，扔到有害垃圾桶");

        knowledge1.save();
        knowledge2.save();
        knowledge3.save();

        System.out.println("测试数据已插入");
    }

    /**
     * 查询并打印所有知识数据
     */
    private static void queryAndPrintAllData() {
        LitePal.findAllAsync(Knowledge.class).listen(new FindMultiCallback<Knowledge>() {
            @Override
            public void onFinish(List<Knowledge> list) {
                for (Knowledge knowledge : list) {
                    System.out.println("ID: " + knowledge.getId());
                    System.out.println("Name: " + knowledge.getName());
                    System.out.println("Kind: " + knowledge.getKind());
                    System.out.println("Answer: " + knowledge.getAnswer());
                    System.out.println("--------------");
                }
            }
        });
    }
}
