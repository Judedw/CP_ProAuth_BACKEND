package com.clearpicture.platform.survey.service;

import com.clearpicture.platform.survey.entity.Authenticated;
import org.springframework.stereotype.Service;

/**
 * AuthenticatedService
 * Created by nuwan on 8/7/18.
 */
@Service
public interface AuthenticatedService {
    Authenticated authenticate(String code);
}
