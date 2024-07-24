package top.fzshuai.blog.mapper;

import top.fzshuai.blog.domain.Tag;
import top.fzshuai.blog.domain.vo.TagVo;
import top.fzshuai.common.core.mapper.BaseMapperPlus;

/**
 * 文章标签Mapper接口
 *
 * @author fzshuai
 * @date 2023-05-03
 */
public interface TagMapper extends BaseMapperPlus<TagMapper, Tag, TagVo> {

    /**
     * 根据id查询标签名称
     *
     * @param tagId 标签id
     * @return 标签名称
     */
    String selectTagNameById(Long tagId);

}
