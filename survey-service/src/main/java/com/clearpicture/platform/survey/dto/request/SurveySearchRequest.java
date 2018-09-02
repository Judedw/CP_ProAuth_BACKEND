package com.clearpicture.platform.survey.dto.request;

import com.clearpicture.platform.dto.request.BaseSearchRequest;
import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * SurveySearchRequest
 * Created by nuwan on 9/2/18.
 */
@Data
public class SurveySearchRequest extends BaseSearchRequest {

    private String sortProperty = "lastModifiedDate";

    //@NotBlank(message = "surveySearchRequest.search.topic.empty")
    private String topic;

    @Pattern(regexp = "lastModifiedDate|name", flags = Pattern.Flag.CASE_INSENSITIVE, message = "{surveySearchRequest.sortProperty.invalid}")
    @Override
    public String getSortProperty() {
        return sortProperty;
    }
}
