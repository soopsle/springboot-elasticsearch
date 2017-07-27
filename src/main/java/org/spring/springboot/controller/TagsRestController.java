package org.spring.springboot.controller;

import org.spring.springboot.domain.Tags;
import org.spring.springboot.repository.TagsRepository;
import org.spring.springboot.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class TagsRestController {
	

	@Autowired
	TagsRepository repository;

    @Autowired
    private TagsService TagsService;

    @RequestMapping(value = "/api/Tags", method = RequestMethod.POST)
    public String createTags(@RequestBody Tags Tags) {
        return TagsService.save(Tags);
    }

    @RequestMapping(value = "/api/Tags/search", method = RequestMethod.GET)
    public List<Tags> searchTags(@RequestParam(value = "pageNumber",required = false) Integer pageNumber,
                                 @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                 @RequestParam(value = "key") String key) {
        return TagsService.search(pageNumber,pageSize,key);
    }
    
    @RequestMapping(value = "/api/Tags/count", method = RequestMethod.GET)
    public Long count() {
        return repository.count();
    }
    
    @RequestMapping(value = "/api/Tags/deleteAll", method = RequestMethod.GET)
    public void deleteAll() {
        repository.deleteAll();
    }
}
