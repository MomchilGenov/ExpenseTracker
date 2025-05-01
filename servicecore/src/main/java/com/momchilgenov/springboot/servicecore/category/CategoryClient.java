package com.momchilgenov.springboot.servicecore.category;

import com.momchilgenov.springboot.servicecore.client.EntityClient;
import com.momchilgenov.springboot.servicecore.dto.EntityWithUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CategoryClient implements EntityClient<CategoryDto> {

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
    public List<CategoryDto> findAll(String username) {
        List<CategoryDto> categories = restTemplate.postForObject(URL_OF_FIND_ALL_CATEGORIES,
                username, List.class);

        return categories;
    }

    @Override
    public void create(EntityWithUserDTO<CategoryDto> entityDto) {
        restTemplate.postForObject(URL_OF_CREATE_CATEGORY,
                entityDto, Void.class);
    }

    @Override
    public CategoryDto getById(String username, Long id) {
        EntityWithUserDTO<CategoryDto> entityDto = new EntityWithUserDTO<>();
        entityDto.setUsername(username);
        entityDto.setId(id);
        return restTemplate.postForObject(URL_OF_GET_CATEGORY_BY_ID, entityDto, CategoryDto.class);
    }

    @Override
    public void update(EntityWithUserDTO<CategoryDto> entityDto) {
        restTemplate.put(URL_OF_UPDATE_CATEGORY, entityDto, Void.class);
    }

    @Override
    public void delete(String username, Long id) {
        EntityWithUserDTO<CategoryDto> entityDto = new EntityWithUserDTO<>();
        entityDto.setUsername(username);
        entityDto.setId(id);
        restTemplate.delete(URL_OF_DELETE_CATEGORY, entityDto, Void.class);
    }

}
