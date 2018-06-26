package org.fastsql.test;

import org.fastsql.annotation.Column;

import java.util.Date;

public class User {

    @Column("auto_id")
    private Long autoId;

    private String name;

    private Integer age;

    private int sex;

    private String remark;


    @Column("create_time")
    private Date createTime;

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getAutoId() {
        return autoId;
    }

    public void setAutoId(Long autoId) {
        this.autoId = autoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "User{" +
                "autoId=" + autoId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
