package top.fzshuai.weblog.domain.bo;

import top.fzshuai.common.core.validate.AddGroup;
import top.fzshuai.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;

import top.fzshuai.common.core.domain.BaseEntity;

/**
 * 访问量业务对象 blog_unique_view
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UniqueViewBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = {EditGroup.class})
    private Long uniqueViewId;

    /**
     * 访问量
     */
    @NotNull(message = "访问量不能为空", groups = {AddGroup.class, EditGroup.class})
    private Integer viewsCount;

}
