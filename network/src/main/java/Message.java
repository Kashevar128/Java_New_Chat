import java.io.Serializable;

public class Message<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private T obj;
    private TypeMessage typeMessage;

    public T getObj() {
        return obj;
    }

    public TypeMessage getTypeMessage() {
        return typeMessage;
    }

    public Message(T obj, TypeMessage typeMessage) {
        this.obj = obj;
        this.typeMessage = typeMessage;
    }
}
