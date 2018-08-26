package me.imtt.oa.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 请假单
 */
public class LeaveVoucher {
    //编号
    private Integer id;

    //事由
    private String cause;

    //创建人编号
    private String createSn;

    //创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private Date createTime;

    //待处理人编号
    private String nextDealSn;

    //请假天数
    private Integer totalDays;

    //状态
    private String status;

    //创建人
    private Employee creator;

    //处理人
    private Employee dealer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getCreateSn() {
        return createSn;
    }

    public void setCreateSn(String createSn) {
        this.createSn = createSn;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNextDealSn() {
        return nextDealSn;
    }

    public void setNextDealSn(String nextDealSn) {
        this.nextDealSn = nextDealSn;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Employee getCreator() {
        return creator;
    }

    public void setCreator(Employee creator) {
        this.creator = creator;
    }

    public Employee getDealer() {
        return dealer;
    }

    public void setDealer(Employee dealer) {
        this.dealer = dealer;
    }
}