package me.imtt.oa.controller;

import me.imtt.oa.biz.ClaimVoucherBiz;
import me.imtt.oa.dto.ClaimVoucherInfo;
import me.imtt.oa.entity.ClaimVoucherItem;
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

        return "redirect:detail?id=" + claimVoucherInfo.getClaimVoucher().getId();
    }


}
