import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
public class Main {
    public static void main(String[] args) {
        String[][] maze = getMatrix("Data/File4");
        ArrayList<String> solution = findPaths(maze);
        printSolution(solution);
        printMatrix(maze);
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

    public static void printMatrix(String[][] matrix) {
        for (String[] line : matrix) {
            System.out.println(Arrays.toString(line));
        }
    }

    public static void printSolution(ArrayList<String>solution)
    {
        System.out.print(solution.get(0));
        for(int i = 1;i<solution.size();i++)
        {
            System.out.print(" ---> " + solution.get(i));
        }
        System.out.println();
    }

    public static ArrayList<String> findPaths(String[][] matrix) {
        ArrayList<String> steps = new ArrayList<>();
        final int[] start = findPointStart(matrix[0],0,".");
        final int[] end = findPointEnd(matrix[matrix.length-1],matrix.length-1,".");
        ArrayList<String> queue = new ArrayList<>();
        steps.add("(" + start[0] + "," + start[1] +")");
        queue.add("(" + start[0] + "," + start[1] +")");
        while(!queue.isEmpty())
        {
            String coord = queue.get(0);
            int prevRow = Integer.parseInt(coord.substring(coord.indexOf("(")+1, coord.indexOf(",")));
            int prevColumn = Integer.parseInt(coord.substring(coord.indexOf(",")+1, coord.indexOf(")")));
            while(prevRow!=end[0] || prevColumn!=end[1])
            {
                int size = steps.size();
                int storageRow = prevRow;
                int storageColumn = prevColumn;
                matrix[prevRow][prevColumn] = "O";
                boolean foundAPath = false;
                String top = null;
                String bottom = null;
                String left = null;
                String right = null;
                if (prevRow != 0) top = matrix[prevRow - 1][prevColumn];
                if (prevRow != matrix.length - 1) bottom = matrix[prevRow + 1][prevColumn];
                if (prevColumn != 0) left = matrix[prevRow][prevColumn - 1];
                if (prevColumn != matrix[0].length - 1) right = matrix[prevRow][prevColumn + 1];
                if (prevRow != 0) top = matrix[prevRow - 1][prevColumn];
                if (prevRow != matrix.length - 1) bottom = matrix[prevRow + 1][prevColumn];
                if (prevColumn != 0) left = matrix[prevRow][prevColumn - 1];
                if (prevColumn != matrix[0].length - 1) right = matrix[prevRow][prevColumn + 1];
                if (top != null && top.equals(".")){
                    prevRow -= 1;
                    steps.add("(" + prevRow + "," + prevColumn +")");
                }
                if (bottom!= null && bottom.equals(".")) {
                    if (!foundAPath){
                        prevRow += 1;
                        steps.add("(" + prevRow + "," + prevColumn +")");
                    }
                    else{
                        int changeRow = storageRow+1;
                        queue.add("(" + changeRow+ "," + storageColumn +")");
                        steps.remove(steps.size()-1);
                    }
                }
                if (left!=null && left.equals(".")) {
                    if (!foundAPath){
                        prevColumn -= 1;
                        steps.add("(" + prevRow + "," + prevColumn +")");
                    }
                    else{
                        int changeColumn = storageColumn-1;
                        queue.add("(" + storageRow+ "," + changeColumn +")");
                        if(steps.size()!=size)
                        {
                            steps.remove(steps.size()-1);
                        }
                    }
                }
                if (right!=null && right.equals(".")) {
                    if (!foundAPath){
                        prevColumn += 1;
                        steps.add("(" + prevRow + "," + prevColumn +")");
                    }
                    else{
                        int changeColumn = storageColumn+1;
                        queue.add("(" + storageRow+ "," + changeColumn +")");;
                        if(steps.size()!=size)
                        {
                            steps.remove(steps.size()-1);
                        }
                    }
                }

            }
            queue.remove(0);
        }
        return steps;
    }

    public static int[] findPointStart(String[] line, int rowNum, String key)
    {
        int[] coord = {rowNum,0};
        for(int i = 0; i<line.length;i++)
        {
            if(line[i].equals(key)){
                coord[1] = i;
                return coord;
            }
        }
        return coord;
    }

    public static int[] findPointEnd(String[] line, int rowNum, String key)
    {
        int[] coord = {rowNum,0};
        for(int i = 0; i<line.length;i++)
        {
            if(line[i].equals(key)){
                coord[1] = i;
            }
        }
        return coord;
    }




}