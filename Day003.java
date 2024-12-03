import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day003 {
  static String testData = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))";
  static String testData2 = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))";
  static String input = String.join("", Utils.readFile("day003.txt"));

  public static int part1(String data) {
    String regex = "mul\\(\\d+,\\d+\\)";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(data);

    int sum = 0;
    while (matcher.find()) {
      String[] nums = matcher.group().split("\\D+");
      int numSum = 1;
      for (String num: nums) {
        if (!num.isEmpty()) {
          numSum *= Integer.parseInt(num);
        }
      }
      sum += numSum;
    }

    return sum;
  }

  public static int part2(String data) {
    String regex = "(?<=^|do\\(\\))(.*?)(?=don't\\(\\)|$)";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(data);

    int sum = 0;
    while (matcher.find()) {
      int groupSum = part1(matcher.group().toString());
      sum += groupSum;
    }

    return sum;
  }

  public static void run() {
    System.out.println("\nDay 003");
    System.out.printf("Part 1 control: %s\n", part1(testData));
    System.out.printf("Part 1 answer: %s\n", part1(input));
    System.out.printf("Part 2 control: %s\n", part2(testData2));
    System.out.printf("Part 2 answer: %s\n", part2(input));
  }
}
