public class Smith {
	int counter = 0;
	int max = 0;

	public Smith(int b) {
		String bits = "1";
		for (int i = 1; i < b; i++) {
			bits += "1";
		}
		max = Integer.parseInt(bits, 2);
		counter = (int)Math.ceil(max/(float)2);
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

	// Determine branches actual outcome
	// Make prediction based on content of counter 
	// Update predictor based on actual outcome
	// 	Counter incremented if taken, decremented if not
	public boolean predict(char outcome) {
		char prediction;

		if (counter <= max/2) { prediction = 'n'; }
		else { prediction = 't'; }

		// System.out.printf("%2d %s %s ", counter, prediction, outcome);

		if (outcome == 'n') { counter--; }
		else { counter++; }
		counter = clamp(counter);

		// System.out.printf("%-2d\n", counter);

		return prediction == outcome;
	}

	public void result() {
		System.out.println("FINAL COUNTER CONTENT:    " + counter);
	}
}
