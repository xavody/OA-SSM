package me.imtt.oa.biz;

import me.imtt.oa.entity.ClaimVoucher;
import me.imtt.oa.entity.ClaimVoucherItem;
import me.imtt.oa.entity.DealRecord;

import java.util.List;

public interface ClaimVoucherBiz {
    /**
     * 保存报销单
     */
    void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> claimVoucherItems);

    /**
     * 获取报销单
     */
    ClaimVoucher get(int id);

    /**
     * 获取报销单明细
     */
    List<ClaimVoucherItem> getItems(int cvId);

    /**
     * 获取报销单审核记录
     */
    List<DealRecord> getRecords(int cvId);

    /**
     * 获取个人报销单
     */
    List<ClaimVoucher> getSelf(String sn);

    /**
     * 获取未处理报销单
     */
    List<ClaimVoucher> getDeal(String sn);

    /**
     * 修改报销单
     */
    void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> claimVoucherItems);

    /**
     * 提交报销单
     */
    void submit(int id);

    /**
     * 对报销单进一步处理
     */
    void deal(DealRecord dealRecord);
}
