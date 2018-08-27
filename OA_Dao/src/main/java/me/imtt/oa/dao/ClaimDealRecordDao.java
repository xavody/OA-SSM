package me.imtt.oa.dao;

import me.imtt.oa.entity.ClaimDealRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("claimDealRecordDao")
public interface ClaimDealRecordDao {
    void insert(ClaimDealRecord claimDealRecord);

    /**
     * 某个报销单的处理记录
     */
    List<ClaimDealRecord> selectByClaimVoucher(int cvId);
}
