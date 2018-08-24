package me.imtt.oa.dao;

import me.imtt.oa.entity.ClaimVoucherItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("claimVoucherItemDao")
public interface ClaimVoucherItemDao {
    void insert(ClaimVoucherItem claimVoucherItem);

    void update(ClaimVoucherItem claimVoucherItem);

    void delete(String id);

    /**
     * 某个报销单的信息
     */
    List<ClaimVoucherItem> selectByClaimVoucher(int cvId);
}
