package example.lucene;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.lucene.indexing.ProductJsonIndexWriter;
import example.lucene.json.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * Runs the example.
 */
public class Application {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String... args) {
        final String idxPath = args[0];
        final String jsonPath = args[1];

        createIndex(idxPath, jsonPath);
    }

    private static void createIndex(final String idxPath, final String jsonPath) {
        LOG.info("Creating a new search index for json files: {}", jsonPath);

        ProductJsonIndexWriter indexWriter = new ProductJsonIndexWriter(idxPath);
        indexWriter.open();

        LOG.info("Adding json files to search index...");

        File dir = new File(jsonPath);
        FilenameFilter filter = (dir1, name) -> name.endsWith(".json");

        File[] filesToLoad = dir.listFiles(filter);

        for (File fileToLoad : filesToLoad) {
            try {
                Product product = MAPPER.readerFor(Product.class).readValue(fileToLoad);
                indexWriter.addProduct(product);
            } catch (IOException e) {
                throw new RuntimeException("Unable to load product file: " + fileToLoad.getName(), e);
            }
        }

        indexWriter.close();
    }
}
