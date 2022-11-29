package pageUtils;

import java.util.ArrayList;
import java.util.List;

public class Links {
    private List<String> links;
    private List<String> availableLinks;

    public Links() {
        links = new ArrayList<String>();
        availableLinks = new ArrayList<String>();
    }

    public List<String> getLinks() {
        return links;
    }

    public List<String> getAvailableLinks() {
        return availableLinks;
    }

    public boolean addLink(String newLink){
        if (links.contains(newLink)) return false;
        else {
            links.add(newLink);
            availableLinks.add(newLink);
            return true;
        }
    }

    public void removeAvailableLink(String link){
        availableLinks.remove(link);
    }
    public void removeLink(String link){
        links.remove(link);
    }
}
