import java.io.*;
import java.net.*;

public class IMClient {
    public static void main(String[] args) throws IOException {
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        Message inMessage = null;
        Socket IMSocket = null;
        
        if (args.length != 3) {
            System.err.println(
                "Usage: java EchoClient <host name> <port number> <username>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        //*GET USERNAME AS ONE OF THE ARGS */
        String userName = args[2];

        try {
            IMSocket = new Socket(hostName, portNumber);
            out = new ObjectOutputStream(IMSocket.getOutputStream());
            in = new ObjectInputStream(IMSocket.getInputStream());
            inMessage = (Message) in.readObject();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        } catch (ClassNotFoundException e){
            System.err.println("IMClient Class not found");
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;


        //*WHILE BUFFER STREAM IS OPEN FROM SERVER */
        while ((fromServer = inMessage.getResponse()) != null) {
            //*PRINT MESSAGES FROM SERVER */
            System.out.println(inMessage.getName() + ": " + fromServer);
            //*IF MESSAGE IS BYE. END CONNECTION */
            if (fromServer.equalsIgnoreCase("Bye."))
                break;
            
            //*GET INPUT FROM CLIENT SIDE */
            System.out.print(userName + ": ");
            fromUser = stdIn.readLine();

            //*IF THERE IS A VALUE ENTERED -> SEND TO SERVER */
            if (fromUser != null) {
                Message message = new Message(userName, fromUser);
                out.writeObject(message);
            }

            try {
                if (!fromServer.equalsIgnoreCase("Bye."))
                    inMessage = (Message) in.readObject();
            }catch (ClassNotFoundException cnfe){
                System.err.println("IMClient: Problem reading object: class not found");
                System.exit(1);
            }
        }
        out.close();
        in.close();
        stdIn.close();
        IMSocket.close();
    }
}