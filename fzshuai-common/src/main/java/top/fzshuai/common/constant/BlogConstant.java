package top.fzshuai.common.constant;


/**
 * 公共常量
 *
 * @author fzshuai
 * @date 2023-05-03
 */
public class BlogConstant {

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

}
