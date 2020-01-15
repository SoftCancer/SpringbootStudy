package com.dongl.boot_config_13_redis.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "user")
public class User implements Serializable {
    @Id
    private String id;

    private String username;

    private String password;

    /**
     * 性别：女：0，男：1
     */
    private Byte sex;

    /**
     * 删除标识：0：不删除，1：删除
     */
    private Byte deleted;

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
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取性别：女：0，男：1
     *
     * @return sex - 性别：女：0，男：1
     */
    public Byte getSex() {
        return sex;
    }

    /**
     * 设置性别：女：0，男：1
     *
     * @param sex 性别：女：0，男：1
     */
    public void setSex(Byte sex) {
        this.sex = sex;
    }

    /**
     * 获取删除标识：0：不删除，1：删除
     *
     * @return deleted - 删除标识：0：不删除，1：删除
     */
    public Byte getDeleted() {
        return deleted;
    }

    /**
     * 设置删除标识：0：不删除，1：删除
     *
     * @param deleted 删除标识：0：不删除，1：删除
     */
    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
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