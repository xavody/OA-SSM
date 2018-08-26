package me.imtt.oa.dao;

import me.imtt.oa.entity.LeaveVoucher;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("LeaveVoucherDao")
public interface LeaveVoucherDao {
    void insert(LeaveVoucher leaveVoucher);

    void update(LeaveVoucher leaveVoucher);

    void delete(Integer id);

    LeaveVoucher select(int id);

    /**
     * 创建者的所有请假单
     */
    List<LeaveVoucher> selectByCreateSn(String csn);

    /**
     * 某个人能够处理的所有请假单
     */
    List<LeaveVoucher> selectByNextDealSn(String ndsn);
}
