import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

class ParsedInput {
  int[][] rules;
  int[][] sequences;

  ParsedInput(int[][] rules, int[][] sequences) {
    this.rules = rules;
    this.sequences = sequences;
  }
}

public class Day005 {
  static String[] testData = new String[] {
    "47|53",
    "97|13",
    "97|61",
    "97|47",
    "75|29",
    "61|13",
    "75|53",
    "29|13",
    "97|29",
    "53|29",
    "61|53",
    "97|53",
    "61|29",
    "47|13",
    "75|47",
    "97|75",
    "47|61",
    "75|61",
    "47|29",
    "75|13",
    "53|13",
    "",
    "75,47,61,53,29",
    "97,61,53,29,13",
    "75,29,13",
    "75,97,47,61,53",
    "61,13,29",
    "97,13,75,29,47",
  };
  static String[] input = Utils.readFile("day005.txt");

  static ParsedInput parseInput(String[] data) {
    ArrayList<int[]> rules = new ArrayList<int[]>();
    ArrayList<int[]> sequences = new ArrayList<int[]>();

    int mode = 0;
    for (String line : data) {
      if (line.isEmpty()) {
        mode = 1;
        continue;
      }

      if (mode == 0) {
        int[] rule = Arrays.stream(line.split("\\|")).mapToInt(Integer::parseInt).toArray();
        rules.add(rule);
      } else {
        int[] sequence = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
        sequences.add(sequence);
      }
    }

    return new ParsedInput(
      rules.toArray(new int[rules.size()][]),
      sequences.toArray(new int[sequences.size()][])
    );
  }

  static int[] getIndexes(int[] sequence, int[] rule) {
    int a = rule[0];
    int b = rule[1];
    int aIndex = IntStream.range(0, sequence.length).filter(i -> sequence[i] == a).findFirst().orElse(-1);
    int bIndex = IntStream.range(0, sequence.length).filter(i -> sequence[i] == b).findFirst().orElse(-1);

    return new int[] { aIndex, bIndex };
  }

  static int part1(String[] data) {
    ParsedInput parsedInput = parseInput(data);
    int sum = 0;

    for (int[] sequence : parsedInput.sequences) {
      boolean isInCorrectOrder = true;
      for (int[] rule : parsedInput.rules) {
        int[] indexes = getIndexes(sequence, rule);

        if (indexes[0] == -1 || indexes[1] == -1) {
          continue;
        }

        if (indexes[0] > indexes[1]) {
          isInCorrectOrder = false;
          break;
        }
      }

      if (isInCorrectOrder) {
        int middleNum = sequence[Math.abs((sequence.length - 1) / 2)];
        sum += middleNum;
      }
    }

    return sum;
  }

  static int part2(String[] data) {
    ParsedInput parsedInput = parseInput(data);
    int sum = 0;

    for (int[] sequence : parsedInput.sequences) {
      boolean wasUnordered = false;
      for (int i = 0; i < parsedInput.rules.length - 1; i++) {
        int[] rule = parsedInput.rules[i];
        int[] indexes = getIndexes(sequence, rule);

        if (indexes[0] == -1 || indexes[1] == -1) {
          continue;
        }

        while (indexes[0] > indexes[1]) {
          wasUnordered = true;

          int temp = sequence[indexes[0]];
          sequence[indexes[0]] = sequence[indexes[1]];
          sequence[indexes[1]] = temp;

          indexes = getIndexes(sequence, rule);
          i = 0;
        }
      }

      if (wasUnordered) {
        int middleNum = sequence[Math.abs((sequence.length - 1) / 2)];
        sum += middleNum;
      }
    }

    return sum;
  }

  static void run() {
    System.out.println("\nDay 005");
    System.out.printf("Part 1 control: %s\n", part1(testData));
    System.out.printf("Part 1 answer: %s\n", part1(input));
    System.out.printf("Part 2 control: %s\n", part2(testData));
    System.out.printf("Part 2 answer: %s\n", part2(input));
  }
}
