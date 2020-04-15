package conrad.codeworkshop.core.api;

public enum FieldName {

  ID("id", true),
  ORIGINAL_TITLE("original_title", false),
  TITLE("title", false),
  OVERVIEW("overview", false),
  STATUS("status", false),
  POPULARITY("popularity", true),
  RUNTIME("runtime", true),
  VOTE_AVERAGE("vote_average", true),
  VOTE_COUNT("vote_count", true),
  ORIGINAL_LANGUAGE("original_language", false);

  private final String fieldName;
  private final boolean sortable;

  FieldName(String fieldName, boolean sortable) {
    this.fieldName = fieldName;
    this.sortable = sortable;
  }

  public String getFieldName() {
    return fieldName;
  }

  public boolean isSortable() {
    return sortable;
  }
}
