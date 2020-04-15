package conrad.codeworkshop.core.api;

import java.util.Locale;

public enum Lang {
  DE("de"),
  NL("nl"),
  HU("hu"),
  PL("pl"),
  FR("fr"),
  DA("da"),
  DK("dk"),
  FI("fi"),
  SE("se"),
  SV("sv"),
  IT("it"),
  HR("hr"),
  SL("sl"),
  CS("cs"),
  SK("sk"),
  EN("en");

  public final String id;

  Lang(final String id) {
    this.id = id;
  }

  public static Lang fromString(final String value) {
    return valueOf(value.toUpperCase(Locale.ENGLISH));
  }
}
