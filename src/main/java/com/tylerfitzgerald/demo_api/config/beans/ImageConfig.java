package com.tylerfitzgerald.demo_api.config.beans;

import com.tylerfitzgerald.demo_api.image.EmojiDrawer;
import com.tylerfitzgerald.demo_api.image.EmojiLoader;
import com.tylerfitzgerald.demo_api.image.ImageDrawer;
import com.tylerfitzgerald.demo_api.image.SubTitleDrawer;
import com.tylerfitzgerald.demo_api.image.TilesDrawer;
import com.tylerfitzgerald.demo_api.image.TitleDrawer;
import java.text.NumberFormat;
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

  @Bean
  public TitleDrawer titleDrawer() {
    return new TitleDrawer();
  }

  @Bean
  public SubTitleDrawer subTitleDrawer() {
    return new SubTitleDrawer();
  }

  @Bean
  public NumberFormat numberFormat() {
    return NumberFormat.getInstance();
  }
}
