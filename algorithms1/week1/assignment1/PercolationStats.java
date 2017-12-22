// Percolation Stats Java - runs multiple sims, and outputs perc stats 
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	// mean 
	private double mean; 
	// stddev 
	private double stddev; 
	// bottom confidence interval
	private double confidenceLo; 
	// upper confidence interval 
	private double confidenceHi; 
	// array of perc thresholds from each run 
	private double[] percArray; 

	public PercolationStats(int n, int t) {
		if (n <= 0 || t <= 0) {throw new IllegalArgumentException("n and t must be > 0.");}
		percArray = new double[t]; 
		for (int s = 0; s < t; s++) {
			Percolation percolation = new Percolation(n); 
			double counter = 0; 
			// loop and open random sites while no path exists
			while (!percolation.percolates()) {
				// random site
				int i = StdRandom.uniform(1, n+1); 
				int j = StdRandom.uniform(1, n+1);
				// if already open skip
				if (percolation.isOpen(i, j)) continue; 
				// otherwise 
				percolation.open(i, j);
				counter++;   
			}
		percArray[s] = counter/(n*n); 
	}
	// compiling stats on the percArray
	mean = StdStats.mean(percArray); 
	stddev = StdStats.stddev(percArray); 
	confidenceLo = mean - ((1.96*stddev)/Math.sqrt(t)); 
	confidenceHi = mean + ((1.96*stddev)/Math.sqrt(t)); 
	}

	public double mean() {return mean;}
	public double stddev() {return stddev;}
	public double confidenceLo() {return confidenceLo;}
	public double confidenceHi() {return confidenceHi;}

	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		int t = Integer.parseInt(args[1]); 
		PercolationStats percStats = new PercolationStats(n, t); 
		StdOut.println("mean                    = " + percStats.mean());
        StdOut.println("stddev                  = " + percStats.stddev());
        StdOut.println("95% confidence interval = " + percStats.confidenceLo()
                + ", " + percStats.confidenceHi());
	}

}