import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

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

  Position(int x, int y, int direction) {
    this.x = x;
    this.y = y;
    this.direction = direction;
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

  static Tile[] generateTiles(String[] data, int rows, int columns) {
    Tile[] tiles = new Tile[data.length * data[0].length()];
    for (int y = 0; y < rows; y++) {
      for (int x = 0; x < columns; x++) {
        tiles[x + y * columns] = new Tile(x, y, columns, data[y].charAt(x));
      }
    }

    return tiles;
  }

  static Position getGuardStartingPosition(String[] data, Tile[] tiles, int rows, int columns) {
    Position guardPosition = new Position(0,0,0);
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

    return guardPosition;
  }

  static Stream<Tile> part1(String[] data) {
    int columns = data[0].length();
    int rows = data.length;
    Tile[] tiles = generateTiles(data, rows, columns);
    Position guardPosition = getGuardStartingPosition(data, tiles, rows, columns);

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

    return Arrays.stream(tiles).filter(tile -> tile.visited);
  }

  static int part2(String[] data) {
    int rows = data.length;
    int columns = data[0].length();
    Tile[] tiles = generateTiles(data, rows, columns);
    Position guardStartingPosition = getGuardStartingPosition(data, tiles, rows, columns);
    Tile[] blockedTiles = part1(data).filter(tile -> tile.index != guardStartingPosition.x + guardStartingPosition.y * columns).toArray(Tile[]::new);

    int loopCount = 0;
    for (Tile blockedTile : blockedTiles) {
      ArrayList<Position> positionHistory = new ArrayList<Position>();
      positionHistory.add(new Position(guardStartingPosition.x, guardStartingPosition.y, guardStartingPosition.direction));

      boolean foundBreakingPoint = false;
      Position guardPosition = new Position(guardStartingPosition.x, guardStartingPosition.y, guardStartingPosition.direction);
      while (!foundBreakingPoint) {
        int tileIndex = guardPosition.x + guardPosition.y * columns;
        int nextTileIndex = tileIndex;
        do {
          int x = guardPosition.x;
          int y = guardPosition.y;

          switch (guardPosition.direction) {
            case 0: { y--; break; }
            case 1: { x++; break; }
            case 2: { y++; break; }
            case 3: { x--; break; }
          }

          if (x < 0 || x >= columns || y < 0 || y >= rows) {
            guardPosition.x = x;
            guardPosition.y = y;
            foundBreakingPoint = true;
            break;
          }

          nextTileIndex = x + y * columns;
          if (tiles[nextTileIndex].isSolid || blockedTile.index == nextTileIndex) {
            int nextDir = guardPosition.direction + 1;
            guardPosition.direction = nextDir > 3 ? 0 : nextDir;
          } else {
            guardPosition.x = x;
            guardPosition.y = y;
          }

          boolean isAlreadyVisited = false;
          for (Position pos : positionHistory) {
            if (pos.x == guardPosition.x && pos.y == guardPosition.y && pos.direction == guardPosition.direction) {
              isAlreadyVisited = true;
              break;
            }
          }

          if (isAlreadyVisited) {
            foundBreakingPoint = true;
            loopCount++;
            System.out.printf("Loop count: %s\n", loopCount);
            break;
          }

          positionHistory.add(new Position(guardPosition.x, guardPosition.y, guardPosition.direction));
        } while (tiles[nextTileIndex].isSolid || blockedTile.index == nextTileIndex);
      }
    }

    return loopCount;
  }

  static void run() {
    System.out.println("\nDay 006");
    System.out.printf("Part 1 control: %s\n", part1(testData).count());
    System.out.printf("Part 1 answer: %s\n", part1(input).count());
    System.out.printf("Part 2 control: %s\n", part2(testData));
    System.out.printf("Part 2 answer: %s\n", part2(input));

  }
}