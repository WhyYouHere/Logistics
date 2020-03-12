import java.util.Scanner;

import ilog.concert.IloException;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

import java.io.File;
import java.util.InputMismatchException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Model3 {

	final static int H = 200;
	final static int W = 3000;
	final static int G = 80;
	final static int L = 18000;

	public static void main(String[] args) throws FileNotFoundException, IloException{

		IloCplex cplex = new IloCplex();
		ArrayList<String> input = new ArrayList<String>();
		ArrayList<Integer> stack_id = new ArrayList<Integer>();
		ArrayList<Integer> position = new ArrayList<Integer>();
		ArrayList<Integer> element_id = new ArrayList<Integer>();
		ArrayList<Integer> length = new ArrayList<Integer>();
		ArrayList<Integer> width = new ArrayList<Integer>();

		try {
			
			Scanner scan = new Scanner(new File("C:\\Users\\Damian\\workspace\\QML2\\src\\stapel0522_01.txt"));
			while (scan.hasNextLine()) {
				
				String strLine = scan.nextLine();
				input.add(strLine);
			}
			scan.close();
		} 
		catch (InputMismatchException e1) {
			
			System.out.println(e1.getMessage());
		}


		for (int i = 0; i < input.size() - 1; i++) {
			
			String str = input.get(i);
			String[] conv = str.split(" ");
			stack_id.add(Integer.parseInt(conv[2])); // stack-ID
			position.add(Integer.parseInt(conv[3])); // position in stack
			element_id.add(Integer.parseInt(conv[4])); // element-ID
			length.add(Integer.parseInt(conv[5])); // length
			width.add(Integer.parseInt(conv[6])); // width
		}

		ArrayList<Integer> stacks = new ArrayList<Integer>(); //Finds all unique batches
		int temp = stack_id.get(0);
		stacks.add(temp);
		for (int i = 1; i < stack_id.size(); i++) {

			if (stack_id.get(i)!=temp) {

				temp = stack_id.get(i);
				stacks.add(temp);
			}
		}

		final int K = stacks.size(); //Amount of unique batches
		final int I = width.size();	//General amount of items
		final int J = I; //For interpretation sake the same thing
		final int M = 100; //Arbitrary large number of desks
		final int R = 3; //Number of stacking places

		ArrayList<Integer> numPos = new ArrayList<Integer>(); //Finds the amount of positions per batch
		for (int i = 0; i < K; i++) {

			int counter = 0;
			for (int j = 0; j < I; j++) {

				if (stacks.get(i) == stack_id.get(j)) {

					counter++;
				}
			}
			numPos.add(counter);
		}

		IloNumVar[][][][] a = new IloNumVar[I][J][K][M];
		IloNumVar[][][] x = new IloNumVar[I][J][K];
		IloNumVar[][][] y = new IloNumVar[I][J][K];
		IloNumVar[][][] c = new IloNumVar[K][M][R];
		IloNumVar[][] f = new IloNumVar[J][K];
		IloNumVar[] q = new IloNumVar[M];
		IloNumVar[] p = new IloNumVar[K];
		IloNumVar[] u = new IloNumVar[M];
		IloNumVar[] z = new IloNumVar[K];

		Integer[][] distance = new Integer[I][I]; //Calculates the combined width of every variable i and j
		for (int i = 0; i < I; i++) {
			
			for (int j = 0; j < I; j++) {
				
				distance[i][j] = Math.abs(width.get(i) + width.get(j));
			}
		}

		for (int k = 0; k < K; k++) {

			p[k] = cplex.numVar(0, Integer.MAX_VALUE);
			z[k] = cplex.boolVar();

			for (int m = 0; m < M; m++) {

				for (int r = 0; r < R; r++) {

					c[k][m][r] = cplex.boolVar();
				}
			}
		}

		for (int m = 0; m < M; m++) {

			q[m] = cplex.boolVar();
			u[m] = cplex.numVar(0, 1);

			for (int i = 0; i < I; i++) {

				for (int j = 0; j < J; j++) {

					for (int k = 0; k < K; k++) {

						a[i][j][k][m] = cplex.boolVar();
					}
				}
			}
		}

		for (int j = 0; j < J; j++) {

			for (int k = 0; k < K; k++) {

				f[j][k] = cplex.numVar(0, Integer.MAX_VALUE);
			}
		}
	}
}