package com.huan.myadt.bean.CGQ_study;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by CGQ on 2017/3/29 0029.
 */
@Entity(nameInDb = "t_question")
public class Question {
    /*@Property(nameInDb = "id")*///通过@Property()这个注解定义我外部数据库的字段名才能解决
    @Id/*(autoincrement=true)*/
    private Long id;
    @Property(nameInDb = "description")
    private String description;//问题描述
    @Transient
    private int index;//序号
    @Transient
    private boolean result;//结果
    @Transient
    private String instructions;//结果说明
    @Transient
    private int score;//得分

    @Generated(hash = 331947387)
    public Question(Long id, String description) {
        this.id = id;
        this.description = description;
    }
    @Generated(hash = 1868476517)
    public Question() {
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
