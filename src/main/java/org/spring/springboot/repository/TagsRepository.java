package org.spring.springboot.repository;

import org.spring.springboot.domain.Tags;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagsRepository extends ElasticsearchRepository<Tags,Long> {
}
