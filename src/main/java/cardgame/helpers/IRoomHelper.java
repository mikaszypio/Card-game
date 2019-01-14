package cardgame.helpers;

import cardgame.model.Room;
import cardgame.model.User;

public interface IRoomHelper {

	public void createRoom(String name);
	public void joinRoom(User user, Room room);
	public void leaveRoom(User user);
}
