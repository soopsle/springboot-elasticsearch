package org.spring.springboot.service.impl;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.springboot.domain.Tags;
import org.spring.springboot.repository.TagsRepository;
import org.spring.springboot.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TagsESServiceImpl implements TagsService {
	
	  /* 分页参数 */
    Integer PAGE_SIZE = 12;          // 每页数量
    Integer DEFAULT_PAGE_NUMBER = 0; // 默认当前页码

	private static final Logger LOGGER = LoggerFactory.getLogger(TagsESServiceImpl.class);

	@Autowired
	TagsRepository repository;

	@Override
	public String save(Tags tag) {
		Tags result = repository.save(tag);
		return result.getId();
	}

	@Override
	public List<Tags> search(Integer pageNumber, Integer pageSize, String key) {
	    // 校验分页参数
        if (pageSize == null || pageSize <= 0) {
            pageSize = PAGE_SIZE;
        }
        if (pageNumber == null || pageNumber < DEFAULT_PAGE_NUMBER) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }
		
		// 分页参数
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		//全部
//		QueryBuilder qb = QueryBuilders.matchAllQuery();
		//某个field 值 boolean match
		QueryBuilder qb = QueryBuilders.matchQuery("id", key);
		//模糊匹配
//		QueryBuilder qb = QueryBuilders.matchPhrasePrefixQuery("tag", key);
		//搜索id中或tag中包含有key的文档（必须与key一致,非模糊）   字段类型注意匹配，不然会报错
//		QueryBuilder qb = QueryBuilders.multiMatchQuery(key, "level","tag");
		//通配符?匹配单个字符，*匹配多个字符  中文有毛病
//		QueryBuilder qb = QueryBuilders.wildcardQuery("tag","*"+key);
/*		//复合查询
		QueryBuilder qb1 = QueryBuilders.wildcardQuery("tag","*"+key+"*");
		QueryBuilder qb2 = QueryBuilders.wildcardQuery("level","*"+key+"*");
		BoolQueryBuilder qb = QueryBuilders.boolQuery();  
		//and
		qb.must(qb1);  
		//or 有权重
		qb.should(qb2);  */
		

		// 创建搜索 DSL 查询
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withPageable(pageable).withQuery(qb).build();

		LOGGER.info("\n searchCity(): key [" + key + "] \n DSL  = \n "
				+ searchQuery.getQuery().toString());

		Page<Tags> searchPageResults = repository.search(searchQuery);
		return searchPageResults.getContent();
	}

}
