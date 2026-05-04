package memory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class SharedMemory {
    private final Map<String, String> knowledgeBase = new ConcurrentHashMap<>();

    public void addInformation(String key, String info) {
        knowledgeBase.put(key, info);
        //System.out.println("Shared Memory Updated: " + key + " -> " + info);
    }

    public String getInformation(String key) {
        return knowledgeBase.get(key);
    }
}