package me.imtt.oa.biz;

import me.imtt.oa.entity.Department;

import java.util.List;

public interface DepartmentBiz {
    void add(Department department);

    void edit(Department department);

    void remove(String sn);

    Department get(String sn);

    List<Department> getAll();
}
