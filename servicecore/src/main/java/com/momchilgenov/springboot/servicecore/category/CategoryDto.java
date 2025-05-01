package com.momchilgenov.springboot.servicecore.category;

public class CategoryDto {
    private Long id;
    private String name;

    public CategoryDto(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
