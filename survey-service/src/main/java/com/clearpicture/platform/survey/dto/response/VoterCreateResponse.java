package com.clearpicture.platform.survey.dto.response;

import lombok.Data;

/**
 * VoterCreateResponse
 * Created by nuwan on 9/6/18.
 */
@Data
public class VoterCreateResponse {

    private String id;

    private String name;

    private String email;

    private String identityNumber;

    private String batchNumber;

}
