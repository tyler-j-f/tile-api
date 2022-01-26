package io.tilenft.config.beans;

import io.tilenft.image.EmojiLoader;
import io.tilenft.image.ImageResourcesLoader;
import io.tilenft.image.drawers.BackgroundDrawer;
import io.tilenft.image.drawers.BurntTokenDrawer;
import io.tilenft.image.drawers.EmojiDrawer;
import io.tilenft.image.drawers.ImageDrawer;
import io.tilenft.image.drawers.SubTitleDrawer;
import io.tilenft.image.drawers.TilesDrawer;
import io.tilenft.image.drawers.TitleDrawer;
import java.text.NumberFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

@Configuration
public class ImageBeansConfig {

  @Autowired private ResourceLoader resourceLoader;

  @Bean
  @Qualifier(value = "emojiResourceLoader")
  public ImageResourcesLoader emojiResourceLoader() {
    return new ImageResourcesLoader(resourceLoader, "classpath:openmoji/*.png");
  }

  @Bean
  @Qualifier(value = "logoResourceLoader")
  public ImageResourcesLoader logoResourceLoader() {
    return new ImageResourcesLoader(resourceLoader, "classpath:logos/*");
  }

  @Bean
  @Qualifier(value = "tileNFTsResourceLoader")
  public ImageResourcesLoader tileNFTsResourceLoader() {
    return new ImageResourcesLoader(resourceLoader, "classpath:tileNFTs/*");
  }

  @Bean
  public EmojiDrawer emojiDrawer() {
    return new EmojiDrawer();
  }

  @Bean
  public EmojiLoader emojiLoader() {
    return new EmojiLoader();
  }

  @Bean
  public ImageDrawer imageDrawer() {
    return new ImageDrawer();
  }

  @Bean
  public TilesDrawer tilesDrawer() {
    return new TilesDrawer();
  }

  @Bean
  public TitleDrawer titleDrawer() {
    return new TitleDrawer();
  }

  @Bean
  public SubTitleDrawer subTitleDrawer() {
    return new SubTitleDrawer();
  }

  @Bean
  public BackgroundDrawer backgroundDrawer() {
    return new BackgroundDrawer();
  }

  @Bean
  public BurntTokenDrawer burntTokenDrawer() {
    return new BurntTokenDrawer();
  }

  @Bean
  public NumberFormat numberFormat() {
    return NumberFormat.getInstance();
  }
}
