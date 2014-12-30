
public class book_data {

	String name;
	int cited;
	int year;
	
	public book_data()
	{
		name = "";
		cited = 0;
	}

	public book_data(String name,int cited,int year)
	{
		this.name = name;
		this.cited = cited;
		this.year = year;
	}
}
