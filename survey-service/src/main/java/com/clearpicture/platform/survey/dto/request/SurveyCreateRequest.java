package com.clearpicture.platform.survey.dto.request;


import com.clearpicture.platform.enums.DateTimePattern;
import com.clearpicture.platform.survey.enums.SurveyType;
import com.clearpicture.platform.survey.validation.annotation.DateFormat;
import com.clearpicture.platform.survey.validation.annotation.ValidSurveyType;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * SurveyCreateRequest
 * Created by nuwan on 8/17/18.
 */
@Data
public class SurveyCreateRequest {

    @NotBlank(message = "surveyCreateRequest.topic.empty")
    @Length(max = 100, message = "surveyCreateRequest.topic.lengthExceeds")
    private String topic;

    @ValidSurveyType(value = {SurveyType.PRODUCT, SurveyType.EVOTE}, ignoreOnNull = true, message = "SurveyCreateRequest.surveyType.invalid")
    private String type;

    @DateFormat(pattern = DateTimePattern.STRICT_DATE, message = "{surveyCreateRequest.startDate.invalid}")
    @NotBlank(message = "surveyCreateRequest.startDate.empty")
    private String startDate;

    @DateFormat(pattern = DateTimePattern.STRICT_DATE, message = "{surveyCreateRequest.endDate.invalid}")
    @NotBlank(message = "surveyCreateRequest.endDate.empty")
    private String endDate;


    private String eVoteId;

    private ProductData product;

    private List<QuestionData> questions;

    @Data
    public static class QuestionData {

        private String id;

    }

    @Data
    public static class EVoteData {

        private String id;

    }

    @Data
    public static class ProductData {

        private String id;

    }
}
