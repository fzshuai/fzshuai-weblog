package com.fzshuai.blog.mapper;

import com.fzshuai.blog.domain.Tag;
import com.fzshuai.blog.domain.vo.TagVO;
import com.fzshuai.common.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 文章标签Mapper接口
 *
 * @author fzshuai
 * @date 2023-05-03
 */
public interface TagMapper extends BaseMapperPlus<TagMapper, Tag, TagVO> {

    /**
     * 根据文章Id查询标签名称
     *
     * @param articleId 文章Id
     * @return 标签名称
     */
    List<String> selectTagNamesByArticleId(Long articleId);
}
