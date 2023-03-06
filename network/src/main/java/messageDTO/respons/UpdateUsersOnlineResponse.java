package messageDTO.respons;

import common.ClientProfile;
import messageDTO.Message;
import messageDTO.TypeMessage;
import java.util.List;

public class UpdateUsersOnlineResponse implements Message {

    private final TypeMessage typeMessage = TypeMessage.SERVICE_MESSAGE_UPDATE_ONLINE_USERS;

    private final List<ClientProfile> profilesUsersOnline;

    public UpdateUsersOnlineResponse(List<ClientProfile> profilesUsersOnline) {
        this.profilesUsersOnline = profilesUsersOnline;
    }

    public List<ClientProfile> getProfilesUsersOnline() {
        return profilesUsersOnline;
    }

    @Override
    public TypeMessage getTypeMessage() {
        return typeMessage;
    }
}
