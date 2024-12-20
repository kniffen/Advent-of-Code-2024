import java.util.Arrays;
import java.util.Scanner;

class Robot {
  int x, y;
  int vx, vy;

  Robot(int x, int y, int vx, int vy) {
    this.x = x;
    this.y = y;
    this.vx = vx;
    this.vy = vy;
  }

  void move(int rows, int columns) {
    x += vx;
    y += vy;

    if (x < 0) x += columns;
    if (x >= columns) x -= columns;
    if (y < 0) y += rows;
    if (y >= rows) y -= rows;
  }
}

public class Day014 {
  static String[] testData = {
    "p=0,4 v=3,-3",
    "p=6,3 v=-1,-3",
    "p=10,3 v=-1,2",
    "p=2,0 v=2,-1",
    "p=0,0 v=1,3",
    "p=3,0 v=-2,-2",
    "p=7,6 v=-1,-3",
    "p=3,0 v=-1,-2",
    "p=9,3 v=2,3",
    "p=7,3 v=-1,2",
    "p=2,4 v=2,-3",
    "p=9,5 v=-3,-3",
  };
  static String[] input = Utils.readFile("day014.txt");

  static Robot[] parseData(String[] data) {
    Robot[] robots = new Robot[data.length];
    for (int i = 0; i < data.length; i++) {
      String[] parts = data[i].split("[^0-9-]+");
      robots[i] = new Robot(
        Integer.parseInt(parts[1]),
        Integer.parseInt(parts[2]),
        Integer.parseInt(parts[3]),
        Integer.parseInt(parts[4])
      );
    }

    return robots;
  }

  static void printGrid(Robot[] robots, int rows, int columns) {
    for (int y = 0; y < rows; y++) {
      for (int x = 0; x < columns; x++) {
        boolean foundRobot = false;
        for (Robot robot : robots) {
          if (robot.x == x && robot.y == y) {
            System.out.print("#");
            foundRobot = true;
            break;
          }
        }
        if (!foundRobot) {
          System.out.print(".");
        }
      }
      System.out.println();
    }
  }

  static int part1(String[] data, int rows, int columns) {
    Robot[] robots = parseData(data);
    int seconds = 100;

    for (int i = 0; i < seconds; i++) {
      for (Robot robot : robots) {
        robot.move(rows, columns);
      }
    }

    int[] quadrants = {0,0,0,0};
    for (Robot robot : robots) {
      double centerX = Math.floor(columns / 2);
      double centerY = Math.floor(rows / 2);

      if (robot.x < centerX && robot.y < centerY) { // top left
        quadrants[0]++;
      } else if (robot.x > centerX && robot.y < centerY) { // top right
        quadrants[1]++;
      } else if (robot.x > centerX && robot.y > centerY) { // bottom right
        quadrants[2]++;
      } else if (robot.x < centerX && robot.y > centerY) { // bottom left
        quadrants[3]++;
      }
    }

    int sum = Arrays.stream(quadrants).reduce(1, (a, b) -> a * b);
    return sum;
  }

  static void part2(String[] data, int rows, int columns) {
    Robot[] robots = parseData(data);

    int maxSeconds = 10_000;
    for (int second = 1; second < maxSeconds; second++) {
      for (Robot robot : robots) {
        robot.move(rows, columns);
      }

      int clusters = 0;
      for (int y = 0; y < rows-2; y++) {
        for (int x = 0; x < columns-2; x++) {
          int foundRobots = 0;
          for (Robot robot : robots) {
            if (
              (robot.x == x   && robot.y == y) ||
              (robot.x == x+1 && robot.y == y) ||
              (robot.x == x+2 && robot.y == y) ||
              (robot.x == x   && robot.y == y+1) ||
              (robot.x == x+1 && robot.y == y+1) ||
              (robot.x == x+2 && robot.y == y+1) ||
              (robot.x == x   && robot.y == y+2) ||
              (robot.x == x+1 && robot.y == y+2) ||
              (robot.x == x+2 && robot.y == y+2)
            ) {
              foundRobots++;
            }
          }
          if (foundRobots >= 9) {
            clusters++;
          }
        }
      }

      if (clusters > 0) {
        printGrid(robots, rows, columns);
        System.out.printf("Potential tree found at %s seconds\n", second);
        System.out.println("Press enter to continue");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        System.out.println("Continuing...");
      }

    }
  }

  static void run() {
    System.out.println("\nDay 014");
    System.out.printf("Part 1 control: %s\n", part1(testData, 7, 11));
    System.out.printf("Part 1 answer: %s\n", part1(input, 103,101));
    part2(input, 103, 101);
  }
}
