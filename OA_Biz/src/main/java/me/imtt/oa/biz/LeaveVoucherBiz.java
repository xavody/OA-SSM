package me.imtt.oa.biz;

import me.imtt.oa.entity.LeaveDealRecord;
import me.imtt.oa.entity.LeaveVoucher;

import java.util.List;

public interface LeaveVoucherBiz {
    /**
     * 保存请假单
     */
    void save(LeaveVoucher leaveVoucher);

    /**
     * 获取请假单
     */
    LeaveVoucher get(int id);

    /**
     * 获取请假单审核记录
     */
    List<LeaveDealRecord> getRecords(int lvId);

    /**
     * 获取个人请假单
     */
    List<LeaveVoucher> getSelf(String sn);

    /**
     * 获取未处理请假单
     */
    List<LeaveVoucher> getDeal(String sn);

    /**
     * 修改请假单
     */
    void update(LeaveVoucher leaveVoucher);

    /**
     * 提交请假单
     */
    void submit(int id);

    /**
     * 对请假单进一步处理
     */
    void deal(LeaveDealRecord leaveDealRecord);
}
