import java.util.Arrays;

public class Day001 {
  static String[] input = Utils.readFile("day001.txt");

  private static final String separator = "   ";

  private static int[][] parseInput(String[] input) {
    int rowCount = input.length;
    int[] leftNumbers = new int[rowCount];
    int[] rightNumbers = new int[rowCount];

    for (int i = 0; i < rowCount; i++) {
      String[] numbers = input[i].split(separator);
      leftNumbers[i] = Integer.parseInt(numbers[0]);
      rightNumbers[i] = Integer.parseInt(numbers[1]);
    }

    return new int[][] {leftNumbers, rightNumbers};
  }

  public static String part1() {
    int rowCount = input.length;
    int[][] columns = parseInput(input);
    int[] leftNumbers = columns[0];
    int[] rightNumbers = columns[1];

    Arrays.sort(leftNumbers);
    Arrays.sort(rightNumbers);

    int sum = 0;
    for (int i = 0; i < rowCount; i++) {
      sum += Math.abs(leftNumbers[i] - rightNumbers[i]);
    }

    return String.valueOf(sum);
  }

  public static String part2() {
    int[][] columns = parseInput(input);
    int[] leftNumbers = columns[0];
    int[] rightNumbers = columns[1];

    int sum = 0;
    for (int i = 0; i < leftNumbers.length; i++) {
      int count = 0;
      for (int j = 0; j < rightNumbers.length; j++) {
        if (leftNumbers[i] == rightNumbers[j]) {
          count++;
        }
      }
      sum += leftNumbers[i] * count;
    }

    return String.valueOf(sum);
  }
}
