package me.imtt.oa.dto;

import me.imtt.oa.entity.ClaimVoucher;
import me.imtt.oa.entity.ClaimVoucherItem;

import java.util.List;

/**
 * 用户提交的报销单信息
 */
public class ClaimVoucherInfo {
    private ClaimVoucher claimVoucher;

    private List<ClaimVoucherItem> items;

    public ClaimVoucher getClaimVoucher() {
        return claimVoucher;
    }

    public void setClaimVoucher(ClaimVoucher claimVoucher) {
        this.claimVoucher = claimVoucher;
    }

    public List<ClaimVoucherItem> getItems() {
        return items;
    }

    public void setItems(List<ClaimVoucherItem> items) {
        this.items = items;
    }
}
