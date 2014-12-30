import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;


public class cit_an {

	/**
	 * @param args
	 */
	
	private static String getUrlSource(String url) throws IOException {
        URL yahoo = new URL(url);
        System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");
        URLConnection yc = yahoo.openConnection();
        //yc.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_5_8; en-US) AppleWebKit/532.5 (KHTML, like Gecko) Chrome/4.0.249.0 Safari/532.5");
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuilder a = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
            a.append(inputLine);
        in.close();
        return a.toString();
    
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		final WebClient webClient = new WebClient();
		final JTextField text_auth_name =  new JTextField("chetan bhagat",20);
		JButton but_submit = new JButton("Submit");
		final JLabel label_res = new JLabel("Search results..");
		final JTextField text_range = new JTextField("2004-2009");
		text_range.setEnabled(false);
		
		final JRadioButton parta = new JRadioButton("Sort - citations",true);
		final JRadioButton partb = new JRadioButton("Sort - year",false);
		final JRadioButton partc = new JRadioButton("Sort - citations - range",false);
		final JRadioButton partd = new JRadioButton("Sort - year - range",false);
		final JRadioButton parte = new JRadioButton("Show - h-index and l-index",false);
		final JRadioButton partf = new JRadioButton("Show - Cit Stats",false);
		
		ButtonGroup grp = new ButtonGroup();
		grp.add(parta);
		grp.add(partb);
		grp.add(partc);
		grp.add(partd);
		grp.add(parte);
		grp.add(partf);
		
		parta.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				text_range.setEnabled(false);
			}
			
		});
		
		partb.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				text_range.setEnabled(false);
			}
			
		});
		
		partc.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				text_range.setEnabled(true);
			}
			
		});
		
		partd.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				text_range.setEnabled(true);
			}
			
		});
		parte.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				text_range.setEnabled(false);
			}
			
		});
		partf.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				text_range.setEnabled(false);
			}
			
		});
		
		//label_res.setMinimumSize(new Dimension(100,100));
		//label_res.setMaximumSize(new Dimension(100,100));
		//label_res.setPreferredSize(new Dimension(100,100));
		//label_res.setSize(100,100);
		
		but_submit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				String auth_name = text_auth_name.getText();
				auth_name = auth_name.replaceAll(" ", "+");
				String src;
				ArrayList<book_data> bookdata = new ArrayList<book_data>();
				ArrayList<book_data> bookdatatemp = new ArrayList<book_data>();
				book_data temp = new book_data();
				int cited;
				int year;
				int lastind;
				String[] cited_split;
				String[] year_split;
				try {
					/*final HtmlPage page_gsch = webClient.getPage("http://scholar.google.co.in/");
					final HtmlForm form_gsch = page_gsch.getHtmlElementById("f");
					final HtmlTextInput text = form_gsch.getInputByName("gs_hp_tsi");
					final HtmlSubmitInput sub = form_gsch.getInputByName("gs_hp_tsb");
					text.setText(auth_name);
					final HtmlPage page_gsch_auth = sub.click();
					label_res.setText(getUrlSource(page_gsch_auth.getUrl().toString()));*/
					src = getUrlSource("http://scholar.google.co.in/scholar?hl=en&q=" + auth_name + "&btnG=").toString();
					Document doc = Jsoup.parse(src);
					Element booklinkstemp = doc.getElementsByTag("html").first().getElementsByTag("body").first().getElementById("gs_top").getElementById("gs_bdy").getElementById("gs_res_bdy").getElementById("gs_ccl");
					Elements booklinks = booklinkstemp.getElementsByClass("gs_r");//.first().getElementsByClass("gs_ri");
					//Elements booklinkss = doc.getElementsByClass("gs_ri");
					//System.out.println(booklinks.toString());
					for(Element book:booklinks)
					{
						book = book.getElementsByClass("gs_ri").first();
						Element bookdet = book.getElementsByClass("gs_a").first();
						Element bookdetbold = bookdet.getElementsByTag("b").first();
								
						Element bookhead = book.getElementsByTag("h3").first();
						Element bookheadtype = bookhead.getElementsByTag("span").first();
						bookheadtype = bookheadtype.getElementsByClass("gs_ct1").first();
						//Element bookheadval = bookhead.getElementsByTag("a").last();
						Element bookheadval = bookhead.getAllElements().last();
						//Element bookheadval = bookhead.select("a").last();
						Element bookcited = book.getElementsByClass("gs_fl").first().getElementsByTag("a").first();
						if(bookheadtype.text().equals("[BOOK]"))
						{
							//bookdata += bookheadval.text() + "   " + bookcited.text() + "<br><br>";
							//System.out.println(bookheadval.text());
							cited_split = bookcited.text().split(" ");
							cited = Integer.parseInt(cited_split[2]);
							//System.out.println(cited);
							
							String str_bookdet = bookdet.toString();
							lastind = str_bookdet.lastIndexOf(bookdetbold.toString());
							str_bookdet = str_bookdet.substring(lastind+1);
							year_split = str_bookdet.split(" ");
							year = Integer.parseInt(year_split[2]);

							
							bookdata.add(new book_data(bookheadval.text(),cited,year));
						}
						
					}
					
					if(parta.isSelected())
					{
						//text_range.setEnabled(false);
						Collections.sort(bookdata,new Comparator<book_data>(){

							@Override
							public int compare(book_data arg0, book_data arg1) {
								return arg1.cited - arg0.cited;
							}
						
						});
						String res="";
						String resbook="";
						
						for(book_data templ:bookdata)
						{
							resbook = templ.name + "   " + " cited : " + templ.cited + "   " + "year : " + templ.year;
							System.out.println(resbook);
							res = res + resbook + "<br>";
						}
						System.out.println("-------------------------------");
						label_res.setText("<html>" + res + "</html>");
					
					}
				
					else if(partb.isSelected())
					{
						//text_range.setEnabled(false);
						Collections.sort(bookdata,new Comparator<book_data>(){

							@Override
							public int compare(book_data arg0, book_data arg1) {
								return arg1.year - arg0.year;
							}
						
						});
						String res="";
						String resbook="";
						
						for(book_data templ:bookdata)
						{
							resbook = templ.name + "   " + " cited : " + templ.cited + "   " + "year : " + templ.year;
							System.out.println(resbook);
							res = res + resbook + "<br>";
						}
						System.out.println("-------------------------------");
						label_res.setText("<html>" + res + "</html>");
					
					}
					
					else if(partc.isSelected())
					{
						//text_range.setEnabled(true);
						bookdatatemp.clear();
						String range_year = text_range.getText();
						String[] range_year_split = range_year.split("-");
						int start_year = Integer.parseInt(range_year_split[0]);
						int end_year = Integer.parseInt(range_year_split[1]);
						
						for(book_data tempyear : bookdata)
						{
							if(tempyear.year<start_year || tempyear.year>end_year)
							{
								//bookdata.remove(bookdata.indexOf(tempyear));
							}
							else bookdatatemp.add(tempyear);
						}
						
						bookdata=bookdatatemp;
						Collections.sort(bookdata,new Comparator<book_data>(){

							@Override
							public int compare(book_data arg0, book_data arg1) {
								return arg1.cited - arg0.cited;
							}
						
						});
						String res="";
						String resbook="";
						
						for(book_data templ:bookdata)
						{
							resbook = templ.name + "   " + " cited : " + templ.cited + "   " + "year : " + templ.year;
							System.out.println(resbook);
							res = res + resbook + "<br>";
						}
						System.out.println("-------------------------------");
						label_res.setText("<html>" + res + "</html>");
					
						
					}
					
					else if(partd.isSelected())
					{
						//text_range.setEnabled(true);
						bookdatatemp.clear();
						String range_year = text_range.getText();
						String[] range_year_split = range_year.split("-");
						int start_year = Integer.parseInt(range_year_split[0]);
						int end_year = Integer.parseInt(range_year_split[1]);
						
						for(book_data tempyear : bookdata)
						{
							if(tempyear.year<start_year || tempyear.year>end_year)
							{
								//bookdata.remove(bookdata.indexOf(tempyear));
							}
								else bookdatatemp.add(tempyear);
						}
						
						bookdata=bookdatatemp;
						Collections.sort(bookdata,new Comparator<book_data>(){

							@Override
							public int compare(book_data arg0, book_data arg1) {
								return arg1.year - arg0.year;
							}
						
						});
						String res="";
						String resbook="";
						
						for(book_data templ:bookdata)
						{
							resbook = templ.name + "   " + " cited : " + templ.cited + "   " + "year : " + templ.year;
							System.out.println(resbook);
							res = res + resbook + "<br>";
						}
						System.out.println("-------------------------------");
						label_res.setText("<html>" + res + "</html>");
					
					}
				
					else if(partf.isSelected())
					{
						//bookdatatemp.clear();
						int avgcite_p=0;
						//ArrayList<book_data> avgcite_y = new ArrayList<book_data>();
						int avgcite_y=0;
						int i=-1;
						int tempa = 0;
						int flag=1;
						
						Collections.sort(bookdata,new Comparator<book_data>(){

							@Override
							public int compare(book_data arg0, book_data arg1) {
								return arg1.year - arg0.year;
							}
						
						});
						System.out.println("Citation Statistics : ");
						System.out.println("Total citations in each year : ");
						for(book_data book_stat : bookdata)
						{
							flag=1;
							avgcite_p+=book_stat.cited;
							if(i==-1)
							{
								tempa = book_stat.year;
								i=1;
							}
							
							if(i==1)
							{
								//avgcite_y.add(book_stat);
								avgcite_y+=book_stat.cited;
							}
							if(book_stat.year==tempa && bookdata.get(1+bookdata.indexOf(book_stat)).year!=tempa)
							{
								i=0;
								System.out.println(tempa + " : " + avgcite_y);
								//flag=0;
							}
							if(i==0 && flag==1)
							{
								tempa = book_stat.year;
								avgcite_y+=book_stat.cited;
								i=1;
							}
							
						}
						
						avgcite_p/=bookdata.size();
						System.out.println("Avg citations per paper : " + avgcite_p);
						System.out.println("-------------------------------");
											
						
					}
					
				} catch (FailingHttpStatusCodeException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		});
		
		JFrame frame_main = new JFrame("Cit_An");
		frame_main.setLayout(new GridLayout(5,1));
		
		frame_main.add(text_auth_name);
		frame_main.add(text_range);

		frame_main.add(parta);
		frame_main.add(partb);
		frame_main.add(partc);
		frame_main.add(partd);
		frame_main.add(parte);
		frame_main.add(partf);
		
		frame_main.add(label_res);
		frame_main.add(but_submit);
		
		frame_main.pack();
		frame_main.setVisible(true);
	}

}
