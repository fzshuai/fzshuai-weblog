package top.fzshuai.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 查询条件
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
public class ConditionVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 页码
     */
    private Integer current;

    /**
     * 条数
     */
    private Integer size;

    /**
     * 搜索内容
     */
    private String keywords;

    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 标签id
     */
    private Long tagId;

    /**
     * 相册id
     */
    private Long albumId;

    /**
     * 登录类型
     */
    private Integer loginType;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 是否审核
     */
    private Integer isReview;

}
