package conrad.codeworkshop.core;

import conrad.codeworkshop.core.resources.SearchResource;
import conrad.codeworkshop.core.services.SearchService;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public final class Main extends Application<Config> {

  public static void main(final String... args) throws Exception {
    new Main().run(args);
  }

  @Override
  public void initialize(final Bootstrap<Config> bootstrap) {

  }

  @Override
  public void run(final Config config, final Environment environment) throws Exception {
    final SearchResource resource = new SearchResource(new SearchService(config));
    environment.jersey().register(resource);
  }


}
