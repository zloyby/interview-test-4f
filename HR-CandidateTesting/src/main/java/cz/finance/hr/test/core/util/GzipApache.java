package cz.finance.hr.test.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;

/**
 * Just utility class for Apache Commons Compress™
 */
public class GzipApache {
    private GzipApache() {
    }

    public static void compressGzip(File input, File output) throws IOException {
        try (GzipCompressorOutputStream out = new GzipCompressorOutputStream(new FileOutputStream(output))) {
            IOUtils.copy(new FileInputStream(input), out);
        }
    }

    public static void decompressGzip(File input, File output) throws IOException {
        try (GzipCompressorInputStream in = new GzipCompressorInputStream(new FileInputStream(input))) {
            IOUtils.copy(in, new FileOutputStream(output));
        }
    }
}
