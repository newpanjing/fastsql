package org.fastsql.test;

import org.fastsql.annotation.Column;
import org.fastsql.annotation.Table;

@Table("sql_test")
public class AnnotationTest {

    @Column("sql_name")
    private String name;

    @Column("table_id")
    private Long id;

    @Column
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
