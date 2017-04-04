import java.time.LocalDate;
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
	private LocalDate dateTime;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH);
	
	public Events(String eventText, String dateTime)
	{
		this.eventText = eventText;
		this.dateTime = LocalDate.parse(dateTime, formatter);	
	}
	
	public String getEventText()
	{
		return eventText;
	}
	
	public LocalDate getDateTime()
	{
	
		return dateTime;
	}

}
