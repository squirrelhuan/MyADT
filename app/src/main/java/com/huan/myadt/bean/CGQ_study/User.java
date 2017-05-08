package com.huan.myadt.bean.CGQ_study;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by anye0 on 2016/7/24.
 */
@Entity
public class User {
    @Id
    private Long id;
    private String name;
    /**
     * instructions 解释说明
     */
    private String instructions;

    private String type;
    @Transient
    private int tempUsageCount; // not persisted
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setInstructions(String instructions){
        this.instructions = instructions;
    }
    public String getInstructions() {
        return this.instructions;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }
    @Generated(hash = 896205807)
    public User(Long id, String name, String instructions, String type) {
        this.id = id;
        this.name = name;
        this.instructions = instructions;
        this.type = type;
    }
    @Generated(hash = 586692638)
    public User() {
    }
}
