package com.tylerfitzgerald.demo_api.config.beans;

import com.tylerfitzgerald.demo_api.image.EmojiDrawer;
import com.tylerfitzgerald.demo_api.image.EmojiLoader;
import com.tylerfitzgerald.demo_api.image.ImageDrawer;
import com.tylerfitzgerald.demo_api.image.TilesDrawer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImageConfig {

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
}
