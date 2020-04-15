package conrad.codeworkshop.core.util;

import org.jetbrains.annotations.Contract;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

public final class HitUtil {
  HitUtil() {
  }

  @SuppressWarnings("unchecked")
  @Nullable
  @Contract("_, _, !null -> !null;null, _, null -> null")
  public static <T> T get(@Nullable final Map<String, Object> source, final String path, @Nullable final T ifNotFound) {
    if (source == null) {
      return ifNotFound;
    }
    return get0(
        source,
        path,
        ifNotFound
    );
  }

  @SuppressWarnings("unchecked")
  @Nullable
  @Contract("_, _, !null -> !null")
  private static <T> T get0(
      @Nonnull final Map<String, Object> source,
      final String path,
      @Nullable final T ifNotFound) {
    int idx = 0;
    Map<String, Object> src = source;
    while (idx < path.length()) {
      final int nextIdx = path.indexOf('.', idx);
      if (nextIdx == -1) {
        final Object next = src.get(path.substring(idx));
        return next != null ? (T) next : ifNotFound;
      }
      final Object next = src.get(path.substring(idx, nextIdx));
      if (!(next instanceof Map)) {
        return ifNotFound;
      }
      src = (Map<String, Object>) next;
      idx = nextIdx + 1;
    }
    return ifNotFound;
  }
}
