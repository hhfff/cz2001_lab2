import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
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
    @FXML
    private TextField kTextField;
    @FXML
    private ChoiceBox<String> algorithmPicker;
    @FXML
    private TextField hopsitalTextField;


    private File dataFile;
    private File hospitalFile;

    private Stage stage;

    private MultiSourceBFS multiSourceBFS;

    private int n=1100000;



    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        algorithmPicker.getItems().add("AB algorithm");
        algorithmPicker.getItems().add("CD algorithm");
        algorithmPicker.getSelectionModel().selectFirst();
        multiSourceBFS=new MultiSourceBFS();
    }





    @FXML
    void chooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Text Files", "*.txt"));
        dataFile =fileChooser.showOpenDialog(stage);
        if(dataFile !=null) textField.setText(dataFile.getAbsolutePath());
    }
    @FXML
    void chooseHospitalFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Text Files", "*.txt"));
        hospitalFile =fileChooser.showOpenDialog(stage);
        if(hospitalFile !=null) hopsitalTextField.setText(hospitalFile.getAbsolutePath());
    }

    @FXML
    void findNearestHospital(ActionEvent event) {
        int selectedAlgorithm=algorithmPicker.getSelectionModel().getSelectedIndex();
        LinkedList<Integer> hospitalList=null;
        if(hopsitalTextField.getText().length()==1){
            hospitalList=new LinkedList<>();
            hospitalList.add(Integer.parseInt(hopsitalTextField.getText()));
        }
        if(hopsitalTextField.getText().contains(",")) {
            hospitalList=new LinkedList<>();
            String[] split=hopsitalTextField.getText().split(",");
            for(int i=0;i<split.length;i++) {
                hospitalList.add(Integer.parseInt(split[i]));
            }
        }
        if(hospitalList==null){
            if(hospitalFile!=null) hospitalList= FileHelper.readHospitalFile(hospitalFile);
            else {
                alert("No hospital file selected or data entered");
                return;
            }
        }

        if(randomDataRadioButton.isSelected()){
            // random graph
            Graph graph=RandomGraph.getRandomGraph(2,4);
            if(selectedAlgorithm==0){
                //a,b algorithm
                long time=multiSourceBFS.searchRandomGraph(graph,hospitalList);
                setRunningTime(time);
            }else{
                //c,d algorithm
                System.out.println("cd algorithm");
            }
        }else {
            // real data
            ArrayList<ArrayList<Integer>> adjacencyList=null;
            if(dataFile==null){
                alert("No data file selected");
                return;
            }else{
                adjacencyList=FileHelper.readRealNetworkData(dataFile,n);
            }
            if (selectedAlgorithm == 0) {
                // ab algo
                long time=multiSourceBFS.searchRealGraph(adjacencyList,hospitalList,n);
                setRunningTime(time);
            } else {
                // cd algo


            }
        }
    }


    private Graph readFile(){
        try {
            Scanner scanner = new Scanner(dataFile);
            Graph graph = new SingleGraph("real data");
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
                        //since is undirected graph, repeat edge will get ignore, eg:0->1, 1->0(ignore)
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




    private void setRunningTime(long time){
        RadioButton radioButton=(RadioButton) radioBtnGroup.getSelectedToggle();
        textArea.appendText(algorithmPicker.getValue()+" "+ radioButton.getText()+" search time: "+(time)+ " nanoseconds\n");

    }

    private void alert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

}
