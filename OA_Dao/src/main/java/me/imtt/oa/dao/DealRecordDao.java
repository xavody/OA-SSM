package me.imtt.oa.dao;

import me.imtt.oa.entity.DealRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("dealRecordDao")
public interface DealRecordDao {
    void insert(DealRecord dealRecord);

    /**
     * 某个报销单的处理记录
     */
    List<DealRecord> selectByClaimVoucher(String cvId);
}
