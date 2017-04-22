import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
	private LocalDate date;
	private LocalTime time;
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH);
	private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:m");
	
	public Events(String eventText, String date, String time)
	{
		this.eventText = eventText;
		this.date = LocalDate.parse(date, dateFormatter);
		System.out.println(time);
		this.time = LocalTime.parse(time, timeFormatter);
	}
	
	public String getEventText()
	{
		return eventText;
	}
	
	public LocalDate getDate()
	{
	
		return date;
	}
	
	public LocalTime getTime()
	{
		return time;
	}

}
