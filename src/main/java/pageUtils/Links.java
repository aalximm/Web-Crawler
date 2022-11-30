package pageUtils;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Links {
    private List<String> links;

    private int count;

    private final int maxCount;

    public int getCount(){
        return count;
    }
    public List<String> getLinks() {
        return links;
    }

    private List<String> availableLinks;

    private Function<String, Boolean> constrain;

    public void setConstrain(Function<String, Boolean> constrain) {
        Function<String, Boolean> defaultConstrain = (link) -> {
            return !links.contains(link) && count < maxCount;
        };
        this.constrain = (String link) -> {return defaultConstrain.apply(link) && constrain.apply(link);};
    }

    public Links(int maxCount){
        links = new ArrayList<String>();
        availableLinks = new ArrayList<String>();
        constrain = (String link) -> {return !links.contains(link);};
        this.maxCount = maxCount;
        count = 0;
    }

    public List<String> getAvailableLinks() {
        return availableLinks;
    }

    public boolean addLink(String newLink){
        if (constrain.apply(newLink)) {
            links.add(newLink);
            availableLinks.add(newLink);
            return true;
        }
        else return false;
    }

    public void removeAvailableLink(String link){
        availableLinks.remove(link);
    }
    public void removeLink(String link){
        links.remove(link);
        availableLinks.remove(link);
        count++;
    }
}
