package by.zloy.db.browser.zeaver.controller.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleValueResponse<T> {

    @ApiModelProperty(required = true)
    private T value;
}