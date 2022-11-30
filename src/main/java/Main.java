import io.github.cdimascio.dotenv.Dotenv;
import jsonlUtils.JsonlFile;
import multiThreading.WebCrawler;
import pageUtils.Links;

import java.io.IOException;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) throws IOException {
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("ROOT_URL");
        JsonlFile jsonl = new JsonlFile("thefreedictionary");
        int maxCount = Integer.parseInt(dotenv.get("NUMBER_OF_LINKS"));
        Links linksObj = new Links(maxCount);
        Function<String, Boolean> constrain = (String link) -> {
          return link.startsWith(url);
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