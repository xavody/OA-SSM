package me.imtt.oa.biz.impl;

import me.imtt.oa.biz.ClaimVoucherBiz;
import me.imtt.oa.dao.ClaimVoucherDao;
import me.imtt.oa.dao.ClaimVoucherItemDao;
import me.imtt.oa.dao.DealRecordDao;
import me.imtt.oa.dao.EmployeeDao;
import me.imtt.oa.entity.ClaimVoucher;
import me.imtt.oa.entity.ClaimVoucherItem;
import me.imtt.oa.entity.DealRecord;
import me.imtt.oa.entity.Employee;
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

    @Resource
    private EmployeeDao employeeDao;

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

    public List<ClaimVoucher> getSelf(String sn) {
        return claimVoucherDao.selectByCreateSn(sn);
    }

    public List<ClaimVoucher> getDeal(String sn) {
        return claimVoucherDao.selectByNextDealSn(sn);
    }

    public void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> claimVoucherItems) {
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        claimVoucher.setStatus(Constant.CLAIM_VOUCHER_CREATED);
        claimVoucherDao.update(claimVoucher);

        //修改后的报销单明细条目与数据库中的旧条目比较
        List<ClaimVoucherItem> oldClaimVoucherItems = claimVoucherItemDao.selectByClaimVoucher(claimVoucher.getId());
        for (ClaimVoucherItem old : oldClaimVoucherItems) {
            boolean isExist = false;
            for (ClaimVoucherItem item : claimVoucherItems) {
                if (item.getId().equals(old.getId())) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                //修改后的报销单明细条目不包含旧的条目了，删除数据库中旧的条目
                claimVoucherDao.delete(old.getId());
            }
        }

        for (ClaimVoucherItem item : claimVoucherItems) {
            item.setClaimVoucherId(claimVoucher.getId());
            if (item.getId() != null && item.getId() > 0) {
                claimVoucherItemDao.update(item);
            } else {
                claimVoucherItemDao.insert(item);
            }
        }
    }

    public void submit(int id) {
        ClaimVoucher claimVoucher = claimVoucherDao.select(id);
        Employee employee = employeeDao.select(claimVoucher.getCreateSn());

        claimVoucher.setStatus(Constant.CLAIM_VOUCHER_SUBMIT);
        claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(
                employee.getDepartmentSn(), Constant.POST_FM).get(0).getSn());

        //报销单提交
        claimVoucherDao.update(claimVoucher);

        //保存记录
        DealRecord dealRecord = new DealRecord();
        dealRecord.setDealWay(Constant.DEAL_SUBMIT);
        dealRecord.setDealSn(employee.getSn());
        dealRecord.setClaimVoucherId(claimVoucher.getId());
        dealRecord.setDealResult(Constant.CLAIM_VOUCHER_SUBMIT);
        dealRecord.setDealTime(new Date());
        dealRecord.setComment("无");

        dealRecordDao.insert(dealRecord);
    }

    public void deal(DealRecord dealRecord) {
        ClaimVoucher claimVoucher = claimVoucherDao.select(dealRecord.getClaimVoucherId());
        Employee employee = employeeDao.select(dealRecord.getDealSn());

        dealRecord.setDealTime(new Date());
        if (dealRecord.getDealWay().equals(Constant.DEAL_PASS)) {
            //审核通过
            if (claimVoucher.getTotalAmount() <= Constant.LIMIT_CHECK ||
                    employee.getPost().equals(Constant.POST_GM)) {
                claimVoucher.setStatus(Constant.CLAIM_VOUCHER_APPROVED);
                claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(
                        null, Constant.POST_CASHIER).get(0).getSn());

                dealRecord.setDealResult(Constant.CLAIM_VOUCHER_APPROVED);
            } else {
                //需要复审
                claimVoucher.setStatus(Constant.CLAIM_VOUCHER_RECHECK);
                claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(
                        null, Constant.POST_GM).get(0).getSn());

                dealRecord.setDealResult(Constant.CLAIM_VOUCHER_RECHECK);
            }
        } else if (dealRecord.getDealWay().equals(Constant.DEAL_BACK)) {
            //审核打回
            claimVoucher.setStatus(Constant.CLAIM_VOUCHER_BACK);
            claimVoucher.setNextDealSn(claimVoucher.getCreateSn());

            dealRecord.setDealResult(Constant.CLAIM_VOUCHER_BACK);
        } else if (dealRecord.getDealWay().equals(Constant.DEAL_REJECT)) {
            //审核拒绝
            claimVoucher.setStatus(Constant.CLAIM_VOUCHER_TERMINATED);
            claimVoucher.setNextDealSn(null);

            dealRecord.setDealResult(Constant.CLAIM_VOUCHER_TERMINATED);
        } else if (dealRecord.getDealWay().equals(Constant.DEAL_PAID)) {
            //已打款
            claimVoucher.setStatus(Constant.CLAIM_VOUCHER_PAID);
            claimVoucher.setNextDealSn(null);

            dealRecord.setDealResult(Constant.CLAIM_VOUCHER_PAID);
        }

        claimVoucherDao.update(claimVoucher);
        dealRecordDao.insert(dealRecord);
    }
}
