import java.io.Serializable;


/**
 * Created by evanllewellyn on 8/12/15. The task class is what stores all of the users
 * tasks for the application. Stores a task name, date, and notes for the given task.
 */

public class task implements Serializable{
    public String name;
    public String[] timeAndDate = new String[3];
    public String taskNotes;

    public task(String name1, String month, String day, String year, String notes) {

        name = name1;

        timeAndDate[0] = month;
        timeAndDate[1] = day;
        timeAndDate[2] = year;

        taskNotes = notes;

    }
}
