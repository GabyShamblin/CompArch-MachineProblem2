import java.math.BigInteger;
import java.util.Arrays;

public class Hybrid {
	int[] chooser;
	int pcBits = 0;
	int max = 3;

	Bimodal bimodal;
	GShare gshare;

	public Hybrid(int k, int m1, int n, int m2) {
		pcBits = k;
		gshare = new GShare(m1, n);
		bimodal = new Bimodal(m2);
		chooser = new int[(int)Math.pow(2, k)];
		Arrays.fill(chooser, 1);
	}

	int getIndex(String adr) {
		String bitAdr = new BigInteger(adr, 16).toString(2);
		// System.out.println("Index bits: " + bitAdr + " -> " + bitAdr.substring(bitAdr.length()-(pcBits+2), bitAdr.length()-2));
		return (int)Long.parseLong(bitAdr.substring(
			bitAdr.length() - (pcBits+2), 
			bitAdr.length() - 2)
			, 2);
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

	// Update branch chooser based on instructions chart
	public void updateChooser(char actual, char gsharePred, char bimodalPred, int index) {
		// Gshare correct and bimodal incorrect, increment
		if (actual == gsharePred && actual != bimodalPred) {
			chooser[index] = clamp(chooser[index]+1);
		}
		// Gshare incorrect and bimodal correct, decrement
		else if (actual != gsharePred && actual == bimodalPred) {
			chooser[index] = clamp(chooser[index]-1);
		}
		// If both correct or incorrect, make no change
	}

	// Update branch chooser based on table in page
	public boolean predict(String branch, char outcome) {
		// Obtain a gshare prediction and a bimodal prediction
		char gsharePred = gshare.prediction(branch);
		char bimodalPred = bimodal.prediction(branch);
		// Determine branch's index (bit k+1 to 2)
		int index = getIndex(branch);
		int choose = chooser[index];
		
		// Make overall prediction. Use index in chooser table
		// 	Chooser value >=2 gshare
		// 	Chooser value <2 bimodal
		// Gshare global branch history register must always be updated
		if (choose < 2) { 
			bimodal.updateCounter(bimodal.getIndex(branch), outcome);
			gshare.updateRegistry(outcome);
			updateChooser(outcome, gsharePred, bimodalPred, index);
			
			return bimodalPred == outcome;
		} else { 
			gshare.updateCounter(gshare.getIndex(branch), outcome);
			gshare.updateRegistry(outcome);
			updateChooser(outcome, gsharePred, bimodalPred, index);

			return gsharePred == outcome;
		}
	}

	public void result() {
		System.out.println("FINAL CHOOSER CONTENTS");
		for (int i = 0; i < chooser.length; i++) {
			System.out.printf("%-3d %d\n", i, chooser[i]);
		}
		gshare.result();
		bimodal.result();
	}
}
