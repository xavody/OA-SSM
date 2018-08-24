package me.imtt.oa.dao;

import me.imtt.oa.entity.ClaimVoucher;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("claimVoucherDao")
public interface ClaimVoucherDao {
    void insert(ClaimVoucher claimVoucher);

    void update(ClaimVoucher claimVoucher);

    void delete(String id);

    ClaimVoucher select(int id);

    /**
     * 创建者的所有报销单
     */
    List<ClaimVoucher> selectByCreateSn(String csn);

    /**
     * 某个人能够处理的所有报销单
     */
    List<ClaimVoucher> selectByNextDealSn(String ndsn);
}
