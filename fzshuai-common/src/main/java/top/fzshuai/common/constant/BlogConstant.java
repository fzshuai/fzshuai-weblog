package top.fzshuai.common.constant;


/**
 * 博客常量
 *
 * @author fzshuai
 * @date 2023-05-03
 */
public interface BlogConstant {

    /**
     * 文章对应的type
     */
    public static final String ARTICLE_TYPE = "articleCommentStrategy";

    public static final Integer ARTICLE_TYPE_VALUE = 1;

    /**
     * 友链对应的type
     */
    public static final String FRIENDLINK_TYPE = "friendLinkCommentStrategy";

    public static final Integer FRIENDLINK_TYPE_VALUE = 2;

    /**
     * 说说对应的type
     */
    public static final String TALK_TYPE = "talkCommentStrategy";

    public static final Integer TALK_TYPE_VALUE = 3;

    /**
     * 默认文章标签/分类
     */
    public static final String DEFAULTCATORTAG = "默认";

    /**
     * 默认文章标签/分类id
     */
    public static final Long DEFAULTCATORTAGID = Long.valueOf(1);

    /**
     * 已审核
     */
    public static final int STATE = 1;

    /**
     * 系统是
     */
    public static final String YES = "Y";

    /**
     * 系统否
     */
    public static final String NO = "N";

    /**
     * 对
     */
    public static final String ISTRUE = "true";

    /**
     * 否
     */
    public static final int FALSE = 0;

    /**
     * 是
     */
    public static final int TRUE = 1;

    /**
     * 高亮标签
     */
    public static final String PRE_TAG = "<span style='color:#f47466'>";

    /**
     * 高亮标签
     */
    public static final String POST_TAG = "</span>";

    /**
     * 当前页码
     */
    public static final String CURRENT = "current";

    /**
     * 页码条数
     */
    public static final String SIZE = "size";

    /**
     * 博主id
     */
    public static final Integer BLOGGER_ID = 1;

    /**
     * 默认条数
     */
    public static final String DEFAULT_SIZE = "10";

    /**
     * 默认用户昵称
     */
    public static final String DEFAULT_NICKNAME = "用户";

    /**
     * 浏览文章集合
     */
    public static String ARTICLE_SET = "articleSet";

    /**
     * 前端组件名
     */
    public static String COMPONENT = "Layout";

    /**
     * 省
     */
    public static final String PROVINCE = "省";

    /**
     * 市
     */
    public static final String CITY = "市";

    /**
     * 未知的
     */
    public static final String UNKNOWN = "未知";

    /**
     * JSON 格式
     */
    public static final String APPLICATION_JSON = "application/json;charset=utf-8";

    /**
     * 默认的配置id
     */
    public static final Integer DEFAULT_CONFIG_ID = 1;

    /**
     * 验证码过期时间
     */
    public static final long CODE_EXPIRE_TIME = 15 * 60;

    /**
     * 验证码
     */
    public static final String USER_CODE_KEY = "code:";

    /**
     * 博客浏览量
     */
    public static final String BLOG_VIEWS_COUNT = "blog_views_count";

    /**
     * 文章浏览量
     */
    public static final String ARTICLE_VIEWS_COUNT = "article_views_count";

    /**
     * 文章点赞量
     */
    public static final String ARTICLE_LIKE_COUNT = "article_like_count";

    /**
     * 用户点赞文章
     */
    public static final String ARTICLE_USER_LIKE = "article_user_like:";

    /**
     * 说说点赞量
     */
    public static final String TALK_LIKE_COUNT = "talk_like_count";

    /**
     * 用户点赞说说
     */
    public static final String TALK_USER_LIKE = "talk_user_like:";

    /**
     * 评论点赞量
     */
    public static final String COMMENT_LIKE_COUNT = "comment_like_count";

    /**
     * 用户点赞评论
     */
    public static final String COMMENT_USER_LIKE = "comment_user_like:";

    /**
     * 网站配置
     */
    public static final String WEBSITE_CONFIG = "website_config";

    /**
     * 用户地区
     */
    public static final String USER_AREA = "user_area";

    /**
     * 访客地区
     */
    public static final String VISITOR_AREA = "visitor_area";

    /**
     * 页面封面
     */
    public static final String PAGE_COVER = "page_cover";

    /**
     * 关于我信息
     */
    public static final String ABOUT = "about";

    /**
     * 访客
     */
    public static final String UNIQUE_VISITOR = "unique_visitor";

}
