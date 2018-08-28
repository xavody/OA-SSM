package me.imtt.oa.controller;

import me.imtt.oa.biz.LeaveVoucherBiz;
import me.imtt.oa.entity.Employee;
import me.imtt.oa.entity.LeaveDealRecord;
import me.imtt.oa.entity.LeaveVoucher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller("leaveVoucherController")
@RequestMapping("/leave_voucher")
public class LeaveVoucherController {
    @Resource
    private LeaveVoucherBiz leaveVoucherBiz;

    @RequestMapping("/detail")
    public String detail(int id, Map<String, Object> map) {
        map.put("leaveVoucher", leaveVoucherBiz.get(id));
        map.put("records", leaveVoucherBiz.getRecords(id));
        return "leave_voucher_detail";
    }

    @RequestMapping("/add")
    public String add(Map<String, Object> map) {
        map.put("leaveVoucher", new LeaveVoucher());
        return "leave_voucher_add";
    }

    @RequestMapping("/addTo")
    public String addTo(HttpSession session, LeaveVoucher leaveVoucher) {
        Employee employee = (Employee) session.getAttribute("employee");
        leaveVoucher.setCreateSn(employee.getSn());
        leaveVoucherBiz.save(leaveVoucher);

        return "redirect:deal";
    }

    /**
     * 个人请假单
     */
    @RequestMapping("/self")
    public String self(HttpSession session, Map<String, Object> map) {
        Employee employee = (Employee) session.getAttribute("employee");
        map.put("list", leaveVoucherBiz.getSelf(employee.getSn()));
        return "leave_voucher_self";
    }

    /**
     * 待处理请假单
     */
    @RequestMapping("/deal")
    public String deal(HttpSession session, Map<String, Object> map) {
        Employee employee = (Employee) session.getAttribute("employee");
        map.put("list", leaveVoucherBiz.getDeal(employee.getSn()));
        return "leave_voucher_deal";
    }

    @RequestMapping("/update")
    public String update(int id, Map<String, Object> map) {
        LeaveVoucher leaveVoucher = leaveVoucherBiz.get(id);
        map.put("leaveVoucher", leaveVoucher);

        return "leave_voucher_update";
    }

    @RequestMapping("/updateTo")
    public String updateTo(HttpSession session, LeaveVoucher leaveVoucher) {
        Employee employee = (Employee) session.getAttribute("employee");
        leaveVoucher.setCreateSn(employee.getSn());
        leaveVoucherBiz.update(leaveVoucher);

        return "redirect:deal";
    }

    /**
     * 提交请假单
     */
    @RequestMapping("/submit")
    public String submit(int id) {
        leaveVoucherBiz.submit(id);
        return "redirect:deal";
    }

    /**
     * 跳转到审核请假单页面
     */
    @RequestMapping("/check")
    public String check(int id, Map<String, Object> map) {
        map.put("leaveVoucher", leaveVoucherBiz.get(id));
        map.put("records", leaveVoucherBiz.getRecords(id));
        LeaveDealRecord leaveDealRecord = new LeaveDealRecord();
        leaveDealRecord.setLeaveVoucherId(id);
        map.put("record", leaveDealRecord);
        return "leave_voucher_check";
    }

    /**
     * 审核请假单
     */
    @RequestMapping("/checkTo")
    public String checkTo(HttpSession session, LeaveDealRecord leaveDealRecord) {
        Employee employee = (Employee) session.getAttribute("employee");
        leaveDealRecord.setDealSn(employee.getSn());
        leaveVoucherBiz.deal(leaveDealRecord);

        return "redirect:deal";
    }
}
