package cardgame.services;

import cardgame.model.User;
import java.util.List;

public interface IUserService {

	public User createUser(String username, String password);
	public List<User> findAllPlayers();
	public List<User> getRanking();
	public void increaseScore(List<User> usersLost, List<User> usersWon);
	public User save(User user);
	public User updateUser(User user, String plainPassword);
	public User updateUser(User user, String username, String plainPassword);
}
