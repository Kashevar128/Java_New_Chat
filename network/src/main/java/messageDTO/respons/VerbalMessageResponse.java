package messageDTO.respons;
import common.ClientProfile;
import messageDTO.Message;
import messageDTO.TypeMessage;

public class VerbalMessageResponse implements Message {

    private final TypeMessage typeMessage = TypeMessage.VERBAL_MESSAGE;

    private final String message;

    private final ClientProfile clientProfile;

    public VerbalMessageResponse(String message, ClientProfile clientProfile) {
        this.message = message;
        this.clientProfile = clientProfile;
    }

    @Override
    public TypeMessage getTypeMessage() {
        return typeMessage;
    }

    public String getMessage() {
        return message;
    }

    public ClientProfile getClientProfile() {
        return clientProfile;
    }
}
