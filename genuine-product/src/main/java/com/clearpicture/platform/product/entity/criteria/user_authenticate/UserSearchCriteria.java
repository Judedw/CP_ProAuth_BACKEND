package com.clearpicture.platform.product.entity.criteria.user_authenticate;

import com.clearpicture.platform.product.entity.user_authenticate.User;
import lombok.Data;

/**
 * UserSearchCriteria
 * Created by Buddhi on 11/28/18.
 */
@Data
public class UserSearchCriteria extends User {

    private String name;

    private Integer pageNumber;

    private Integer pageSize;

    private String sortProperty;

    private String sortDirection;
}
