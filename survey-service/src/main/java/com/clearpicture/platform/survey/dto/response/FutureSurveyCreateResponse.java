package com.clearpicture.platform.survey.dto.response;


import com.clearpicture.platform.modelmapper.ModelMappingAware;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * created by Raveen -  18/10/24
 * FutureSurveyCreateRequest - Handling every advanced surveys & e votes
 */

@Data
public class FutureSurveyCreateResponse {

    private String id;
    private String title;
    private String clientId;
    private String jsonContent;
    private List<PageData> pages;

    @Data
    public static class PageData {

        private String id;
        private String name;
        private List<ElementData> elements;

        @Data
        public static class ElementData {

            private String qcode;
            private String type;
            private String name;
            private List<Choice> choices;


            @Data
            public static class Choice {

                private String value;
                private String text;
                private byte[] imageLink;


            }

        }

    }
}
