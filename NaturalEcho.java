
class NaturalEcho implements Effect{
    double a;
   	double l;
   

    long sampleFreq;

    public NaturalEcho(long sf) {
	a = 0.7;
	l = 0.6;

	sampleFreq = sf;
    }

    public Double effect(int i, Double[] sig, int bufLen) {
	int mask = bufLen - 1;
	
	double norm = 1.0 / (1 + a);
	int N = (int) (0.3 * sampleFreq);
	
	return (norm * (		sig[i] - 
		l * sig[(i + bufLen - 1) & mask] + 
		l  * sig[(i + bufLen - 1) & mask]+ 
		a*(1-l) * sig[(i + bufLen - N) & mask]));
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
	NaturalEcho eff = new NaturalEcho(sampleFreq);
	Double[] trans = eff.transform(sig);

	for (int i = 0; i < trans.length; i++)
	    System.out.print(trans[i] + " ");
    }
}
