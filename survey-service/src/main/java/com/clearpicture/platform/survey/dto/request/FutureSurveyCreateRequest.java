package com.clearpicture.platform.survey.dto.request;


import com.clearpicture.platform.modelmapper.ModelMappingAware;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * created by Raveen -  18/10/24
 * FutureSurveyCreateRequest - Handling every advanced surveys & e votes
 */

@Data
public class FutureSurveyCreateRequest {

    @NotBlank(message = "")
    @Length(max = 1000, message = "")
    private String title;

    @NotBlank(message = "")
    private String clientId;

    @NotBlank(message = "")
    private List<PageData> pages;

    @NotBlank(message = "Future survey content should not be empty !")
    private String jsonContent;

    @Data
    public static class PageData implements ModelMappingAware {

        private String name;
        private List<ElementData> elements;

        @Override
        public Class<?> getDestinationType() {
            return PageData.class;
        }

        @Data
        public static class ElementData implements ModelMappingAware {

            @NotBlank(message = "")
            private String type;

            @NotBlank(message = "")
            private String name;

            @NotBlank(message = "")
            private String qcode;

            private List<Object> choices;

            @Override
            public Class<?> getDestinationType() {
                return ElementData.class;
            }
        }


    }

}
