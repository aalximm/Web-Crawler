package multiThreading;

import jsonlUtils.JsonlFile;
import pageUtils.Links;
import pageUtils.Page;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
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
            Page page = new Page(url);
            synchronized (file){
                String html = page.getHtml();
                file.write(url,
                        html.replaceAll("\n", "").replaceAll("\r", "")
                );
                System.out.println("Add url: " + url);
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
            crawlers.forEach(ForkJoinTask::join);
        } catch (Error | IOException e) {
            synchronized (linksObj){
                linksObj.removeLink(url);
            }
        }
    }
}
