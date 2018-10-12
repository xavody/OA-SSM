package me.imtt.oa.dao;

import me.imtt.oa.entity.Employee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 员工 Dao 接口层
 * @author twu
 */
@Repository("employeeDao")
public interface EmployeeDao {
    /**
     * 添加员工
     * @param employee 员工
     */
    void insert(Employee employee);

    /**
     * 更新员工信息
     * @param employee 员工
     */
    void update(Employee employee);

    /**
     * 删除员工
     * @param sn 员工编号
     */
    void delete(String sn);

    /**
     * 查询员工
     * @param sn 员工编号
     * @return 员工
     */
    Employee select(String sn);

    /**
     * 查询所有员工
     * @return 员工（复数
     */
    List<Employee> selectAll();

    /**
     * 通过部门编号和职位获取员工
     * @param dsn 部门编号
     * @param post 职位
     * @return 员工（复数）
     */
    List<Employee> selectByDepartmentAndPost(@Param("dsn") String dsn, @Param("post") String post);
}
