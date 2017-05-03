import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * The Class Events is responsible for representing events that may affect the
 * simulation.
 * 
 * @author Jonathan Magbadelo
 */
public class Events {

	private String[] eventType = new String[3];
	private String eventText;
	private LocalDate date;
	private LocalTime time;
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH);
	private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:m");

	/**
	 * Instantiates a new event.
	 *
	 * @param eventText            the event text
	 * @param eventDate the event date
	 * @param eventTime the event time
	 * @param action the action
	 * @param type the type of the event
	 * @param duration the duration of the event
	 */
	public Events(String eventText, String eventDate, String eventTime, String action, String type, String duration) {
		this.eventText = eventText;
		this.date = LocalDate.parse(eventDate, dateFormatter);
		this.time = LocalTime.parse(eventTime, timeFormatter);
		eventType[0] = action;
		eventType[1] = type;
		eventType[2] = String.valueOf(date.plusDays(Integer.valueOf(duration)));
	}

	/**
	 * Gets the event text.
	 *
	 * @return the event text
	 */
	public String getEventText() {
		return eventText;
	}

	/**
	 * Gets the date of the event.
	 *
	 * @return the date of the event
	 */
	public LocalDate getDate() {

		return date;
	}

	/**
	 * Gets the time of the event.
	 *
	 * @return the time of the event
	 */
	public LocalTime getTime() {
		return time;
	}

	/**
	 * Gets the event type.
	 *
	 * @return the event type
	 */
	public String[] getEventType() {
		return eventType;
	}

}
