package com.clearpicture.platform.survey.controller;

import com.clearpicture.platform.modelmapper.ModelMapper;
import com.clearpicture.platform.response.wrapper.ListResponseWrapper;
import com.clearpicture.platform.service.CryptoService;
import com.clearpicture.platform.survey.dto.response.VoterCreateResponse;
import com.clearpicture.platform.survey.entity.Voter;
import com.clearpicture.platform.survey.service.VoterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

/**
 * VoterController
 * Created by nuwan on 9/6/18.
 */
@RestController
public class VoterController {

    @Autowired
    private VoterService voterService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CryptoService cryptoService;

    @PostMapping(value = "${app.endpoint.votersCreate}")
    public ResponseEntity<ListResponseWrapper<VoterCreateResponse>> uploadFile(
            @RequestParam(value = "file",required = false)MultipartFile file,@RequestParam(value = "client",required = false) String client) throws IOException, ServletException {

        List<Voter> voters = voterService.uploadFile(file,cryptoService.decryptEntityId(client));

        List<VoterCreateResponse> answerTemplatesSuggestions = modelMapper.map(voters, VoterCreateResponse.class);

        return new ResponseEntity<ListResponseWrapper<VoterCreateResponse>>(
                new ListResponseWrapper<VoterCreateResponse>(answerTemplatesSuggestions), HttpStatus.OK);


    }
}
