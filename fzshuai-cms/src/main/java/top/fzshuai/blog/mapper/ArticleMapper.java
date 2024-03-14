package top.fzshuai.blog.mapper;

import top.fzshuai.blog.domain.Article;
import top.fzshuai.blog.domain.dto.*;
import top.fzshuai.blog.domain.vo.ArticleVO;
import top.fzshuai.blog.domain.vo.ConditionVO;
import top.fzshuai.common.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章Mapper接口
 *
 * @author fzshuai
 * @date 2023-05-03
 */
public interface ArticleMapper extends BaseMapperPlus<ArticleMapper, Article, ArticleVO> {

    /**
     * 查询首页文章
     *
     * @param current 页码
     * @param size    大小
     * @return 文章列表
     */
    List<ArticleHomeDTO> selectArticleHomeList(@Param("current") Long current, @Param("size") Long size);

    /**
     * 根据id查询文章
     *
     * @param articleId 文章id
     * @return 文章信息
     */
    ArticleDetailDTO selectArticleDetailById(@Param("articleId") Long articleId);

    /**
     * 根据条件查询文章
     *
     * @param current   页码
     * @param size      大小
     * @param condition 条件
     * @return 文章列表
     */
    List<ArticlePreviewDTO> selectArticlePreviewList(@Param("current") Long current, @Param("size") Long size, @Param("condition") ConditionVO condition);

    /**
     * 查询后台文章
     *
     * @param current   页码
     * @param size      大小
     * @param condition 条件
     * @return 文章列表
     */
    List<ArticleBackDTO> selectAdminArticleList(@Param("current") Long current, @Param("size") Long size, @Param("condition") ConditionVO condition);

    /**
     * 查询后台文章总量
     *
     * @param condition 条件
     * @return 文章总量
     */
    Integer selectAdminArticleCount(@Param("condition") ConditionVO condition);

    /**
     * 查看文章的推荐文章
     *
     * @param articleId 文章id
     * @return 文章列表
     */
    List<ArticleRecommendDTO> selectRecommendArticleList(@Param("articleId") Long articleId);

    /**
     * 文章统计
     *
     * @return 文章统计结果
     */
    List<ArticleStatisticsDTO> selectArticleStatisticsList();
}
