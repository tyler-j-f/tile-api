package com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.traitPickers;

import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.WeightlessTraitContext;
import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.WeightlessTraitException;
import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.WeightlessTraitInterface;
import java.util.Random;

public class ColorTraitPicker implements WeightlessTraitInterface {

  @Override
  public String getValue(WeightlessTraitContext context) throws WeightlessTraitException {
    Long seedForTrait = context.getSeedForTrait();
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