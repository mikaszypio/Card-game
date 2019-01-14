package cardgame.helpers;

import java.time.OffsetTime;
import org.springframework.stereotype.Component;

@Component
public class ChatHelper implements IChatHelper {
	
	@Override
	public String getTime() {
		String time = OffsetTime.now().toString();
		return time.substring(0, time.length()-10);
	}
}
