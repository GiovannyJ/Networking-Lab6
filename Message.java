import java.io.Serializable;

public class Message implements Serializable{
    //Properties
    private String name;
    private String response;

    //Constructor
    public Message(String name, String response){
        this.name = name;
        this.response = response;
    }

    //Getters
    public String getName(){
        return this.name;
    }

    public String getResponse(){
        return this.response;
    }

    //Setters
    public void setName(String name){
        this.name = name;
    }

    public void setResponse(String response){
        this.response = response;
    }
    
}
