public class Template {
  static String[] testData = new String[] {};
  static String[] input = Utils.readFile("template.txt");

  static String parseInput(String[] data) {
    return data[0];
  }

  public static int part1(String[] data) {
    return 0;
  }

  static void run() {
    System.out.println("\nDay 000");
    System.out.printf("Part 1 control: %s\n", part1(testData));
    System.out.printf("Part 1 answer: %s\n", part1(input));
  }
}
