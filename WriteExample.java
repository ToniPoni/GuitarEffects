import java.io.*;

public class WriteExample
{
   public static void main(String[] args)
   {
      try
      {
         int sampleRate = 44100;    // Samples per second
         double duration = 5.0;     // Seconds

         // Calculate the number of frames required for specified duration
         long numFrames = (long)(duration * sampleRate);

         // Create a wav file with the name specified as the first argument
         WavFile wavFile = WavFile.newWavFile(new File(args[0]), 2, numFrames, 16, sampleRate);

         // Create a buffer of 100 frames
         int buffLen = 100;
         double[][] buffer = new double[2][buffLen];

         // Initialise a local frame counter
         long frameCounter = 0;

         // Loop until all frames written
         while (frameCounter < numFrames)
         {
            // Determine how many frames to write, up to a maximum of the buffer size

            long remaining = wavFile.getFramesRemaining();
            int toWrite = (remaining > buffLen) ? buffLen : (int) remaining;

            // Fill the buffer, one tone per channel
            for (int s=0 ; s<toWrite ; s++, frameCounter++)
            {
               buffer[0][s] = Math.sin(2.0 * Math.PI * 300 * frameCounter / sampleRate);
               buffer[1][s] = Math.sin(2.0 * Math.PI * 500 * frameCounter / sampleRate);
            }
            Double [] channel1 = new Double [buffLen];
            Double [] channel2 = new Double [buffLen];

            
            for(int i = 0; i < toWrite; i++){
               channel1[i] = buffer[0][i];
               channel2[i] = buffer[1][i];
            }

            Echo echo = new Echo(sampleRate);
            Double[] newChanel1 = echo.transform(channel1);
            Double[] newChanel2 = echo.transform(channel2);
            double[][] transBuffer = new double[2][newChanel1.length];


            for (int i=0 ; i < newChanel1.length ; i++)
            {
               transBuffer[0][i] = newChanel1[i];
               transBuffer[1][i] = newChanel2[i];
            }

            // Write the buffer

            wavFile.writeFrames(buffer, toWrite);
         }

         // Close the wavFile
         wavFile.close();
      }
      catch (Exception e)
      {
         System.err.println(e);
      }
   }
}