import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Puzzle2048 {

	static long score = 0;

	public static void main(String[] args) {

		int[][] matrix = { { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };
		generateRandomNumber(matrix);

		print(matrix);

		Scanner sc = new Scanner(System.in);
		String move = "Y";
		while (!move.equals("N")) {
			System.out.println("Please enter your move");
			move = sc.nextLine();

			int[][] tempMatrix = copy(matrix);

			switch (move) {
			case "U":
				rotate(matrix);
				rightMove(matrix);
				rotate(matrix);
				rotate(matrix);
				rotate(matrix);
				break;
			case "D":
				rotate(matrix);
				rotate(matrix);
				rotate(matrix);
				rightMove(matrix);
				rotate(matrix);
				break;
			case "L":
				rotate(matrix);
				rotate(matrix);
				rightMove(matrix);

				rotate(matrix);
				rotate(matrix);
				break;
			case "R":
				rightMove(matrix);
				break;
			default:
				break;
			}

			// Check for the equality of matrices after moving the blocks
			if (!isEqual(matrix, tempMatrix)) {
				// Generate Random number only if the original matrix has changed
				generateRandomNumber(matrix);
				System.out.println();
				System.out.println();
			}
			print(matrix);
			System.out.println("Score:"+score);
		}
		System.out.println();
	}

	public static void generateRandomNumber(int[][] matrix) {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if (matrix[i][j] == 0) {
					list.add("" + i + j);
				}
			}
		}
		Random rndm = new Random();
		int rndmNumber = rndm.nextInt(list.size());
		String boxToAddZero = list.get(rndmNumber);

		int[] numbers = { 2, 4 };
		int numberIndex = rndm.nextInt(numbers.length);
		int i = Integer.valueOf("" + boxToAddZero.charAt(0));
		int j = Integer.valueOf("" + boxToAddZero.charAt(1));
		matrix[i][j] = numbers[numberIndex];
	}

	public static boolean isEqual(int[][] matrix, int[][] tempMatrix) {

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if (tempMatrix[i][j] != matrix[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	public static int[][] copy(int[][] matrix) {
		int[][] tempMatrix = new int[4][4];

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				tempMatrix[i][j] = matrix[i][j];
			}
		}

		return tempMatrix;
	}

	public static void print(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static void merge(int start, int end, int[] matrix) {

		matrix[end] = matrix[end] * 2;
		score = score + matrix[end];
		// Shift all the numbers after merging
		while (start > 0) {
			matrix[start] = matrix[start - 1];
			start--;
		}
		matrix[start] = 0;
	}

	public static void fillEmptySpacesAfterMerge(int[] row) {

		int count = row.length - 1;
		while (count >= 0) {
			int end = count;
			int destination = -1;
			boolean flag = true;
			while (end > 0) {
				if (row[end] == 0) {
					if (flag) {
						destination = end;
						flag = false;
					}
					end--;
				} else {
					break;
				}
			}
			if (destination != -1) {
				swap(end, destination, row);
			}
			count--;
		}
	}

	public static void swap(int i, int j, int[] row) {
		int temp = row[i];
		row[i] = row[j];
		row[j] = temp;
	}

	public static void rightMove(int[][] matrix) {

		// Loop through 4 rows
		// initialize start and end

		for (int i = 0; i < matrix.length; i++) {
			int[] row = new int[4];
			row[0] = matrix[i][0];
			row[1] = matrix[i][1];
			row[2] = matrix[i][2];
			row[3] = matrix[i][3];
			int end = row.length - 1;
			int start = end - 1;
			while (end >= 0 && start >= 0 && start < end) {
				if (row[end] == row[start] && row[end] != 0) {
					merge(start, end, row);
					if (row[1] == 0 || row[2] == 0 || row[3] == 0) {
						fillEmptySpacesAfterMerge(row);
					}
					end--;
					start = end - 1;
				} else if (row[start] == 0) {
					while (start >= 0 && row[start] == 0) {
						start--;
					}
				} else if (row[end] == 0) {
					while (end >= 0 && row[end] == 0) {
						end--;
					}
					start = end - 1;
				} else {
					end--;
					start = end - 1;
				}
			}
			if (row[1] == 0 || row[2] == 0 || row[3] == 0) {
				fillEmptySpacesAfterMerge(row);
			}
			matrix[i][0] = row[0];
			matrix[i][1] = row[1];
			matrix[i][2] = row[2];
			matrix[i][3] = row[3];
		}
	}

	public static void rotate(int[][] matrix) {
		for (int i = 0; i <= matrix.length / 2; i++) {
			for (int j = i; j < matrix.length; j++) {
				int temp = matrix[i][j];
				matrix[i][j] = matrix[j][i];
				matrix[j][i] = temp;
			}
		}
		int size = matrix.length - 1;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length / 2; j++) {
				int temp = matrix[i][j];
				matrix[i][j] = matrix[i][size - j];
				matrix[i][size - j] = temp;
			}
		}
	}

}
