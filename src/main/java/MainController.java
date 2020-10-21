import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.*;
import java.util.Scanner;

public class MainController {
    @FXML
    private RadioButton randomDataRadioButton;

    @FXML
    private ToggleGroup radioBtnGroup;

    @FXML
    private RadioButton realDataRadioButton;

    @FXML
    private TextField textField;

    @FXML
    private TextArea textArea;


    private File file;
    private Stage stage;
    private String fileName="output.txt";

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    void OpenFileFolder(ActionEvent event) {
        System.out.println("open");
    }

    @FXML
    void chooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Text Files", "*.txt"),
                new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
                new ExtensionFilter("All Files", "*.*"));
        file=fileChooser.showOpenDialog(stage);
        if(file!=null) textField.setText(file.getAbsolutePath());

    }

    @FXML
    void generateData(ActionEvent event) {
        if(randomDataRadioButton.isSelected()){
            saveToTxtFile("hello");


        }else {
            if(file==null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("No file selected");
                alert.showAndWait();

            }
            else{

                textArea.appendText("Process file\n");
                readFile();
                textArea.appendText("Process file finished\n");

            }
        }
//        System.setProperty("org.graphstream.ui", "swing");
//        Graph graph = new SingleGraph("Random");
//        Generator gen = new RandomGenerator(2);
//        gen.addSink(graph);
//        gen.begin();
//        for(int i=0; i<5; i++)
//            gen.nextEvents();
//        gen.end();
//        graph.display();
//        System.out.println("hellow");
    }

    private Graph readFile(){
        try {
            Scanner scanner = new Scanner(file);
            Graph graph = new SingleGraph("Random");
            while (scanner.hasNext()){
                String data=scanner.nextLine();
                if(!data.contains("#")){
                    String[] splited=data.split("\\t");
                    if(graph.getNode(splited[0])==null){
                        Node node=graph.addNode(splited[0]);
//                        node.setAttribute("ui.label", splited[0]);
//                        node.setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 40px; text-alignment: center;");

                    }
                    if(graph.getNode(splited[1])==null){
                        Node node=graph.addNode(splited[1]);
//                        node.setAttribute("ui.label", splited[1]);
//                        node.setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 40px; text-alignment: center;");
                    }
                    try {
                        graph.addEdge(splited[0]+"_"+splited[1],splited[0],splited[1]);
                    }catch (EdgeRejectedException edgeRejectedException){
                        //since is directed graph, repeat edge will get ignore, eg:0->1, 1->0(ignore)
                        //System.out.println(edgeRejectedException.getMessage());
                    }


                }

            }
            return graph;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    private void saveToTxtFile(String str){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileName);
            //will replace whole file
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(str);

            printWriter.println("Some String");
            printWriter.printf("Product name is %s and its price is %d $", "iPhone", 1000);
            printWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}
