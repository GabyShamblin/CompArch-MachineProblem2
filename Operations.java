public class Operations {
	String sim = "";
	int b = 0;
	int k = 0;
	int m1 = 0;
	int n = 0;
	int m2 = 0;
	String trace = "";

	int preds = 0;
	int miss = 0;

	Smith smith;
	Bimodal bimodal;

	public Operations(String sim, int bits) {
		this.sim = sim.toLowerCase();
		if (sim.equals("smith")) {
			smith = new Smith(bits);
		} else if (sim.equals("bimodal")) {
			bimodal = new Bimodal(bits);
		}
	}

	public void run(String branch, char outcome) {
		if (sim.equals("smith")) {
			if (!smith.predict(outcome)) {
				miss++;
			}
			preds++;
		} else if (sim.equals("bimodal")) {
			if (!bimodal.predict(branch, outcome)) {
				miss++;
			}
			preds++;
		}
	}

	public void results() {
		float missRate = (float)miss/(float)preds * 100;
		
		System.out.println("OUTPUT");
		System.out.println("number of predictions:    " + preds);
		System.out.println("number of mispredictions: " + miss);
		System.out.printf ("misprediction rate:       %.2f%s", missRate, "%\n");
		if (sim.equals("smith")) {
			smith.result();
		} else if (sim.equals("bimodal")) {
			bimodal.result();
		}
	}
}
