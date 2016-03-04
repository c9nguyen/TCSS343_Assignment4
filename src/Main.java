
public class Main {

//	static int n;
//	static int min = 999999999;
//	static int[][] input;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] input = {{0, 2, 3, 7}, {99, 0, 2, 4}, {99, 99, 0, 2}, {99, 99, 99, 0}};
		
		System.out.println(FindBest(input, 0, 4));
	}
	
	public static int FindBest(int[][] input, int srs, int des) {
		int min = 9999999;
		int n = input.length;
		for (int i = srs + 1; i < n; i++) {
			int a = input[srs][i];
			int b = FindBest(input, i, input.length);
			min = Math.min(a + b, min);
		}
		
		
		
		min = Math.min(min, input[srs][input.length - 1]);
		
//		min += input[srs][des-1];
		
		return min;
	}

}
