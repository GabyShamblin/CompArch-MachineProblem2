import java.util.Arrays;

public class Bimodal {
	int[] counters;
	int m = 0;
	int max = 7;

	public Bimodal(int b) {
		m = b;
		counters = new int[(int)Math.pow(2, m)];
		Arrays.fill(counters, 4);
	}

	int getIndex(String adr) {
		return (int)((Long.parseLong(adr, 16) >> 2) & (counters.length - 1));
	}

	public int clamp(int counter) {
		if (counter < 0) {
			return 0;
		} else if (counter > max) {
			return max;
		} else {
			return counter;
		}
	}

	// Determine branches index into prediction table
	// Make prediction
	// 	Get counter from table 
	// Update predictor based on actual outcome
	// 	Branches counter increments if take, decrements if not
	public boolean predict(String branch, char outcome) {
		char prediction;
		int index = getIndex(branch);
		int counter = counters[index];

		if (counter < 4) { prediction = 'n'; }
		else { prediction = 't'; }

		// System.out.printf("%2d %s %s ", counter, prediction, outcome);

		if (outcome == 'n') { counter--; }
		else { counter++; }
		counters[index] = clamp(counter);

		// System.out.printf("%-2d\n", counter);

		return prediction == outcome;
	}

	public void result() {
		System.out.println("FINAL BIMODAL CONTENTS");
		for (int i = 0; i < counters.length; i++) {
			System.out.printf("%-2d %d\n", i, counters[i]);
		}
	}
}
