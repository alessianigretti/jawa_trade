import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

// TODO: Auto-generated Javadoc
/**
 * The Class Events.
 */

/**
 * @author jon
 *
 */
public class Events {
	
	private String eventText;
	private LocalDate date;
	private LocalTime time;
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH);
	private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:m");
	
	/**
	 * Instantiates a new events.
	 *
	 * @param eventText the event text
	 * @param date the date
	 * @param time the time
	 */
	public Events(String eventText, String date, String time)
	{
		this.eventText = eventText;
		this.date = LocalDate.parse(date, dateFormatter);
		System.out.println(time);
		this.time = LocalTime.parse(time, timeFormatter);
	}
	
	/**
	 * Gets the event text.
	 *
	 * @return the event text
	 */
	public String getEventText()
	{
		return eventText;
	}
	
	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public LocalDate getDate()
	{
	
		return date;
	}
	
	/**
	 * Gets the time.
	 *
	 * @return the time
	 */
	public LocalTime getTime()
	{
		return time;
	}

}
