import io.github.cdimascio.dotenv.Dotenv;
import jsonlUtils.JsonlFile;
import multiThreading.WebCrawler;
import pageUtils.Links;

import java.io.IOException;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("ROOT_URL");
        int timeout = Integer.parseInt(dotenv.get("TIMEOUT")) * 1000;
        String fileName = dotenv.get("RESULT_FILE_NAME");
        JsonlFile jsonl = new JsonlFile(fileName);
        Links linksObj = new Links();
        Function<String, Boolean> constrain = (String link) -> {
          return link.startsWith("http://www.thefreedictionary.com/")
                  || link.startsWith("https://www.thefreedictionary.com/");
//                  && !link.startsWith("http://up.")
//                  && !link.startsWith("http://secure")
//                  && !link.startsWith()
//                  && !link.contains("facebook")
        };
        linksObj.setConstrain(constrain);
        jsonl.createFile();
        jsonl.open();
        ForkJoinPool fjp = ForkJoinPool.commonPool();
        WebCrawler crawler = new WebCrawler(url, linksObj, jsonl);
        fjp.execute(crawler);
        Thread.sleep(timeout);
        System.out.println("terminating...");
        fjp.shutdown();
        jsonl.close();
    }
}