import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfReader;


public class cit_an {

    /**
     * @param args
     */

    private static int hindex(int[] arr) {
        int[] temp = new int[arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            temp[Math.min(arr.length, arr[i])]++;
        }
        int sum = 0;
        for (int i = temp.length - 1; i >= 0; i--) {
            sum += temp[i];
            if (sum >= i) return i;
        }

        return 0;
    }

    private static String getUrlSource(String str_url) throws IOException {
        URL url = new URL(str_url);
        System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");
        URLConnection yc = url.openConnection();

        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuilder src_url = new StringBuilder();
        while ((inputLine = in .readLine()) != null)
        src_url.append(inputLine); in .close();
        return src_url.toString();

    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        final WebClient webClient = new WebClient();
        final JTextField text_auth_name = new JTextField("chetan bhagat", 20);

        JButton but_submit = new JButton("Submit");

        JButton but_show = new JButton("Show citations");

        final JLabel label_res = new JLabel("Search results..");
        //final JTextField label_res = new JTextField("Search results");
        final JTextField text_range = new JTextField("2004-2009");
        text_range.setEnabled(false);

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();

        TitledBorder title;
        title = BorderFactory.createTitledBorder("Select operation");
        panel1.setBorder(title);
        panel1.setLayout(new GridLayout(3, 3));

        TitledBorder title1;
        title1 = BorderFactory.createTitledBorder("Select mode");
        panel2.setBorder(title1);

        final JRadioButton parta = new JRadioButton("Sort - citations", true);
        final JRadioButton partb = new JRadioButton("Sort - year", false);
        final JRadioButton partc = new JRadioButton("Sort - citations - range", false);
        final JRadioButton partd = new JRadioButton("Sort - year - range", false);
        final JRadioButton parte = new JRadioButton("Show - h-index and i-index", false);
        final JRadioButton partf = new JRadioButton("Show - Cit Stats", false);

        panel1.add(parta);
        panel1.add(partb);
        panel1.add(partc);
        panel1.add(partd);
        panel1.add(parte);
        panel1.add(partf);

        final JRadioButton auth = new JRadioButton("Search for Author", true);
        final JRadioButton jour = new JRadioButton("Search for Journal", false);

        panel2.add(auth);
        panel2.add(jour);

        ButtonGroup grp = new ButtonGroup();
        grp.add(parta);
        grp.add(partb);
        grp.add(partc);
        grp.add(partd);
        grp.add(parte);
        grp.add(partf);

        ButtonGroup grpch = new ButtonGroup();
        grpch.add(auth);
        grpch.add(jour);

        parta.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                text_range.setEnabled(false);
            }

        });

        partb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                text_range.setEnabled(false);
            }

        });

        partc.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                text_range.setEnabled(true);
            }

        });

        partd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                text_range.setEnabled(true);
            }

        });
        parte.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                text_range.setEnabled(false);
            }

        });
        partf.addActionListener(new ActionListener() {

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

        but_submit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub

                String auth_name = text_auth_name.getText();
                auth_name = auth_name.replaceAll(" ", "+");
                String src;
                ArrayList < book_data > bookdata = new ArrayList < book_data > ();
                ArrayList < book_data > bookdatatemp = new ArrayList < book_data > ();
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
                    Elements booklinks = booklinkstemp.getElementsByClass("gs_r"); //.first().getElementsByClass("gs_ri");
                    //Elements booklinkss = doc.getElementsByClass("gs_ri");
                    //System.out.println(booklinks.toString());
                    if (auth.isSelected()) {
                        bookdata.clear();
                        for (Element book: booklinks)

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
                            if (bookheadtype.text().equals("[BOOK]")) {
                                //bookdata += bookheadval.text() + "   " + bookcited.text() + "<br><br>";
                                //System.out.println(bookheadval.text());
                                cited_split = bookcited.text().split(" ");
                                cited = Integer.parseInt(cited_split[2]);
                                //System.out.println(cited);

                                String str_bookdet = bookdet.toString();
                                lastind = str_bookdet.lastIndexOf(bookdetbold.toString());
                                str_bookdet = str_bookdet.substring(lastind + 1);
                                year_split = str_bookdet.split(" ");
                                year = Integer.parseInt(year_split[2]);


                                bookdata.add(new book_data(bookheadval.text(), cited, year));
                            }

                        }

                        if (parta.isSelected()) {
                            //text_range.setEnabled(false);
                            Collections.sort(bookdata, new Comparator < book_data > () {

                                @Override
                                public int compare(book_data arg0, book_data arg1) {
                                    return arg1.cited - arg0.cited;
                                }

                            });
                            String res = "";
                            String resbook = "";

                            for (book_data templ: bookdata) {
                                resbook = templ.name + "   " + " cited : " + templ.cited + "   " + "year : " + templ.year;
                                System.out.println(resbook);
                                res = res + resbook + "<br>";
                            }
                            System.out.println("-------------------------------");
                            label_res.setText("<html>" + res + "</html>");

                        } else if (partb.isSelected()) {
                            //text_range.setEnabled(false);
                            Collections.sort(bookdata, new Comparator < book_data > () {

                                @Override
                                public int compare(book_data arg0, book_data arg1) {
                                    return arg1.year - arg0.year;
                                }

                            });
                            String res = "";
                            String resbook = "";

                            for (book_data templ: bookdata) {
                                resbook = templ.name + "   " + " cited : " + templ.cited + "   " + "year : " + templ.year;
                                System.out.println(resbook);
                                res = res + resbook + "<br>";
                            }
                            System.out.println("-------------------------------");
                            label_res.setText("<html>" + res + "</html>");

                        } else if (partc.isSelected()) {
                            //text_range.setEnabled(true);
                            bookdatatemp.clear();
                            String range_year = text_range.getText();
                            String[] range_year_split = range_year.split("-");
                            int start_year = Integer.parseInt(range_year_split[0]);
                            int end_year = Integer.parseInt(range_year_split[1]);

                            for (book_data tempyear: bookdata) {
                                if (tempyear.year < start_year || tempyear.year > end_year) {
                                    //bookdata.remove(bookdata.indexOf(tempyear));
                                } else bookdatatemp.add(tempyear);
                            }

                            bookdata = bookdatatemp;
                            Collections.sort(bookdata, new Comparator < book_data > () {

                                @Override
                                public int compare(book_data arg0, book_data arg1) {
                                    return arg1.cited - arg0.cited;
                                }

                            });
                            String res = "";
                            String resbook = "";

                            for (book_data templ: bookdata) {
                                resbook = templ.name + "   " + " cited : " + templ.cited + "   " + "year : " + templ.year;
                                System.out.println(resbook);
                                res = res + resbook + "<br>";
                            }
                            System.out.println("-------------------------------");
                            label_res.setText("<html>" + res + "</html>");


                        } else if (partd.isSelected()) {
                            //text_range.setEnabled(true);
                            bookdatatemp.clear();
                            String range_year = text_range.getText();
                            String[] range_year_split = range_year.split("-");
                            int start_year = Integer.parseInt(range_year_split[0]);
                            int end_year = Integer.parseInt(range_year_split[1]);

                            for (book_data tempyear: bookdata) {
                                if (tempyear.year < start_year || tempyear.year > end_year) {
                                    //bookdata.remove(bookdata.indexOf(tempyear));
                                } else bookdatatemp.add(tempyear);
                            }

                            bookdata = bookdatatemp;
                            Collections.sort(bookdata, new Comparator < book_data > () {

                                @Override
                                public int compare(book_data arg0, book_data arg1) {
                                    return arg1.year - arg0.year;
                                }

                            });
                            String res = "";
                            String resbook = "";

                            for (book_data templ: bookdata) {
                                resbook = templ.name + "   " + " cited : " + templ.cited + "   " + "year : " + templ.year;
                                System.out.println(resbook);
                                res = res + resbook + "<br>";
                            }
                            System.out.println("-------------------------------");
                            label_res.setText("<html>" + res + "</html>");

                        } else if (partf.isSelected()) {
                            //bookdatatemp.clear();
                            int avgcite_p = 0;
                            //ArrayList<book_data> avgcite_y = new ArrayList<book_data>();
                            int avgcite_y = 0;
                            int i = -1;
                            int tempa = 0;
                            int flag = 1;

                            Collections.sort(bookdata, new Comparator < book_data > () {

                                @Override
                                public int compare(book_data arg0, book_data arg1) {
                                    return arg1.year - arg0.year;
                                }

                            });
                            System.out.println("Citation Statistics : ");
                            System.out.println("Total citations in each year : ");
                            for (book_data book_stat: bookdata) {
                                flag = 1;
                                avgcite_p += book_stat.cited;
                                if (i == -1) {
                                    tempa = book_stat.year;
                                    i = 1;
                                }

                                if (i == 1) {
                                    //avgcite_y.add(book_stat);
                                    avgcite_y += book_stat.cited;
                                }
                                if (book_stat.year == tempa && bookdata.get(1 + bookdata.indexOf(book_stat)).year != tempa) {
                                    i = 0;
                                    System.out.println(tempa + " : " + avgcite_y);
                                    //flag=0;
                                }
                                if (i == 0 && flag == 1) {
                                    tempa = book_stat.year;
                                    avgcite_y += book_stat.cited;
                                    i = 1;
                                }

                            }

                            avgcite_p /= bookdata.size();
                            System.out.println("Avg citations per paper : " + avgcite_p);
                            System.out.println("-------------------------------");
                            label_res.setText("<html>" + "Citation Statistics : " + "<br>" + "Total citations in each year : " + "<br>" + tempa + " : " + avgcite_y + "<br>" + "Avg citations per paper : " + avgcite_p + "</html>");

                        } else if (parte.isSelected()) {
                            int ind = 0;
                            int i_ind = 0;
                            int[] arr = new int[bookdata.size()];
                            for (book_data temph: bookdata) {
                                arr[ind] = temph.cited;
                                ind++;
                                if (temph.cited >= 10) {
                                    i_ind++;
                                }
                            }
                            System.out.println("h-index : " + hindex(arr));
                            System.out.println("i-index : " + i_ind);
                            System.out.println("-------------------------------");
                            label_res.setText("<html>" + "h-index : " + hindex(arr) + "<br>" + "i-index : " + i_ind + "</html>");

                        }
                    } else if (jour.isSelected()) {
                        bookdata.clear();
                        for (Element book: booklinks) {
                            book = book.getElementsByClass("gs_ri").first();
                            Element bookdet = book.getElementsByClass("gs_a").first();
                            Element bookdetbold = bookdet.getElementsByTag("b").first();

                            Element bookhead = book.getElementsByTag("h3").first();
                            Element bookheadval = bookhead.getAllElements().last();
                            Element bookcited = book.getElementsByClass("gs_fl").first().getElementsByTag("a").first();

                            cited_split = bookcited.text().split(" ");
                            cited = Integer.parseInt(cited_split[2]);
                            //System.out.println(cited);

                            String str_bookdet = bookdet.toString();
                            lastind = str_bookdet.lastIndexOf(bookdetbold.toString());
                            str_bookdet = str_bookdet.substring(lastind + 1);
                            year_split = str_bookdet.split(" ");
                            year = Integer.parseInt(year_split[2]);

                            bookdata.add(new book_data(bookheadval.text(), cited, year));
                        }

                        if (parta.isSelected()) {
                            //text_range.setEnabled(false);
                            Collections.sort(bookdata, new Comparator < book_data > () {

                                @Override
                                public int compare(book_data arg0, book_data arg1) {
                                    return arg1.cited - arg0.cited;
                                }

                            });
                            String res = "";
                            String resbook = "";

                            for (book_data templ: bookdata) {
                                resbook = templ.name + "   " + " cited : " + templ.cited + "   " + "year : " + templ.year;
                                System.out.println(resbook);
                                res = res + resbook + "<br>";
                            }
                            System.out.println("-------------------------------");
                            label_res.setText("<html>" + res + "</html>");

                        } else if (partb.isSelected()) {
                            //text_range.setEnabled(false);
                            Collections.sort(bookdata, new Comparator < book_data > () {

                                @Override
                                public int compare(book_data arg0, book_data arg1) {
                                    return arg1.year - arg0.year;
                                }

                            });
                            String res = "";
                            String resbook = "";

                            for (book_data templ: bookdata) {
                                resbook = templ.name + "   " + " cited : " + templ.cited + "   " + "year : " + templ.year;
                                System.out.println(resbook);
                                res = res + resbook + "<br>";
                            }
                            System.out.println("-------------------------------");
                            label_res.setText("<html>" + res + "</html>");

                        } else if (partc.isSelected()) {
                            //text_range.setEnabled(true);
                            bookdatatemp.clear();
                            String range_year = text_range.getText();
                            String[] range_year_split = range_year.split("-");
                            int start_year = Integer.parseInt(range_year_split[0]);
                            int end_year = Integer.parseInt(range_year_split[1]);

                            for (book_data tempyear: bookdata) {
                                if (tempyear.year < start_year || tempyear.year > end_year) {
                                    //bookdata.remove(bookdata.indexOf(tempyear));
                                } else bookdatatemp.add(tempyear);
                            }

                            bookdata = bookdatatemp;
                            Collections.sort(bookdata, new Comparator < book_data > () {

                                @Override
                                public int compare(book_data arg0, book_data arg1) {
                                    return arg1.cited - arg0.cited;
                                }

                            });
                            String res = "";
                            String resbook = "";

                            for (book_data templ: bookdata) {
                                resbook = templ.name + "   " + " cited : " + templ.cited + "   " + "year : " + templ.year;
                                System.out.println(resbook);
                                res = res + resbook + "<br>";
                            }
                            System.out.println("-------------------------------");
                            label_res.setText("<html>" + res + "</html>");


                        } else if (partd.isSelected()) {
                            //text_range.setEnabled(true);
                            bookdatatemp.clear();
                            String range_year = text_range.getText();
                            String[] range_year_split = range_year.split("-");
                            int start_year = Integer.parseInt(range_year_split[0]);
                            int end_year = Integer.parseInt(range_year_split[1]);

                            for (book_data tempyear: bookdata) {
                                if (tempyear.year < start_year || tempyear.year > end_year) {
                                    //bookdata.remove(bookdata.indexOf(tempyear));
                                } else bookdatatemp.add(tempyear);
                            }

                            bookdata = bookdatatemp;
                            Collections.sort(bookdata, new Comparator < book_data > () {

                                @Override
                                public int compare(book_data arg0, book_data arg1) {
                                    return arg1.year - arg0.year;
                                }

                            });
                            String res = "";
                            String resbook = "";

                            for (book_data templ: bookdata) {
                                resbook = templ.name + "   " + " cited : " + templ.cited + "   " + "year : " + templ.year;
                                System.out.println(resbook);
                                res = res + resbook + "<br>";
                            }
                            System.out.println("-------------------------------");
                            label_res.setText("<html>" + res + "</html>");

                        } else if (partf.isSelected()) {
                            //bookdatatemp.clear();
                            int avgcite_p = 0;
                            //ArrayList<book_data> avgcite_y = new ArrayList<book_data>();
                            int avgcite_y = 0;
                            int i = -1;
                            int tempa = 0;
                            int flag = 1;

                            Collections.sort(bookdata, new Comparator < book_data > () {

                                @Override
                                public int compare(book_data arg0, book_data arg1) {
                                    return arg1.year - arg0.year;
                                }

                            });
                            System.out.println("Citation Statistics : ");
                            System.out.println("Total citations in each year : ");
                            for (book_data book_stat: bookdata) {
                                flag = 1;
                                avgcite_p += book_stat.cited;
                                if (i == -1) {
                                    tempa = book_stat.year;
                                    i = 1;
                                }

                                if (i == 1) {
                                    //avgcite_y.add(book_stat);
                                    avgcite_y += book_stat.cited;
                                }
                                if (book_stat.year == tempa && bookdata.get(1 + bookdata.indexOf(book_stat)).year != tempa) {
                                    i = 0;
                                    System.out.println(tempa + " : " + avgcite_y);
                                    //flag=0;
                                }
                                if (i == 0 && flag == 1) {
                                    tempa = book_stat.year;
                                    avgcite_y += book_stat.cited;
                                    i = 1;
                                }

                            }

                            avgcite_p /= bookdata.size();
                            System.out.println("Avg citations per paper : " + avgcite_p);
                            System.out.println("-------------------------------");
                            label_res.setText("<html>" + "Citation Statistics : " + "<br>" + "Total citations in each year : " + "<br>" + tempa + " : " + "<br>" + avgcite_y + "Avg citations per paper : " + avgcite_p + "</html>");


                        } else if (parte.isSelected()) {
                            int ind = 0;
                            int i_ind = 0;
                            int[] arr = new int[bookdata.size()];
                            for (book_data temph: bookdata) {
                                arr[ind] = temph.cited;
                                ind++;
                                if (temph.cited >= 10) {
                                    i_ind++;
                                }
                            }
                            System.out.println("h-index : " + hindex(arr));
                            System.out.println("i-index : " + i_ind);
                            System.out.println("-------------------------------");

                            label_res.setText("<html>" + "h-index : " + hindex(arr) + "<br>" + "i-index : " + i_ind + "</html>");
                        }


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

        but_show.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                String auth_name = text_auth_name.getText();
                auth_name = auth_name.replaceAll(" ", "+");
                String src;
                String final_text = "";
                try {
                    src = getUrlSource("http://scholar.google.co.in/scholar?hl=en&q=" + auth_name + "&btnG=").toString();

                    Document doc = Jsoup.parse(src);
                    Element booklinkstemp = doc.getElementsByTag("html").first().getElementsByTag("body").first().getElementById("gs_top").getElementById("gs_bdy").getElementById("gs_res_bdy").getElementById("gs_ccl");
                    Elements booklinks = booklinkstemp.getElementsByClass("gs_r"); //.first().getElementsByClass("gs_ri");

                    for (Element book: booklinks) {
                        book = book.getElementsByClass("gs_ri").first();
                        Element bookhead = book.getElementsByTag("h3").first();
                        //Element bookheadval = bookhead.getAllElements().last();
                        Element bookcited = book.getElementsByClass("gs_fl").first().getElementsByTag("a").first();
                        //System.out.println(bookcited.toString());
                        String link_cite = "http://scholar.google.co.in" + bookcited.attr("href").toString();

                        Document doct = Jsoup.parse(getUrlSource(link_cite));
                        Element booklinkstemp1 = doct.getElementsByTag("html").first().getElementsByTag("body").first().getElementById("gs_top").getElementById("gs_bdy").getElementById("gs_res_bdy").getElementById("gs_ccl");
                        Elements booklinks1 = booklinkstemp1.getElementsByClass("gs_r"); //.first().getElementsByClass("gs_ri");
                        int flag = 1;
                        /* for(Element book1:booklinks1)
                    {
                    	 book1 = book1.getElementsByClass("gs_ri").first();
                         Element bookhead1 = book1.getElementsByTag("h3").first();      
                         Element ele = bookhead1.getElementsByTag("span").first();
                         if(ele!=null)
                         {
                         ele = ele.getElementsByClass("gs_ctc").first();
                         if(ele!=null)	ele = ele.getElementsByClass("gs_ct1").first();
                         //System.out.println(ele);
                         if(ele!=null && ele.text().equals("[PDF]"))
                         {
                           	 System.out.println(ele);
                           	 String pdf_disp="";
                           	 byte[] ch = new byte[1024];
                           	 String linkpdf = bookhead1.getAllElements().last().attr("href");
                           	 URL url1 = new URL(linkpdf);
                           	 URLConnection urlConn = url1.openConnection();
                           	 //FileOutputStream fos1 = new FileOutputStream("download.pdf");
                           	 InputStream is1 = url1.openStream();
                           	 while(is1.read(ch)!= -1)
                           	 {
                           		 pdf_disp += ch.toString();
                           	 }
                           	 label_res.setText(pdf_disp);
                           	 if(flag==1) break;
                           	 /*byte[] ba1 = new byte[1024];
                           	 int baLength;
                           	 while ((baLength = is1.read(ba1)) != -1) {
                                fos1.write(ba1, 0, baLength);
                           	 }
                           	 fos1.flush();
                           	 fos1.close();
                           	 is1.close();
                           	PdfReader r = PdfReader.fileReader(linkpdf);
                           	PdfDocument docp = new PdfDocument(r);
                            try {
                            	label_res.setText(docp.toString());
                            	if(flag==1) break;
                            
                            } catch (Exception e) {
                             e.printStackTrace(); }
                   
                           	
                         }
                         }
                    }*/

                        /*if()
                    {
                    	Document doct = Jsoup.parse(getUrlSource(link_cite));
                    	label_res.setText(doct.toString());
                    }*/

                    }
                    //label_res.setText("<html>" + final_text + "</html>");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        });


        JFrame frame_main = new JFrame("Cit_An");
        frame_main.setLayout(new GridLayout(7, 1));
        frame_main.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panelin = new JPanel();
        panelin.setSize(200, 50);
        panelin.setLayout(new GridLayout(1, 1));
        text_auth_name.setSize(100, 50);
        text_range.setSize(100, 50);
        text_range.setMaximumSize(new Dimension(100, 50));
        panelin.add(text_auth_name);
        panelin.add(text_range);

        frame_main.add(panelin, BorderLayout.PAGE_START);

        JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayout(1, 1));
        panel3.add(panel1);
        panel3.add(panel2);

        frame_main.add(panel3);

        JPanel panellab = new JPanel();
        label_res.setAutoscrolls(true);
        panellab.add(label_res);
        frame_main.add(panellab);

        JPanel panelsub = new JPanel();
        panelsub.add(but_submit);
        panelsub.add(but_show);
        frame_main.add(panelsub);

        frame_main.pack();
        frame_main.setVisible(true);
    }

}