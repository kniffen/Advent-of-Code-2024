import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utils {
  public static String[] readFile(String filePath) {
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      return br.lines().toArray(String[]::new);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }
}
