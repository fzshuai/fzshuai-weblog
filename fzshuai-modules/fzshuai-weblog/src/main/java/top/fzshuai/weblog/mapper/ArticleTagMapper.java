package top.fzshuai.weblog.mapper;

import top.fzshuai.weblog.domain.ArticleTag;
import top.fzshuai.weblog.domain.vo.ArticleTagVo;
import top.fzshuai.common.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章和文章标签关联Mapper接口
 *
 * @author fzshuai
 * @date 2023-05-03
 */
public interface ArticleTagMapper extends BaseMapperPlus<ArticleTagMapper, ArticleTag, ArticleTagVo> {

    /**
     * 根据文章id获取文章对应的标签名称列表
     *
     * @param articleId 文章id
     * @return 标签列表
     */
    List<String> selectArticleTagNameList(@Param("articleId") Long articleId);

}
