import java.util.ArrayList;
import java.util.Arrays;

public class Day011 {
  static String testData = "125 17";
  static String input = Utils.readFile("day011.txt")[0];

  static ArrayList<String> parseSequence(ArrayList<String> sequence) {
    ArrayList<String> workingSequence = new ArrayList<String>();
    for (int i = 0; i < sequence.size(); i++) {
      String str = sequence.get(i);
      long number = Long.parseLong(str);
      int length = str.length();

      if (number == 0) {
        workingSequence.add("1");
      } else if (length % 2 == 0) {
        length = (int) Math.pow(10, length / 2);
        long a = number / length;
        long b = number % length;
        workingSequence.add(String.valueOf(a));
        workingSequence.add(String.valueOf(b));
      } else {
        workingSequence.add(String.valueOf(number * 2024));
      }
    }

    return workingSequence;
  }

  static int part1(String data) {
    ArrayList<String> sequence = new ArrayList<String>(Arrays.asList(data.split(" ")));

    int blinks = 25;
    for (int i = 0; i < blinks; i++) {
      sequence = parseSequence(sequence);
    }

    return sequence.size();
  }

  static void run() {
    System.out.println("\nDay 011");
    System.out.printf("Part 1 control: %s\n", part1(testData));
    System.out.printf("Part 1 answer: %s\n", part1(input));
  }
}
