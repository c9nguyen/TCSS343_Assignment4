import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class TCSS343 {

//	static int n;
//	static int min = 999999999;
	static int[][] input;
	static int[] dp;
	static int[] cheapestSet;
	
	public static void main(String[] args) {
    	Scanner sc = new Scanner(System.in);
    	ArrayList<String> inputList = new ArrayList<String>();
    	String[] line;
    	int size;
   
		
    	
//    	/* Reading input file passing via command line */
//    	while (sc.hasNext()) {
//    		inputList.add(sc.nextLine());
//    	}
//    	
//    	size = inputList.size();
//    	input = new int[size][size];
//    	
//    	//Reading input
//    	for (int i = 0; i < size; i++) {
//    		line = inputList.get(i).split("\t");
//    		
//    		for (int j = 0; j < size ; j++) {
//    			if (line[j].equals("NA")) {
//    				input[i][j] = (int) Double.POSITIVE_INFINITY;
//    			} else {
//    				input[i][j] = Integer.parseInt(line[j]);
//    			}
//    		}
//    	}
    	
    	
		/* Using hardcode input*/
//		int[][] hardcode = {{0, 2, 3, 7}, {99, 0, 2, 4}, {99, 99, 0, 2}, {99, 99, 99, 99, 0}};	
//		input = hardcode;
//		size = input.length;
		
    	/* Using input generator */
		size = 20;
		input = randomTestCase(size);
		
//    	/* Print out input table */
//		for (int i = 0; i < size; i++) {
//			
//			for (int j = 0; j < size; j++) {
//				if (j < i) {
//					System.out.print("inf" + "\t");
//				} else {
//					System.out.print(input[i][j] + "\t");
//				}
//			}
//			
//			System.out.println();
//		}
//		System.out.println();
		
    	dp = new int[input[0].length];	// For dynamic program
    	
    	writeOut();
//    	
    	BruteForce2();
//		
//       	DivideAndConquer2();
//    	
    	Dynamic2();
//    	
//    	int[] bun = cheapDynamic(input);
//    	
//    	for (int i : bun) {
//    		System.out.print(i + "\t");
//    	}
//    	
//    	System.out.print(cheapestSet[input.length - 1]);

    	DivideAndConquer3();
	}
	
	
	public static void BruteForce2(){
		BestPath finalPort = BruteForce2(0);
		
		System.out.println("Using Brute-Force, min cost: \t\t" + finalPort.cost);
		
		System.out.print("Path: ");
		while (finalPort != null) {						//Reaches last port
			System.out.print(finalPort.port + 1 + " to ");
			finalPort = finalPort.next;
		}
		
		System.out.println(input.length + "\n");
	}
	
	public static BestPath BruteForce2(int srs) {
		BestPath bestOne = new BestPath();
		
		bestOne.port = srs;
		bestOne.cost = input[srs][input.length - 1];
		
		int n = input.length;
		
		for (int i = srs + 1; i < n - 1; i++) {
			int a = input[srs][i];
			BestPath current = BruteForce2(i);
			int b = current.cost;

			if (a + b < bestOne.cost) {
				bestOne.cost = a + b;
				current.previous = bestOne;
				bestOne.next = current;
			}
			
		}
		
		return bestOne;
	}
	
	public static void DivideAndConquer2() {
		BestPath[] minPath = DivideAndConquer2(0, input.length);
		Stack<Integer> stack = new Stack<Integer>();
		
		BestPath lastOne = minPath[input.length - 1];
		
		System.out.println("Using Divide&Conquer, min cost: \t" + lastOne.cost);
			
		lastOne = lastOne.previous;
		
		while (lastOne.previous != null) {
			stack.push(lastOne.port + 1);
			lastOne = lastOne.previous;
		}
		
		System.out.print("Path: " + 1 + " to ");
		while (!stack.isEmpty()) {
			System.out.print(stack.pop() + " to ");
		}
		System.out.println(input.length);
		System.out.println();
		
	}
	
	public static void DivideAndConquer3() {
    	BestPath a = DivideAndConquer3(0,input.length - 1);
    	
    	System.out.println(a.cost);
    	
    	System.out.print(1 + " to ");
    	
    	while (a.port < input.length - 1) {
    		System.out.print(a.port + 1 + " to ");
    		a = a.next;
    	}
    	
    	System.out.println(input.length);
	}
	
	/**
	 * Calcualte the best cost to go from srs to des
	 * @param srs
	 * @param des
	 * @return the next port object
	 */
	public static BestPath DivideAndConquer3(int srs, int des) {
		int length = des - srs;
		BestPath goTo = new BestPath();
		goTo.cost = input[srs][des];
		goTo.port = des;
		
		if (length != 0) {
			

			for (int i = srs + 1; i < length; i++) {
				BestPath a = DivideAndConquer3(srs, i);

				BestPath b = DivideAndConquer3(i, des);

				if (a.cost + b.cost < goTo.cost) {
					goTo.cost = a.cost + b.cost;
					goTo.port = i;					
					goTo.next = b;
				}

			}
		}
		
		return goTo;
	}
	
	public static BestPath[] DivideAndConquer2(int srs, int des) {
		BestPath[] pathsCost = new BestPath[des - srs];
		
		if (des - srs > 1 ) {

			BestPath[] half1 = DivideAndConquer2(srs, (des + srs) / 2);
			BestPath[] half2 = DivideAndConquer2((des + srs) / 2, des);
	
			for (int i = 0; i < half1.length; i++) {
				pathsCost[i] = half1[i];
			}
			
			for (int i =  0; i < half2.length; i++) {		
		
				half2[i].cost = half2[i].previous.cost + input[half2[i].previous.port][half2[i].port];
				
				int d = half2[i].port;
				
				for (int j = 0; j < half1.length + i ; j++) {
					int s = pathsCost[j].port;
					int cost =  pathsCost[j].cost + input[s][d];

					
					if (half2[i].cost > cost) {
						half2[i].previous = pathsCost[j];
						half2[i].cost = cost;
					}
				}
				
				pathsCost[i + half1.length] = half2[i];
			}
			
			
		} else {
			pathsCost[0] = new BestPath();
			BestPath one = new BestPath();
			
			one.port = 0;
			
			pathsCost[0].previous = one;
			pathsCost[0].port = srs;
			pathsCost[0].cost = input[0][srs];
		}
		
		return pathsCost;
	}
	

	
	public static int[] cheapDynamic(int[][] input) {
		cheapestSet = new int[input.length]; //cheapest cost at each post
		int[] postSet = new int[input.length];//post taking for cheapest cost
		cheapestSet[0] = 0;
		for (int i = 1; i < input.length; i++) {
			cheapestSet[i] = Integer.MAX_VALUE;
			postSet[i] = i - 1;
			for (int j = i - 1; j >= 0; j--) {
				if (cheapestSet[j] + input[j][i] < cheapestSet[i]) {
					postSet[i] = j;
					cheapestSet[i] = cheapestSet[j] + input[j][i];
				}
			}
		}
		System.out.println("DYNAMIC PROGRAMMING");
		return postSet;
	}
	
	
	public static void Dynamic2() {
		int[] dp2 = new int[input.length];
		int[] from = new int[input.length];
		Stack<Integer> stack = new Stack<Integer>();
	
		for (int i = 1; i < dp2.length; i++) {
			dp2[i] = (int) Double.POSITIVE_INFINITY;
		}
		
		int n = input.length;
		
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < i; j++) {
				int cost = input[j][i] + dp2[j];

				if (dp2[i] > cost) {
					dp2[i] = cost;
					from[i] = j;
				}
			}
		}
		
		System.out.println("Using Dynamic Program, min cost: \t" + dp2[n-1]);
		
		int index = from[n-1];	// Get the port that goes to last port
		
		while (index > 0) {			// Trace back ports until first port
			stack.push(index);
			index = from[index];
			
		}

		System.out.print("Path: " + 1 + " to ");
		while (!stack.isEmpty()) {
			System.out.print(stack.pop() + 1 + " to ");
		}
		System.out.println(n);
		System.out.println();
	}
	
	
	public static int[][] randomTestCase(int sizeN) {
		int[][] testCase = new int[sizeN][sizeN];
		Random rd = new Random();
		for (int i = 0; i < sizeN; i++) {
			
			for (int j = 0; j < sizeN; j++) {
				if (j == i) {
					testCase[i][j] = 0;
				} else if (j < i) {
					testCase[i][j] = (int) Double.POSITIVE_INFINITY;
				} else {
					int num = rd.nextInt(sizeN * 100);
					testCase[i][j] = num;
				}
			}
		}
		
		return testCase;
	}
	
	public static void writeOut() {
		int size = input.length;
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(size + ".txt")));
			
			for (int i = 0 ; i < size; i++) {
				for (int j = 0; j < size ; j++) {
					if (j == i) bw.write(0);
					else if (j < i) bw.write("NA");
					else bw.write(String.valueOf(input[i][j]));
					if (j < size - 1) bw.write("\t");
				}
				bw.write("\r\n");
			}
			
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static class BestPath {
		BestPath previous;
		BestPath next;
		int port;
		int cost;
		
		public BestPath() {
			previous = null;
			next = null;
			port = -1;
			cost = 0;
		}
	}
	

}
