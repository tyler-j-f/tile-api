package com.tylerfitzgerald.demo_api.config.beans;

import com.tylerfitzgerald.demo_api.image.EmojiLoader;
import com.tylerfitzgerald.demo_api.image.ImageResourcesLoader;
import com.tylerfitzgerald.demo_api.image.drawers.BurntTokenDrawer;
import com.tylerfitzgerald.demo_api.image.drawers.EmojiDrawer;
import com.tylerfitzgerald.demo_api.image.drawers.ImageDrawer;
import com.tylerfitzgerald.demo_api.image.drawers.SubTitleDrawer;
import com.tylerfitzgerald.demo_api.image.drawers.TilesDrawer;
import com.tylerfitzgerald.demo_api.image.drawers.TitleDrawer;
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
