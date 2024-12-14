import java.util.ArrayList;
import java.util.HashSet;

public class Day012 {
  static String[] testData = {
    "RRRRIICCFF",
    "RRRRIICCCF",
    "VVRRRCCFFF",
    "VVRCCCJFFF",
    "VVVVCJJCFE",
    "VVIVCCJJEE",
    "VVIIICJJEE",
    "MIIIIIJJEE",
    "MIIISIJEEE",
    "MMMISSJEEE",
  };
  static String[] input = Utils.readFile("day012.txt");

  static void mapRegion(int x, int y, char c, int rows, int columns, HashSet<Integer> region, String[] data) {
    int index = y * columns + x;
    if (region.contains(index)) return;

    region.add(index);
    if (y > 0         && data[y-1].charAt(x) == c) { mapRegion(x,   y-1, c, rows, columns, region, data); }
    if (x > 0         && data[y].charAt(x-1) == c) { mapRegion(x-1, y, c,   rows, columns, region, data); }
    if (y < rows-1    && data[y+1].charAt(x) == c) { mapRegion(x,   y+1, c, rows, columns, region, data); }
    if (x < columns-1 && data[y].charAt(x+1) == c) { mapRegion(x+1, y, c,   rows, columns, region, data); }
  }

  static ArrayList<HashSet<Integer>> parseInput(String[] data) {
    ArrayList<HashSet<Integer>> regions = new ArrayList<HashSet<Integer>>();

    int rows = data.length;
    int columns = data[0].length();
    for (int y = 0; y < rows; y++) {
      for (int x = 0; x < columns; x++) {
        int index = y * columns + x;
        char c = data[y].charAt(x);

        boolean alreadyMapped = false;
        for (HashSet<Integer> region : regions) {
          if (region.contains(index)) {
            alreadyMapped = true;
            break;
          }
        }
        if (alreadyMapped) continue;

        HashSet<Integer> region = new HashSet<Integer>();
        mapRegion(x, y, c, rows, columns, region, data);
        regions.add(region);
      }
    }
    return regions;
  }

  static int part1(String[] data) {
    ArrayList<HashSet<Integer>> regions = parseInput(data);

    int rows = data.length;
    int columns = data[0].length();
    int totalPrice = 0;
    for (HashSet<Integer> region : regions) {
      int area = region.size();
      int perimeter = 0;
      char c = data[region.iterator().next() / columns].charAt(region.iterator().next() % columns);
      for (int index : region) {
        int x = index % columns;
        int y = index / columns;
        if (y == 0         || data[y-1].charAt(x) != c) { perimeter++; }
        if (x == columns-1 || data[y].charAt(x+1) != c) { perimeter++; }
        if (y == rows-1    || data[y+1].charAt(x) != c) { perimeter++; }
        if (x == 0         || data[y].charAt(x-1) != c) { perimeter++; }
      }
      totalPrice += area * perimeter;
    }

    return totalPrice;
  }

  static void run() {
    System.out.println("\nDay 012");
    System.out.printf("Part 1 control: %s\n", part1(testData));
    System.out.printf("Part 1 answer: %s\n", part1(input));
  }
}
