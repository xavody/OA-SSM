package me.imtt.oa.biz.impl;

import me.imtt.oa.biz.ClaimVoucherBiz;
import me.imtt.oa.dao.ClaimVoucherDao;
import me.imtt.oa.dao.ClaimVoucherItemDao;
import me.imtt.oa.dao.DealRecordDao;
import me.imtt.oa.entity.ClaimVoucher;
import me.imtt.oa.entity.ClaimVoucherItem;
import me.imtt.oa.entity.DealRecord;
import me.imtt.oa.global.Constant;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("claimVoucherBiz")
public class ClaimVoucherBizImpl implements ClaimVoucherBiz {
    @Resource
    private ClaimVoucherDao claimVoucherDao;

    @Resource
    private ClaimVoucherItemDao claimVoucherItemDao;

    @Resource
    private DealRecordDao dealRecordDao;

    public void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> claimVoucherItems) {
        claimVoucher.setCreateTime(new Date());
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        claimVoucher.setStatus(Constant.CLAIM_VOUCHER_CREATED);

        claimVoucherDao.insert(claimVoucher);

        for (ClaimVoucherItem item : claimVoucherItems) {
            item.setClaimVoucherId(claimVoucher.getId());
            claimVoucherItemDao.insert(item);
        }
    }

    public ClaimVoucher get(int id) {
        return claimVoucherDao.select(id);
    }

    public List<ClaimVoucherItem> getItems(int cvId) {
        return claimVoucherItemDao.selectByClaimVoucher(cvId);
    }

    public List<DealRecord> getRecords(int cvId) {
        return dealRecordDao.selectByClaimVoucher(cvId);
    }
}
