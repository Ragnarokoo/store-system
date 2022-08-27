package com.cy.store.entity;

import java.io.Serializable;

/**
 * 表示省市区的数据实体类
 */
public class District extends BaseEntity implements Serializable {

    private Integer id; // 唯一标识
    private String parent; // 父区域的代号
    private String code; // 本身的代号
    private String name; // 代号本身的名称

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof District)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        District district = (District) o;

        if (getId() != null ? !getId().equals(district.getId()) : district.getId() != null) {
            return false;
        }
        if (getParent() != null ? !getParent().equals(district.getParent()) : district.getParent() != null) {
            return false;
        }
        if (getCode() != null ? !getCode().equals(district.getCode()) : district.getCode() != null) {
            return false;
        }
        return getName() != null ? getName().equals(district.getName()) : district.getName() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + (getParent() != null ? getParent().hashCode() : 0);
        result = 31 * result + (getCode() != null ? getCode().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "District{" +
                "id=" + id +
                ", parent='" + parent + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
