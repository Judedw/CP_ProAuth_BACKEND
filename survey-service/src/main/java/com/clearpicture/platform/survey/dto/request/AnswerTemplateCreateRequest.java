package com.clearpicture.platform.survey.dto.request;

import com.clearpicture.platform.modelmapper.ModelMappingAware;
import com.clearpicture.platform.survey.entity.Answer;
import com.clearpicture.platform.survey.enums.AnswerTemplateType;
import com.clearpicture.platform.survey.validation.annotation.ValidAnswerTemplateType;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * AnswerTemplateCreateRequest
 * Created by nuwan on 8/18/18.
 */
@Data
public class AnswerTemplateCreateRequest {

    @NotBlank(message = "answerTemplateCreateRequest.name.empty")
    @Length(max = 100, message = "answerTemplateCreateRequest.name.lengthExceeds")
    private String name;

    @ValidAnswerTemplateType(value = { AnswerTemplateType.FREE_TEXT, AnswerTemplateType.MULTIPLE_OPTIONS,
            AnswerTemplateType.SINGLE_ANSWER_OPTION }, ignoreOnNull = true, message = "answerTemplateCreateRequest.answerTemplateType.invalid")
    private String answerTemplateType;

    @NotEmpty(message = "answerTemplateCreateRequest.answers.empty")
    private @Valid List<Answers> answers;

    private Boolean reverseDisplay;

    @Data
    public static class Answers implements ModelMappingAware {

        @Length(max = 100, message = "answerTemplateCreateRequest.lable.lengthExceeds")
        private String lable;

        @Min(value = 0, message = "{answerTemplatesCreateRequest.answer.minValue.invalid}")
        @Max(value = 100, message = "{answerTemplatesCreateRequest.answer.maxValue.invalid}")
        private BigDecimal value;

        //To get the weight of the number(ex: good=5,best=10,better=15)
        @NotNull
        private Integer optionNumber;

        @Override
        public Class<?> getDestinationType() {
            return Answer.class;
        }
    }
}
