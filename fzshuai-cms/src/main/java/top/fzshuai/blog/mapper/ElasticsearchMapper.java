package top.fzshuai.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import top.fzshuai.blog.domain.dto.ArticleSearchDTO;

/**
 * elasticsearch
 *
 * @author fzshuai
 * @date 2024-01-21
 */
@Mapper
public interface ElasticsearchMapper extends ElasticsearchRepository<ArticleSearchDTO, Long> {
}
