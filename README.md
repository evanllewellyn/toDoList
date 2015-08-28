# toDo
toDo is a to do list application written in Java and features a GUI created with JavaFX and CSS.

#Functions

toDo allows a user to upload tasks by date in the form of mm/dd/yyyy. Each task can also include a text note.

toDo has three different task sorting functions, task name, date, and today. Sorting by date requires the form mm/dd/yyyy and displays all tasks assigned to that date. Today works the same way but displays the current days tasks, not requiring a date input. An example of this sort can be seen below. 

![alt text](https://github.com/evanllewellyn/toDoList/blob/master/toDoPic/sortToday.png "sort by today")

Sort by name displays alls tasks with the entered name, ignoring cases. It displays the tasks in the form of 
<taskname> | date: <mm-dd-yyyy>, an example of this can be seen below. 

![alt text](https://github.com/evanllewellyn/toDoList/blob/master/toDoPic/sortname.png "sort by name")

In addition to the sorting functions, toDo can also remove tasks as they are completed with the "Completed" button below the task list display. The "Notes" button is also located below the task list display. The "notes" button 
displays a popup info box with the text note that was entered with the selected task. An example of this function can be seen below. 

![alt text](https://github.com/evanllewellyn/toDoList/blob/master/toDoPic/showcomment.png "note function")

Finally, toDo keeps the user's task data persistant over multiple uses through object serialization. The data is written to a .txt file in its local directory. To save the data the user must hit the large "Save" button, which launches a popup confirmation box. toDo also features a "Clear All Tasks" button that will delete all task data.
The save function is shown below. 

![alt text](https://github.com/evanllewellyn/toDoList/blob/master/toDoPic/savepic.png "save example")



Written by Evan Llewellyn, August 2015. 






