import java.util.ArrayList;
import java.util.HashMap;

class Node {
  int x;
  int y;

  Node(int x, int y) {
    this.x = x;
    this.y = y;
  }
}

public class Day008 {
  static String[] testData = new String[] {
    "............",
    "........0...",
    ".....0......",
    ".......0....",
    "....0.......",
    "......A.....",
    "............",
    "............",
    "........A...",
    ".........A..",
    "............",
    "............",
  };
  static String[] input = Utils.readFile("day008.txt");

  static HashMap<Character, ArrayList<Node>> parseInput(String[] data, int rows, int columns) {
    HashMap<Character, ArrayList<Node>> map = new HashMap<Character, ArrayList<Node>>();
    for (int y = 0; y < rows; y++) {
      for (int x = 0; x < columns; x++) {
        char c = data[y].charAt(x);
        if (c == '.') {
          continue;
        }

        ArrayList<Node> nodes = map.get(c);
        if (nodes == null) {
          nodes = new ArrayList<Node>();
        }

        Node node = new Node(x, y);
        nodes.add(node);
        map.put(c, nodes);
      }
    }

    return map;
  }

  static Node[] findAdjacentNodes(Node node1, Node node2) {
    int distX = node2.x - node1.x;
    int distY = node2.y - node1.y;
    Node node3 = new Node(node1.x - distX, node1.y - distY);
    Node node4 = new Node(node2.x + distX, node2.y + distY);
    return new Node[] {node3, node4};
  }

  static ArrayList<Node> findAntiNodes(HashMap<Character, ArrayList<Node>> map) {
    ArrayList<Node> antiNodes = new ArrayList<Node>();
    for (char key : map.keySet()) {
      ArrayList<Node> nodes = map.get(key);
      for (int i = 0; i < nodes.size() - 1; i++) {
        Node node1 = nodes.get(i);
        for (int j = i + 1; j < nodes.size(); j++) {
          Node node2 = nodes.get(j);
          Node[] adjacentNodes = findAdjacentNodes(node1, node2);
          for (Node node : adjacentNodes) {
            antiNodes.add(node);
          }
        }
      }

    }

    return antiNodes;
  }

  static ArrayList<Node> findAntiNodesPart2(HashMap<Character, ArrayList<Node>> map, int rows, int columns) {
    ArrayList<Node> antiNodes = new ArrayList<Node>();
    for (char key : map.keySet()) {
      ArrayList<Node> nodes = map.get(key);
      for (int i = 0; i < nodes.size() - 1; i++) {
        Node node1 = nodes.get(i);
        for (int j = i + 1; j < nodes.size(); j++) {
          Node node2 = nodes.get(j);

          antiNodes.add(node1);
          antiNodes.add(node2);


          int distX = node2.x - node1.x;
          int distY = node2.y - node1.y;
          int x1 = node1.x - distX;
          int y1 = node1.y - distY;
          int x2 = node2.x + distX;
          int y2 = node2.y + distY;

          while (
            (x1 >= 0 && x1 < columns && y1 >= 0 && y1 < rows) ||
            (x2 >= 0 && x2 < columns && y2 >= 0 && y2 < rows)
          ) {
            Node node3 = new Node(x1, y1);
            Node node4 = new Node(x2, y2);
            antiNodes.add(node3);
            antiNodes.add(node4);
            x1 -= distX;
            y1 -= distY;
            x2 += distX;
            y2 += distY;
          }
        }
      }

    }

    return antiNodes;
  }

  static void cleanupAnyNodes(ArrayList<Node> antiNodes, int rows, int columns) {
    // Remove anti-nodes that are outside the grid
    antiNodes.removeIf(node -> node.x < 0 || node.x >= columns || node.y < 0 || node.y >= rows);

    // Remove duplicate anti nodes
    for (int i = 0; i < antiNodes.size() - 1; i++) {
      Node node1 = antiNodes.get(i);
      for (int j = i + 1; j < antiNodes.size(); j++) {
        Node node2 = antiNodes.get(j);
        if (node1.x == node2.x && node1.y == node2.y) {
          antiNodes.remove(j);
          j--;
        }
      }
    }
  }

  public static int part1(String[] data) {
    int rows = data.length;
    int columns = data[0].length();
    HashMap<Character, ArrayList<Node>> map = parseInput(data, rows, columns);
    ArrayList<Node> antiNodes = findAntiNodes(map);
    cleanupAnyNodes(antiNodes, rows, columns);

    return antiNodes.size();
  }

  public static int part2(String[] data) {
    int rows = data.length;
    int columns = data[0].length();
    HashMap<Character, ArrayList<Node>> map = parseInput(data, rows, columns);
    ArrayList<Node> antiNodes = findAntiNodesPart2(map, rows, columns);
    cleanupAnyNodes(antiNodes, rows, columns);

    return antiNodes.size();
  }

  static void run() {
    System.out.println("\nDay 008");
    System.out.printf("Part 1 control: %s\n", part1(testData));
    System.out.printf("Part 1 answer: %s\n", part1(input));
    System.out.printf("Part 2 control: %s\n", part2(testData));
    System.out.printf("Part 2 answer: %s\n", part2(input));
  }
}