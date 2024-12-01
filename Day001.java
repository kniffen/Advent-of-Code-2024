import java.util.Arrays;

public class Day001 {
  static String[] input = Utils.readFile("day001.txt");

  private static final String separator = "   ";

  public static String part1() {
    int rowCount = input.length;
    int[] leftNumbers = new int[rowCount];
    int[] rightNumbers = new int[rowCount];

    for (int i = 0; i < rowCount; i++) {
      String[] numbers = input[i].split(separator);
      leftNumbers[i] = Integer.parseInt(numbers[0]);
      rightNumbers[i] = Integer.parseInt(numbers[1]);
    }

    Arrays.sort(leftNumbers);
    Arrays.sort(rightNumbers);

    int sum = 0;
    for (int i = 0; i < rowCount; i++) {
      sum += Math.abs(leftNumbers[i] - rightNumbers[i]);
    }

    return String.valueOf(sum);
  }
}
