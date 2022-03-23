package ru.bazhenov.shoplist.service.converters;

import org.springframework.stereotype.Component;
import ru.bazhenov.shoplist.persist.entity.User;
import ru.bazhenov.shoplist.response.UserData;

@Component
public class UserConverter {

    public UserData toResponse(User user) {
        return new UserData(user.getId(), user.getUsername());
    }

}
