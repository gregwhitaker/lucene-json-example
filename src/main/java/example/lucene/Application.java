package example.lucene;

import example.lucene.json.JsonDocumentParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String... args) {
        JsonDocumentParser parser = new JsonDocumentParser(args[0]);
    }
}
