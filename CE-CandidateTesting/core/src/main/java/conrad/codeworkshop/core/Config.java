package conrad.codeworkshop.core;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;

public final class Config extends Configuration {

  @NotNull
  private boolean shouldDoSearch;

  @NotNull
  private String elasticsearchHost;

  @NotNull
  private Integer elasticsearchPort;

  @JsonProperty("shouldDoSearch")
  public boolean isShouldDoSearch() {
    return shouldDoSearch;
  }

  @JsonProperty("elasticsearchHost")
  public String getElasticsearchHost() {
    return elasticsearchHost;
  }

  @JsonProperty("elasticsearchPort")
  public Integer getElasticsearchPort() {
    return elasticsearchPort;
  }
}
