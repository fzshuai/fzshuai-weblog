package top.fzshuai.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.fzshuai.blog.domain.Tag;
import top.fzshuai.blog.domain.dto.TagDto;
import top.fzshuai.blog.domain.vo.PageResultVo;
import top.fzshuai.blog.domain.vo.TagVo;
import top.fzshuai.blog.domain.bo.TagBo;
import top.fzshuai.common.core.page.TableDataInfo;
import top.fzshuai.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 文章标签Service接口
 *
 * @author fzshuai
 * @date 2023-05-03
 */
public interface ITagService extends IService<Tag> {

    /**
     * 查询前台标签列表
     *
     * @return 标签列表
     */
    PageResultVo<TagDto> selectTagList();

    /**
     * 查询文章标签
     */
    TagVo selectTagById(Long tagId);

    /**
     * 查询文章标签列表
     */
    TableDataInfo<TagVo> selectTagPageList(TagBo bo, PageQuery pageQuery);

    /**
     * 查询文章标签列表
     */
    List<TagVo> selectTagList(TagBo bo);

    /**
     * 新增文章标签
     */
    Boolean insertByBo(TagBo bo);

    /**
     * 修改文章标签
     */
    Boolean updateByBo(TagBo bo);

    /**
     * 校验并批量删除文章标签信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

}
