package top.fzshuai.weblog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import top.fzshuai.weblog.domain.dto.ArticleSearchDto;

/**
 * elasticsearch
 *
 * @author fzshuai
 * @date 2024-01-21
 */
@Mapper
public interface ElasticsearchMapper extends ElasticsearchRepository<ArticleSearchDto, Long> {

}
