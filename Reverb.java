
class Reverb implements Effect{
    double a;
    //double l;
   
    

    long sampleFreq;

    public Reverb(long sf) {
	a = 0.4;
	

	sampleFreq = sf;
    }

    public Double effect(int i, Double[] sig1, int bufLen) {
	int mask = bufLen - 1;
	
	//double norm = 1.0 / (1 + a);
	int N = (int) (0.02 * sampleFreq);
	
	return ( -a * sig1[i] +
		sig1[(i + bufLen - N) & mask] +
		a * sig1[(i + bufLen - N) & mask]);
		//c * sig[(i + bufLen - 2 * N) & mask]);
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
