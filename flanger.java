class Flanger implements Effect{
    double phi;
    double omega;
   

    long sampleFreq;

    public Flanger(long sf) {
	phi = (0.1 * 2 * Math.PI)/sampleFreq;
	omega = 0;

	sampleFreq = sf;
    }

    public Double effect(int i, Double[] sig, int bufLen) {
	int mask = bufLen - 1;
	int N = (int) (0.002 * sampleFreq);
	int d = (int) (N * (1 + Math.cos(omega)));
	//int N = (int) (0.3 * sampleFreq);
	omega = omega + phi;
	return (0.5 + (sig[i] + sig[(i + bufLen - d) & mask]));
    }

    public Double[] transform(Double[] sig) {
		int bufLen = 1;
		while (bufLen <= sig.length)
		    bufLen *= 2;
		bufLen /= 2;
		
		Double[] trans = new Double[bufLen];
		for (int i = 0; i < bufLen; i++)
		    trans[i] = effect(i, sig, bufLen);

		return trans;
    }
}
