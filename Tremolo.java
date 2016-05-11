
class Tremolo implements Effect{
    double phi;
    double omega;
    

    long sampleFreq;

    public Tremolo(long sf) {	
	sampleFreq = sf;
	phi = (27 * 2 * Math.PI)/sampleFreq;
	omega = 0;
    }

    public Double effect(int i, Double[] sig, int bufLen) {
	int mask = bufLen - 1;
	
	omega = omega + phi;
	return (1 + (Math.cos(omega)/4)) * sig[i];
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
