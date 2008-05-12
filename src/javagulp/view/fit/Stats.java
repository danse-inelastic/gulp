package javagulp.view.fit;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class Stats {
	
	public class Something implements Comparable<Something> {

		public int compareTo(Something o) {
			if (compareValues) {
				Double a = new Double(this.value);
				Double b = new Double(o.value);
				return a.compareTo(b);
			} else {
				Double a = new Double(this.zScore);
				Double b = new Double(o.zScore);
				return a.compareTo(b);
			}
		}

		public double value, zScore;
		
		public Something(double value) {
			this.value = value;
		}
	}
	
	public ArrayList<Something> values = new ArrayList<Something>();
	public ArrayList<ArrayList<Something>> pops = new ArrayList<ArrayList<Something>>();
	public double average;
	public double variance;
	public double standardDeviation;
	public double percentOutliers = 25.0;
	public boolean compareValues = true;

	public Stats(double[] values) {
		super();
		
		for (double d : values) {
			if (d != 0) //exclude zero?
				this.values.add(new Something(d));
			//System.out.println(d);
		}
		System.out.println("values.size() " + this.values.size());
		findPopulations();
		for (double d : values)
			this.values.add(new Something(d));
		System.out.println("values.size() " + this.values.size());
		recalculate();
	}
	
	public void recalculate() {
		//average
		average = 0;
		for (Something d: values)
			average += d.value;
		average /= values.size();
		
		//variance
		variance = 0;
		for (Something d: values)
			variance += Math.pow(d.value - average, 2);
		variance /= values.size();
		
		//standard deviation
		standardDeviation = Math.sqrt(variance);
		
		//z Score
		for (int i=0; i < values.size(); i++) {
			Something s = values.get(i);
			s.zScore = Math.abs((s.value - average) / standardDeviation);
		}
	}
	
	public void removeOutliers() {
		//TODO test to make sure outliers are being removed.
		compareValues = false;
		Collections.sort(values);
		int numberToRemove = (int) Math.round(percentOutliers / 100.0 * values.size());
		for (int i=0; i < numberToRemove; i++) {
			System.out.println("removing the outlier " + values.remove(values.size()-1).value);
		}
		compareValues = true;
		Collections.sort(values);
		recalculate();
	}
	
	public void findPopulations() {
		compareValues = true;
		Collections.sort(values);
		
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(15);
		
		try {
			for (int i=0; i < values.size()-1; i++) {
				double a = values.get(i).value;
				//System.out.println("a " + a);
				boolean skip = true;
				
				if (pops.size() > 0) {
					ArrayList<Something> temp = pops.get(pops.size()-1);
					double b = temp.get(temp.size()-1).value;
					//System.out.println("b " + b);
					double avg = 0;
					for (Something s: temp)
						avg += s.value;
					avg /= temp.size();
					double change = Math.abs((a-avg)/a);
					//System.out.println("pop change " + change);
					if (change < 0.05) {//if value is within 5% of the average
						//add to existing population
						temp.add(values.remove(i));
						i--;
						skip = false;
					}
				}
				
				if (skip) {
					double b = values.get(i+1).value;
					//System.out.println("b " + b);
					double change = Math.abs((a-b)/a);
					//System.out.println("change " + change);
					if (change < 0.05) {//if values are within 5% of each other
						//add both to a new population
						ArrayList<Something> temp = new ArrayList<Something>();
						temp.add(values.remove(i));
						temp.add(values.remove(i));
						pops.add(temp);
						i--;
					}
				}
			}
			
			
			
			/*//for each value
			for (int i=0; i < values.size() && i >= 0; i++) {
				double a = values.get(i).value;
				
				//compare with each population average
				boolean exit = false;
				for (int j=0; j < pops.size() && !exit; j++) {
					ArrayList<Something> temp = pops.get(j);
					double avg = 0;
					for (Something s: temp)
						avg += s.value;
					avg /= temp.size();
					
					double change = Math.abs((a-avg)/a);
					System.out.println("pop change " + change);
					if (change < 0.05) {//if value is within 5% of the average
						//add to existing population
						temp.add(values.remove(i));
						i--;
						exit = true;
					}
				}
				
				//otherwise compare with all other values
				exit = false;
				for (int j=i+1; j < values.size() && !exit; j++) {
					double b = values.get(j).value;
					double change = Math.abs((a-b)/a);
					System.out.println("change " + change);
					if (change < 0.05) {//if values are within 5% of each other
						//add both to a new population
						ArrayList<Something> temp = new ArrayList<Something>();
						temp.add(values.remove(i));
						temp.add(values.remove(j-1));
						pops.add(temp);
						i--;i--;
						exit = true;
					}
				}
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		values.clear();
		
		//print out populations for debugging
		System.out.println();
		System.out.println("population");
		System.out.println();
		for (ArrayList<Something> p: pops) {
			for (Something s: p) {
				System.out.print(s.value + " ");
			}
			System.out.println();
		}
	}
}
