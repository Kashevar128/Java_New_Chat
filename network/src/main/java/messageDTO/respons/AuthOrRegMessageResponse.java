package messageDTO.respons;

import common.ClientProfile;
import messageDTO.Message;
import messageDTO.TypeMessage;

import java.util.List;

public class AuthOrRegMessageResponse implements Message {

    private final TypeMessage typeMessage = TypeMessage.SERVICE_MESSAGE_AUTH_REG;

    private final boolean regOK;

    private List<ClientProfile> clientProfiles;

    private List<VerbalMessageResponse> listDialogs;

    private ClientProfile clientProfile;

    public AuthOrRegMessageResponse(boolean regOK) {
        this.regOK = regOK;
    }

    public AuthOrRegMessageResponse(boolean regOK, ClientProfile clientProfile,
                                    List<ClientProfile> clientProfiles,
                                    List<VerbalMessageResponse> listDialogs) {
        this.regOK = regOK;
        this.clientProfile = clientProfile;
        this.clientProfiles = clientProfiles;
        this.listDialogs = listDialogs;
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

    public List<ClientProfile> getClientProfiles() {
        return clientProfiles;
    }

    public List<VerbalMessageResponse> getListDialogs() {
        return listDialogs;
    }
}
