package me.imtt.oa.entity;

/**
 * 账号权限
 */
public class Authority {
    /**
     * 编号
     */
    private Integer id;

    /**
     * 权限等级
     */
    private Integer level;

    /**
     * 描述
     */
    private String comment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
