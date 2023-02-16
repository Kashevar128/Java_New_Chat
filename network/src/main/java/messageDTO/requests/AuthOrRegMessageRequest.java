package messageDTO.requests;

import common.ClientProfile;
import messageDTO.Message;
import messageDTO.TypeMessage;

public class AuthOrRegMessageRequest implements Message {

    private final TypeMessage typeMessage = TypeMessage.SERVICE_MESSAGE_AUTH_REG;

    private final ClientProfile clientProfile;

    private final int operation;

    public AuthOrRegMessageRequest(ClientProfile clientProfile, int operation) {
        this.clientProfile = clientProfile;
        this.operation = operation;
    }

    @Override
    public TypeMessage getTypeMessage() {
        return typeMessage;
    }

    public ClientProfile getClientProfile() {
        return clientProfile;
    }

    public int getOperation() {
        return operation;
    }
}
