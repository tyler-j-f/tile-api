package io.tilenft.image;

import java.io.IOException;
import java.util.Arrays;
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

  public Resource[] getResources() throws IOException {
    if (resources == null) {
      loadResources();
    }
    return resources;
  }

  public Resource[] getRandomResourceList(int listSize, Long randomSeed) throws IOException {
    if (resources == null) {
      loadResources();
    }
    Resource[] randomResources = new Resource[listSize];
    for (int x = 0; x < listSize; x++) {
      // Increment the random seed so that we do not get the same random item every time.
      randomResources[x] = getRandomResource(randomSeed++);
    }
    return randomResources;
  }

  public Resource[] getRandomResourceList(Long randomSeed) throws IOException {
    return getRandomResourceList(randomListSize, randomSeed);
  }

  public Resource getRandomResource(Long randomSeed) throws IOException {
    if (resources == null) {
      loadResources();
    }
    int rnd = new Random(randomSeed).nextInt(resources.length);
    return resources[rnd];
  }

  public Resource getResourceByName(String name) throws IOException {
    if (resources == null) {
      loadResources();
    }
    return Arrays.stream(resources)
        .sequential()
        .filter(resource -> resource.getFilename().equals(name))
        .findFirst()
        .get();
  }

  public Resource[] getResourcesByName(String[] names) throws IOException {
    Resource[] resources = new Resource[names.length];
    int x = 0;
    for (String name : names) {
      Resource a = getResourceByName(name);
      resources[x++] = a;
    }
    return resources;
  }

  public int[] getResourcesIndexes(String[] filenames) throws IOException, ImageException {
    if (resources == null) {
      loadResources();
    }
    int numberOfResources = filenames.length;
    int[] indexes = new int[numberOfResources];
    for (int x = 0; x < numberOfResources; x++) {
      indexes[x] = getResourceIndex(filenames[x]);
    }
    return indexes;
  }

  public int getResourceIndex(String filename) throws IOException, ImageException {
    System.out.println("DEBUG getResourceIndex filename: " + filename);
    if (resources == null) {
      loadResources();
    }
    for (int x = 0; x < resources.length; x++) {
      if (resources[x].getFilename().equals(filename)) {
        return x;
      }
    }
    throw new ImageException("Unable to find image. filename: " + filename);
  }
}
