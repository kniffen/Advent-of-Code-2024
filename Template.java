public class Template {
  static String[] input = Utils.readFile("template.txt");

  public static String part1() {
    return String.join(", ", input);
  }

  public static String part2() {
    return String.join(" & ", input);
  }
}
