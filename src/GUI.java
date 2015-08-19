import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



//TO DO: finish up doList (more sorting), add category/description on top part of gui and possibly in center list.
// In center list, add button for selected task brings up a new window with the NOTES.
// make the textFields reset after hitting buttons.
// CSS stuff. dope background/colors/resize
// add warning window on clear button, save confirmed window on save.
// comment and clean up shit code

/**
 * Created by evanllewellyn on 8/13/15.
 */
public class GUI extends Application {

    TextField month;
    TextField day;
    TextField year;
    TextField taskName;
    TextField notes;

    ListView<String> tasks;

    TextField taskDesF;
    TextField monthS;
    TextField dayS;
    TextField yearS;

    //This arrayList of tasks will contain the tasks from doList's taskList that match the last form of sorting.
    ArrayList<task> currentTasks;

    @Override
    public void start(Stage primaryStage) {

        //Reading in the doList object from the stored data in data.txt
        fileStore fs = new fileStore();
        doList list = fs.readSavedList();

        primaryStage.setTitle("To Do Organizer");


        //Using a BorderPane Layout
        BorderPane borderPane = new BorderPane();

        /*
         * TOP PORTION OF BORDERPANE: Task adding.
         */


        // Creating HBox for top section of the borderPane layout
        HBox topAdd = new HBox();
        topAdd.setSpacing(10);

        //Creating label for top section of layout
        Label topLabel = new Label("ADD TASK:");

        /*
         * Creating button for top section of layout. When action is performed
         * reads data entered from the text fields. Uses regular expressions
         * to make sure the dates are appropriate before creating a task and
         * adding it to doList's taskList. Clears the text fields after each add attempt.
         */
        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            String mn = month.getText();
            String da = day.getText();
            String yr = year.getText();
            String des = taskName.getText();
            String no = notes.getText();

            Pattern mreg = Pattern.compile("^(0|1)[\\d]$");
            Pattern dreg = Pattern.compile("^(0|1|2|3)[\\d]$");
            Pattern yreg = Pattern.compile("^\\d\\d\\d\\d$");

            Matcher mmatch = mreg.matcher(mn);
            Matcher dmatch = dreg.matcher(da);
            Matcher ymatch = yreg.matcher(yr);

            boolean mbol = mmatch.matches();
            boolean dbol = dmatch.matches();
            boolean ybol = ymatch.matches();

            if (mbol && dbol && ybol && des.length() > 0) {
                list.taskList.add(new task(des, mn, da, yr, no));
                infoBox.infoB("Task Added", "Task successfully added.");
            } else if( des.length() == 0) {
                infoBox.infoB("No Task Name Specified", "You must specify a task name.");
            } else {
                infoBox.infoB("Incorrect Date Input", "Incorrect Date Input. \n Enter a valid date in the form mm/dd/yyyy\n" +
                    "Example: August 1st, 2015 --> 08/01/2015");
            }
            month.clear();
            day.clear();
            year.clear();
            taskName.clear();
            notes.clear();
        });

        //Creating and configuring each text field for the top section of the borderpane, will be used for add function
        month = new TextField();
        month.setPromptText("Month: mm");
        month.setMaxWidth(100);

        day = new TextField();
        day.setPromptText("Day: dd");
        day.setMaxWidth(100);

        year = new TextField();
        year.setPromptText("Year: yyyy");
        year.setMaxWidth(100);

        taskName = new TextField();
        taskName.setPromptText("Task Name");

        notes = new TextField();
        notes.setPromptText("Notes");
        notes.setMaxWidth(150);

        //adding all of the textFields and add button to the top HBox, adds HBox to top of borderPane layout
        topAdd.getChildren().addAll(topLabel, month, day, year, taskName, notes, addButton);
        borderPane.setTop(topAdd);


        /*
         *  CENTER PORTION OF BORDERPANE: Info display.
         */

        // Creating VBox for this section.
        VBox center = new VBox();
        center.setSpacing(5);


        //Label for the ListView below
        Label centerDes = new Label("Today's To Do List");

        //Creating ListView that will display information of the list
        tasks = new ListView<>();
        tasks.setPrefWidth(300);

        //Initially loads today's tasks to the ListView whenever the toDoList is started.
        currentTasks = list.printToday();
        for(int i = 0; i < currentTasks.size(); i++) {
            task curTask = currentTasks.get(i);
            String s;
            s = curTask.name;
            tasks.getItems().add(s);
        }

        //Creating Hbox for buttons under the VList in the center section of BorderPane
        HBox centerHB = new HBox();

        /*
         * Creating "Completed" button. When action is performed
         * matches the selected text with its corresponding
         * task object in the currentTasks arrayList. Removes the text from the ListView.
         * Removes the task from the currentTasks list and from the doList's taskList.
         */
        Button completedB = new Button("Completed");
        completedB.setOnAction(event6 -> {
            FocusModel<String> s = tasks.getFocusModel();
            String str = s.getFocusedItem();

            if(str.contains("| Date: ")) {
                for(int i = 0; i < currentTasks.size(); i++) {

                    task curTask = currentTasks.get(i);
                    String dateString = curTask.name + " | Date: " + curTask.timeAndDate[0] + "-" + curTask.timeAndDate[1] +
                            "-" + curTask.timeAndDate[2];

                    if(str.compareTo(dateString) == 0) {
                        // below line used to exit loop after removal.
                        i += currentTasks.size();
                        tasks.getItems().remove(str);
                        list.removeTask(curTask);
                        currentTasks.remove(curTask);
                    }
                }

            } else {
                for (int i = 0; i < currentTasks.size(); i++) {
                    task curTask = currentTasks.get(i);
                    if (curTask.name.compareTo(str) == 0) {
                        // below line used to exit loop after removal.
                        i += currentTasks.size();
                        tasks.getItems().remove(str);
                        list.removeTask(curTask);
                        currentTasks.remove(curTask);
                    }
                }
            }
            infoBox.infoB("Completed", "Task has been removed from the list.");

        });


        /*
         * Creates the "notes" button. Matches the focused text from the ListView
         * with its appropriate task by iterating through currentTasks. If matched
         * displays an InfoBox alert window with the tasks notes.
         */
        Button notesB = new Button("Notes");
        notesB.setOnAction(event5 -> {
            FocusModel<String> s = tasks.getFocusModel();
            String str = s.getFocusedItem();


            for(int i = 0; i < currentTasks.size(); i++) {
                task curTask = currentTasks.get(i);
                String dateString = curTask.name + " | Date: " + curTask.timeAndDate[0] + "-" + curTask.timeAndDate[1] + "-" + curTask.timeAndDate[2];
                if (curTask.name.compareTo(str) == 0 || str.compareTo(dateString) == 0) {
                    String curDes = curTask.taskNotes;
                    if (curDes.compareTo("") != 0) {
                        infoBox.infoB("Notes", curDes);
                    } else {
                        infoBox.infoB("Notes", "No notes for this task.");
                    }
                }
            }
        });

        //Adding specifications and components to the HBox that will go into the VBox for the center
        centerHB.getChildren().addAll(completedB, notesB);
        centerHB.setSpacing(20);
        centerHB.setAlignment(Pos.CENTER);

        //Adding the HBox to the VBox that will go into the center
        center.getChildren().addAll(centerDes, tasks, centerHB);
        center.setAlignment(Pos.CENTER);

        //Adding the VBox into the center portion of the BorderPane layout
        borderPane.setCenter(center);

        /*
         * LEFT PORTION OF BORDERPANE: Types of task sorting.
         */

        //Creating VBox and its specifications for left side
        VBox left = new VBox();
        left.setSpacing(30);


        //Creating label for left side and adding its corresponding CSS design
        Label leftSideDes = new Label("                                Task Sorting");
        leftSideDes.setPrefWidth(500);
        leftSideDes.getStyleClass().add("leftLabel");

        //Creating HBox for task sorting on left side
        HBox leftTaskSec = new HBox();
        leftTaskSec.setSpacing(3);

        //Creating label and specifications for task sorting section
        Label taskDes = new Label(" Sort by Task Name:");
        taskDes.setPrefWidth(180);

        //Creating textField for the task sorting
        taskDesF = new TextField();
        taskDesF.setPromptText("Task Name");

        /*
         * Creating Sort button. If the text field is empty when action occurs, creates a InfoBox popup window.
         * Otherwise calls doList's printName method which will return a list of tasks that match the
         * indicated name. The task list returned is set the currentTasks field. currentTasks is then iterated
         * through and each task is printed the ListView "tasks" in the center portion of the BorderPane layout.
         */
        Button taskDesB = new Button("Sort!");
        taskDesB.setOnAction(event -> {
            String tName = taskDesF.getText();
            if(tName.length() == 0) {
                infoBox.infoB("No Task Name Specified", "You must specify a task name.");
            } else {
                currentTasks = list.printName(tName);

                tasks.getItems().clear();
                for (int i = 0; i < currentTasks.size(); i++) {
                    task curTask = currentTasks.get(i);
                    String s = curTask.name + " | Date: " + curTask.timeAndDate[0] + "-" + curTask.timeAndDate[1] + "-" + curTask.timeAndDate[2];
                    tasks.getItems().add(s);
                }
            }
            taskDesF.clear();
        });

        //adding all components to the task sorting HBox
        leftTaskSec.getChildren().addAll(taskDes, taskDesF, taskDesB);

        //Creating an HBox and its specifications for a sort by date function
        HBox leftDaySec = new HBox();
        leftDaySec.setSpacing(3);
        leftDaySec.setPrefWidth(700);

        //Creating label for the sort by date function
        Label dateSec = new Label("      Sort by Date:");
        dateSec.setPrefWidth(180);

        //Creating textFields and their specifications for month, day, and year
        monthS = new TextField();
        monthS.setPromptText("Month: mm");
        monthS.setPrefWidth(100);

        dayS = new TextField();
        dayS.setPromptText("Day: dd");
        dayS.setPrefWidth(100);

        yearS = new TextField();
        yearS.setPromptText("Year: yyyy");
        yearS.setPrefWidth(100);

        /*
         * Creating button for sort. When action is performed checks for valid date inputs with regular expressions.
         * If incorrect, creates an InfoBox popup window. Otherwise calls doList's printDate method which will return
         * a list of tasks that match the indicated name. The task list returned is set the currentTasks field.
         * currentTasks is then iterated through and each task is printed the ListView "tasks" in the center portion
         * of the BorderPane layout.
         */
        Button dateB = new Button("Sort!");
        dateB.setOnAction(event -> {
            String mn = monthS.getText();
            String da = dayS.getText();
            String yr = yearS.getText();

            Pattern mreg = Pattern.compile("^(0|1)[\\d]$");
            Pattern dreg = Pattern.compile("^(0|1|2|3)[\\d]$");
            Pattern yreg = Pattern.compile("^\\d\\d\\d\\d$");

            Matcher mmatch = mreg.matcher(mn);
            Matcher dmatch = dreg.matcher(da);
            Matcher ymatch = yreg.matcher(yr);

            boolean mbol = mmatch.matches();
            boolean dbol = dmatch.matches();
            boolean ybol = ymatch.matches();

            if (mbol && dbol && ybol) {
                currentTasks = list.printDate(mn, da, yr);

                tasks.getItems().clear();
                for (int i = 0; i < currentTasks.size(); i++) {
                    task curTask = currentTasks.get(i);
                    String s;
                    s = curTask.name;
                    tasks.getItems().add(s);
                }

            } else {
                infoBox.infoB("Incorrect Date Input", "Incorrect Date Input. \n Enter a valid date in the form mm/dd/yyyy\n" +
                        "Example: August 1st, 2015 --> 08/01/2015");
            }

            monthS.clear();
            dayS.clear();
            yearS.clear();
        });

        //Adding components to the sort by date HBox
        leftDaySec.getChildren().addAll(dateSec, monthS, dayS, yearS, dateB);

        //Creating HBox and specifications for the sort by today function
        HBox leftTodaySec = new HBox();
        leftTodaySec.setSpacing(3);

        //Creating label and specification for sort by today
        Label todaySec = new Label("     Sort by Today:");
        todaySec.setPrefWidth(180);

        /*
         * Creating button for the sort by today function. Upon action calls doList's printToday method which returns
         * a list of tasks that occur on the current day. This list is set to the currentTasks field.
         * It then iterates through currentTasks field and prints each task to the ListView in the
         * center portion of the BorderPane layout.
         */
        Button todayB = new Button("Sort!");
        todayB.setOnAction(event2 -> {
            currentTasks = list.printToday();

            tasks.getItems().clear();
            for (int i = 0; i < currentTasks.size(); i++) {
                task curTask = currentTasks.get(i);
                String s;
                s = curTask.name;
                tasks.getItems().add(s);
            }
        });

        // Adding components for the sort by today function to the HBox
        leftTodaySec.getChildren().addAll(todaySec, todayB);

        // Adding the 3 sort type's HBoxes to the VBox that will be on the left side of the borderPane layout.
        left.getChildren().addAll(leftSideDes, leftTaskSec, leftDaySec, leftTodaySec);
        left.setMaxWidth(600);

        // Setting the left side of the borderPane to the VBox containing the sorting types.
        borderPane.setLeft(left);

        /*
         * BOTTOM PORTION OF BORDERPANE: Clear and save.
         */

        //Creating HBox and specifications for bottom section
        HBox bottom = new HBox();
        bottom.setSpacing(100);


        /*
         * Creating button to clear tasks. When an action is performed it creates a ConfirmBox window. Based
         * on the value returned from the ConfirmBox window it will calls doList's clearTasks method or do nothing.
         */
        Button clear = new Button("Clear All Tasks");
        clear.setOnAction(event1 -> {
            boolean result = ConfirmBox.confirm("Clear?", "Are you sure you want to clear?\nAll data will be lost.");
            if (result == true) {
                list.clearTasks();
            }
        });

        /*
         * Creating button to save tasks. When an action is performed it creates a ConfirmBox window. Based
         * on the value returned from the ConfirmBox window it will call fileStore's saveList() method, which
         * will save the current doList object into the data.txt file.
         */
        Button save = new Button("Save");
        save.setPrefWidth(200);
        save.setOnAction(event -> {
            boolean result = ConfirmBox.confirm("Save", "Are you sure you want to save?");
            if (result == true) {
                fs.saveList(list);
            }
        });

        //Adding the two buttons to the HBox for the bottom section.
        bottom.getChildren().addAll(clear, save);

        //Adding the HBox to the bottom section of the BorderPane layout.
        borderPane.setBottom(bottom);

        //Creating a Scene containing the borderPane and its added components, size 500x1000
        Scene scene = new Scene(borderPane, 500, 1000);

        //Adding the CSS design from DoList.css to the scene.
        scene.getStylesheets().add("DoList.css");

        //Adding the scene and specifications to the stage.
        primaryStage.setScene(scene);
        primaryStage.setHeight(500);
        primaryStage.setWidth(1000);
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}

