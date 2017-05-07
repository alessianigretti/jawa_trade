import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.junit.Test;

public class EventsTest {

	Events events = new Events("Event Text", "Jan 1 2017", "09:00", "Action", "Type", "2");
	
	/**
	 * Test of the getDate method in the Events class. This test validates that the event date is parsed correctly.
	 */
	@Test
	public void testGetDate() {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH);
		assertEquals(LocalDate.parse("Jan 1 2017", dateFormatter), events.getDate());
	}
	
	/**
	 * Test of the getEventText method in the Events class. This test validates that the event information is passed properly.
	 */
	@Test
	public void testGetEventText() {
		assertEquals("Event Text", events.getEventText());
	}
	
	/**
	 * Test of the getEventType method in the Events class. This test validates that the event type is properly parsed. 
	 */
	@Test
	public void testGetEventType() {
		String[] type = new String[3];
		type[0] = "Action";
		type[1] = "Type";
		type[2] = "2017-01-03";
		assertArrayEquals(type, events.getEventType());
	}
	
	/**
	 * Test of the getTime method in the Events class. This test validates that the the time is parsed correctly.
	 */
	@Test
	public void testGetTime() {
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:m");
		assertEquals(LocalTime.parse("09:00", timeFormatter), events.getTime());
	}
	
	/**
	 * Test of the getTrigger method in the Events class. This test validates that the trigger method changes whether an event is active or not.
	 */
	@Test
	public void testTrigger(){
		assertFalse(events.isTrriggered());
		events.trigger();
		assertTrue(events.isTrriggered());
	}
	
}
