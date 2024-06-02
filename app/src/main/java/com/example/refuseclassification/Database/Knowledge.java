package com.example.refuseclassification.Database;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * 代表知识数据库表的实体类
 * 继承自 LitePalSupport 以支持 LitePal 的 ORM 功能
 */
/**
 * scc
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


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }


    public void setKind(String kind) {
        this.kind = kind;
    }


    public String getKind() {
        return kind;
    }


    public void setAnswer(String answer) {
        this.answer = answer;
    }

 
    public String getAnswer() {
        return answer;
    }
}
