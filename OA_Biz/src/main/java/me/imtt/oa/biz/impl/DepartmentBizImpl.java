package me.imtt.oa.biz.impl;

import me.imtt.oa.biz.DepartmentBiz;
import me.imtt.oa.dao.DepartmentDao;
import me.imtt.oa.entity.Department;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("departmentBiz")
public class DepartmentBizImpl implements DepartmentBiz {
    @Resource
    private DepartmentDao departmentDao;

    public void add(Department department) {
        departmentDao.insert(department);
    }

    public void edit(Department department) {
        departmentDao.update(department);
    }

    public void remove(String sn) {
        departmentDao.delete(sn);
    }

    public Department get(String sn) {
        return departmentDao.select(sn);
    }

    public List<Department> getAll() {
        return departmentDao.selectAll();
    }
}
