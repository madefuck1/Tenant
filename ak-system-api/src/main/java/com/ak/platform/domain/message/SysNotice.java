package com.ak.platform.domain.message;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.ak.common.core.domain.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 通知公告表 sys_notice
 *
 * @author Vean
 */
@Getter
@Setter
public class SysNotice extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 公告ID
     */
    private Long noticeId;

    /**
     * 公告标题
     */
    @NotBlank(message = "公告标题不能为空")
    @Size(min = 0, max = 50, message = "公告标题不能超过50个字符")
    private String noticeTitle;

    /**
     * 公告类型（1通知 2公告）
     */
    private String noticeType;

    /**
     * 公告内容
     */
    private String noticeContent;

    /**
     * 所属租户
     */
    @NotBlank(message = "所属租户不能为空")
    private String tenantCode;

    /**
     * 公告状态（0正常 1关闭）
     */
    private String status;

}
