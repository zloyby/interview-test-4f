package by.zloy.db.browser.zeaver.controller.request;

import io.swagger.annotations.ApiModelProperty;
import java.util.Optional;
import javax.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class Pagination {

    @Min(value = 0, message = "Can not be negative")
    @ApiModelProperty(value = "Defaults to '0'. Allows to specify a page with elements to get.", example = "0")
    private Integer page;

    @Min(value = 1, message = "Should be positive")
    @ApiModelProperty(value = "Defaults to '50'. Allows to specify how many elements to get per page.", example = "50")
    private Integer pageSize;

    @ApiModelProperty(value = "Property to sort. Default value is id of entity.", example = "name")
    private String sortProperty;

    @ApiModelProperty(value = "Sort direction. Possible values: ASC, DESC", example = "DESC")
    private Sort.Direction sortDirection;

    public PageRequest toPageRequest() {
        String DEFAULT_PROPERTY = "id";
        return PageRequest.of(
                Optional.ofNullable(page).orElse(0),
                Optional.ofNullable(pageSize).orElse(50),
                Optional.ofNullable(sortDirection).orElse(Sort.Direction.DESC),
                Optional.ofNullable(sortProperty).orElse(DEFAULT_PROPERTY));
    }
}
