package com.clearpicture.platform.survey.dto.request;

import com.clearpicture.platform.modelmapper.ModelMappingAware;
import com.clearpicture.platform.survey.entity.AnswerTemplate;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * QuestionCreateRequest
 * Created by nuwan on 8/17/18.
 */
@Data
public class QuestionCreateRequest {

    @NotBlank(message = "questionCreateRequest.question.empty")
    @Length(max = 1000, message = "questionCreateRequest.question.lengthExceeds")
    private String name;

    @Valid
    @NotNull(message = "questionCreateRequest.answerTemplate.empty")
    private AnswerTemplateData answerTemplate;

    @Data
    public static class AnswerTemplateData implements ModelMappingAware {

        @NotBlank(message = "questionCreateRequest.answerTemplate.id.empty")
        private String id;

        private String name;

        @Override
        public Class<?> getDestinationType() {
            return AnswerTemplate.class;
        }
    }

}
