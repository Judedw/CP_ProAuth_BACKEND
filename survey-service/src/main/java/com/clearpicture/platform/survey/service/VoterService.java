package com.clearpicture.platform.survey.service;

import com.clearpicture.platform.survey.entity.Voter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * VoterService
 * Created by nuwan on 9/6/18.
 */
public interface VoterService {

    List<Voter> uploadFile(MultipartFile file,Long clientId) throws IOException;
}
