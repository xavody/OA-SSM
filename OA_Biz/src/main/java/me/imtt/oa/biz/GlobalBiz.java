package me.imtt.oa.biz;

import me.imtt.oa.entity.Employee;

public interface GlobalBiz {
    Employee login(String sn, String password);

    void changePassword(Employee employee);
}
