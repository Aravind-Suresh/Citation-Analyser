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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
				book_data temp = new book_data();
				int cited;
				String[] cited_split;
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
							System.out.println(cited);
							
							bookdata.add(new book_data(bookheadval.text(),cited));
						}
						
					}
						
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
						resbook = templ.name + "   " + " cited by " + templ.cited;
						System.out.println(resbook);
						res = res + resbook + "<br>";
					}
					//System.out.println(bookdata.toString());
					label_res.setText("<html>" + res + "</html>");
				
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
		frame_main.setLayout(new GridLayout(3,1));
		
		frame_main.add(text_auth_name);
		frame_main.add(label_res);
		frame_main.add(but_submit);
		
		frame_main.pack();
		frame_main.setVisible(true);
	}

}
