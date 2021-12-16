package io.tileNft.config.beans;

import io.tileNft.image.EmojiLoader;
import io.tileNft.image.ImageResourcesLoader;
import io.tileNft.image.drawers.BurntTokenDrawer;
import io.tileNft.image.drawers.EmojiDrawer;
import io.tileNft.image.drawers.ImageDrawer;
import io.tileNft.image.drawers.SubTitleDrawer;
import io.tileNft.image.drawers.TilesDrawer;
import io.tileNft.image.drawers.TitleDrawer;
import java.text.NumberFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

@Configuration
public class ImageBeansConfig {

  @Autowired private ResourceLoader resourceLoader;

  @Bean
  public ImageResourcesLoader imageResourcesLoader() {
    return new ImageResourcesLoader(resourceLoader, "classpath:openmoji/*.png");
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
  public BurntTokenDrawer burntTokenDrawer() {
    return new BurntTokenDrawer();
  }

  @Bean
  public NumberFormat numberFormat() {
    return NumberFormat.getInstance();
  }
}
