import java.net.*;
import java.io.*;

public class IMProtocol {
    //** initialize state types
    private static final int WAITING = 0;
    private static final int IN_CONVERSATION = 1;

    //** initialize state
    private int state = WAITING;

    /**
     * Used to process the input variable and 
     * return an appropriate string as well as switches state
     * @param theInput: the variable to be processed
     * @return string according to the input variable
     */
    public String processInput(String theInput) {
        //*Initialize the output */
        String theOutput = null;

        if (state == WAITING) {
            /*Initial state is waiting so the output to send
            to the client is connection Established */
            theOutput = "Connection Established";
            //*change state since client is connected */
            state = IN_CONVERSATION;
            //*If the state is in conversation */
        } else if (state == IN_CONVERSATION) {
            if (!theInput.equalsIgnoreCase("Bye.")) {
                //*let server send its own output */
                theOutput = "";
            } else {
                //*Send the output bye */
                theOutput = "Bye.";
                //*change sate to waiting for new connection */
                state = WAITING;
            }
        }
        //*return what output was processed from the input */
        return theOutput;
    }
}