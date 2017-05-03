import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.junit.Test;

public class EventsTest {

	Events events = new Events("Event Text", "Jan 1 2017", "09:00", "Action", "Type", "2");
	
	@Test
	public void testGetDate() {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH);
		assertEquals(LocalDate.parse("Jan 1 2017", dateFormatter), events.getDate());
	}
	
	@Test
	public void testGetEventText() {
		assertEquals("Event Text", events.getEventText());
	}
	
	@Test
	public void testGetEventType() {
		String[] type = new String[3];
		type[0] = "Action";
		type[1] = "Type";
		type[2] = "2017-01-03";
		assertArrayEquals(type, events.getEventType());
	}
	
	@Test
	public void testGetTime() {
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:m");
		assertEquals(LocalTime.parse("09:00", timeFormatter), events.getTime());
	}
	
}
