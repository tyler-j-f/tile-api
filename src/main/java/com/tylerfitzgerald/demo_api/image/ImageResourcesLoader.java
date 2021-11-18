package com.tylerfitzgerald.demo_api.image;

import java.io.IOException;
import java.util.Random;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;

public class ImageResourcesLoader {

  private ResourceLoader resourceLoader;
  private static Resource[] resources;
  private String loadPattern;
  private int randomListSize;
  private static final int DEFAULT_RANDOM_LIST_SIZE = 4;

  public ImageResourcesLoader(
      ResourceLoader resourceLoader, String loadPattern, int randomListSize) {
    this.resourceLoader = resourceLoader;
    this.loadPattern = loadPattern;
    this.randomListSize = randomListSize;
  }

  public ImageResourcesLoader(ResourceLoader resourceLoader, String loadPattern) {
    this.resourceLoader = resourceLoader;
    this.loadPattern = loadPattern;
    this.randomListSize = DEFAULT_RANDOM_LIST_SIZE;
  }

  public void loadResources() throws IOException {
    resources =
        ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources(loadPattern);
  }

  public Resource[] getRandomResourceList() throws IOException {
    if (resources == null) {
      loadResources();
    }
    Resource[] randomResources = new Resource[randomListSize];
    for (int x = 0; x < randomListSize; x++) {
      randomResources[x] = getRandomResource(resources);
    }
    return randomResources;
  }

  private Resource getRandomResource(Resource[] resources) {
    int rnd = new Random().nextInt(this.resources.length);
    return this.resources[rnd];
  }
}
