import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class sim {
	static String sim = "";
	static int b = 0;
	static int k = 0;
	static int m1 = 0;
	static int n = 0;
	static int m2 = 0;
	static String trace = "";
	static Operations ops;

	static void printInput(String[] argv) {
		System.out.println("COMMAND");
		System.out.print("java sim ");
		for (int i = 0; i < argv.length; i++) {
			System.out.print(argv[i] + " ");
		}
		System.out.println();
	}
	
	static void readOperations(String line, int count) {
		String[] adr = line.split(" ");
		// System.out.println("----------------------------------------");
		// System.out.println("# " + (count+1) + " : " + line);
	
		if (adr[1].charAt(0) != 't' && adr[1].charAt(0) != 'n') {
			throw new IllegalArgumentException("Invalid instruction");
		}
		ops.run(adr[0], adr[1].charAt(0));
	}

	public static void main(String[] args) {
		sim = args[0];
		printInput(args);
		
		if (sim.equalsIgnoreCase("smith")) {
			ops = new Operations(sim, Integer.parseInt(args[1]));
			trace = args[2];
		} else if (sim.equalsIgnoreCase("bimodal")) {
			ops = new Operations(sim, Integer.parseInt(args[1]));
			trace = args[2];
		} else if (sim.equalsIgnoreCase("gshare")) {
			// If n == 0, use bimodal branch instead
			if (Integer.parseInt(args[2]) == 0) {
				ops = new Operations("bimodal", Integer.parseInt(args[1]));
			} else {
				ops = new Operations(sim, Integer.parseInt(args[1]), Integer.parseInt(args[2]));
			}
			trace = args[3];
		} else if (sim.equalsIgnoreCase("hybrid")) {
			k = Integer.parseInt(args[1]);
			m1 = Integer.parseInt(args[2]);
			n = Integer.parseInt(args[3]);
			m2 = Integer.parseInt(args[4]);
			ops = new Operations(sim, k, m1, n, m2);
			trace = args[5];
		} else {
			throw new IllegalArgumentException("Invalid sim (" + args[0] + ")");
		}


		try {
			File input = new File(trace);
			Scanner reader = new Scanner(input);
	
			String line;
			int maxCount = Integer.MAX_VALUE;
			// maxCount = 10;
			int count = 0;
	
			while (reader.hasNextLine() && count < maxCount) {
				line = reader.nextLine();
				readOperations(line, count);
				// ops.outputCaches();
				count++;
			}
	
			reader.close();
			ops.results();
			// ops.graph();
		} catch (FileNotFoundException e) {
			System.out.println("Error reading file");
			e.printStackTrace();
		}
	}
}
