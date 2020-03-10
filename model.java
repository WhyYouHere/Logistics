import java.util.Scanner;
import java.io.File;
import java.util.InputMismatchException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class DataScanner {
	
	public static void main(String[] args) throws FileNotFoundException{
		ArrayList<String> input = new ArrayList<String>();
		
		try {
			Scanner scan = new Scanner(new File("C:\\Users\\454504at\\eclipse-workspace\\Data Dycore\\stapel0522_01.txt"));
			while (scan.hasNextLine()) {
				String strLine = scan.nextLine();
				input.add(strLine);
			}
			scan.close();
		} catch (InputMismatchException e1) {
			System.out.println(e1.getMessage());
		}
		
		int[][] data = new int[input.size()][5];
		
		for (int i = 0; i < input.size() - 1; i++) {
			String str = input.get(i);
			String[] conv = str.split(" ");
			data[i][0] = Integer.parseInt(conv[2]); // stack-ID
			data[i][1] = Integer.parseInt(conv[3]); // position in stack
			data[i][2] = Integer.parseInt(conv[4]); // element-ID
			data[i][3] = Integer.parseInt(conv[5]); // length
			data[i][4] = Integer.parseInt(conv[6]); // width
		}
		
        System.out.println(Arrays.deepToString(data));
	}
}
