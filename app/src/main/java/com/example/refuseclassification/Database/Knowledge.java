package com.example.refuseclassification.Database;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * 代表知识数据库表的实体类
 * 继承自 LitePalSupport 以支持 LitePal 的 ORM 功能
 */
public class Knowledge extends LitePalSupport implements Serializable {

    // 知识项的唯一标识符
    private int id;
    // 知识项的名称
    private String name;
    // 知识项的类别
    private String kind;
    // 知识项的答案或描述
    private String answer;

    /**
     * 无参构造函数
     * 必须有一个无参构造函数以供 LitePal 调用
     */
    public Knowledge() {

    }

    /**
     * 带参构造函数
     * 用于创建带有初始值的 Knowledge 对象
     *
     * @param id     唯一标识符
     * @param name   名称
     * @param kind   类别
     * @param answer 答案或描述
     */
    public Knowledge(int id, String name, String kind, String answer) {
        this.id = id;
        this.name = name;
        this.kind = kind;
        this.answer = answer;
    }

    /**
     * 设置唯一标识符
     *
     * @param id 唯一标识符
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 获取唯一标识符
     *
     * @return 唯一标识符
     */
    public int getId() {
        return id;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取名称
     *
     * @return 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置类别
     *
     * @param kind 类别
     */
    public void setKind(String kind) {
        this.kind = kind;
    }

    /**
     * 获取类别
     *
     * @return 类别
     */
    public String getKind() {
        return kind;
    }

    /**
     * 设置答案或描述
     *
     * @param answer 答案或描述
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * 获取答案或描述
     *
     * @return 答案或描述
     */
    public String getAnswer() {
        return answer;
    }
}
