package pr;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;

public class AvlVisualization extends Application {
	private Pane mainPane;
	private TextField insertNodeValue,deleteNodeValue,searchNodeValue;
	private Text post,pre,in;
    private Button addNodeButton,deleteNodeButton,searchButton,inorderButton,postorderButton,preorderButton;
    public  float circleRadiusSize = 25.0f; 

    public  ArrayList<Integer> elements = new ArrayList<>();

    public  AVLTree avlTree = new AVLTree();

    @Override
    public void start(Stage primaryStage) {
    	
    	BorderPane bp1=new BorderPane();
    	
    	bp1.setStyle("-fx-background-color: #08448c");
    	
    	Text title = new Text("AVL Tree");
		title.setFont(Font.font("Helvetica", FontWeight.BOLD, 35));
		title.setFill(Color.WHITE); 
		
		HBox top = new HBox(60);
		top.setPadding(new Insets(60, 160, 0, 160));
		top.setStyle("-fx-background-color: #08448c");
		top.getChildren().add(title);
		top.setAlignment(Pos.CENTER);
    	
    	bp1.setTop(top);
    	
    	GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(5, 25, 25, 25));
		
		Text op = new Text("Operations");
		op.setFont(Font.font("Helvetica",FontWeight.BOLD,15));
		op.setFill(Color.WHITE); 
		grid.add(op, 0, 0);
		
		insertNodeValue = new TextField();
		grid.add(insertNodeValue, 0, 2);
		
		addNodeButton = new Button("Insert");
        addNodeButton.setOnAction(event -> onAddButtonClicked());
        grid.add(addNodeButton, 1, 2);
        addNodeButton.getStyleClass().add("login-button");
        
        deleteNodeValue = new TextField();
		grid.add(deleteNodeValue, 0, 3);
		
		deleteNodeButton = new Button("Delete");
        deleteNodeButton.setOnAction(event -> onDeleteButtonClicked());
        grid.add(deleteNodeButton, 1, 3);
        deleteNodeButton.getStyleClass().add("login-button");
        
        searchNodeValue = new TextField();
		grid.add(searchNodeValue, 0, 4);
		
		searchButton = new Button("Search");
        searchButton.setOnAction(event -> onSearchButtonClicked());
        grid.add(searchButton, 1, 4);
        searchButton.getStyleClass().add("login-button");
        
        Text trv = new Text("Tree traversal");
		trv.setFont(Font.font("Helvetica",FontWeight.BOLD,15));
		trv.setFill(Color.WHITE); 
		grid.add(trv, 0, 8);
		
		in = new Text();
		in.setFont(Font.font("Helvetica", 15));
		in.setFill(Color.WHITE); 
		grid.add(in, 0, 10);
		
		inorderButton = new Button("Inorder");
		inorderButton.setOnAction(event -> inOrderButtonClicked());
        grid.add(inorderButton, 0, 9);
        inorderButton.getStyleClass().add("login-button");
        
        post = new Text();
		post.setFont(Font.font("Helvetica", 15));
		post.setFill(Color.WHITE); 
		grid.add(post, 0, 12);
		
        postorderButton = new Button("Postorder");
        postorderButton.setOnAction(event -> postOrderButtonClicked());
        grid.add(postorderButton, 0, 11);
        postorderButton.getStyleClass().add("login-button");
        
        pre = new Text();
		pre.setFont(Font.font("Helvetica", 15));
		pre.setFill(Color.WHITE); 
		grid.add(pre, 0, 14);
        
        preorderButton = new Button("Preorder");
        preorderButton.setOnAction(event -> preOrderButtonClicked());
        grid.add(preorderButton, 0, 13);
        preorderButton.getStyleClass().add("login-button");
        
		bp1.setCenter(grid);	
    	
    	BorderPane bp2=new BorderPane();
    	
    	mainPane=new Pane();
        bp2.setCenter(mainPane);
    	
    	HBox hbox = new HBox(bp1, bp2);
        Scene scene = new Scene(hbox,1200,700);
        
