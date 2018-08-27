package me.imtt.oa.biz.impl;

import me.imtt.oa.biz.ClaimVoucherBiz;
import me.imtt.oa.dao.ClaimVoucherDao;
import me.imtt.oa.dao.ClaimVoucherItemDao;
import me.imtt.oa.dao.ClaimDealRecordDao;
import me.imtt.oa.dao.EmployeeDao;
import me.imtt.oa.entity.ClaimVoucher;
import me.imtt.oa.entity.ClaimVoucherItem;
import me.imtt.oa.entity.ClaimDealRecord;
import me.imtt.oa.entity.Employee;
import me.imtt.oa.global.Constants.ConstantClaimVoucher;
import me.imtt.oa.global.Constants.ConstantPosts;
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
    private ClaimDealRecordDao claimDealRecordDao;

    @Resource
    private EmployeeDao employeeDao;

    public void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> claimVoucherItems) {
        claimVoucher.setCreateTime(new Date());
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        claimVoucher.setStatus(ConstantClaimVoucher.CLAIM_VOUCHER_CREATED);

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

    public List<ClaimDealRecord> getRecords(int cvId) {
        return claimDealRecordDao.selectByClaimVoucher(cvId);
    }

    public List<ClaimVoucher> getSelf(String sn) {
        return claimVoucherDao.selectByCreateSn(sn);
    }

    public List<ClaimVoucher> getDeal(String sn) {
        return claimVoucherDao.selectByNextDealSn(sn);
    }

    public void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> claimVoucherItems) {
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        claimVoucher.setStatus(ConstantClaimVoucher.CLAIM_VOUCHER_CREATED);
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

        claimVoucher.setStatus(ConstantClaimVoucher.CLAIM_VOUCHER_SUBMIT);
        claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(
                employee.getDepartmentSn(), ConstantPosts.POST_FM).get(0).getSn());

        //报销单提交
        claimVoucherDao.update(claimVoucher);

        //保存记录
        ClaimDealRecord claimDealRecord = new ClaimDealRecord();
        claimDealRecord.setDealWay(ConstantClaimVoucher.DEAL_SUBMIT);
        claimDealRecord.setDealSn(employee.getSn());
        claimDealRecord.setClaimVoucherId(claimVoucher.getId());
        claimDealRecord.setDealResult(ConstantClaimVoucher.CLAIM_VOUCHER_SUBMIT);
        claimDealRecord.setDealTime(new Date());
        claimDealRecord.setComment("无");

        claimDealRecordDao.insert(claimDealRecord);
    }

    public void deal(ClaimDealRecord claimDealRecord) {
        ClaimVoucher claimVoucher = claimVoucherDao.select(claimDealRecord.getClaimVoucherId());
        Employee employee = employeeDao.select(claimDealRecord.getDealSn());

        claimDealRecord.setDealTime(new Date());
        if (claimDealRecord.getDealWay().equals(ConstantClaimVoucher.DEAL_PASS)) {
            //审核通过
            if (claimVoucher.getTotalAmount() <= ConstantClaimVoucher.LIMIT_CHECK ||
                    employee.getPost().equals(ConstantPosts.POST_GM)) {
                claimVoucher.setStatus(ConstantClaimVoucher.CLAIM_VOUCHER_APPROVED);
                claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(
                        null, ConstantPosts.POST_CASHIER).get(0).getSn());

                claimDealRecord.setDealResult(ConstantClaimVoucher.CLAIM_VOUCHER_APPROVED);
            } else {
                //需要复审
                claimVoucher.setStatus(ConstantClaimVoucher.CLAIM_VOUCHER_RECHECK);
                claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(
                        null, ConstantPosts.POST_GM).get(0).getSn());

                claimDealRecord.setDealResult(ConstantClaimVoucher.CLAIM_VOUCHER_RECHECK);
            }
        } else if (claimDealRecord.getDealWay().equals(ConstantClaimVoucher.DEAL_BACK)) {
            //审核打回
            claimVoucher.setStatus(ConstantClaimVoucher.CLAIM_VOUCHER_BACK);
            claimVoucher.setNextDealSn(claimVoucher.getCreateSn());

            claimDealRecord.setDealResult(ConstantClaimVoucher.CLAIM_VOUCHER_BACK);
        } else if (claimDealRecord.getDealWay().equals(ConstantClaimVoucher.DEAL_REJECT)) {
            //审核拒绝
            claimVoucher.setStatus(ConstantClaimVoucher.CLAIM_VOUCHER_TERMINATED);
            claimVoucher.setNextDealSn(null);

            claimDealRecord.setDealResult(ConstantClaimVoucher.CLAIM_VOUCHER_TERMINATED);
        } else if (claimDealRecord.getDealWay().equals(ConstantClaimVoucher.DEAL_PAID)) {
            //已打款
            claimVoucher.setStatus(ConstantClaimVoucher.CLAIM_VOUCHER_PAID);
            claimVoucher.setNextDealSn(null);

            claimDealRecord.setDealResult(ConstantClaimVoucher.CLAIM_VOUCHER_PAID);
        }

        claimVoucherDao.update(claimVoucher);
        claimDealRecordDao.insert(claimDealRecord);
    }
}
