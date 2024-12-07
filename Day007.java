import java.util.Arrays;

class Parsed007Input {
  long sum;
  int[] values;

  Parsed007Input(long sum, int[] values) {
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
      long sum = Long.parseLong(parts[0]);
      int[] values = Arrays.stream(parts[1].split(" ")).mapToInt(Integer::parseInt).toArray();
      Parsed007Input parsedInput = new Parsed007Input(sum, values);
      parsedInputs[i] = parsedInput;
    }

    return parsedInputs;
  }

  static int getMaximumCombinations(int length, int operators) {
    int maxCombinations = (int) Math.pow(operators, length - 1);
    return maxCombinations;
  }

  static long calculateExpression(int[] values, int operation) {
    long result = values[0];
    for (int i = 0; i < values.length-1; i++) {
      long value = values[i+1];
      if (((operation >> i) & 1) == 1) {
        result += value;
      } else {
        result *= value;
      }
    }

    return result;
  }

  static long calculateExpressionPart2(int[] values, int operation) {
    long result = values[0];
    int j = operation;
    for (int i = 0; i < values.length-1; i++) {
      long value = values[i+1];
      switch (j % 3) {
        case 0: {
          result += value;
          break;
        }
        case 1: {
          result *= value;
          break;
        }
        case 2: {
          String combined = result + "" + value;
          result = Long.parseLong(combined);
          break;
        }
      }
      j /= 3;
    }
    return result;
  }

  static boolean evaluateCombinations(int[] values, int maxCombinations, long target, boolean combine) {
    for (int i = 0; i < maxCombinations; i++) {
      long sum = combine ? calculateExpressionPart2(values, i) : calculateExpression(values, i);
      if (sum == target) {
        return true;
      }
    }

    return false;
  }

  public static long part1(String[] data) {
    long sum = 0;

    Parsed007Input[] validInstructions =
      Arrays
        .stream(parseInput(data))
        .filter(parsedInput -> evaluateCombinations(parsedInput.values, getMaximumCombinations(parsedInput.values.length, 2), parsedInput.sum, false))
        .toArray(Parsed007Input[]::new);

    for (Parsed007Input validInstruction : validInstructions) {
      sum = sum + validInstruction.sum;
    }

     return sum;
  }

  public static long part2(String[] data) {
    long sum = 0;

    Parsed007Input[] validInstructions =
      Arrays
        .stream(parseInput(data))
        .filter(parsedInput -> {
          int maxCombinations = getMaximumCombinations(parsedInput.values.length, 2);
          boolean isValid = evaluateCombinations(parsedInput.values, maxCombinations, parsedInput.sum, false);
          if (isValid) {
            return true;
          }

          maxCombinations = getMaximumCombinations(parsedInput.values.length, 3);
          isValid = evaluateCombinations(parsedInput.values, maxCombinations, parsedInput.sum, true);
          return isValid;
        })
        .toArray(Parsed007Input[]::new);

    for (Parsed007Input validInstruction : validInstructions) {
      sum = sum + validInstruction.sum;
    }

    return sum;
  }

  static void run() {
    System.out.println("\nDay 007");
    System.out.printf("Part 1 control: %s\n", part1(testData));
    System.out.printf("Part 1 answer: %s\n", part1(input));
    System.out.printf("Part 2 control: %s\n", part2(testData));
    System.out.printf("Part 2 answer: %s\n", part2(input));

  }
}