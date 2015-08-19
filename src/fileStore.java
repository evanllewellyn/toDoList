

import java.io.*;

/**
 * Created by evanllewellyn on 8/13/15. fileStore uses object io to save and read a toDoList object
 * so that the user's to do list is saved in between application uses.
 */
public class fileStore{


    /*
     * Opens the output stream and writes the current doList. In the finally block
     * closes the stream if it has been opened.
     */
    public void saveList(doList dlist) {
        OutputStream outputS = null;
        ObjectOutputStream objoutS = null;

        try {

            outputS = new FileOutputStream("data.txt");
            objoutS = new ObjectOutputStream(outputS);
            objoutS.writeObject(dlist);
            objoutS.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (objoutS != null) {
                    objoutS.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * Opens the input stream and reads and casts the object to a doList. In the finally block
     * closes the stream if it has been opened. If it returns a FileNotFoundException
     * it indicates no data has been previously saved and creates a new empty doList.
     */

    public doList readSavedList() {
        doList dlist = null;
        InputStream inS = null;
        ObjectInputStream objinS = null;

        try {
            inS = new FileInputStream("data.txt");
            objinS = new ObjectInputStream(inS);
            dlist = (doList) objinS.readObject();

        } catch (FileNotFoundException e) {
            dlist = new doList();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (objinS != null) {
                    objinS.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return dlist;

    }

}
