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
	public User createAnon() {
		Role role = roleRepository.findById(3).get();
		List<User> anons = userRepository.findAllTemporaryUsers(Sort.by("username").ascending());
		int number = anons.size() + 1;
		User anon = new User("anon" + number, "pass" + number, role);
		return userRepository.save(anon);
	}
	
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
	
	@Override
	public User getUnregistered() {
		List<User> unregistered = userRepository.findTemporaryUsers(Sort.by("username").ascending());
		if (!unregistered.isEmpty()) {
			return unregistered.get(0);
		}
		
		return null;
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
	
	@Override
	public User findFreeAnon() {
		User unregistered = getUnregistered();
		if (unregistered == null) {
			unregistered = createAnon();
		}
		
		return unregistered;
	}
}
