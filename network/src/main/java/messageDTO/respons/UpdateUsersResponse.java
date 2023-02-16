package messageDTO.respons;

import common.ClientProfile;
import messageDTO.Message;
import messageDTO.TypeMessage;
import java.util.List;

public class UpdateUsersResponse implements Message {

    private final TypeMessage typeMessage = TypeMessage.SERVICE_MESSAGE_UPDATE_USERS;

    private final List<ClientProfile> profilesUsers;

    public UpdateUsersResponse(List<ClientProfile> profilesUsers) {
        this.profilesUsers = profilesUsers;
    }

    public List<ClientProfile> getProfilesUsers() {
        return profilesUsers;
    }

    @Override
    public TypeMessage getTypeMessage() {
        return typeMessage;
    }
}
