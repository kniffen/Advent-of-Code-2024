import java.util.Arrays;

public class Day002 {
  static String[] input = Utils.readFile("day002.txt");

  public static String part1() {
    int validLineCount = 0;

    for (String line : input) {
      int[] levels = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();

      boolean isSafe = true;
      int trend = 0;
      for (int i = 1; i < levels.length; i++) {
        int level = levels[i];
        int prevLevel = levels[i - 1];
        int diff = Math.abs(level - prevLevel);

        if (diff < 1 || diff > 3) {
          isSafe = false;
        }
        trend += (level > prevLevel) ? 1 : -1;
      }

      if  (isSafe && Math.abs(trend) == levels.length - 1) {
        validLineCount++;
      }
    }

    return String.valueOf(validLineCount);
  }
}
