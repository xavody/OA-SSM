package me.imtt.oa.entity;

/**
 * 员工
 */
public class Employee {
    /**
     * 编号
     */
    private String sn;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 所属部门编号
     */
    private String departmentSn;

    /**
     * 职位
     */
    private String post;

    /**
     * 权限编号
     */
    private Integer authId;

    /**
     * 所属部门
     */
    private Department department;

    /**
     * 权限
     */
    private Authority authority;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartmentSn() {
        return departmentSn;
    }

    public void setDepartmentSn(String departmentSn) {
        this.departmentSn = departmentSn;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Integer getAuthSn() {
        return authId;
    }

    public void setAuthSn(Integer authId) {
        this.authId = authId;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}