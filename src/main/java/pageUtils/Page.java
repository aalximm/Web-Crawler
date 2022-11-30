package pageUtils;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;

public class Page {
    private String url;
    private String html;
    public Page(String url) throws IOException {
        this.url = url;
        this.html = Jsoup.connect(url).get().html();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtml() {
        return html;
    }

    public List<String> getLinks(){
        List<String> links = Jsoup.parse(html, url).select("body a[href]").eachAttr("abs:href");
        return links;
    }
}
