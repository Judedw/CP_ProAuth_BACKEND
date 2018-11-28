package com.clearpicture.platform.product.service.user_authenticate;

import com.clearpicture.platform.product.entity.user_authenticate.User;
import com.clearpicture.platform.product.entity.criteria.user_authenticate.UserSearchCriteria;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * UserService
 * Created by Buddhi on 11/28/18.
 */
public interface UserService {

    User save(User user);

    Page<User> search(UserSearchCriteria criteria);

    User retrieve(Long id);

//    User update(User user);
    User update(User user, String type);

    User delete(Long aLong);

    List<User> retrieveForSuggestions();

    List<User> allUsers();
}
