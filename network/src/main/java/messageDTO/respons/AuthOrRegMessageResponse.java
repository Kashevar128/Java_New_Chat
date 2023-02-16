package messageDTO.respons;

import common.ClientProfile;
import messageDTO.Message;
import messageDTO.TypeMessage;

public class AuthOrRegMessageResponse implements Message {

    private final TypeMessage typeMessage = TypeMessage.SERVICE_MESSAGE_AUTH_REG;

    private final boolean regOK;

    private ClientProfile clientProfile;

    public AuthOrRegMessageResponse(boolean regOK) {
        this.regOK = regOK;
    }

    public AuthOrRegMessageResponse(boolean regOK, ClientProfile clientProfile) {
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
