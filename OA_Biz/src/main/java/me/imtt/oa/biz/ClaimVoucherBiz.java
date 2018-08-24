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
}
