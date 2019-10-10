import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Model {


    static int[][] solvedBoard;
    static int[][] unSolvedBoard;
    private static int boardSize;
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static boolean finished = false;
    private static LinkedList<Integer> independentBlocksList;
    private static LinkedList<Integer> dependentBlocksList;
    private static final int EMPTY_FIELD=0;

    Model(int i) {
        boardSize = i;
    }

    private static void init() {
        solvedBoard = new int[boardSize][boardSize];
    }

    private static void newBoard() {
        initBlockLists();
        makeIndependentBlocks();
        fillBoard();
    }

    private static void initBlockLists() {
        independentBlocksList = giveIndependentBlockList();
        dependentBlocksList = newList();
        dependentBlocksList.removeAll(independentBlocksList);
    }

    private static void fillBoard() {
        int errorCounter = 0;
        for (int blockIndex = dependentBlocksList.get(0); blockIndex < dependentBlocksList.size(); blockIndex++) {
            for (int numberToInsert = 1; numberToInsert <= boardSize; numberToInsert++) {
                List<Position> possiblePositionList = givePossiblePositionsRandom(dependentBlocksList.get(blockIndex), numberToInsert);
                if (!possiblePositionList.isEmpty()) {
                    Position positionToInsert = possiblePositionList.remove(0);
                    solvedBoard[positionToInsert.row][positionToInsert.col] = numberToInsert;
                } else {
                    clearBlock(dependentBlocksList.get(blockIndex));
                    if (blockIndex > 0) {
                        clearBlock(dependentBlocksList.get(blockIndex - 1));
                        blockIndex--;
                    }
                    if (errorCounter >= 1000) {
                        errorCounter = 0;
                        solvedBoard = new int[boardSize][boardSize];
                        makeIndependentBlocks();
                    } else {
                        numberToInsert = 0;
                        errorCounter++;
                    }
                }
            }
        }if(containsZero(solvedBoard)){
            newBoard();
        }
        finished = true;
    }

    private static boolean containsZero(int[][] solvedBoard) {
        for(int [] row:solvedBoard){
            for(int col:row){
                if(col==0){
                    return true;
                }
            }
        }
        return false;
    }

    private static void clearBlock(Integer currentBlock) {
        int blockAmountInRowOrColumn = (int) (Math.sqrt(solvedBoard.length));
        List<Position> list = new LinkedList<>();
        int firstRow = currentBlock - currentBlock % blockAmountInRowOrColumn;
        int lastRow = firstRow + blockAmountInRowOrColumn - 1;
        int firstColumn = (currentBlock % blockAmountInRowOrColumn) * blockAmountInRowOrColumn;
        int lastColumn = firstColumn + blockAmountInRowOrColumn - 1;
        for (int row = firstRow; row <= lastRow; row++) {
            for (int col = firstColumn; col <= lastColumn; col++) {
                solvedBoard[row][col] = 0;
            }
        }
    }

    private static LinkedList<Integer> giveIndependentBlockList() {
        LinkedList<Integer> factors = new LinkedList<>();
        for (int blockNumber = 0; blockNumber < boardSize; blockNumber += (int) (Math.sqrt(boardSize) + 1)) {
            factors.add(blockNumber);
        }
        return factors;
    }

    private static void makeIndependentBlocks() {
        List<Integer> independentBlocks = giveIndependentBlockList();
        for (int numberToInsert = 1; numberToInsert <=boardSize; numberToInsert++) {
            for (int independentBlock : independentBlocks) {
                List<Position> possiblePositions = givePossiblePositionsRandom(independentBlock, numberToInsert);
                Position p = possiblePositions.remove(0);
                solvedBoard[p.row][p.col] = numberToInsert;
            }
        }
    }

    private static List<Position> givePossiblePositionsRandom(int currentBlock, int numberToInsert) {
        int blockAmountInRowOrColumn = (int) (Math.sqrt(solvedBoard.length));
        List<Position> list = new LinkedList<>();
        int firstRow = currentBlock - currentBlock % blockAmountInRowOrColumn;
        int lastRow = firstRow + blockAmountInRowOrColumn - 1;
        int firstColumn = (currentBlock % blockAmountInRowOrColumn) * blockAmountInRowOrColumn;
        int lastColumn = firstColumn + blockAmountInRowOrColumn - 1;
        for (int row = firstRow; row <= lastRow; row++) {
            for (int col = firstColumn; col <= lastColumn; col++) {
                if (solvedBoard[row][col] == 0 && !collisionInRowOrColumn(row, col, numberToInsert)) {
                    list.add(Position.newPosition(row, col));
                }
            }
        }
        Collections.shuffle(list);
        return list;
    }

    private static boolean collisionInRowOrColumn(int row, int column, int numberToInsert) {
        for (int rowAndColumn = 0; rowAndColumn < boardSize; rowAndColumn++) {
            if (solvedBoard[row][rowAndColumn] == numberToInsert || solvedBoard[rowAndColumn][column] == numberToInsert) {
                return true;
            }
        }
        return false;
    }

    private static LinkedList<Integer> newList() {
        LinkedList<Integer> ls = new LinkedList<>();
        for (int i = 1; i < boardSize; i++) {
            ls.add(i);
        }
        return ls;
    }

    public void create() {
        init();
        newBoard();
        //clearFields();
    }

    private void clearFields() {
        int[][] boardToRemoveFrom = copyBoard(solvedBoard);
        while (boardsMatch(solveFromTop(boardToRemoveFrom), solveFromGround(boardToRemoveFrom))) {
            int row = (int) (Math.random() * boardSize);
            int col = (int) (Math.random() * boardSize);
            if (boardToRemoveFrom[row][col] != 0) {
                unSolvedBoard = copyBoard(boardToRemoveFrom);
                boardToRemoveFrom[row][col] = 0;
            }
        }

    }

    private boolean boardsMatch(int[][] boardOne, int[][] boardTwo) {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (boardOne[row][col] != boardTwo[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    private int[][] solveFromGround(int[][] board) {
        return board;
    }

    private int[][] solveFromTop(int[][] board) {
        int[][] boardToInsert = copyBoard(board);
        List<Position> positionList=new LinkedList<>();
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (boardToInsert[row][col]==EMPTY_FIELD){
                    positionList.add(Position.newPosition(row,col));
                    for(int number=1;number<=boardSize;number++){
                        if(!collision(boardToInsert,row,col,number)){
                            boardToInsert[row][col]=number;
                            break;
                        }else{
                            if(number==boardSize){

                            }
                        }
                    }
                }
            }
        }
        return boardToInsert;
    }

    private boolean collision(int[][] boardToInsert, int row, int col, int number) {
        for(int test=0;test<boardSize;test++){
            if(boardToInsert[row][test]==number||boardToInsert[test][col]==number){
                return true;
            }
        }
        return collisionInBlock(boardToInsert, row, col, number);
    }

    private boolean collisionInBlock(int[][] board, int row, int col, int number) {
        int blockLength=(int)Math.sqrt(boardSize);
        int firstRow=row-row%blockLength;
        int lastRow = firstRow+blockLength-1;
        int firstCol=col-col%blockLength;
        int lastCol=firstCol+blockLength-1;
        List<Integer> list = new LinkedList<>();
        for(row=firstRow;row<=lastRow;row++){
            for (col=firstCol;col<=lastCol;col++){
                int insert = board[row][col];
                if(!list.contains(insert)){
                    list.add(insert);
                }else{
                    return true;
                }
            }
        }
        return false;
    }

    private int[][] copyBoard(int[][] solvedBoard) {
        int[][] ret = new int[boardSize][boardSize];
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                ret[row][col] = solvedBoard[row][col];
            }
        }
        return ret;
    }

}


