public class View {
    Model model;

    public View(Model model) {
        this.model = model;
    }

    public void printUnsolvedBoard(Model model) {
        for (int[] row : model.unSolvedBoard) {
            for (int col : row) {
                System.out.print(col+"  ");
            }
            System.out.println();
        }
    }

    public void printSolvedBoard(Model model) {
        for (int[] row : model.solvedBoard) {
            for (int col : row) {
                System.out.print(col+"  ");
            }
            System.out.println();
        }
    }
}
