package example.lucene;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.lucene.indexing.ProductJsonIndexWriter;
import example.lucene.json.Product;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoublePoint;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Runs the example.
 */
public class Application {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String... args) throws Exception {
        final String idxPath = args[0];
        final String jsonPath = args[1];

        createIndex(idxPath, jsonPath);

        final Directory indexDirectory = FSDirectory.open(Paths.get(idxPath));
        final IndexReader indexReader = DirectoryReader.open(indexDirectory);
        final IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        findAllMensProducts(indexSearcher);
        findAllWomensProducts(indexSearcher);
        findAllWomensApparel(indexSearcher);
        findAllProductsBetweenFortyFiveAndOneHundredDollars(indexSearcher);
    }

    private static void createIndex(final String idxPath, final String jsonPath) {
        LOG.info("Creating a new search index for json files: {}", jsonPath);

        ProductJsonIndexWriter indexWriter = new ProductJsonIndexWriter(idxPath);
        indexWriter.open();

        LOG.info("Adding json files to search index...");

        File dir = new File(jsonPath);
        FilenameFilter filter = (dir1, name) -> name.endsWith(".json");

        for (File fileToLoad : Objects.requireNonNull(dir.listFiles(filter))) {
            try {
                Product product = MAPPER.readerFor(Product.class).readValue(fileToLoad);
                indexWriter.addProduct(product);
            } catch (IOException e) {
                throw new RuntimeException("Unable to load product file: " + fileToLoad.getName(), e);
            }
        }

        indexWriter.close();
    }

    private static void findAllMensProducts(IndexSearcher indexSearcher) throws IOException {
        LOG.info("==============================================");
        LOG.info("Query: findAllMensProducts");

        Term t = new Term("gender", "male");
        Query query = new TermQuery(t);

        TopDocs foundDocs = indexSearcher.search(query, 10);

        for(ScoreDoc sd : foundDocs.scoreDocs) {
            Document d = indexSearcher.doc(sd.doc);
            LOG.info("Found: {}", d.get("shortName"));
        }
    }

    private static void findAllWomensProducts(IndexSearcher indexSearcher) throws IOException {
        LOG.info("==============================================");
        LOG.info("Query: findAllWomensProducts");

        Term t = new Term("gender", "female");
        Query query = new TermQuery(t);

        TopDocs foundDocs = indexSearcher.search(query, 10);

        for(ScoreDoc sd : foundDocs.scoreDocs) {
            Document d = indexSearcher.doc(sd.doc);
            LOG.info("Found: {}", d.get("shortName"));
        }
    }

    private static void findAllWomensApparel(IndexSearcher indexSearcher) throws IOException {
        LOG.info("==============================================");
        LOG.info("Query: findAllWomensApparel");

        Query query = new BooleanQuery.Builder()
                .add(new BooleanClause(new TermQuery(new Term("gender", "female")), BooleanClause.Occur.MUST))
                .add(new BooleanClause(IntPoint.newExactQuery("categoryCode", 2), BooleanClause.Occur.MUST))
                .build();

        TopDocs foundDocs = indexSearcher.search(query, 10);

        for(ScoreDoc sd : foundDocs.scoreDocs) {
            Document d = indexSearcher.doc(sd.doc);
            LOG.info("Found: {}", d.get("shortName"));
        }
    }

    private static void findAllProductsBetweenFortyFiveAndOneHundredDollars(IndexSearcher indexSearcher) throws IOException {
        LOG.info("==============================================");
        LOG.info("Query: findAllProductsBetweenFortyFiveAndOneHundredDollars");

        Query query = DoublePoint.newRangeQuery("listPrice", 45.0, 100.0);

        TopDocs foundDocs = indexSearcher.search(query, 10);

        for(ScoreDoc sd : foundDocs.scoreDocs) {
            Document d = indexSearcher.doc(sd.doc);
            LOG.info("Found: {}", d.get("shortName"));
        }
    }
}
