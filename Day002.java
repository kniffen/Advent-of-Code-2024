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

  public static String part2() {
    int validLineCount = 0;

    for (String line : input) {
      int[] levels = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
      int[][] levelVersions = new int[levels.length][levels.length];
      for (int i = 0; i < levels.length; i++) {
        int[] levelVersion = new int[levels.length-1];
        for (int j = 0; j < levels.length; j++) {
          if (j == i) {
            continue;
          }
          levelVersion[j > i ? j - 1 : j] = levels[j];
        }
        levelVersions[i] = levelVersion;
      }

      int[][] allVersions = new int[levels.length+1][levels.length];
      allVersions[0] = levels;
      for (int i = 0; i < levelVersions.length; i++) {
        allVersions[i+1] = levelVersions[i];
      }

      int safeCount = 0;
      for (int[] version : allVersions) {
        boolean isSafeVersion = true;
        int trend = 0;
        for (int i = 1; i < version.length; i++) {
          int level = version[i];
          int prevLevel = version[i - 1];
          int diff = Math.abs(level - prevLevel);

          if (diff < 1 || diff > 3) {
            isSafeVersion = false;
          }
          trend += (level > prevLevel) ? 1 : -1;
        }

        if  (isSafeVersion && Math.abs(trend) == version.length - 1) {
          safeCount++;
        }
      }

      if (safeCount > 0) {
        validLineCount++;
      }
    }

    return String.valueOf(validLineCount);
  }
}