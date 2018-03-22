package org.home.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

@Data
@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestResponse {

    private TestStatus result;
    private String message;

    @Builder
    public TestResponse(TestStatus result, String message) {
        this.result = result;
        this.message = message;
    }
}
