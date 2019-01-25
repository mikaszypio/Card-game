package cardgame;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import cardgame.model.User;
import cardgame.repositories.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void whenFindByUsername_playerExists_thenReturnThisUser() {
		//given
		User user = new User("testuser");
		entityManager.persist(user);
		entityManager.flush();
		
		//when
		User found = userRepository.findByUsername(user.getUsername());
		
		//then
		assertEquals(user, found);
	}
	
	@Test
	public void whenFindByUsername_playerNotExists_thenFoundIsNull() {
		
		//when
		User found = userRepository.findByUsername("test");
		
		//then
		assertEquals(null, found);
	}

}
