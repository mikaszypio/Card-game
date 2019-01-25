package cardgame;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import cardgame.model.Room;
import cardgame.repositories.RoomRepository;
import cardgame.repositories.UserRepository;
import cardgame.services.IRoomService;
import cardgame.services.RoomService;

@RunWith(SpringRunner.class)
public class RoomServiceTest {
	
	@TestConfiguration
	static class RoomServiceTestContextConfiguration {
		
		@Bean
		public IRoomService iRoomService() {
			return new RoomService();
		}
	}
	
	@Autowired
	private IRoomService iRoomService;
	
	@MockBean
	private RoomRepository roomRepository;
	
	@MockBean
	private UserRepository userRepository;
	
	@Test
	public void whenGetEmptyRoom_thenTestroomShouldBeFound() {
		Room room = new Room("testroom");
		Mockito.when(roomRepository.findByActiveAndReadiness(room.getActive(), room.getReadiness())).thenReturn(room);
		Room found = iRoomService.getEmptyRoom("testroom");
		assertEquals("testroom", found.getName());
	}
}
