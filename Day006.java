import java.util.Arrays;

class Tile {
  int index;
  boolean visited;
  boolean isSolid;

  Tile(int x, int y, int columns, char c) {
    this.index = x + y * columns;
    this.visited = false;
    this.isSolid = c == '#';
  }
}

class Position {
  int x;
  int y;
  int direction;

  Position(int x, int y) {
    this.x = x;
    this.y = y;
    this.direction = 0;
  }
}

public class Day006 {
  static String[] testData = new String[] {
    "....#.....",
    ".........#",
    "..........",
    "..#.......",
    ".......#..",
    "..........",
    ".#..^.....",
    "........#.",
    "#.........",
    "......#...",
  };
  static String[] input = Utils.readFile("day006.txt");

  static int part1(String[] data) {
    Tile[] tiles = new Tile[data.length * data[0].length()];
    int columns = data[0].length();
    int rows = data.length;
    for (int y = 0; y < rows; y++) {
      for (int x = 0; x < columns; x++) {
        tiles[x + y * columns] = new Tile(x, y, columns, data[y].charAt(x));
      }
    }

    Position guardPosition = new Position(0,0);
    for (int y = 0; y < rows; y++) {
      for (int x = 0; x < columns; x++) {
        if (data[y].charAt(x) == '^') {
          guardPosition.x = x;
          guardPosition.y = y;
          int index = x + y * columns;
          tiles[index].visited = true;
          break;
        }
      }
    }

    while (guardPosition.x >= 0 && guardPosition.x < columns && guardPosition.y >= 0 && guardPosition.y < rows) {
      int index = guardPosition.x + guardPosition.y * columns;

      if (index < 0 || index >= tiles.length) {
        break;
      }

      tiles[index].visited = true;


      int nextIndex = index;
      do {
        int x = guardPosition.x;
        int y = guardPosition.y;

        switch (guardPosition.direction) {
          case 0: { y--; break; }
          case 1: { x++; break; }
          case 2: { y++; break; }
          case 3: { x--; break; }
        }
        nextIndex = x + y * columns;
        if (nextIndex < 0 || nextIndex >= tiles.length) {
          guardPosition.x = x;
          guardPosition.y = y;
          break;
        }

        if (tiles[nextIndex].isSolid) {
          int nextDir = guardPosition.direction + 1;
          guardPosition.direction = nextDir > 3 ? 0 : nextDir;
        } else {
          guardPosition.x = x;
          guardPosition.y = y;
        }
      } while (tiles[nextIndex].isSolid);
    }

    int tilesVisited = (int) Arrays.stream(tiles).filter(tile -> tile.visited).count();
    return tilesVisited;
  }

  static void run() {
    System.out.println("\nDay 006");
    System.out.printf("Part 1 control: %s\n", part1(testData));
    System.out.printf("Part 1 answer: %s\n", part1(input));
  }
}
