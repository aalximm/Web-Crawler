import jsonlUtils.JsonlFile;
import multiThreading.WebCrawler;
import pageUtils.Links;

import java.io.IOException;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) throws IOException {
        String url = "http://www.thefreedictionary.com/";
        JsonlFile jsonl = new JsonlFile("thefreedictionary");
        Links linksObj = new Links();
        Function<String, Boolean> constrain = (String link) -> {
          return link.contains("dictionary");
        };
        linksObj.setConstrain(constrain);
        jsonl.createFile();
        jsonl.open();
//        ForkJoinPool fjp = ForkJoinPool.commonPool();
        WebCrawler crawler = new WebCrawler(url, linksObj, jsonl);
        crawler.fork();
        crawler.join();
        jsonl.close();
    }
}