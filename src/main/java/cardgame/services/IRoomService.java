package cardgame.services;

import java.util.List;

import cardgame.game.model.Gracz;
import cardgame.model.Room;
import cardgame.model.User;

public interface IRoomService {

	public boolean checkReadiness(Room room);
	public Room getEmptyRoom(String name);
	public void joinRoom(User user, Room room);
	public void leaveRoom(User user);
	public Room save(Room room);
}