        String style= getClass().getResource("style.css").toExternalForm();
		scene.getStylesheets().add(style);

        primaryStage.setScene(scene);
        primaryStage.setTitle("AVL Tree Project");
        primaryStage.show();
       
    }

    private void onAddButtonClicked() {
    	 int nodeValue;

         try{

             nodeValue = Integer.parseInt(insertNodeValue.getText());

             if(!doesElementExist(nodeValue, elements)){

                 cleanMainPane();
                 elements.add(nodeValue);
                 addElementToAvl(nodeValue);
                 drawAvl();

             } else showMessage("This element exists in the tree!");


         } catch(Exception ex){

        	 showMessage("Value must be numeric");
         }

    }
    
    private void onDeleteButtonClicked() {
   	 int nodeValue;

        try{

            nodeValue = Integer.parseInt(deleteNodeValue.getText());

            if(doesElementExist(nodeValue, elements)){

                cleanMainPane();
                elements.remove(Integer.valueOf(nodeValue));
                deleteNodeFromAvl(Integer.toString(nodeValue));
                drawAvl();

            } else showMessage("This element does not exist in the tree!");


        } catch(Exception ex){

            showMessage("Value must be numeric");

        }

   }
    
    private void onSearchButtonClicked() {
   	 int nodeValue;

        try{

            nodeValue = Integer.parseInt(searchNodeValue.getText());

            if(!doesElementExist(nodeValue, elements)){

            	showMessage("This element does not exist in the tree!");

            } else showMessage("This element exists in the tree!");


        } catch(Exception ex){

            showMessage("Value must be numeric");

        }

   }
    
    public  boolean doesElementExist(int element, ArrayList<Integer> elements){

        Long res = elements.stream().filter(n -> n == element).count();
        return res > 0;

    }

    public  void addElementToAvl(int element){

        avlTree.insertElement(element);

    }

    public  void drawNodeCircle(String element, double xPosition, double yPosition){

        double extraWidth = 1.0f;
        double extraHeight = 10.0f;


        Circle circle = createCircle();
        Text text = createText(element);

        Group group = new Group(circle);

        StackPane stack = new StackPane();
        stack.setLayoutX(xPosition);
        stack.setLayoutY(yPosition);
        stack.getChildren().addAll(group, text);

        mainPane.getChildren().add(stack);
        mainPane.setPrefWidth(mainPane.getPrefWidth()+ extraWidth);
        mainPane.setPrefHeight(mainPane.getPrefHeight()+ extraHeight);

    }

    public  void deleteNodeFromAvl(String element){

        cleanMainPane();
        avlTree.deleteElement(Integer.parseInt(element));
        elements.remove((Integer)Integer.parseInt(element));

        if(elements.size() > 0)
            drawAvl();


    }

    public  Text createText(String nodeValue){

        final Text text = new Text(nodeValue);
        text.setFont(new Font(12));
        text.setBoundsType(TextBoundsType.VISUAL);

        return text;

    }

    public  Circle createCircle(){

        Circle circle = new Circle();

        circle.setRadius(circleRadiusSize);
        circle.setStroke(Color.DEEPSKYBLUE);
        circle.setStrokeWidth(2);
        circle.setStrokeType(StrokeType.INSIDE);
        circle.setFill(Color.AZURE);
        circle.relocate(0, 0);


        return circle;

    }

    public  void cleanMainPane(){
    		mainPane.getChildren().clear();
    }

    public  void drawLine(double xPosition1, double yPosition1, double xPosition2, double yPosition2){

        Line line = new Line(xPosition1, yPosition1, xPosition2, yPosition2);
        mainPane.getChildren().add(line);

    }

    public  void drawAvl(){

        Node root = avlTree.getRoot();

        if(root == null) {
            showMessage("The tree is empty");
            return;
        }

        int height = root.getHeight()+1;

        int width = (int)Math.pow(2, height-1);

        List<Node> current = new ArrayList<>(1);
        List<Node> next = new ArrayList<>(2);

        List<Float> previousXPositions = new ArrayList<>(1);
        List<Float> actualXPositions = new ArrayList<>(2);

        current.add(root);

        int elements = 1;

        int acum = 0;

        float actualPositionX = 0;
        float actualPositionY = 0;

        for(int i = 0; i < height; i++) {

            actualPositionX = ((int)Math.pow(2, height-1-i) - 1)*circleRadiusSize;

            int counter = 0;

            // Print tree node elements
            for(Node n : current) {

                if(n == null) {

                    next.add(null);
                    next.add(null);

                } else {

                    drawNodeCircle(Integer.toString(n.getKey()), actualPositionX, actualPositionY);
                    next.add(n.getLeft());
                    next.add(n.getRight());

                }

                if(counter%2==0) previousXPositions.add(actualPositionX);

                counter++;

                actualXPositions.add(actualPositionX);
                actualPositionX += circleRadiusSize*Math.round(width / (acum + 1));

            }

            if(i>0) acum++;

            drawConnectingLines(actualXPositions, previousXPositions, current, actualPositionY);

            actualPositionY+=circleRadiusSize*4;

            previousXPositions = actualXPositions;

            elements *= 2;
            current = next;
            next = new ArrayList<>(elements);
            actualXPositions = new ArrayList<>(elements);

        }

    }

    public void drawConnectingLines(List<Float> actualXPositions, List<Float> previousXPositions, List<Node> current, float actualPositionY){

        int indexPreviousX = 0;
        for(int j=0;j<actualXPositions.size();j++){

            if(j%2==0) {
                if (current.get(j) != null)
                    drawLine(previousXPositions.get(indexPreviousX)+circleRadiusSize, actualPositionY-circleRadiusSize*2, actualXPositions.get(j)+circleRadiusSize, actualPositionY);
            } else {
                if (current.get(j) != null)
                    drawLine(previousXPositions.get(indexPreviousX)+circleRadiusSize, actualPositionY-circleRadiusSize*2, actualXPositions.get(j)+circleRadiusSize, actualPositionY);
                indexPreviousX++;
            }


        }

    }
    
    public static List<Integer> postOrder(Node root) {
        List<Integer> result = new ArrayList<>();
        postOrderHelper(root, result);
        return result;
    }

    private static void postOrderHelper(Node root, List<Integer> result) {
        if (root == null)
            return;
        postOrderHelper(root.left, result);
        postOrderHelper(root.right, result);
        result.add(root.key);
    }
    
    private void postOrderButtonClicked() {
    	
    	Node root = avlTree.getRoot();
    	List<Integer> el=postOrder(root);
    	post.setText(el.toString());

   }

    public static List<Integer> preOrder(Node root) {
        List<Integer> result = new ArrayList<>();
        preOrderHelper(root, result);
        return result;
    }

    private static void preOrderHelper(Node root, List<Integer> result) {
        if (root == null)
            return;
        result.add(root.key);
        preOrderHelper(root.left, result);
        preOrderHelper(root.right, result);
    }
    
    private void preOrderButtonClicked() {
    	
    	Node root = avlTree.getRoot();
    	List<Integer> el=preOrder(root);
    	pre.setText(el.toString());

   }

    public static List<Integer> inOrder(Node root) {
        List<Integer> result = new ArrayList<>();
        inOrderHelper(root, result);
        return result;
    }

    private static void inOrderHelper(Node root, List<Integer> result) {
        if (root == null)
            return;
        inOrderHelper(root.left, result);
        result.add(root.key);
        inOrderHelper(root.right, result);
    }
    
    private void inOrderButtonClicked() {
    	
    	Node root = avlTree.getRoot();
    	List<Integer> el=inOrder(root);
    	in.setText(el.toString());

   }

    private void showMessage(String message){
        JOptionPane.showMessageDialog(null, message);
    }

    public static void main(String[] args) {
        launch(args);
    }
}