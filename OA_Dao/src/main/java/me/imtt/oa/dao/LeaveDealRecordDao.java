package me.imtt.oa.dao;

import me.imtt.oa.entity.LeaveDealRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("leaveDealRecordDao")
public interface LeaveDealRecordDao {
    void insert(LeaveDealRecord leaveDealRecord);

    /**
     * 某个请假单的处理记录
     */
    List<LeaveDealRecord> selectByLeaveVoucher(int lvId);
}
