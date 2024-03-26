package top.fzshuai.blog.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import top.fzshuai.common.annotation.Translation;
import top.fzshuai.common.constant.TransConstant;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 网站配置视图对象 blog_website_config
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
public class WebsiteConfigVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long websiteConfigId;

    /**
     * 配置信息
     */
    @ExcelProperty(value = "配置信息")
    private String config;

    /**
     * 网站头像
     */
    private String websiteAvatar;

    /**
     * 网站头像Url
     */
    @Translation(type = TransConstant.OSS_ID_TO_URL, mapper = "websiteAvatar")
    private String websiteAvatarUrl;

    /**
     * 网站名称
     */
    private String websiteName;

    /**
     * 网站作者
     */
    private String websiteAuthor;

    /**
     * 网站介绍
     */
    private String websiteIntro;

    /**
     * 网站公告
     */
    private String websiteNotice;

    /**
     * 网站创建时间
     */
    private String websiteCreateTime;

    /**
     * 网站备案号
     */
    private String websiteRecordNo;

    /**
     * 社交登录列表
     */
    private List<String> socialLoginList;

    /**
     * 社交url列表
     */
    private List<String> socialUrlList;

    /**
     * qq
     */
    private String qq;

    /**
     * github
     */
    private String github;

    /**
     * gitee
     */
    private String gitee;

    /**
     * 游客头像
     */
    private String touristAvatar;

    /**
     * 游客头像Url
     */
    @Translation(type = TransConstant.OSS_ID_TO_URL, mapper = "touristAvatar")
    private String touristAvatarUrl;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户头像Url
     */
    @Translation(type = TransConstant.OSS_ID_TO_URL, mapper = "userAvatar")
    private String userAvatarUrl;

    /**
     * 是否评论审核
     */
    private Integer isCommentReview;

    /**
     * 是否留言审核
     */
    private Integer isMessageReview;

    /**
     * 是否邮箱通知
     */
    private Integer isEmailNotice;

    /**
     * 是否打赏
     */
    private Integer isReward;

    /**
     * 微信二维码
     */
    private String weiXinQRCode;

    /**
     * 微信二维码Url
     */
    @Translation(type = TransConstant.OSS_ID_TO_URL, mapper = "weiXinQRCode")
    private String weiXinQRCodeUrl;

    /**
     * 支付宝二维码
     */
    private String alipayQRCode;

    /**
     * 支付宝二维码Url
     */
    @Translation(type = TransConstant.OSS_ID_TO_URL, mapper = "alipayQRCode")
    private String alipayQRCodeUrl;

    /**
     * 是否开启聊天室
     */
    private Integer isChatRoom;

    /**
     * websocket地址
     */
    private String websocketUrl;

    /**
     * 是否开启音乐
     */
    private Integer isMusicPlayer;

}
