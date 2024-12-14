class Button {
  int x, y;
  Button(int x, int y) {
    this.x = x;
    this.y = y;
  }
}

class Price {
  int x, y;
  Price(int x, int y) {
    this.x = x;
    this.y = y;
  }
}

class Machine {
  Button a;
  Button b;
  Price price;

  Machine(Button a, Button b, Price price) {
    this.a = a;
    this.b = b;
    this.price = price;
  }
}

public class Day013 {
  static String[] testData = {
    "Button A: X+94, Y+34",
    "Button B: X+22, Y+67",
    "Prize: X=8400, Y=5400",
    "",
    "Button A: X+26, Y+66",
    "Button B: X+67, Y+21",
    "Prize: X=12748, Y=12176",
    "",
    "Button A: X+17, Y+86",
    "Button B: X+84, Y+37",
    "Prize: X=7870, Y=6450",
    "",
    "Button A: X+69, Y+23",
    "Button B: X+27, Y+71",
    "Prize: X=18641, Y=10279",
  };
  static String[] input = Utils.readFile("day013.txt");

  static Machine[] parseInput(String[] data) {
    Machine[] machines = new Machine[(data.length / 4)+1];
    for (int i = 0; i < data.length; i += 4) {
      String[] parts = data[i].split("[^0-9-]+");
      Button a = new Button(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));

      parts = data[i + 1].split("[^0-9-]+");
      Button b = new Button(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));

      parts = data[i + 2].split("[^0-9-]+");
      Price price = new Price(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));

      Machine machine = new Machine(a, b, price);
      machines[i / 4] = machine;
    }

    return machines;
  }

  static int part1(String[] data) {
    Machine[] machines = parseInput(data);
    int score = 0;
    int maxIterations = 100;
    for (Machine machine : machines) {
      int machineScore = Integer.MAX_VALUE;
      for (int i = 0; i < maxIterations; i++) {
        for (int j = 0; j < maxIterations; j++) {
          if (
            (machine.a.x * i + machine.b.x * j == machine.price.x) &&
            (machine.a.y * i + machine.b.y * j == machine.price.y)
          ) {
            machineScore = Math.min(machineScore, i * 3 + j);
          }
        }
      }
      if (machineScore < Integer.MAX_VALUE) {
        score += machineScore;
      }
    }

    return score;
  }

  static long part2(String[] data) {
    Machine[] machines = parseInput(data);
    long score = 0;
    for (Machine machine : machines) {
      long priceX = machine.price.x + 10000000000000L;
      long priceY = machine.price.y + 10000000000000L;
      double a = (double) (priceX * machine.b.y - priceY * machine.b.x) / (machine.a.x * machine.b.y - machine.a.y * machine.b.x);
      double b = (double) (priceX - machine.a.x * a) / machine.b.x;

      if (a % 1 == 0 && b % 1 == 0) {
        score += a * 3 + b;
      }
    }
    return score;
  }

  static void run() {
    System.out.println("\nDay 013");
    System.out.printf("Part 1 control: %s\n", part1(testData));
    System.out.printf("Part 1 answer: %s\n", part1(input));
    System.out.printf("Part 2 answer: %s\n", part2(input));
  }
}
