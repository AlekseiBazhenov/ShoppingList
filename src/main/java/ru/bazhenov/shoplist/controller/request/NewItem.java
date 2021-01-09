package ru.bazhenov.shoplist.controller.request;

import javax.validation.constraints.NotBlank;

public class NewItem {

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
