package me.imtt.oa.controller;

import me.imtt.oa.biz.DepartmentBiz;
import me.imtt.oa.biz.EmployeeBiz;
import me.imtt.oa.entity.Employee;
import me.imtt.oa.global.Constants.ConstantPosts;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Map;

@Controller("employeeController ")
@RequestMapping("/employee")
public class EmployeeController {
    @Resource
    private EmployeeBiz employeeBiz;

    @Resource
    private DepartmentBiz departmentBiz;

    @RequestMapping("/list")
    public String list(Map<String, Object> map) {
        map.put("list", employeeBiz.getAll());
        return "employee_list";
    }

    //要使用springMVC的form标签必须传递一个参数
    @RequestMapping("/add")
    public String add(Map<String, Object> map) {
        map.put("employee", new Employee());
        map.put("dlist", departmentBiz.getAll());
        map.put("plist", ConstantPosts.getPosts());
        return "employee_add";
    }

    @RequestMapping("/addTo")
    public String addTo(Employee employee) {
        employeeBiz.add(employee);
        return "redirect:list";
    }

    @RequestMapping(value = "/update", params = "sn")
    public String update(String sn, Map<String, Object> map) {
        map.put("employee", employeeBiz.get(sn));
        map.put("dlist", departmentBiz.getAll());
        map.put("plist", ConstantPosts.getPosts());
        return "employee_update";
    }

    @RequestMapping("/updateTo")
    public String updateTo(Employee employee) {
        employeeBiz.edit(employee);
        return "redirect:list";
    }

    @RequestMapping(value = "/remove", params = "sn")
    public String remove(String sn) {
        employeeBiz.remove(sn);
        return "redirect:list";
    }
}
