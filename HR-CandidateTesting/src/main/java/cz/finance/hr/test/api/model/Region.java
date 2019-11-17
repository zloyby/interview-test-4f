package cz.finance.hr.test.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import javax.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Region {

    /**
     * Unique region ID (generated).
     */
    @Nonnull
    Long id;

    /**
     * Region name.
     */
    @Nonnull
    String name;

    /**
     * Reference to {@link Country#getId()}.
     */
    @Nonnull
    Long countryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(@Nonnull Long countryId) {
        this.countryId = countryId;
    }

}
