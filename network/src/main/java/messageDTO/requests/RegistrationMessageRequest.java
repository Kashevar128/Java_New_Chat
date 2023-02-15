package messageDTO.requests;

import common.ClientProfile;
import messageDTO.Message;
import messageDTO.TypeMessage;

public class RegistrationMessageRequest implements Message {

    private final TypeMessage typeMessage = TypeMessage.SERVICE_MESSAGE_REGISTRATION;

    private final ClientProfile clientProfile;

    public RegistrationMessageRequest(ClientProfile clientProfile) {
        this.clientProfile = clientProfile;
    }

    public ClientProfile getClientProfile() {
        return clientProfile;
    }

    @Override
    public TypeMessage getTypeMessage() {
        return typeMessage;
    }
}
