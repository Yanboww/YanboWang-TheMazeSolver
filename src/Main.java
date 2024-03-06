import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        String[][] maze = getMatrix("Data/superMaze2");
        MazeSolver m = new MazeSolver(maze);
        ArrayList<String> solution = m.findPaths();
        m.printSolution(solution);
        m.printSolutionMaze(solution);
    }

    public static String[][] getMatrix(String fileName) {
        File f = new File(fileName);
        Scanner s = null;
        try {
            s = new Scanner(f);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(1);
        }

        ArrayList<String> fileData = new ArrayList<String>();
        while (s.hasNextLine())
            fileData.add(s.nextLine());

        int rows = fileData.size();
        int cols = fileData.get(0).length();

        String[][] matrix = new String[rows][cols];

        for (int i = 0; i < fileData.size(); i++) {
            String d = fileData.get(i);
            for (int j = 0; j < d.length(); j++) {
                String number = d.charAt(j) + "";
                matrix[i][j] = number;
            }
        }
        return matrix;
    }
}