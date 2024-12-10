import java.util.ArrayList;
import java.util.HashSet;

class Map{
  int[][] map;
  int rows;
  int columns;

  Map(String[] data){
    this.rows = data.length;
    this.columns = data[0].length();

    this.map = new int[rows][columns];
    for (int y = 0; y < rows; y++){
      for (int x = 0; x < columns; x++){
        char c = data[y].charAt(x);
        this.map[y][x] = Character.getNumericValue(c);
      }
    }
  }

  int get(int x, int y){
    if (x < 0 || x >= columns || y < 0 || y >= rows){
      return -1;
    }
    return map[y][x];
  }
}

class Coord {
  int x;
  int y;
  int value;

  Coord(int x, int y, int value){
    this.x = x;
    this.y = y;
    this.value = value;
  }
}

class Day010 {
  static String[] testData = {
    "89010123",
    "78121874",
    "87430965",
    "96549874",
    "45678903",
    "32019012",
    "01329801",
    "10456732",
  };
  static String[] input = Utils.readFile("day010.txt");

  static void trace(Map map, int n, int x, int y, ArrayList<Coord> path) {
    if (x < 0 || x >= map.columns || y < 0 || y >= map.rows) {
      return;
    }

    path.add(new Coord(x, y, n));
    if (n == 0) {
      return;
    }

    int top    = map.get(x,   y-1);
    int right  = map.get(x+1, y);
    int bottom = map.get(x,   y+1);
    int left   = map.get(x-1, y);

    if (top    == n-1) { trace(map, top,    x,   y-1, path);}
    if (right  == n-1) { trace(map, right,  x+1, y,   path);}
    if (bottom == n-1) { trace(map, bottom, x,   y+1, path);}
    if (left   == n-1) { trace(map, left,   x-1, y,   path);}
  }

  static  ArrayList<ArrayList<Coord>> getPaths(Map map ) {
    ArrayList<ArrayList<Coord>> paths = new ArrayList<ArrayList<Coord>>();

    for (int y = 0; y < map.rows; y++){
      for (int x = 0; x < map.columns; x++){
        int n = map.get(x, y);
        if (n == 9) {
          ArrayList<Coord> path = new ArrayList<Coord>();
          trace(map, n, x, y, path);
          paths.add(path);
        }
      }
    }

    return paths;
  }

  static int part1(String[] data) {
    Map map = new Map(data);
    ArrayList<ArrayList<Coord>> paths = getPaths(map);

    int score = 0;
    for (ArrayList<Coord> path : paths){
      HashSet<Integer> indexes = new HashSet<Integer>();
      for (Coord coord : path){
        if (coord.value == 0) {
          int index = coord.x + coord.y * map.columns;
          indexes.add(index);
        }
      }
      score += indexes.size();
    }
    return score;
  }

  static int part2(String[] data) {
    Map map = new Map(data);
    ArrayList<ArrayList<Coord>> paths = getPaths(map);

    int sum = 0;
    for (ArrayList<Coord> path : paths){
      for (Coord coord : path){
        if (coord.value == 0) {
          sum++;
        }
      }
    }

    return sum;
  }

  static void run() {
    System.out.println("\nDay 010");
    System.out.printf("Part 1 control: %s\n", part1(testData));
    System.out.printf("Part 1 answer: %s\n", part1(input));
    System.out.printf("Part 2 control: %s\n", part2(testData));
    System.out.printf("Part 2 answer: %s\n", part2(input));
  }

}
