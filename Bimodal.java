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

	public char prediction(String branch) {
		int index = getIndex(branch);
		int counter = counters[index];
		if (counter < 4) { return 'n'; }
		else { return 't'; }
	}

	public void updateCounter(int index, char outcome) {
		int counter = counters[index];
		if (outcome == 'n') { 
			counter--; 
		} else { 
			counter++; 
		}
		counters[index] = clamp(counter);
	}

	public boolean predict(String branch, char outcome) {
		// Determine branches index into prediction table
		int index = getIndex(branch);
		int counter = counters[index];
		
		// Make prediction
		// 	Get counter from table 
		char prediction = prediction(branch);
		
		// Update predictor based on actual outcome
		// 	Branches counter increments if take, decrements if not
		if (outcome == 'n') { counter--; }
		else { counter++; }
		counters[index] = clamp(counter);

		return prediction == outcome;
	}

	public void result() {
		System.out.println("FINAL BIMODAL CONTENTS");
		for (int i = 0; i < counters.length; i++) {
			System.out.printf("%-3d %d\n", i, counters[i]);
		}
	}
}
