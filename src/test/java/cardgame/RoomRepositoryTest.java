package cardgame;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import cardgame.model.Room;
import cardgame.repositories.RoomRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoomRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Test
	public void whenFindByActiveAndReadiness_roomFound_thenReturnThisRoom() {
		//given
		Room room = new Room("testroom");
		entityManager.persist(room);
		entityManager.flush();
		
		//when
		Room found = roomRepository.findByActiveAndReadiness(room.getActive(), room.getReadiness());
		
		//then
		assertTrue(found.getName().equals(found.getName()));
	}
	
	@Test(expected = NullPointerException.class)
	public void whenFindByActiveAndReadiness_roomNotFound_thenNullPointerException() {
		Room found = roomRepository.findByActiveAndReadiness(false, (byte) -1);
		
		assertTrue(found.equals(null));
	}
	
	@Test
	public void whenFindByName_roomFound_thenReturnThisRoom() {
		Room room = new Room("testroom");
		entityManager.persist(room);
		entityManager.flush();
		
		Room found = roomRepository.findByName(room.getName());
		
		assertTrue(found.getName().equals(room.getName()));
	}
	
	@Test(expected = NullPointerException.class)
	public void whenFindByName_roomNotFound_thenNullPointerException() {
		Room found = roomRepository.findByName("testroom");
		
		assertTrue(found.getName().equals(null));
	}

}
