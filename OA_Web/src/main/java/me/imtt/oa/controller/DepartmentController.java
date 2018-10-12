package me.imtt.oa.controller;

import me.imtt.oa.biz.DepartmentBiz;
import me.imtt.oa.entity.Department;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Map;

@Controller("departmentController ")
@RequestMapping("/department")
public class DepartmentController {
    @Resource
    private DepartmentBiz departmentBiz;

    @RequestMapping("/list")
    public String list(Map<String, Object> map) {
        map.put("list", departmentBiz.getAll());
        return "department_list";
    }

    @RequestMapping("/add")
    public String add(Map<String, Object> map) {
        //要使用springMVC的form标签必须传递一个参数
        map.put("department", new Department());
        return "department_add";
    }

    @RequestMapping("/addTo")
    public String addTo(Department department) {
        departmentBiz.add(department);
        return "redirect:list";
    }

    /**
     * params参数进行过滤，必须传递一个参数
     */
    @RequestMapping(value = "/update", params = "sn")
    public String update(String sn, Map<String, Object> map) {
        map.put("department", departmentBiz.get(sn));
        return "department_update";
    }

    @RequestMapping("/updateTo")
    public String updateTo(Department department) {
        departmentBiz.edit(department);
        return "redirect:list";
    }

    @RequestMapping(value = "/remove", params = "sn")
    public String remove(String sn) {
        departmentBiz.remove(sn);
        return "redirect:list";
    }
}
