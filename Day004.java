public class Day004 {
  static String[] testData = {
    "MMMSXXMASM",
    "MSAMXMSMSA",
    "AMXSXMAAMM",
    "MSAMASMSMX",
    "XMASAMXAMM",
    "XXAMMXXAMA",
    "SMSMSASXSS",
    "SAXAMASAAA",
    "MAMMMXMMMM",
    "MXMXAXMASX",
  };
  static String[] input = Utils.readFile("day004.txt");

  static String[] getSubstrings(int x, int y, String[] data, int size) {
    int rows = data.length;
    int columns = data[0].length();

    char[] horizontal = new char[size];
    char[] vertical = new char[size];
    char[] diagonal1 = new char[size];
    char[] diagonal2 = new char[size];

    for (int i = 0; i < size; i++) {
      int rightIndex = x + i;
      int leftIndex = x - i;
      int bottomIndex = y + i;

      if (rightIndex < columns) {
        horizontal[i] = data[y].charAt(rightIndex);
      }

      if (bottomIndex < rows) {
        vertical[i] = data[y + i].charAt(x);
      }

      if (rightIndex < columns && bottomIndex < rows) {
        diagonal1[i] = data[bottomIndex].charAt(rightIndex);
      }

      if (bottomIndex < rows && leftIndex >= 0) {
        diagonal2[i] = data[bottomIndex].charAt(leftIndex);
      }
    }

    return new String[] {
      String.valueOf(horizontal),
      String.valueOf(vertical),
      String.valueOf(diagonal1),
      String.valueOf(diagonal2),
    };
  }

  static int part1(String[] data) {
    int rows = data.length;
    int columns = data[0].length();
    int count = 0;

    for (int y = 0; y < rows; y++) {
      for (int x = 0; x < columns; x++) {
        String[] grid = getSubstrings(x, y, data, 4);
        for (String line : grid) {
          if (line.equals("XMAS") || line.equals("SAMX")) {
            count++;
          }
        }
      }
    }

    return count;
  }

  public static void run() {
    System.out.println("\nDay 004");
    System.out.printf("Part 1 control: %s\n", part1(testData));
    System.out.printf("Part 1 answer: %s\n", part1(input));
  }
}
