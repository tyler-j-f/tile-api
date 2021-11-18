package com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits;

import com.tylerfitzgerald.demo_api.image.ImageResourcesLoader;
import java.io.IOException;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;

public class ColorTraitPicker implements WeightlessTraitInterface {

  @Autowired private ImageResourcesLoader imageResourcesLoader;

  @Override
  public String getValue(Long seedForTrait) throws WeightlessTraitException {
    int rInt, gInt, bInt, len;
    String r, g, b;
    // Get r value
    rInt = new Random(seedForTrait++).nextInt(0, 255);
    r = String.valueOf(rInt);
    len = r.length();
    for (int x = 0; x < 3 - len; x++) {
      r = "0" + r;
    }
    // Get g value
    gInt = new Random(seedForTrait++).nextInt(0, 255);
    g = String.valueOf(gInt);
    len = g.length();
    for (int x = 0; x < 3 - len; x++) {
      g = "0" + g;
    }
    // Get b value
    bInt = new Random(seedForTrait++).nextInt(0, 255);
    b = String.valueOf(bInt);
    len = b.length();
    for (int x = 0; x < 3 - len; x++) {
      b = "0" + b;
    }
    return r + g + b;
  }

  @Override
  public String getDisplayValue(Long seedForTrait) throws WeightlessTraitException {
    return "";
  }
}
