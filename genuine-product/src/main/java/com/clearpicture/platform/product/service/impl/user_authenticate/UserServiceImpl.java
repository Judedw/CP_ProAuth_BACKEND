package com.clearpicture.platform.product.service.impl.user_authenticate;

import com.clearpicture.platform.exception.ComplexValidationException;
import com.clearpicture.platform.product.entity.criteria.user_authenticate.UserSearchCriteria;
import com.clearpicture.platform.product.entity.user_authenticate.User;
import com.clearpicture.platform.product.repository.user_authenticate.UserRepository;
import com.clearpicture.platform.product.service.user_authenticate.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

/**
 * UserServiceImpl
 * Created by Buddhi on 11/28/18.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }


    @Override
    public Page<User> search(UserSearchCriteria criteria) {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public User retrieve(Long id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if(user != null) {
                return user.get();
            } else {
                throw new ComplexValidationException("user", "userViewRequest.userNotExist");
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public User update(User user, String type) {
        User currentUser = null;
        try {
            currentUser = userRepository.getOne(user.getId());
        } catch (EntityNotFoundException e) {
            throw new ComplexValidationException("user", "userUpdateRequest.userNotExist");
        }

        if(type.equals("AccountSetting")){
            currentUser.setName(user.getName());
            currentUser.setEmail(user.getEmail());
            currentUser.setDescription(user.getDescription());
            currentUser.setAddress_line1(user.getAddress_line1());
            currentUser.setAddress_line2(user.getAddress_line2());
            currentUser.setCity(user.getCity());
            currentUser.setPostal_code(user.getPostal_code());
            currentUser.setState(user.getState());
            currentUser.setCountry(user.getCountry());
        } else if (type.equals("PasswordSetting")) {
            currentUser.setPassword(user.getPassword());
        } else if (type.equals("ImageSetting")) {
//            currentUser.setImageObject(user.getImageObject());
        }

        return userRepository.save(currentUser);
    }

    @Override
    public User delete(Long aLong) {
        return null;
    }

    @Override
    public List<User> retrieveForSuggestions() {
        return null;
    }

    @Override
    public List<User> allUsers() {
        return userRepository.findAll();
    }
}
