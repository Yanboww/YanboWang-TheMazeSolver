import java.util.ArrayList;
import java.util.Arrays;

public class MazeSolver {
   private String[][] matrix;
   private final String reset ="\033[0m";
   private final String red = "\033[0;31m";
   private final String blue = "\033[0;34m";
    public MazeSolver(String[][] m)
    {
        matrix = m;
    }
    public ArrayList<String> findPaths() {
        ArrayList<String> steps = new ArrayList<>();
        final int[] start = findPointStart(matrix[0],0,".");
        final int[] end = findPointEnd(matrix[matrix.length-1],matrix.length-1,".");
        ArrayList<String> queue = new ArrayList<>();
        queue.add("(" + start[0] + "," + start[1] +")");
        while(!queue.isEmpty())
        {
            //System.out.println(queue);
            String coord = queue.get(0);
            int prevRow = Integer.parseInt(coord.substring(coord.indexOf("(")+1, coord.indexOf(",")));
            int prevColumn = Integer.parseInt(coord.substring(coord.indexOf(",")+1, coord.indexOf(")")));
            steps.add(coord);
            String breakPoint = "";
            while(prevRow!=end[0] || prevColumn!=end[1])
            {
                int storageRow = prevRow;
                int storageColumn = prevColumn;
                matrix[prevRow][prevColumn] = red + "O" + reset;
                boolean foundAPath = false;
                if (up(storageRow,storageColumn,steps)){
                    prevRow -= 1;
                    foundAPath = true;
                }
                if (down(storageRow,storageColumn,foundAPath,steps)) {
                    if (!foundAPath){
                        prevRow += 1;
                        foundAPath = true;
                    }
                    else{
                        int changeRow = storageRow+1;
                        breakPoint = steps.get(steps.size()-1);
                        queue.add(1,"(" + changeRow+ "," + storageColumn +")");
                    }
                }
                if (left(storageRow,storageColumn,foundAPath,steps)) {
                    if (!foundAPath){
                        prevColumn -= 1;
                        foundAPath = true;
                    }
                    else{
                        int changeColumn = storageColumn-1;
                        breakPoint = steps.get(steps.size()-1);
                        queue.add(1,"(" + storageRow+ "," + changeColumn +")");
                    }
                }
                if (right(storageRow,storageColumn,foundAPath,steps)) {
                    if (!foundAPath){
                        prevColumn += 1;
                        steps.add("(" + prevRow + "," + prevColumn +")");
                        foundAPath = true;
                    }
                    else{
                        int changeColumn = storageColumn+1;
                        breakPoint = steps.get(steps.size()-1);
                        queue.add(1,"(" + storageRow+ "," + changeColumn +")");
                    }
                }
                if(!foundAPath){
                    steps = listSub(breakPoint,steps);
                    //System.out.println(steps);
                    //printMatrix(matrix);
                    break;
                }
            }
            queue.remove(0);
            if(steps.contains("(" + end[0] + "," + end[1] +")")) break;
        }
        checkList(steps);
        return steps;
    }

    public int[] findPointStart(String[] line, int rowNum, String key)
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

    public int[] findPointEnd(String[] line, int rowNum, String key)
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

    public ArrayList<String> listSub(String start, ArrayList<String> steps)
    {
        //System.out.println(steps);
        //System.out.println(start + " --");
        int coord = steps.indexOf(start);
        ArrayList<String> list = new ArrayList<>();
        if(coord == 0 || coord == -1) return steps;
        for(int i = 0; i<coord; i++)
        {
            list.add(steps.get(i));
        }
        //System.out.println(list);
        return list;
    }

    public void checkList(ArrayList<String> steps)
    {
        for(int i = steps.size()-1;i>0;i--)
        {
            String coord = steps.get(i);
            String coord1 = steps.get(i-1);
            int row = Integer.parseInt(coord.substring(coord.indexOf("(")+1, coord.indexOf(",")));
            int column = Integer.parseInt(coord.substring(coord.indexOf(",")+1, coord.indexOf(")")));
            int prevRow = Integer.parseInt(coord1.substring(coord1.indexOf("(")+1, coord1.indexOf(",")));
            int prevColumn = Integer.parseInt(coord1.substring(coord1.indexOf(",")+1, coord1.indexOf(")")));
            if(Math.abs(row-prevRow)>1 || Math.abs(prevColumn-column)>1 || row!=prevRow && column!=prevColumn|| steps.indexOf(coord1)!=i-1)
            {
                steps.remove(i-1);
            }
        }
        steps.remove(steps.size()-1);
    }

    public boolean up(int prevRow, int prevColumn, ArrayList<String>steps)
    {
        String top = null;
        if (prevRow != 0) top = matrix[prevRow - 1][prevColumn];
        if (prevRow != 0) top = matrix[prevRow - 1][prevColumn];
        if (top != null && top.equals(".")){
            prevRow -= 1;
            steps.add("(" + prevRow + "," + prevColumn +")");
            return true;
        }
        return false;
    }
    public boolean down(int prevRow, int prevColumn, boolean foundAPath, ArrayList<String> steps)
    {
        String bottom = null;
        if (prevRow != matrix.length - 1) bottom = matrix[prevRow + 1][prevColumn];
        if (prevRow != matrix.length - 1) bottom = matrix[prevRow + 1][prevColumn];
        if (bottom!= null && bottom.equals(".")) {
            if (!foundAPath){
                prevRow += 1;
                steps.add("(" + prevRow + "," + prevColumn +")");
            }
            return true;
        }
        return false;
    }
    public boolean right(int prevRow, int prevColumn, boolean foundAPath,ArrayList<String> steps)
    {
        String right = null;
        if (prevColumn != matrix[0].length - 1) right = matrix[prevRow][prevColumn + 1];
        if (prevColumn != matrix[0].length - 1) right = matrix[prevRow][prevColumn + 1];
        if (right!=null && right.equals(".")) {
            if (!foundAPath){
                prevColumn += 1;
                steps.add("(" + prevRow + "," + prevColumn +")");

            }
            return true;
        }
        return false;
    }
    public boolean left(int prevRow, int prevColumn, boolean foundAPath, ArrayList<String>steps)
    {
        String left = null;
        if (prevColumn != 0) left = matrix[prevRow][prevColumn - 1];
        if (prevColumn != 0) left = matrix[prevRow][prevColumn - 1];
        if (left!=null && left.equals(".")) {
            if (!foundAPath){
                prevColumn -= 1;
                steps.add("(" + prevRow + "," + prevColumn +")");
            }
            return true;
        }
        return false;
    }

    public void printSolution(ArrayList<String>solution)
    {
        System.out.print(solution.get(0));
        for(int i = 1;i<solution.size();i++)
        {
            System.out.print(" ---> " + solution.get(i));
        }
        System.out.println();
    }
    public void printMatrix() {
        for (String[] line : matrix) {
            System.out.println(Arrays.toString(line));
        }
    }


    public void printSolutionMaze(ArrayList<String> steps)
    {
        for(String coord : steps)
        {
            int row = Integer.parseInt(coord.substring(1,coord.indexOf(",")));
            int column = Integer.parseInt(coord.substring(coord.indexOf(",")+1,coord.length()-1));
            matrix[row][column] = blue + "V" + reset;

        }
        printMatrix();
    }


}
