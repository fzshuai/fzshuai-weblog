package com.fzshuai.system.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 用户信息vo
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVO {

    /**
     * 用户昵称
     */
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    /**
     * 用户简介
     */
    private String intro;

    /**
     * 个人网站
     */
    private String webSite;
}
