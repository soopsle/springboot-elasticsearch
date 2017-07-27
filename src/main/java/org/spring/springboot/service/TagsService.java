
package org.spring.springboot.service;

import org.spring.springboot.domain.Tags;

import java.util.List;

public interface TagsService {

    String save(Tags tag);

    List<Tags> search(Integer pageNumber, Integer pageSize, String searchContent);
}