public class Smith {
	int counter = 0;
	int max = 0;

	public Smith(int b) {
		if (b == 1) {
			counter = 1;
			max = 1;
		} else if (b == 2) {
			counter = 2;
			max = 3;
		} else if (b == 3) {
			counter = 4;
			max = 7;
		} else if (b == 4) {
			counter = 8;
			max = 15;
		}
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
