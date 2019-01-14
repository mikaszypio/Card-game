package cardgame.helpers;

import cardgame.model.User;
import java.util.List;

public interface IUserHelper {

	public User createUser(String username, String password);
	public void increaseScore(List<User> users);
	public void updateUser(User user, String username, String plainPassword);
}
