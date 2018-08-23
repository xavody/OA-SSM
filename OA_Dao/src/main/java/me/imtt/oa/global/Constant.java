package me.imtt.oa.global;

import java.util.ArrayList;
import java.util.List;

/**
 * 各种常量
 */
public class Constant {
    public static final String POST_STAFF = "员工";

    public static final String POST_FM = "部门经理";

    public static final String POST_GM = "总经理";

    public static final String POST_CASHIER = "财务";

    public static List<String> getPosts() {
        List<String> posts = new ArrayList<String>();
        posts.add(POST_STAFF);
        posts.add(POST_FM);
        posts.add(POST_GM);
        posts.add(POST_CASHIER);

        return posts;
    }

    /**
     * 费用类别
     */
    public List<String> getItems() {
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
