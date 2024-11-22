import java.math.BigInteger;
import java.util.Arrays;

public class GShare {
	int[] counters;
	int pcBits = 0;
	int historyBits = 0;
	int gbhr = 0;
	int max = 7;

	public GShare(int pc, int bits) {
		pcBits = pc;	// m
		historyBits = bits;  // n
		counters = new int[(int)Math.pow(2, pc)];
		Arrays.fill(counters, 4);
	}

	int getIndex(String adr) {
		String bitAdr = new BigInteger(adr, 16).toString(2);
		// System.out.println("Index bits: " + bitAdr + " -> " + bitAdr.substring(bitAdr.length()-(pcBits+1), bitAdr.length()-2));
		return (int)Long.parseLong(bitAdr.substring(
			bitAdr.length() - (pcBits+1), 
			bitAdr.length() - 2)
			, 2) ^ gbhr;
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

	public int prediction(String branch) {
		int index = getIndex(branch);
		return counters[index];
	}

	public void updateCounter(int index, char outcome) {
		int counter = counters[index];
		if (outcome == 'n') { 
			counter--; 
		} else { 
			counter++; 
		}
		updateRegistry(outcome);
		counters[index] = clamp(counter);
	}

	public void updateRegistry(char outcome) {
		if (outcome == 'n') {
			gbhr = (gbhr >> 1) | (0 << (historyBits - 1));
		} else {
			gbhr = (gbhr >> 1) | (1 << (historyBits - 1));
		}
	}
	
	public boolean predict(String branch, char outcome) {
		// Determine branches index into prediction table
		// 	gbhr xor pc bits
		char prediction;
		int index = getIndex(branch);
		int counter = counters[index];
		
		// Make prediction
		// 	Get counter from table 
		if (counter < 4) { prediction = 'n'; }
		else { prediction = 't'; }
		
		// Update predictor based on actual outcome
		// 	Branches counter increments if take, decrements if not
		updateCounter(index, outcome);
		// Update global branch history register
		// 	Shift actual outcome into the left 
		updateRegistry(outcome);

		return prediction == outcome;
	}

	public void result() {
		System.out.println("FINAL GSHARE CONTENTS");
		for (int i = 0; i < counters.length; i++) {
			System.out.printf("%-3d %d\n", i, counters[i]);
		}
	}
}
