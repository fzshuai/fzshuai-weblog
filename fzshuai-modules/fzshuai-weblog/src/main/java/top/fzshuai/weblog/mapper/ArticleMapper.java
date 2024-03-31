package top.fzshuai.weblog.mapper;

import top.fzshuai.weblog.domain.Article;
import top.fzshuai.weblog.domain.dto.*;
import top.fzshuai.weblog.domain.vo.ArticleVo;
import top.fzshuai.weblog.domain.vo.ConditionVo;
import top.fzshuai.common.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章Mapper接口
 *
 * @author fzshuai
 * @date 2023-05-03
 */
public interface ArticleMapper extends BaseMapperPlus<ArticleMapper, Article, ArticleVo> {

    /**
     * 查询首页文章
     *
     * @param current 页码
     * @param size    大小
     * @return 文章列表
     */
    List<ArticleHomeDto> selectArticleHomeList(@Param("current") Long current, @Param("size") Long size);

    /**
     * 根据id查询文章
     *
     * @param articleId 文章id
     * @return 文章信息
     */
    ArticleDetailDto selectArticleDetailById(@Param("articleId") Long articleId);

    /**
     * 根据条件查询文章
     *
     * @param current   页码
     * @param size      大小
     * @param condition 条件
     * @return 文章列表
     */
    List<ArticlePreviewDto> selectArticlePreviewList(@Param("current") Long current, @Param("size") Long size, @Param("condition") ConditionVo condition);

    /**
     * 查询后台文章
     *
     * @param current   页码
     * @param size      大小
     * @param condition 条件
     * @return 文章列表
     */
    List<ArticleBackDto> selectAdminArticleList(@Param("current") Long current, @Param("size") Long size, @Param("condition") ConditionVo condition);

    /**
     * 查询后台文章总量
     *
     * @param condition 条件
     * @return 文章总量
     */
    Integer selectAdminArticleCount(@Param("condition") ConditionVo condition);

    /**
     * 查看文章的推荐文章
     *
     * @param articleId 文章id
     * @return 文章列表
     */
    List<ArticleRecommendDto> selectRecommendArticleList(@Param("articleId") Long articleId);

    /**
     * 文章统计
     *
     * @return 文章统计结果
     */
    List<ArticleStatisticsDto> selectArticleStatisticsList();

}
