public class Main {

    public static void main(String[] args) {
        Model model = new Model(9);
        model.create();
        View view = new View(model);
        Controller controller = new Controller(model, view);
        view.printSolvedBoard(model);
    }
}
