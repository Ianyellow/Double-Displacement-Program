/*
* Date: May  20, 2021
* Name: Ian, William, Leo
* Teacher: Mr. Ho
* Description: A program that finds the double displacement reaction of compounds, given a chemical or word equation
**/

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
        
    }    
    @Override
    /**
     * Description: create a scene for user to input their compounds
     * @author Leo Shi
     */
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Chemical Calculator");

        // Create the registration form grid pane
        GridPane gridPane = createCalculatorPane();
        // Add UI controls to the registration form grid pane
        addUIControls(gridPane);
        // Create a scene with registration form grid pane as the root node
        Scene scene = new Scene(gridPane, 800, 500);
        // Set the scene in primary stage  
        primaryStage.setScene(scene);
        
        primaryStage.show();
    }
    /**
     * Description: set up the gridPane
     * @author Leo Shi
     * @return gridPane
     */
    private GridPane createCalculatorPane() {
        // Instantiate a new Grid Pane
        GridPane gridPane = new GridPane();

        // Position the pane at the center of the screen, both vertically and horizontally
        gridPane.setAlignment(Pos.CENTER);

        // Set a padding of 20px on each side
        gridPane.setPadding(new Insets(40, 40, 40, 40));

        // Set the horizontal gap between columns
        gridPane.setHgap(20);

        // Set the vertical gap between rows
        gridPane.setVgap(20);

        // columnOneConstraints will be applied to all the nodes placed in column one.
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

        return gridPane;
    }
    /**
     * Description: Add details to the scene
     * @author Leo Shi
     * @param gridPane
     * @throws Exception
     */
    private void addUIControls(GridPane gridPane) throws Exception {
        // Add image
        FileInputStream input = new FileInputStream("C:/Users/dunyu/Desktop/cs Finals/Double-Displacement-Program/src/test-tube.png");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);

        // Add rule 1
        Label rule1 = new Label("Do not enter the charges. Only the chemical name e.g. O, Na, Al");
        rule1.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        gridPane.add(rule1, 1, 0);
        GridPane.setMargin(rule1, new Insets(10,20,0,10));
        
        // Add rule 2
        Label rule2 = new Label("Add a space between two elements. e.g. Na2 SO4 + Ba Cl2");
        rule2.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        gridPane.add(rule2, 1, 1);
        GridPane.setMargin(rule2, new Insets(10,20,0,10));

        // Add Header
        Label headerLabel = new Label("Enter Compounds Below" ,imageView);
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        gridPane.add(headerLabel, 0,2,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));
        

        // Add First Compound Label
        Label compoundOneLabel = new Label("First : ");
        gridPane.add(compoundOneLabel, 0,3);

        // Add First Compound Field
        TextField compoundOneField = new TextField();
        compoundOneField.setPrefHeight(40);
        gridPane.add(compoundOneField, 1,3);

        // Add Second Compound Label
        Label compoundTwoLabel = new Label("Second : ");
        gridPane.add(compoundTwoLabel, 0, 4);

        // Add Second Compound Text Field
        TextField compoundTwoField = new TextField();
        compoundTwoField.setPrefHeight(40);
        gridPane.add(compoundTwoField, 1, 4);

        // Add Submit Button
        Button submitButton = new Button("Submit");
        submitButton.setPrefHeight(40);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(100);
        gridPane.add(submitButton, 0, 5, 2, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setMargin(submitButton, new Insets(20, 0,20,0));

        // Add some Description
        Label description = new Label("--> Some compounds that contain OH are not available yet, please wait for update :)");
        description.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        gridPane.add(description, 1, 6);
        GridPane.setMargin(description, new Insets(10,20,0,10));

            //Creating the mouse event handler 
            EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
            @Override 
            public void handle(MouseEvent e) { 
                // If compound one field is empty
                if(compoundOneField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "First Compound is empty. Please enter a valid compound");
                    return;
                }
                // If compound two field is empty
                if(compoundTwoField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "Second Compound is empty. Please enter a valid compound");
                    return;
                }

                String compoundOneTest = compoundOneField.getText();
                String compoundTwoTest = compoundTwoField.getText();
                String[] compoundOne = compoundOneField.getText().split(" ");
                String[] compoundTwo = compoundTwoField.getText().split(" ");
                boolean[] reaction = {false};   // boolean to detect for reaction
                boolean[] foundIon = {false, false, false, false, true};    // boolean for input validation
                int accum = 0;      // accumulator to count lower case letters
                
                if(containsSpace(compoundOneTest) == false){
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "First Compound is invalid, please add a space between two elements, e.g. Na2 SO4");
                    return;
                }
                if(containsSpace(compoundTwoTest) == false){
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "Second Compound is invalid, please add a space between two elements, e.g. Na2 SO4");
                    return;
                }

                // indentify if the user inputs a chemical or word equation
                for (int i = 0; i < compoundOneTest.length(); i ++) {
                    // if the character is a lower case
                    if (Character.isLowerCase(compoundOneTest.charAt(i)) == true) {
                        accum++;        // accumulating the integer
                    }
                }

                // declaring all necessary variables
                // set all amounts at 1 for now
                int[] firstMole = {1, 1, 1};
                int[] secondMole = {1, 1, 1};
                int[] newFirstMole = {1, 1, 1};
                int[] newSecondMole = {1, 1, 1};

                String[] stringMoleOne = {"", "", ""};      // empty strings for now
                String[] stringMoleTwo = {"", "", ""};      // empty strings for now

                try{
                    identifyFirstNum(compoundOne, firstMole);
                }
                catch(ArrayIndexOutOfBoundsException ee){
                    showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Cannot recognize input!", "Please reinput again!");
                    return;
                }

        // Condition: if the user inputs a chemical equation
        if (accum < 5) {
            // call identifyFirstNum method
            identifyFirstNum(compoundOne, firstMole);
            identifyFirstNum(compoundTwo, secondMole);

            // call cationAmount method
            cationAmount(compoundOne, firstMole);
            cationAmount(compoundTwo, secondMole);

            // call anionAmount method
            anionAmount(compoundOne, firstMole);
            anionAmount(compoundTwo, secondMole);
        }

        try {
            // call scanCharges method
            scanCharges(compoundOne, compoundTwo, newFirstMole, newSecondMole, foundIon, accum);
        }
        // catch file not found error when reading the method
        catch (FileNotFoundException E) {
            E.printStackTrace();
        }

        // call typoValidate method
        typoValidate(foundIon);

        // Condition: if the user inputs a chemical equation
        if (accum < 5) {
            // call reduceCharges method
            reduceCharges(newFirstMole);
            reduceCharges(newSecondMole);

            // call arrangeMoles method
            arrangeMoles(firstMole, secondMole);

            // call balance method
            balance(firstMole, secondMole, newFirstMole, newSecondMole);

            // call removeSingularIons method
            removeSingularIons(newFirstMole, stringMoleOne);
            removeSingularIons(newSecondMole, stringMoleTwo);

            // call addBrackets method
            addBrackets(compoundOne, compoundTwo, newFirstMole, newSecondMole);
        }
        

        try {
            // call doesItExist method 
            checkPercipitate(reaction, compoundOne, compoundTwo);
        }
        // catch file not found error when reading the method
        catch (FileNotFoundException E) {
            E.printStackTrace();
        }

        // condense the compounds to one singular string to be outputted
        String newCompoundOne =  compoundOne[0] + stringMoleOne[1] + " " + compoundTwo[1] + stringMoleOne[2];
        String newCompoundTwo =  " + " + compoundTwo[0] + stringMoleTwo[1] + " " + compoundOne[1] + stringMoleTwo[2];

        // if the user inputs an unrecognized ion
        if (foundIon[4] == false) {
            showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Cannot recognize input!", "Please reinput again!");
        }
        // display the product after a reaction occurs
        else if (reaction[0] == true) { 
            showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Calculate Successful!", compoundOneField.getText() + " + " + compoundTwoField.getText() + "  -->  " + newCompoundOne + newCompoundTwo);
        }
        // display no reaction if a reaction doesn't occur
        else {
            showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Calculate Successful!", "--> no reaction" );
        }
    


                // Successful 
                // showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Calculate Successful!", "Answer:  " ); // output the answer 
            }
        
            };  
        //Registering the event filter 
        submitButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);  
        }
        
        private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.initOwner(owner);
            alert.show();
        }
    /**
     * @Description: Balance the chemical equation
     * 
     * @author Ian
     * @param firstMole         int array of the original amount of moles each ion has in compound 1 before reaction
     * @param secondMole        int array of the original amount of moles each ion has in compound 2 before reaction
     * @param newFirstMole      int array of the new amount of moles each ion has in compound 1 after reaction
     * @param newSecondMole     int array of the new amount of moles each ion has in compound 2 after reaction
     */
    public static void balance(int[] firstMole, int[] secondMole, int[] newFirstMole, int[] newSecondMole) {
        // Do something to balance the chemical equation
        // too complicated for the 3 of us 
    }
    /**
     * @Description: remove moles that have an anount of 1, we don't show it as an output
     * 
     * @author Ian      
     * @param moles     the int array storing the amount of each ion in the compound after reaction
     * @param stringMoleOne     string array of the amount of each ion in the compound after reaction
     */
    public static void removeSingularIons(int[] moles , String[] stringMole) {
        // store the amount to a string array
        for (int i = 0; i <  moles.length; i++) {
            stringMole[i] = Integer.toString(moles[i]);
            
            // iterating the array to check if it equals to 1
            if (moles[i] == 1) {
                stringMole[i] = "";  // make it empty
            }
        }
    }
    /**
     * @Description: add brackets on polyatomic ions when there's more than one of then
     * 
     * @author Ian
     * @param compoundOne   the array containing the first compound in the chemical equation
     * @param compoundTwo   the array containing the second compound in the chemical equation
     * @param newFirstMole  int array storing the charges of the first compound after the DD reaction
     * @param newSecondMole int array storing the charges of the seond compound after the DD reaction
     */
    public static void addBrackets(String[] compoundOne, String[] compoundTwo, int[] newFirstMole, int[] newSecondMole) {
        // if the polyatomic ion is more than 1 in the reaction
        if (newFirstMole[2] != 1 && Character.toString(compoundTwo[1].charAt(compoundTwo[1].length()-1) ).matches("-?\\d+")) {
            compoundTwo[1] = "(" + compoundTwo[1] + ")";    // add brackets
        }
        // if the polyatomic ion in the second compound is more than 1 in the reaction
        if (newSecondMole[2] != 1 && Character.toString(compoundOne[1].charAt(compoundOne[1].length()-1) ).matches("-?\\d+")) {
            compoundOne[1] = "(" + compoundOne[1] + ")";    // add brackets
        }
    }
    /**
     * @Description: find the total amount of moles in the original compound and arrange them in the same 
     *              pattern as the charges after the double displacement reaction
     * 
     * @author Ian 
     * @param firstMole     int array storing the moles of the first compound before the DD reaction
     * @param secondMole    int array storing the moles of the second compound before the DD reaction
     */
    public static void arrangeMoles(int[] firstMole, int[] secondMole) {
        int temp;    // a temporary integer

        // iterate accross each index and multiply the amount by the amount at the first index
        for (int i = 1; i < 3; i++) {
            firstMole[i] = firstMole[i]*firstMole[0];
            secondMole[i] = secondMole[i]*secondMole[0];
        }

        // switching the amounts of the anions
        temp = firstMole[2];
        firstMole[2] = secondMole[2];
        secondMole[2] = temp;
    }
    /**
     * @Description: Method to scan the percipitate chart and check if a reaction occurs in a 
     *              word equation.
     * @author Ian & William
     * @param reaction      boolean array to indicate if a reaction occurs or not
     * @param arrOne        string array of the first compound inputted by the user
     * @param arrTwo        string array of the second compound inputted by the user
     * @throws FileNotFoundException    outputs error the the file is not found in the same folder
     */
    public static void checkPercipitate(boolean[] reaction, String[] arrOne, String[] arrTwo) throws FileNotFoundException{
        // The path to the csv file may vary
        String path = "C:/Users/dunyu/Desktop/cs Finals/Double-Displacement-Program/src/solubility - Sheet1 (2).csv";
        
        //Parsing a csv file into BufferedReader class constructor
        BufferedReader br = new BufferedReader(new FileReader(path));

        // Variable line that equals to nothing right now
        String line = "";

        // Try, catch, and finally statement are for if the code goes wrong
        // Try block goes first 
        try{
            // A while loop that infinantly go over the file and read each line unitl it is empty
            while((line = br.readLine()) != null){
                // A string array that seperates the different infos by the comma in the file
                String[] value = line.split(",");

                // condition: if anion in compound 1 and 2 are found
                if ((value[0].indexOf(arrOne[1]) > -1) || (value[0].indexOf(arrTwo[1]) > -1 )) {
                    // iterate across the horizontal row
                    for (String ion : value) {
                        // condition: if the anion from compound 1 is in the same row as the cation from compound 2
                        // Or   if the anion from compound 2 is in the same row as the cation from compound 1
                        if (  (  ion.indexOf(arrTwo[0]) > -1 && (value[0].indexOf(arrOne[1]) > -1)) || (ion.indexOf(arrOne[0]) > -1 && (value[0].indexOf(arrTwo[1]) > -1))  ) {
                            reaction[0] = true;     // a percipitate forms --> reaction occurs
                        }
                    }
                }
            }
        }
        // The two catch files are for if the file is not found from the file path, they will print the files' stack trace
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        // Finally block that will be executed in every case, success or caught exception
        finally{
            // Close the bufferscanner if all lines in the file is read
            if(br != null){
                try{
                    br.close();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * @Description: reduce the same or factorable charges
     * 
     * @author Ian
     * @param mole    the int array containing charges from the csv file
     */
    public static void reduceCharges(int[] mole) {
        // iterate and divide charges if possible
        for (int i = 2; i < 5; i++) {
            // if both are divisible by 2 or 3
            if ((mole[1]%i == 0 ) && (mole[2]%i == 0)) {
                mole[1] = mole[1]/i;    // deviding both by the common number
                mole[2] = mole[2]/i;    // deviding both by the common number
            }
        }
    }
    /**
     * @Description: check how many anions there are in the compound and store that amount, and 
     *              remove brackets if necessary.
     * 
     * @author Ian
     * @param compound  the String array containing the compound the user inputted
     * @param moles    the int array for storing the amount of each ion in the compound
     */
    public static void anionAmount(String[] compound, int[] moles) {
        // store the polyatomic ion and remove the brakets
        if (Character.toString(compound[1].charAt(0)).equals("(")) {
            moles[2] = Character.getNumericValue(compound[1].charAt(compound[1].length()-1));
            compound[1] = compound[1].substring(1, compound[1].length()-2);
        }

        // if the anion has at least two letters and isn't polyatomic or is hydroxide
        if (compound[1].length() > 1 && Character.isUpperCase(compound[1].charAt(1)) == false || compound[1].indexOf("OH") > -1) {
            // if the anion is multiple
            if (Character.toString(compound[1].charAt(compound[1].length()-1)).matches("-?\\d+")) {
                // store the amount
                moles[2] = Character.getNumericValue(compound[1].charAt(compound[1].length()-1));
                compound[1] = compound[1].substring(0, compound[1].length()-1);     // remove the integer
            }
        }
    }
    /**
     * @Description: check how many cations there are in the compound and store that amount.
     * 
     * @author Ian
     * @param compound  the String array containing the compound the user inputted
     * @param moles    the int array for storing the amount of each ion in the compound
     */
    public static void cationAmount(String[] compound, int[] moles) {
        // if cation is more than one
        if (Character.toString(compound[0].charAt(compound[0].length()-1)).matches("-?\\d+")) {
            // store the amount of the cation
            moles[1] = Character.getNumericValue(compound[0].charAt(compound[0].length()-1));
            compound[0] = compound[0].substring(0, compound[0].length()-1);     // remove the integer
        }
    }
    /**
     * @Description: if the user inputted a number before the compound, store it to the charges array and remove it
     *              from the compound array.
     * 
     * @author Ian
     * @param compound  the String array containing the compound the user inputted
     * @param moles    the int array for storing the amount of each ion in the compound
     */
    public static void identifyFirstNum(String[] compound, int[] moles) {
        // if the first index is a integer
        if (compound[0].matches("-?\\d+")) {
            moles[0] = Integer.parseInt(compound[0]);   // store the integer
            compound[0] = compound[1];      // switch indexes
            compound[1] = compound[2];      // switch indexes
        }
    }
    /**
     * @Description: check if all ions are correctly typed / inputted
     * 
     * @author Ian
     * @param foundIon  boolean array indicating if an ion is found
     */
    public static void typoValidate(boolean[] foundIon) {
        // detect if an ion exists
        for (int i = 0; i < 4; i++) {
            // if there's one ion that doesn't exist
            if (foundIon[i] == false) {
                foundIon[4] = false; // make the forth index false
            }
        }
    }
    /**
     * @Description: Scan the csv and grab charges. Validates user input for ions.
     * 
     * @author William Wu
     * @param compoundOne   the array containing the first compound in the chemical equation
     * @param compoundTwo   the array containing the second compound in the chemical equation
     * @param newFirstMole  int array storing the charges of the first compound after the DD reaction
     * @param newSecondMole int array storing the charges of the seond compound after the DD reaction
     * @param foundIon      boolean array indicating if an ion is found
     * @param wordOrChemical      int holding the number of lower cases in the compounds
     * @throws FileNotFoundException    outputs error the the file is not found in the same folder
     */
    public static void scanCharges(String[] compoundOne, String[] compoundTwo, int[] newFirstMole,
    int[] newSecondMole, boolean[] foundIon, int wordOrChemical) throws FileNotFoundException{ 
        // need path
        String path = "C:/Users/dunyu/Desktop/cs Finals/Double-Displacement-Program/src/charges - Sheet1 (4).csv";

        //Parsing a csv file into BufferedReader class constructor
        BufferedReader br = new BufferedReader(new FileReader(path));

        // Variable line that equals to nothing right now
        String line = "";
        int accum = 0;      // accumulator to find the row number

        // Try, catch, and finally statement are for if the code goes wrong
        // Try block goes first 
        try{
            // A while loop that infinantly go over the file and read each line unitl it is empty
            while((line = br.readLine()) != null){
                // A string array that seperates the different infos by the comma in the file
                String[] value = line.split(",");
                accum++;    // accumulating the row number

                // if the row arrives at 61 where the charges end, stop storing charges
                // this is for the chemical equations
                if (accum <= 61 && wordOrChemical < 5) {
                    // if the cation from the first compound is found and has the same length
                    if (value[0].indexOf(compoundOne[0]) > -1  && value[0].length() == compoundOne[0].length()) {
                        newFirstMole[2] = Integer.parseInt(value[1]);   // store the charge
                    }
                    // if the cation from the second compound is found and has the same length
                    else if (value[0].indexOf(compoundTwo[0]) > -1  && value[0].length() == compoundTwo[0].length()) {
                        newSecondMole[2] = Integer.parseInt(value[1]);  // store the charge
                    }
                    // if the anion from the second compound is found and has the same length
                    else if (value[0].indexOf(compoundTwo[1]) > -1  && value[0].length() == compoundTwo[1].length()) {
                        newFirstMole[1] = Integer.parseInt(value[1]);   // store the charge
                    }
                    // if the anion from the first compound is found and has the same length
                    else if (value[0].indexOf(compoundOne[1]) > -1  && value[0].length() == compoundOne[1].length()) {
                        newSecondMole[1] = Integer.parseInt(value[1]);  // store the charge
                    }

                    // validate if the ions the user inputs a correct ion name/formula
                    if (foundIon[0] == false && value[0].length() == compoundOne[0].length()) {
                        foundIon[0] = value[0].contains(compoundOne[0]);
                    }
                    if (foundIon[1] == false  && value[0].length() == compoundOne[1].length()) {
                        foundIon[1] = value[0].contains(compoundOne[1]);
                    }
                    if (foundIon[2] == false  && value[0].length() == compoundTwo[0].length()) {
                        foundIon[2] = value[0].contains(compoundTwo[0]);
                    }
                    if (foundIon[3] == false  && value[0].length() == compoundTwo[1].length()) {
                        foundIon[3] = value[0].contains(compoundTwo[1]);
                    }
                }

                // this is the input validation for word equations
                // start validating at row 62
                if (wordOrChemical > 5 && accum > 61) {
                    // validate if the ions the user inputs a correct ion name/formula
                    if (foundIon[0] == false && value[0].length() == compoundOne[0].length()) {
                        foundIon[0] = value[0].contains(compoundOne[0]);
                    }
                    if (foundIon[1] == false  && value[0].length() == compoundOne[1].length()) {
                        foundIon[1] = value[0].contains(compoundOne[1]);
                    }
                    if (foundIon[2] == false  && value[0].length() == compoundTwo[0].length()) {
                        foundIon[2] = value[0].contains(compoundTwo[0]);
                    }
                    if (foundIon[3] == false  && value[0].length() == compoundTwo[1].length()) {
                        foundIon[3] = value[0].contains(compoundTwo[1]);
                    }
                }
            }
        }
        // The two catch files are for if the file is not found from the file path, they will print the files' stack trace
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        // Finally block that will be executed in every case, success or caught exception
        finally{
            // Close the bufferscanner if all lines in the file is read
            if(br != null){
                try{
                    br.close();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
    /**@Description: Determine if the compounds that the user inputs have a space between two elements. 
     * @author Leo Shi
     * @param line
     * @return space 
     */
    public static boolean containsSpace(String line){
        boolean space= false; 
        if(line != null){
            // if there is space inside, return true
            for(int i = 0; i < line.length(); i++){
    
                if(line.charAt(i) == ' '){
                space= true;
                }
            }
        }
        return space;
    }
}
