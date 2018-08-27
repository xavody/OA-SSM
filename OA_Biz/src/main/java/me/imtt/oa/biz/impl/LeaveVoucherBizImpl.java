package me.imtt.oa.biz.impl;

import me.imtt.oa.biz.LeaveVoucherBiz;
import me.imtt.oa.dao.EmployeeDao;
import me.imtt.oa.dao.LeaveDealRecordDao;
import me.imtt.oa.dao.LeaveVoucherDao;
import me.imtt.oa.entity.*;
import me.imtt.oa.global.Constants.ConstantLeaveVoucher;
import me.imtt.oa.global.Constants.ConstantPosts;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("leaveVoucherBiz")
public class LeaveVoucherBizImpl implements LeaveVoucherBiz {
    @Resource
    private LeaveVoucherDao leaveVoucherDao;

    @Resource
    private LeaveDealRecordDao leaveDealRecordDao;

    @Resource
    private EmployeeDao employeeDao;

    public void save(LeaveVoucher leaveVoucher) {
        leaveVoucher.setCreateTime(new Date());
        leaveVoucher.setNextDealSn(leaveVoucher.getCreateSn());
        leaveVoucher.setStatus(ConstantLeaveVoucher.Leave_VOUCHER_CREATED);

        leaveVoucherDao.insert(leaveVoucher);
    }

    public LeaveVoucher get(int id) {
        return leaveVoucherDao.select(id);
    }

    public List<LeaveDealRecord> getRecords(int lvId) {
        return leaveDealRecordDao.selectByLeaveVoucher(lvId);
    }

    public List<LeaveVoucher> getSelf(String sn) {
        return leaveVoucherDao.selectByCreateSn(sn);
    }

    public List<LeaveVoucher> getDeal(String sn) {
        return leaveVoucherDao.selectByNextDealSn(sn);
    }

    public void update(LeaveVoucher leaveVoucher) {
        leaveVoucher.setNextDealSn(leaveVoucher.getCreateSn());
        leaveVoucher.setStatus(ConstantLeaveVoucher.Leave_VOUCHER_CREATED);
        leaveVoucherDao.update(leaveVoucher);
    }

    public void submit(int id) {
        LeaveVoucher leaveVoucher = leaveVoucherDao.select(id);
        Employee employee = employeeDao.select(leaveVoucher.getCreateSn());

        leaveVoucher.setStatus(ConstantLeaveVoucher.DEAL_SUBMIT);
        leaveVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(
                employee.getDepartmentSn(), ConstantPosts.POST_FM).get(0).getSn());

        //请假单提交
        leaveVoucherDao.update(leaveVoucher);

        //保存记录
        LeaveDealRecord leaveDealRecord = new LeaveDealRecord();
        leaveDealRecord.setDealWay(ConstantLeaveVoucher.DEAL_SUBMIT);
        leaveDealRecord.setDealSn(employee.getSn());
        leaveDealRecord.setLeaveVoucherId(leaveVoucher.getId());
        leaveDealRecord.setDealResult(ConstantLeaveVoucher.Leave_VOUCHER_SUBMIT);
        leaveDealRecord.setDealTime(new Date());
        leaveDealRecord.setComment("无");

        leaveDealRecordDao.insert(leaveDealRecord);
    }

    public void deal(LeaveDealRecord leaveDealRecord) {

    }

}
