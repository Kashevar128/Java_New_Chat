package messageDTO.respons;

import common.ClientProfile;
import messageDTO.Message;
import messageDTO.TypeMessage;

public class RegistrationMessageResponse implements Message {

    private final TypeMessage typeMessage = TypeMessage.SERVICE_MESSAGE_REGISTRATION;

    private boolean regOK;

    private ClientProfile clientProfile;

    public RegistrationMessageResponse(boolean regOK) {
        this.regOK = regOK;
    }

    public RegistrationMessageResponse(boolean regOK, ClientProfile clientProfile) {
        this.regOK = regOK;
        this.clientProfile = clientProfile;
    }

    @Override
    public TypeMessage getTypeMessage() {
        return typeMessage;
    }

    public boolean isRegOK() {
        return regOK;
    }

    public ClientProfile getClientProfile() {
        return clientProfile;
    }
}
