package cardgame.services;

import cardgame.model.Role;
import cardgame.model.User;
import cardgame.repositories.RoleRepository;
import cardgame.repositories.UserRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserRepository userRepository;

	@Override
	public User createUser(String username, String password) {
		
		Role role = roleRepository.findById(2).get();
		User user = new User(username, password, role);
		return userRepository.save(user);
	}

	@Override
	public List<User> findAllPlayers() {
		
		return userRepository.findAllPlayers(Sort.by("username").ascending());
	}
	
	@Override
	public List<User> getRanking() {

		return userRepository.findAllPlayers(Sort.by("score").descending());
	}
	
	public float recalculateScore(User user) {
		return (float) user.getGamesWon() / user.getGames() * 100;
	}

	@Override
	@Transactional
	public void increaseScore(List<User> usersLost, List<User> usersWon) {

		usersLost.stream().map((user) -> {
			user.setGames(user.getGames() + 1);
			return user;
		}).map((user) -> {
			user.setScore(recalculateScore(user));
			return user;
		}).forEachOrdered((user) -> {
			userRepository.save(user);
		});
		
		usersWon.stream().map((user) -> {
			user.setGames(user.getGames() + 1);
			return user;
		}).map((user) -> {
			user.setGamesWon(user.getGamesWon() +1);
			return user;
		}).map((user) -> {
			user.setScore(recalculateScore(user));
			return user;
		}).forEachOrdered((user) -> {
			userRepository.save(user);
		});
	}

	@Override
	public User save(User user) {

		return userRepository.save(user);
	}
	
	@Override
	public User updateUser(User user, String plainPassword) {

		return updateUser(user, user.getUsername(), plainPassword);
	}

	@Override
	public User updateUser(User user, String username, String plainPassword) {

		user.setUsername(username);
		user.setPassword(plainPassword);
		return userRepository.save(user);
	}
}
