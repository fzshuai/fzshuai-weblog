package com.fzshuai.blog.mapper;

import com.fzshuai.blog.domain.dto.ArticleSearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * elasticsearch
 *
 * @author fzshuai
 * @date 2024-01-21
 */
@Mapper
public interface ElasticsearchMapper extends ElasticsearchRepository<ArticleSearchDTO, Long> {
}
