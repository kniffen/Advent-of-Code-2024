import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Collectors;

class Instruction {
  String a;
  String b;
  String o;

  Instruction(String a, String b, String o) {
    this.a = a;
    this.b = b;
    this.o = o;
  }
}

class Day024 {
  static HashMap<String, Integer> values = new HashMap<String, Integer>();
  static HashMap<String, Instruction> instructions = new HashMap<String, Instruction>();

  static String[] testData = {
    "x00: 1",
    "x01: 0",
    "x02: 1",
    "x03: 1",
    "x04: 0",
    "y00: 1",
    "y01: 1",
    "y02: 1",
    "y03: 1",
    "y04: 1",
    "",
    "ntg XOR fgs -> mjb",
    "y02 OR x01 -> tnw",
    "kwq OR kpj -> z05",
    "x00 OR x03 -> fst",
    "tgd XOR rvg -> z01",
    "vdt OR tnw -> bfw",
    "bfw AND frj -> z10",
    "ffh OR nrd -> bqk",
    "y00 AND y03 -> djm",
    "y03 OR y00 -> psh",
    "bqk OR frj -> z08",
    "tnw OR fst -> frj",
    "gnj AND tgd -> z11",
    "bfw XOR mjb -> z00",
    "x03 OR x00 -> vdt",
    "gnj AND wpb -> z02",
    "x04 AND y00 -> kjc",
    "djm OR pbm -> qhw",
    "nrd AND vdt -> hwm",
    "kjc AND fst -> rvg",
    "y04 OR y02 -> fgs",
    "y01 AND x02 -> pbm",
    "ntg OR kjc -> kwq",
    "psh XOR fgs -> tgd",
    "qhw XOR tgd -> z09",
    "pbm OR djm -> kpj",
    "x03 XOR y03 -> ffh",
    "x00 XOR y04 -> ntg",
    "bfw OR bqk -> z06",
    "nrd XOR fgs -> wpb",
    "frj XOR qhw -> z04",
    "bqk OR frj -> z07",
    "y03 OR x01 -> nrd",
    "hwm AND bqk -> z03",
    "tgd XOR rvg -> z12",
    "tnw OR pbm -> gnj",
  };
  static String[] input = Utils.readFile("day024.txt");

  static void parseInput(String[] input) {
    int mode = 0;
    for (String line : input) {
      if (line.isEmpty()) {
        mode++;
        continue;
      }
      if (mode == 0) {
        String[] parts = line.split(": ");
        values.put(parts[0], Integer.parseInt(parts[1]));
      } else {
        String[] parts = line.split(" -> ");
        String[] ops = parts[0].split(" ");
        String a = ops[0];
        String o = ops[1];
        String b = ops[2];
        instructions.put(parts[1].trim(), new Instruction(a, b, o));
      }
    }
  }

  static int getValue(String key) {
    if (values.containsKey(key)) {
      return values.get(key);
    }

    Instruction instruction = instructions.get(key);
    int a = getValue(instruction.a);
    int b = getValue(instruction.b);

    switch (instruction.o) {
      case "AND": return a & b;
      case "OR":  return a | b;
      case "XOR": return a ^ b;
      default:    return 0;
    }
  }

  static BigInteger part1(String[] data) {
    parseInput(data);

    HashMap<String, Integer> results = new HashMap<String, Integer>();
    for (String key : instructions.keySet()) {
      if (!key.startsWith("z")) {
        continue;
      }

      results.put(key, getValue(key));
    }

    String[] keys = results.keySet().toArray(new String[0]);
    Arrays.sort(keys, Collections.reverseOrder());

    String bin = Arrays.stream(keys).map(key -> results.get(key).toString()).collect(Collectors.joining());
    return new BigInteger(bin, 2);
  }

  static void run() {
    System.out.println("\nDay 024");
    System.out.printf("Part 1 control: %s\n", part1(testData));
    System.out.printf("Part 1 answer: %s\n", part1(input));
  }
}