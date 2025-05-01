package com.momchilgenov.springboot.mvcweb.category;

import com.momchilgenov.springboot.mvcweb.client.EntityClient;
import com.momchilgenov.springboot.mvcweb.dto.EntityWithUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class CategoryClient implements EntityClient<Category> {

    private final String URL_OF_FIND_ALL_CATEGORIES;
    private final String URL_OF_CREATE_CATEGORY;
    private final String URL_OF_GET_CATEGORY_BY_ID;
    private final String URL_OF_UPDATE_CATEGORY;
    private final String URL_OF_DELETE_CATEGORY;
    private final RestTemplate restTemplate;

    @Autowired
    public CategoryClient(RestTemplate restTemplate,
                          @Value("URL_OF_FIND_ALL_CATEGORIES") String URL_OF_FIND_ALL_CATEGORIES,
                          @Value("URL_OF_CREATE_CATEGORY") String URL_OF_CREATE_CATEGORY,
                          @Value("URL_OF_GET_CATEGORY_BY_ID") String URL_OF_GET_CATEGORY_BY_ID,
                          @Value("URL_OF_UPDATE_CATEGORY") String URL_OF_UPDATE_CATEGORY,
                          @Value("URL_OF_DELETE_CATEGORY") String URL_OF_DELETE_CATEGORY) {
        this.URL_OF_FIND_ALL_CATEGORIES = URL_OF_FIND_ALL_CATEGORIES;
        this.URL_OF_CREATE_CATEGORY = URL_OF_CREATE_CATEGORY;
        this.URL_OF_GET_CATEGORY_BY_ID = URL_OF_GET_CATEGORY_BY_ID;
        this.URL_OF_UPDATE_CATEGORY = URL_OF_UPDATE_CATEGORY;
        this.URL_OF_DELETE_CATEGORY = URL_OF_DELETE_CATEGORY;
        this.restTemplate = restTemplate;


    }

    @Override
    public List<Category> findAll(String username) {
        List<Category> categories = restTemplate.postForObject(URL_OF_FIND_ALL_CATEGORIES,
                username, List.class);

        return categories;
    }

    @Override
    public void create(EntityWithUserDTO<Category> entityDto) {

    }

    @Override
    public Category getById(String username, Long id) {
        return null;
    }

    @Override
    public void update(EntityWithUserDTO<Category> entityDto) {

    }

    @Override
    public void delete(String username, Long id) {

    }
}
