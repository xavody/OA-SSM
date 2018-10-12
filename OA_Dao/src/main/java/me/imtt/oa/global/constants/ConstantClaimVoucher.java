package me.imtt.oa.global.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * 报销单相关
 */
public class ConstantClaimVoucher {
    /**
     * 费用类别
     */
    public static List<String> getItems() {
        List<String> items = new ArrayList<String>();
        items.add("交通");
        items.add("餐饮");
        items.add("住宿");
        items.add("办公");
        items.add("其它");

        return items;
    }

    //报销单状态
    public static final String CLAIM_VOUCHER_CREATED = "新创建";
    public static final String CLAIM_VOUCHER_SUBMIT = "已提交";
    public static final String CLAIM_VOUCHER_APPROVED = "已审核";
    public static final String CLAIM_VOUCHER_BACK = "已打回";
    public static final String CLAIM_VOUCHER_TERMINATED = "已中止";
    public static final String CLAIM_VOUCHER_RECHECK = "待复审";
    public static final String CLAIM_VOUCHER_PAID = "已打款";

    //审核额度
    public static final double LIMIT_CHECK = 5000.00;

    //处理状态
    public static final String DEAL_CREATE = "创建";
    public static final String DEAL_SUBMIT = "提交";
    public static final String DEAL_UPDATE = "修改";
    public static final String DEAL_BACK = "打回";
    public static final String DEAL_REJECT = "拒绝";
    public static final String DEAL_PASS = "通过";
    public static final String DEAL_PAID = "打款";
}
