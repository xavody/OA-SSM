package me.imtt.oa.controller;

import me.imtt.oa.biz.ClaimVoucherBiz;
import me.imtt.oa.dto.ClaimVoucherInfo;
import me.imtt.oa.entity.DealRecord;
import me.imtt.oa.entity.Employee;
import me.imtt.oa.global.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller("claimVoucherController")
@RequestMapping("/claim_voucher")
public class ClaimVoucherController {
    @Resource
    private ClaimVoucherBiz claimVoucherBiz;

    @RequestMapping("/detail")
    public String detail(int id, Map<String, Object> map) {
        map.put("claimVoucher", claimVoucherBiz.get(id));
        map.put("items", claimVoucherBiz.getItems(id));
        map.put("records", claimVoucherBiz.getRecords(id));
        return "claim_voucher_detail";
    }

    @RequestMapping("/add")
    public String add(Map<String, Object> map) {
        map.put("items", Constant.getItems());
        map.put("claimVoucherInfo", new ClaimVoucherInfo());

        return "claim_voucher_add";
    }

    @RequestMapping("/addTo")
    public String addTo(HttpSession session, ClaimVoucherInfo claimVoucherInfo) {
        Employee employee = (Employee) session.getAttribute("employee");
        claimVoucherInfo.getClaimVoucher().setCreateSn(employee.getSn());
        claimVoucherBiz.save(claimVoucherInfo.getClaimVoucher(), claimVoucherInfo.getItems());

//        return "redirect:detail?id=" + claimVoucherInfo.getClaimVoucher().getId();
        return "redirect:deal";
    }

    /**
     * 个人报销单
     */
    @RequestMapping("/self")
    public String self(HttpSession session, Map<String, Object> map) {
        Employee employee = (Employee) session.getAttribute("employee");
        map.put("list", claimVoucherBiz.getSelf(employee.getSn()));
        return "claim_voucher_self";
    }

    /**
     * 待处理报销单
     */
    @RequestMapping("/deal")
    public String deal(HttpSession session, Map<String, Object> map) {
        Employee employee = (Employee) session.getAttribute("employee");
        map.put("list", claimVoucherBiz.getDeal(employee.getSn()));
        return "claim_voucher_deal";
    }

    @RequestMapping("/update")
    public String update(int id, Map<String, Object> map) {
        map.put("items", Constant.getItems());

        ClaimVoucherInfo claimVoucherInfo = new ClaimVoucherInfo();
        claimVoucherInfo.setClaimVoucher(claimVoucherBiz.get(id));
        claimVoucherInfo.setItems(claimVoucherBiz.getItems(id));
        map.put("claimVoucherInfo", claimVoucherInfo);

        return "claim_voucher_update";
    }

    @RequestMapping("/updateTo")
    public String updateTo(HttpSession session, ClaimVoucherInfo claimVoucherInfo) {
        Employee employee = (Employee) session.getAttribute("employee");
        claimVoucherInfo.getClaimVoucher().setCreateSn(employee.getSn());
        claimVoucherBiz.update(claimVoucherInfo.getClaimVoucher(), claimVoucherInfo.getItems());

        return "redirect:deal";
    }

    /**
     * 提交报销单
     */
    @RequestMapping("/submit")
    public String submit(int id) {
        claimVoucherBiz.submit(id);
        return "redirect:deal";
    }

    /**
     * 跳转到审核报销单页面
     */
    @RequestMapping("/check")
    public String check(int id, Map<String, Object> map) {
        map.put("claimVoucher", claimVoucherBiz.get(id));
        map.put("items", claimVoucherBiz.getItems(id));
        map.put("records", claimVoucherBiz.getRecords(id));
        DealRecord dealRecord = new DealRecord();
        dealRecord.setClaimVoucherId(id);
        map.put("record", dealRecord);
        return "claim_voucher_check";
    }

    /**
     * 审核报销单
     */
    @RequestMapping("/checkTo")
    public String checkTo(HttpSession session, DealRecord dealRecord) {
        Employee employee = (Employee) session.getAttribute("employee");
        dealRecord.setDealSn(employee.getSn());
        claimVoucherBiz.deal(dealRecord);

        return "redirect:deal";
    }
}
