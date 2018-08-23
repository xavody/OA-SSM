package me.imtt.oa.biz.impl;

import me.imtt.oa.biz.GlobalBiz;
import me.imtt.oa.dao.EmployeeDao;
import me.imtt.oa.entity.Employee;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("globalBiz")
public class globalBizImpl implements GlobalBiz {
    @Resource
    private EmployeeDao employeeDao;

    public Employee login(String sn, String password) {
        Employee employee = employeeDao.select(sn);
        if (employee != null && employee.getPassword().equals(password)) {
            return employee;
        }
        return null;
    }

    public void changePassword(Employee employee) {
        employeeDao.update(employee);
    }
}
