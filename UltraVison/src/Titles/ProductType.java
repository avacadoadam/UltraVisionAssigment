package Titles;

public enum ProductType {

   Music("music"),Video("video"),TV("tv");

   private String type;

   ProductType(String type) {
      this.type = type;
   }

   public String getType() {
      return type;
   }
}
