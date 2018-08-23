package me.imtt.oa.entity;

/**
 * 部门
 */
public class Department {
    //编号
    private String sn;

    //名称
    private String name;

    //位置
    private String address;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}