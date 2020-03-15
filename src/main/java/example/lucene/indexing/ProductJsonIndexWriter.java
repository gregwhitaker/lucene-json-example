package example.lucene.indexing;

import example.lucene.json.Product;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoublePoint;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Builds the search index for the product JSON files.
 */
public class ProductJsonIndexWriter {
    private static final Logger LOG = LoggerFactory.getLogger(ProductJsonIndexWriter.class);

    private final String idxPath;

    private IndexWriter indexWriter;

    public ProductJsonIndexWriter(final String idxPath) {
        this.idxPath = idxPath;
    }

    public void open() {
        try {
            Directory dir = FSDirectory.open(Paths.get(idxPath));
            StandardAnalyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

            //Always overwrite the index for this example
            iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

            LOG.info("Opening search index: {}", idxPath);

            indexWriter = new IndexWriter(dir, iwc);
        } catch (IOException e) {
            throw new RuntimeException("Unable to open search index", e);
        }
    }

    public void addProduct(Product product) {
        Document doc = new Document();
        doc.add(new StringField("id", product.getId(), Field.Store.YES));

        if (product.isActive()) {
            doc.add(new IntPoint("active", 1));
        } else {
            doc.add(new IntPoint("active", 0));
        }

        doc.add(new StringField("category", product.getCategory(), Field.Store.YES));
        doc.add(new IntPoint("categoryCode", product.getCategoryCode()));
        doc.add(new TextField("shortName", product.getShortName(), Field.Store.YES));
        doc.add(new TextField("longName", product.getLongName(), Field.Store.YES));
        doc.add(new TextField("description", product.getDescription(), Field.Store.NO));
        doc.add(new StringField("gender", product.getGender(), Field.Store.YES));
        doc.add(new DoublePoint("msrpPrice", product.getPrices().getMsrp()));
        doc.add(new DoublePoint("listPrice", product.getPrices().getList()));
        doc.add(new DoublePoint("salePrice", product.getPrices().getSale()));

        try {
            LOG.info("Adding Product '{}' to search index", product.getId());

            indexWriter.addDocument(doc);
            indexWriter.commit();
        } catch (IOException e) {
            throw new RuntimeException("Unable to add document to search index", e);
        }
    }

    public void close() {
        if (isOpen()) {
            try {
                LOG.info("Closing search index: {}", idxPath);

                indexWriter.commit();
                indexWriter.close();
            } catch (IOException e) {
                throw new RuntimeException("Unable to close search index", e);
            }
        }
    }

    public boolean isOpen() {
        if (indexWriter != null) {
            return indexWriter.isOpen();
        }

        return false;
    }
}
