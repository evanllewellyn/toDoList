import java.io.*;
import java.time.MonthDay;
import java.time.Year;
import java.util.ArrayList;


/**
 * Created by evanllewellyn on 8/12/15. The doList class handles all of the backend of the application.
 * a doList object contains a list of tasks.
 */
public class doList implements Serializable{

    public ArrayList<task> taskList;

    //Constructor
    public doList() {
        taskList = new ArrayList<task>();
    }


    /*
     * printToday returns a list of tasks based on the current day. Calls printDate
     * with the current date as its parameters.
     */
    public ArrayList<task> printToday() {


        Year yr = Year.now();
        MonthDay md = MonthDay.now();

        String cDay = Integer.toString(md.getDayOfMonth());
        if (cDay.length() == 1){
            cDay = "0" + cDay;
        }
        String cMon = Integer.toString(md.getMonthValue());
        if (cMon.length() == 1){
            cMon = "0" + cMon;
        }
        String cYr = Integer.toString(yr.getValue());

        return printDate(cMon, cDay, cYr);
    }


    /*
     * printDate returns a list of tasks that occur on the date indicated by its parameters.
     *  Iterates through the doList's taskList matching each task's date fields.
     */
    public ArrayList<task> printDate(String m, String d, String yr) {
        ArrayList<task> todayL = new ArrayList<task>();

        for (int i = 0; i < taskList.size(); i++ ) {

            task curEle = taskList.get(i);

            if (curEle.timeAndDate[0].compareToIgnoreCase(m) == 0 && curEle.timeAndDate[1].compareToIgnoreCase(d) == 0 &&
                        curEle.timeAndDate[2].compareToIgnoreCase(yr) == 0) {

                todayL.add(taskList.get(i));
            }
        }

        return todayL;
    }

    /*
     * printName returns a list of tasks that match the parameter name.
     *  Iterates through the doList's taskList and adding each matching task
     *  to the list to be returned.
     */

    public ArrayList<task> printName(String name) {
        ArrayList<task> taskL = new ArrayList<task>();

        for (int i = 0; i < taskList.size(); i++) {
            task curEle = taskList.get(i);
            if (name.compareToIgnoreCase( curEle.name) == 0) {
                taskL.add(curEle);
            }
        }

        return taskL;
    }

    // removeTask removes the parameter task from doList's taskList.
    public void removeTask(task t) {
        taskList.remove(t);
    }

    // clearTask clears all tasks from doList's taskList.
    public void clearTasks() {
        taskList.clear();
    }


}

