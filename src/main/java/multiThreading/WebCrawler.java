package multiThreading;

import jsonlUtils.JsonlFile;
import pageUtils.Links;
import pageUtils.Page;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class WebCrawler extends RecursiveAction {
    private String url;
    Links linksObj;
    JsonlFile file;


    public WebCrawler(String url, Links linksObj, JsonlFile file){
        this.url = url;
        this.linksObj = linksObj;
        this.file = file;
    }

    @Override
    protected void compute() {
        try {
            System.out.println("Start fetching " + url);
            long start_fetching = new Date().getTime();
            Page page = new Page(url);
            System.out.println("Stop fetching: " + (new Date().getTime() - start_fetching));
            synchronized (file){
                try{
                    String html = page.getHtml();
                    System.out.println("Start writing");
                    long start_writing = new Date().getTime();
                    file.write(url,
                            html.replaceAll("\n", "").replaceAll("\r", "")
                    );
                    System.out.println("Stop writing: " + (new Date().getTime() - start_writing));
                }
                catch(Error e){
                    linksObj.removeLink(url);
                    linksObj.removeAvailableLink(url);
                    System.out.println("url fetch failed: " + url);
                    return;
                }
            }
            synchronized (linksObj) {
                linksObj.removeAvailableLink(url);
            }
            List<String> newLinks = page.getLinks();
            List<WebCrawler> crawlers = new ArrayList<WebCrawler>();
            for(String link : newLinks){
                synchronized (linksObj) {
                    if (linksObj.addLink(link)) {
                        crawlers.add(new WebCrawler(link, this.linksObj, this.file));
                        crawlers.get(crawlers.size() - 1).fork();
                    }
                }
            }
            crawlers.forEach((v) -> v.join());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
