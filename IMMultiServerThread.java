import java.net.*;
import java.io.*;

public class IMMultiServerThread extends Thread {
    private Socket socket = null;
    private String userName = "Server";

    public IMMultiServerThread(Socket socket) {
        super("IMMultiServerThread");
        this.socket = socket;
    }
    
    public void run() {

        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            IMProtocol kkp = new IMProtocol();
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String outputLine;

            //*SEND INITIAL MESSAGE TO CLIENT THAT THE CONNECTION IS ESTABLISHED */
            outputLine = kkp.processInput(null);
            Message message = new Message(this.userName, outputLine);
            out.writeObject(message);
            System.out.println(this.userName + ": " + outputLine);

            Message inMessage = (Message) in.readObject();
            
            
            //**WHILE YOU CAN READ INPUT FROM THE BUFFER STREAM */
            while ((inMessage.getResponse()) != null) {
                //**ALL MESSAGES IN THIS THREAD ARE FROM THE USERNAME THAT IS SAVED */
                //*print to this thread */
                System.out.println(inMessage.getName() + ": " + inMessage.getResponse());
                //*determine output */
                outputLine = kkp.processInput(inMessage.getResponse());

                //*If the output is bye close connection */
                if (outputLine.equalsIgnoreCase("Bye.")){
                    message = new Message(this.userName, outputLine);
                    out.writeObject(message);
                    break;
                }

                //*GET INPUT FROM SERVER SIDE */
                System.out.print(this.userName + ": ");
                outputLine = stdIn.readLine();
                //*SEND OUTPUT TO CLIENT ON THREAD */
                message = new Message(this.userName, outputLine);
                out.writeObject(message);

                try{
                    if(!outputLine.equalsIgnoreCase("Bye."))
                        inMessage = (Message) in.readObject();
                }catch (ClassNotFoundException cnfe){
                    System.err.println("IMServerThread: Problem reading object: class not found");
                    System.exit(1);
                }
            }
            //*CLOSE CONNECTION IF YOU ARE DONE */
            socket.close();
            in.close();
            out.close();
        } catch (ClassNotFoundException e){
            System.err.println("IMClient Class not found");
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}