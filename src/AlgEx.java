import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class AlgEx {
    private static class Cell {
        int color;
        int rowIndex;
        int colIndex;
        boolean inRegion;
        boolean isNeighbor;

        public Cell() {
            color = 0;
            rowIndex = -1;
            colIndex = -1;
            inRegion = false;
            isNeighbor = false;
        }

        public Cell(int color, int rowIndex, int colIndex) {
            this.color = color;
            this.rowIndex = rowIndex;
            this.colIndex = colIndex;
            this.inRegion = false;
            this.isNeighbor = false;
        }
    }

    private static void checkNeighbors(int rowIndex, int colIndex) {
        if (rowIndex - 1 > -1 && !field[rowIndex - 1][colIndex].isNeighbor) {
            neighbors[field[rowIndex - 1][colIndex].color - 1].add(field[rowIndex - 1][colIndex]);
            field[rowIndex - 1][colIndex].isNeighbor = true;
        }
        if (rowIndex + 1 < rowNum && !field[rowIndex + 1][colIndex].isNeighbor) {
            neighbors[field[rowIndex + 1][colIndex].color - 1].add(field[rowIndex + 1][colIndex]);
            field[rowIndex + 1][colIndex].isNeighbor = true;
        }
        if (colIndex - 1 > -1 && !field[rowIndex][colIndex - 1].isNeighbor) {
            neighbors[field[rowIndex][colIndex - 1].color - 1].add(field[rowIndex][colIndex - 1]);
            field[rowIndex][colIndex - 1].isNeighbor = true;
        }
        if (colIndex + 1 < colNum && !field[rowIndex][colIndex + 1].isNeighbor) {
            neighbors[field[rowIndex][colIndex + 1].color - 1].add(field[rowIndex][colIndex + 1]);
            field[rowIndex][colIndex + 1].isNeighbor = true;
        }
    }

    private static Cell[][] field;
    private static Deque<Cell>[] neighbors;
    private static int rowNum;
    private static int colNum;
    private static int colorNum;
    private static int stepsNum;
    private static int[] steps;

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("floodit.in"));
        FileWriter writer = new FileWriter("floodit.out");
        String[] buf = reader.readLine().split(" ");
        StringBuilder writeBuf = new StringBuilder("");
        Cell cell;
        rowNum = Integer.parseInt(buf[0]);
        colNum = Integer.parseInt(buf[1]);
        colorNum = Integer.parseInt(buf[2]);
        stepsNum = Integer.parseInt(buf[3]);
        steps = new int[stepsNum];
        field = new Cell[rowNum][colNum];
        neighbors = new ArrayDeque[colorNum];
        for (int i = 0; i < colorNum; i++) {
            neighbors[i] = new ArrayDeque<>();
        }
        for (int i = 0; i < rowNum; i++) {
            buf = reader.readLine().split(" ");
            for (int j = 0; j < colNum; j++) {
                field[i][j] = new Cell(Integer.parseInt(buf[j]), i, j);
            }
        }
        buf = reader.readLine().split(" ");
        for (int i = 0; i < stepsNum; i++) {
            steps[i] = Integer.parseInt(buf[i]);
        }
        field[0][0].inRegion = true;
        field[0][0].isNeighbor = true;
        if (colNum > 1) {
            neighbors[field[0][1].color - 1].add(field[0][1]);
            field[0][1].isNeighbor = true;
        }
        if (rowNum > 1) {
            neighbors[field[1][0].color - 1].add(field[1][0]);
            field[1][0].isNeighbor = true;
        }
        while ((cell = neighbors[field[0][0].color - 1].poll()) != null) {
            checkNeighbors(cell.rowIndex, cell.colIndex);
            cell.inRegion = true;
        }
        for (int i = 0; i < stepsNum; i++) {
            while ((cell = neighbors[steps[i] - 1].poll()) != null) {
                checkNeighbors(cell.rowIndex, cell.colIndex);
                cell.inRegion = true;
            }
        }
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                if (field[i][j].inRegion) {
                    writeBuf.append(steps[stepsNum - 1]);
                    writeBuf.append(" ");
                } else {
                    writeBuf.append(field[i][j].color);
                    writeBuf.append(" ");
                }
            }
            writeBuf.append("\n");
        }
        writer.write(writeBuf.toString());
        reader.close();
        writer.close();
    }
}
