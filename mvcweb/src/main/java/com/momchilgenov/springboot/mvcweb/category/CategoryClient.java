package com.momchilgenov.springboot.mvcweb.category;

import com.momchilgenov.springboot.mvcweb.client.EntityClient;
import com.momchilgenov.springboot.mvcweb.dto.EntityWithUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CategoryClient implements EntityClient<Category> {

    private final String URL_OF_FIND_ALL_CATEGORIES;
    private final String URL_OF_CREATE_CATEGORY;
    private final String URL_OF_GET_CATEGORY_BY_ID;
    private final String URL_OF_UPDATE_CATEGORY;
    private final String URL_OF_DELETE_CATEGORY;
    private final String URL_OF_IS_CATEGORY_DELETABLE;
    private final RestTemplate restTemplate;

    @Autowired
    public CategoryClient(RestTemplate restTemplate,
                          @Value("${URL_OF_FIND_ALL_CATEGORIES}") String URL_OF_FIND_ALL_CATEGORIES,
                          @Value("${URL_OF_CREATE_CATEGORY}") String URL_OF_CREATE_CATEGORY,
                          @Value("${URL_OF_GET_CATEGORY_BY_ID}") String URL_OF_GET_CATEGORY_BY_ID,
                          @Value("${URL_OF_UPDATE_CATEGORY}") String URL_OF_UPDATE_CATEGORY,
                          @Value("${URL_OF_DELETE_CATEGORY}") String URL_OF_DELETE_CATEGORY,
                          @Value("${URL_OF_IS_CATEGORY_DELETABLE}") String URL_OF_IS_CATEGORY_DELETABLE) {
        this.URL_OF_FIND_ALL_CATEGORIES = URL_OF_FIND_ALL_CATEGORIES;
        this.URL_OF_CREATE_CATEGORY = URL_OF_CREATE_CATEGORY;
        this.URL_OF_GET_CATEGORY_BY_ID = URL_OF_GET_CATEGORY_BY_ID;
        this.URL_OF_UPDATE_CATEGORY = URL_OF_UPDATE_CATEGORY;
        this.URL_OF_DELETE_CATEGORY = URL_OF_DELETE_CATEGORY;
        this.URL_OF_IS_CATEGORY_DELETABLE = URL_OF_IS_CATEGORY_DELETABLE;
        this.restTemplate = restTemplate;


    }

    @Override
    public List<Category> findAll(String username) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(username, headers);
        ResponseEntity<List<Category>> response = restTemplate.exchange(
                URL_OF_FIND_ALL_CATEGORIES,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<List<Category>>() {
                }
        );

        return response.getBody();
    }

    @Override
    public void create(EntityWithUserDTO<Category> entityDto) {
        restTemplate.postForObject(URL_OF_CREATE_CATEGORY,
                entityDto, Void.class);
    }

    @Override
    public Category getById(String username, Long id) {
        EntityWithUserDTO<Category> entityDto = new EntityWithUserDTO<>();
        entityDto.setUsername(username);
        entityDto.setId(id);
        return restTemplate.postForObject(URL_OF_GET_CATEGORY_BY_ID, entityDto, Category.class);
    }

    @Override
    public void update(EntityWithUserDTO<Category> entityDto) {
        restTemplate.put(URL_OF_UPDATE_CATEGORY, entityDto, Void.class);
    }

    @Override
    public void delete(String username, Long id) {
        EntityWithUserDTO<Category> entityDto = new EntityWithUserDTO<>();
        entityDto.setUsername(username);
        entityDto.setId(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EntityWithUserDTO<Category>> requestEntity = new HttpEntity<>(entityDto, headers);
        ResponseEntity<Void> response = restTemplate.exchange(
                URL_OF_DELETE_CATEGORY,
                HttpMethod.DELETE,
                requestEntity,
                Void.class
        );

    }

    public boolean isDeletable(Long categoryId) {
        String url = URL_OF_IS_CATEGORY_DELETABLE + "/{id}";
        ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class, categoryId);
        return response.getBody();
    }
}
