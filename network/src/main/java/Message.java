import java.io.Serializable;

public class Message<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private T obj;

    private ClientProfile clientProfile;

    private TypeMessage typeMessage;

    public T getObj() {
        return obj;
    }

    public TypeMessage getTypeMessage() {
        return typeMessage;
    }

    public ClientProfile getClientProfile() {
        return clientProfile;
    }

    public Message(T obj, ClientProfile clientProfile, TypeMessage typeMessage) {
        this.obj = obj;
        this.typeMessage = typeMessage;
    }
}
