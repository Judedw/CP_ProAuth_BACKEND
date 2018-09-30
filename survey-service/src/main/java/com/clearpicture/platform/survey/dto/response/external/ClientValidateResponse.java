package com.clearpicture.platform.survey.dto.response.external;

import lombok.Data;

/**
 * ClientValidateResponse
 * Created by nuwan on 9/24/18.
 */
@Data
public class ClientValidateResponse {

    private ClientValidateData content;

    @Data
    public static class ClientValidateData {

        private Boolean isValid;

    }


}
