public class MyColorProgram { 
   public static void main(String[] args) throws Exception { 
      int[][][] original = ColorImage.read("src/mushroom.jpeg");
      int[][][] manipulated = sobelFilter(original);
      ColorImage.write("upDownMushroom.jpeg", manipulated); 
      ColorImageWindow iw = new ColorImageWindow(original, manipulated); 
   }//main 

   public static int[][][] upDown(int[][][] samples) { 
      int[][][] newSamples = new int[samples.length][samples[0].length][3]; 
      for (int row = 0; row < samples.length; row = row + 1)
         for (int col = 0; col < samples[row].length; col = col + 1) 
            for (int c = 0; c < samples[row][col].length; c = c + 1) 
               newSamples[row][col][c] = samples[samples.length-row-1][col][c]; 
      return newSamples; 
   }//upDown

   public static int[][][] leftRight(int[][][] samples){
      int[][][] newSamples = new int[samples.length][samples[0].length][3];
      for (int row = 0; row < samples.length; row = row + 1)
         for (int col = 0; col < samples[row].length; col = col + 1)
            for (int c = 0; c < samples[row][col].length; c = c + 1)
               newSamples[row][col][c] = samples[row][samples[0].length-col-1][c];
      return newSamples;
   }//leftRight

   public static int[][][] invert(int[][][] samples) {
      int[][][] newSamples = new int[samples.length][samples[0].length][3];
      for (int row = 0; row < samples.length; row = row + 1)
         for (int col = 0; col < samples[row].length; col = col + 1)
            for (int c = 0; c < samples[row][col].length; c = c + 1)
              newSamples[row][col][c] = 255 -samples[row][col][c];
      return newSamples;
   }//invert

   public static int[][][] toGray(int[][][] samples){
      int[][][] newSamples = new int[samples.length][samples[0].length][3];
      for (int row = 0; row < samples.length; row = row + 1)
         for (int col = 0; col < samples[row].length; col = col + 1) {
            double L = 0.299*samples[row][col][0]+0.587*samples[row][col][1]+0.114*samples[row][col][2];
            int LL = (int)Math.round(L);
            for (int c = 0; c < samples[row][col].length; c = c + 1) {
               newSamples[row][col][c] = LL;
            }
         }
      return newSamples;
   }//toGray

   public static int[][][] toBlackWhite(int[][][] samples){
      int[][][] newSamples = new int[samples.length][samples[0].length][3];
      for (int row = 0; row < samples.length; row = row + 1)
         for (int col = 0; col < samples[row].length; col = col + 1) {
            double L = 0.299 * samples[row][col][0] + 0.587 * samples[row][col][1] + 0.114 * samples[row][col][2];
            for (int c = 0; c < samples[row][col].length; c = c + 1) {
               if (L < 128)
                  newSamples[row][col][c] = 0;
               else
                  newSamples[row][col][c] = 255;
            }
         }
      return newSamples;
   }//toBlackWhite

   public static int[][][] sharpenTwo(int[][][] samples){
      int[][][] newSamples = new int[samples.length][samples[0].length][3];
      for (int row = 0; row < samples.length; row = row + 1)
         for (int col = 0; col < samples[row].length; col = col + 1)
            for (int c = 0; c < samples[row][col].length; c = c + 1) {
               if (row > 0 && col > 0 && row < (samples.length - 1) && col < (samples[0].length - 1)) {
                  newSamples[row][col][c] = ((5 * samples[row][col][c])
                          - (samples[row - 1][col][c])
                          - (samples[row ][col - 1][c])
                          - (samples[row ][col + 1][c])
                          - (samples[row + 1][col][c]));
                  if (newSamples[row][col][c] < 0)
                     newSamples[row][col][c] = 0;
                  else if (newSamples[row][col][c] > 255)
                     newSamples[row][col][c] = 255;
               }
            }
      return newSamples;
   }//sharpenTwo

   public static int[][][] sharpenOne(int[][][] samples){
      int[][][] newSamples = new int[samples.length][samples[0].length][3];
      for (int row = 0; row < samples.length; row = row + 1)
         for (int col = 0; col < samples[row].length; col = col + 1)
            for (int c = 0; c < samples[row][col].length; c = c + 1) {
               if (row > 0 && col > 0 && row < (samples.length - 1) && col < (samples[0].length - 1)) {
                  newSamples[row][col][c] = ((9 * samples[row][col][c])
                          - (samples[row - 1][col][c]) - (samples[row ][col - 1][c])
                          - (samples[row ][col + 1][c])
                          - (samples[row + 1][col][c])
                          - (samples[row -1][col -1][c])
                          - (samples[row -1][col +1][c])
                          - (samples[row +1][col -1][c])
                          - (samples[row + 1][col +1][c]));
                  if (newSamples[row][col][c] < 0)
                     newSamples[row][col][c] = 0;
                  else if (newSamples[row][col][c] > 255)
                     newSamples[row][col][c] = 255;
               }
            }
      return newSamples;
   }//sharpenOne

   public static int[][][] sobelFilter(int[][][] samples) {
      int[][][] newSamples = new int[samples.length][samples[0].length][3];
      int newSamplesX ;
      int newSamplesY ;
      for (int row = 0; row < samples.length; row = row + 1)
         for (int col = 0; col < samples[row].length; col = col + 1)
            for (int c = 0; c < samples[row][col].length; c = c + 1) {
               if (row > 0 && col > 0 && row < (samples.length - 1) && col < (samples[0].length - 1)) {
                  newSamplesX = ((-1)*(samples[row-1][col-1][c])
                          - 2*(samples[row - 1][col][c])
                          - (samples[row -1][col +1][c])
                          + (samples[row +1][col -1][c])
                          + 2*(samples[row +1][col][c])
                          + (samples[row +1][col +1][c]));

                  newSamplesY = ((-1)*(samples[row-1][col-1][c])
                          - 2*(samples[row][col - 1][c])
                          - (samples[row + 1][col - 1][c])
                          + (samples[row - 1][col + 1][c])
                          + 2*(samples[row ][col + 1][c])
                          + (samples[row + 1][col + 1][c]));

                  newSamples[row][col][c] = (int)Math.sqrt(Math.pow(newSamplesX, 2) + Math.pow(newSamplesY,2));

                  if (newSamples[row][col][c] < 0)
                     newSamples[row][col][c] = 0;
                  if (newSamples[row][col][c] > 255)
                     newSamples[row][col][c] = 255;
                  }
               }
      return newSamples;
            }//sobelFilter
}//MyColorProgram
