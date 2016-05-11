import java.io.*;
	public class ReadWriteExample{
		public static void main(String[] args){
			WavFile wavFile = WavFile.openWavFile(new File(args[0]));

			int numChannels = wavFile.getNumChannels();

			int buffLen = 2048;
			double[] buffer = new double[buffLen * numChannels];
			do
			{
				// Read frames into buffer
				framesRead = wavFile.readFrames(buffer, buffLen);

			}
			while (framesRead != 0);
			for(int i = 0; i < buffLen; i++)
			System.out.print(buffer + ", ")

		}
	}