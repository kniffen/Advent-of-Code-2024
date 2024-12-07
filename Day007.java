import java.math.BigInteger;
import java.util.Arrays;

class Parsed007Input {
  BigInteger sum;
  int[] values;

  Parsed007Input(BigInteger sum, int[] values) {
      this.sum = sum;
      this.values = values;
  }
}

public class Day007 {
  static String[] testData = new String[] {
    "190: 10 19",
    "3267: 81 40 27",
    "83: 17 5",
    "156: 15 6",
    "7290: 6 8 6 15",
    "161011: 16 10 13",
    "192: 17 8 14",
    "21037: 9 7 18 13",
    "292: 11 6 16 20",
  };
  static String[] input = Utils.readFile("day007.txt");

  static Parsed007Input[] parseInput(String[] data) {
    Parsed007Input[] parsedInputs = new Parsed007Input[data.length];
    for (int i = 0; i < data.length; i++) {
      String[] parts = data[i].split(": ");
      BigInteger sum = new BigInteger(parts[0]);
      int[] values = Arrays.stream(parts[1].split(" ")).mapToInt(Integer::parseInt).toArray();
      Parsed007Input parsedInput = new Parsed007Input(sum, values);
      parsedInputs[i] = parsedInput;
    }

    return parsedInputs;
  }

  static BigInteger calculateExpression(int[] values, int operation) {
    BigInteger result = BigInteger.valueOf(values[0]);
    for (int i = 0; i < values.length-1; i++) {
      BigInteger value = BigInteger.valueOf(values[i+1]);
      if (((operation >> i) & 1) == 1) {
        result = result.add(value);
      } else {
        result = result.multiply(value);
      }
    }

    return result;
  }

  static boolean evaluateCombinations(int[] values, BigInteger target) {
    int operationCount = values.length - 1;
    int maxCombinations = 1 << operationCount;
    for (int i = 0; i < maxCombinations; i++) {
      BigInteger sum = calculateExpression(values, i);
      if (sum.equals(target)) {
        return true;
      }
    }

    return false;
  }

  public static BigInteger part1(String[] data) {
    BigInteger sum = BigInteger.ZERO;

    Parsed007Input[] validInstructions =
      Arrays
        .stream(parseInput(data))
        .filter(parsedInput -> evaluateCombinations(parsedInput.values, parsedInput.sum))
        .toArray(Parsed007Input[]::new);

    for (Parsed007Input validInstruction : validInstructions) {
      sum = sum.add(validInstruction.sum);
    }

     return sum;
  }

  static void run() {
    System.out.println("\nDay 007");
    System.out.printf("Part 1 control: %s\n", part1(testData));
    System.out.printf("Part 1 answer: %s\n", part1(input));
  }
}