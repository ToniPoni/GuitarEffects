
class Echo implements Effect{
    double a;
    double b;
    double c;

    int sampleFreq;

    public Echo(int sf) {
	a = 1;
	b = 0.7;
	c = 0.5;
	sampleFreq = sf;
    }

    public Double effect(int i, Double[] sig, int bufLen) {
	int mask = bufLen - 1;
	
	double norm = 1.0 / (a + b + c);
	int N = (int) (0.3 * sampleFreq);
	
	return norm * (a * sig[i] +
		b * sig[(i + bufLen - N) & mask] +
		c * sig[(i + bufLen - 2 * N) & mask]);
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

    static public void main(String args[]) {
	System.out.println("Echo");

	int M = 32;
	Double[] sig = new Double[M];
	for (int i = 0; i < M; i++) {
	    sig[i] = 0.0;
	}

	sig[10] = 1.0;

	for (int i = 0; i < M; i++)
	    System.out.print(sig[i] + " ");
	System.out.println();

	int sampleFreq = 44100;
	Echo eff = new Echo(sampleFreq);
	Double[] trans = eff.transform(sig);

	for (int i = 0; i < trans.length; i++)
	    System.out.print(trans[i] + " ");
    }
}
