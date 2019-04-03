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

   /**
    * Used to indentify Which enum is needed from Sting manly for use in sqlite3 database as there is no Set datatype.
    * @param productType
    * @return correct type of ProductType
    */
   public static ProductType IndentifyFromString(String productType){
      switch (productType.toLowerCase()){
         case "music":return ProductType.Music;
         case "video":return ProductType.Video;
         case "tv":return ProductType.TV;
         default: return null;
      }
   }
}
