public class MyGrayProgram { 
   public static void main(String[] args) throws Exception{ 
      int[][] original = GrayImage.read("src/mushroom.jpeg");
      int[][] manipulated = contour(original);
      GrayImage.write("LeftRightMushroom.jpeg", manipulated);
      GrayImageWindow iw = new GrayImageWindow(original, manipulated); 
   }//main 

   public static int[][] upDown(int[][] samples) { 
      int[][] newSamples = new int[samples.length][samples[0].length]; 
      for (int row = 0; row < samples.length; row = row + 1) 
         for (int col = 0; col < samples[row].length; col = col + 1) 
            newSamples[row][col] = samples[samples.length - row - 1 ][col]; 
      return newSamples; 
    }//upDown

   public static int[][] LeftRight(int[][] samples){
      int[][] newSamples = new int[samples.length][samples[0].length];
      for(int row = 0; row < samples.length; row = row + 1) {
         for (int col = 0; col < samples[row].length; col = col + 1) {
            newSamples[row][col] = samples[row][samples[0].length - col - 1];
         }
      }
      return newSamples;
   }

   public static int[][] invert(int[][] samples){
      int[][] newSamples = new int[samples.length][samples[0].length];
      for(int row = 0; row < samples.length; row = row + 1) {
         for (int col = 0; col < samples[row].length; col = col + 1) {
            newSamples[row][col] = 255 - samples[row][col];
         }
      }
      return newSamples;
   }

   public static int[][] toBlackWhite(int[][] samples){
      int[][] newSamples = new int[samples.length][samples[0].length];
      for(int row = 0; row < samples.length; row = row + 1) {
         for (int col = 0; col < samples[row].length; col = col + 1) {
            if (samples[row][col] >= 128)
               newSamples[row][col] = 255;
            else
               newSamples[row][col] = 0;
         }
      }
      return newSamples;
   }
   public static int[][] contour(int[][] samples){
      int[][] newSamples = new int[samples.length][samples[0].length];
      for(int row = 0; row < samples.length; row = row + 1) {
         for (int col = 0; col < samples[row].length; col = col + 1) {
            if (samples[row][col] < 128)
               if (isOnFrame(row, col, samples))
                  newSamples[row][col] = 0;
         }
      }
      return newSamples;

   }
   public static boolean isOnFrame(int row, int col, int[][] sample){

      if(row >= 1 || col >= 1 || row < sample.length || col < sample[0].length) {


         if (sample[row - 1][col] >= 128 || sample[row + 1][col] >= 128
                 || sample[row][col - 1] >= 128 || sample[row][col + 1] >= 128)
            return true;
         else
            return false;
      }
      else if (row == 0 || col == 0 || row == sample.length || col == sample[0].length)
         return true;
      else
         return false;
   }
}//MyGrayProgram
