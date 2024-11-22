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
	GShare gshare;
	Hybrid hybrid;

	public Operations(String sim, int bits) {
		this.sim = sim.toLowerCase();
		if (sim.equals("smith")) {
			smith = new Smith(bits);
		} else if (sim.equals("bimodal")) {
			bimodal = new Bimodal(bits);
		}
	}

	public Operations(String sim, int pcBits, int branchBits) {
		this.sim = sim.toLowerCase();
		gshare = new GShare(pcBits, branchBits);
	}

	public Operations(String sim, int k, int m1, int n, int m2) {
		this.sim = sim.toLowerCase();
		hybrid = new Hybrid(k, m1, n, m2);
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
		} else if (sim.equals("gshare")) {
			if (!gshare.predict(branch, outcome)) {
				miss++;
			}
			preds++;
		} else if (sim.equals("hybrid")) {
			if (!hybrid.predict(branch, outcome)) {
				miss++;
			}
			preds++;
		}
	}

	public void results() {
		float missRate = Math.round((float)miss/(float)preds * 10000) / (float)100;

		System.out.println("OUTPUT");
		System.out.println("number of predictions:    " + preds);
		System.out.println("number of mispredictions: " + miss);
		System.out.println("misprediction rate:       " + String.format("%.2f", missRate) + "%");
		if (sim.equals("smith")) {
			smith.result();
		} else if (sim.equals("bimodal")) {
			bimodal.result();
		} else if (sim.equals("gshare")) {
			gshare.result();
		} else if (sim.equals("hybrid")) {
			hybrid.result();
		}
	}
}
