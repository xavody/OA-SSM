package me.imtt.oa.biz.impl;

import me.imtt.oa.biz.EmployeeBiz;
import me.imtt.oa.dao.EmployeeDao;
import me.imtt.oa.entity.Employee;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("employeeBiz")
public class EmployeeBizImpl implements EmployeeBiz {
    @Resource
    private EmployeeDao employeeDao;

    public void add(Employee employee) {
        employee.setPassword("123456");
        employeeDao.insert(employee);
    }

    public void edit(Employee employee) {
        employeeDao.update(employee);
    }

    public void remove(String sn) {
        employeeDao.delete(sn);
    }

    public Employee get(String sn) {
        return employeeDao.select(sn);
    }

    public List<Employee> getAll() {
        return employeeDao.selectAll();
    }
}
