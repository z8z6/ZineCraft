package com.cxxcxx.zinecraft.api.accessory;

import java.util.ArrayList;
import java.util.List;

import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

public final class CollectibleCatalogKt {
  private static final int TOOLTIP_LINE_CHARACTERS = 42;
  @NotNull
  private static final String ZERO_WIDTH_SPACE = "\u200b";

  @NotNull
  public static final List<LocalizedTooltipLine> wrapLocalizedTooltip(
      @NotNull String zhCn, @NotNull String enUs, int firstLineCharacters, int continuationCharacters
  ) {
    if (zhCn.length() <= 0 || enUs.length() <= 0) {
      int q = 0;
      String string1 = "tooltip 文本不能为空";
      throw new IllegalArgumentException(string1.toString());
    }

    if (firstLineCharacters <= 0 || continuationCharacters <= 0) {
      int p = 0;
      String string = "tooltip 行宽必须大于 0";
      throw new IllegalArgumentException(string.toString());
    }

    int zhLines = requiredTooltipLines(zhCn, firstLineCharacters, continuationCharacters);
    int enLines = requiredTooltipLines(enUs, firstLineCharacters, continuationCharacters);
    int i = Math.max(zhLines, enLines);
    List list = splitTooltipText(zhCn, i, firstLineCharacters, continuationCharacters);
    List list1 = splitTooltipText(enUs, i, firstLineCharacters, continuationCharacters);
    ArrayList arrayList = new ArrayList(i);

    for (int l = 0; l < i; l++) {
      int m = l;
      int it = m;
      ArrayList arrayList1 = arrayList;
      int o = 0;
      arrayList1.add(new LocalizedTooltipLine((String) list.get(it), (String) list1.get(it)));
    }

    return arrayList;
  }

  // $VF: synthetic method
  public static List wrapLocalizedTooltip$default(String var0, String var1, int var2, int var3, int var4, Object var5) {
    if ((var4 & 4) != 0) {
      var2 = 42;
    }

    if ((var4 & 8) != 0) {
      var3 = 42;
    }

    return wrapLocalizedTooltip(var0, var1, var2, var3);
  }

  private static final int requiredTooltipLines(String text, int firstLineCharacters, int continuationCharacters) {
    int i = text.codePointCount(0, text.length());
    return i <= firstLineCharacters ? 1 : 1 + (i - firstLineCharacters + continuationCharacters - 1) / continuationCharacters;
  }

  private static final List<String> splitTooltipText(String text, int lineCount, int firstLineCharacters, int continuationCharacters) {
    int[] i = text.codePoints().toArray();
    int j = 0;
    ArrayList arrayList = new ArrayList(lineCount);

    for (int k = 0; k < lineCount; k++) {
      int l = k;
      int index = l;
      ArrayList arrayList1 = arrayList;
      int n = 0;
      int o = i.length - j;
      String string1;
      if (o == 0) {
        string1 = "\u200b";
      } else {
        int p = lineCount - index;
        int q = index == 0 ? firstLineCharacters : continuationCharacters;
        int r = (p - 1) * continuationCharacters;
        int s = Math.max(1, o - r);
        int t = (o + p - 1) / p;
        int u = RangesKt.coerceIn(t, s, Math.min(q, o));
        String string = new String(i, j, u);
        int v = 0;
        j += u;
        string1 = string;
      }

      arrayList1.add(string1);
    }

    return arrayList;
  }
}

