import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * 
 */

/**
 * @author jon
 *
 */
public class Events {
	
	private String eventText;
	private LocalDateTime dateTime;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d yyyy k:m", Locale.ENGLISH);
	
	public Events(String eventText, String dateTime)
	{
		this.eventText = eventText;
		this.dateTime = LocalDateTime.parse(dateTime, formatter);	
	}
	
	public String getEventText()
	{
		return eventText;
	}
	
	public LocalDateTime getDateTime()
	{
		return dateTime;
	}

}
