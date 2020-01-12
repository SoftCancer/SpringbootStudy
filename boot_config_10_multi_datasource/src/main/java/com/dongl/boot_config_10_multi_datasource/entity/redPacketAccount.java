package com.dongl.boot_config_10_multi_datasource.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "red_packet_account")
public class redPacketAccount implements Serializable {
    /**
     * 唯一 id
     */
    @Id
    private String id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 账户余额
     */
    @Column(name = "balance_amount")
    private Long balanceAmount;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    /**
     * 获取唯一 id
     *
     * @return id - 唯一 id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置唯一 id
     *
     * @param id 唯一 id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取账户余额
     *
     * @return balance_amount - 账户余额
     */
    public Long getBalanceAmount() {
        return balanceAmount;
    }

    /**
     * 设置账户余额
     *
     * @param balanceAmount 账户余额
     */
    public void setBalanceAmount(Long balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取修改时间
     *
     * @return update_time - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}