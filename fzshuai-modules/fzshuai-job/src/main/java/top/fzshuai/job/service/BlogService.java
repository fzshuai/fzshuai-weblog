package top.fzshuai.job.service;

import cn.hutool.core.bean.BeanUtil;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.client.protocol.ScoredEntry;
import org.springframework.stereotype.Service;
import top.fzshuai.blog.domain.Article;
import top.fzshuai.blog.domain.vo.ArticleVo;
import top.fzshuai.blog.mapper.ArticleMapper;
import top.fzshuai.common.utils.redis.RedisUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static top.fzshuai.common.constant.BlogConstant.ARTICLE_VIEWS_COUNT;

/**
 * 博客浏览量
 *
 * @author fzshuai
 * @date 2023/10/12 21:19
 * @since 1.0
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class BlogService {

    private final ArticleMapper baseMapper;

    /**
     * 更新文章浏览量
     */
    @XxlJob("updateBlogViewJobHandler")
    public void updateBlogViewJobHandler() throws Exception {
        log.info("开始将缓存中的文章浏览量数据更新到数据库...");
        Collection<ScoredEntry<Object>> zSetEntryRange = RedisUtils.getZSetEntryRange(ARTICLE_VIEWS_COUNT, 0, -1);
        List<Article> articleList = new ArrayList<>();
        zSetEntryRange.forEach(
            item -> {
                ArticleVo articleVO = baseMapper.selectVoById((Long) item.getValue());
                articleVO.setViewCount(item.getScore().intValue());
                Article article = BeanUtil.toBean(articleVO, Article.class);
                articleList.add(article);
            }
        );
        baseMapper.updateBatchById(articleList, 1000);
        log.info("更新文章浏览量结束");
    }
}
