package cz.finance.hr.test.core.util;

import java.util.List;
import org.hamcrest.core.IsNull;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CsvParserTest {

    @Test
    public void test_no_quote() {
        String line = "10,AU,Australia";

        List<String> result = CsvParser.parseLine(line);

        assertThat(result, IsNull.notNullValue());
        assertThat(result.size(), is(3));
        assertThat(result.get(0), is("10"));
        assertThat(result.get(1), is("AU"));
        assertThat(result.get(2), is("Australia"));
    }

    @Test
    public void test_no_quote_but_double_quotes_in_column() throws Exception {
        String line = "10,AU,Aus\"\"tralia";

        List<String> result = CsvParser.parseLine(line);

        assertThat(result, IsNull.notNullValue());
        assertThat(result.size(), is(3));
        assertThat(result.get(0), is("10"));
        assertThat(result.get(1), is("AU"));
        assertThat(result.get(2), is("Aus\"tralia"));
    }

    @Test
    public void test_double_quotes() {
        String line = "\"10\",\"AU\",\"Australia\"";

        List<String> result = CsvParser.parseLine(line);

        assertThat(result, IsNull.notNullValue());
        assertThat(result.size(), is(3));
        assertThat(result.get(0), is("10"));
        assertThat(result.get(1), is("AU"));
        assertThat(result.get(2), is("Australia"));
    }

    @Test
    public void test_double_quotes_but_double_quotes_in_column() {
        String line = "\"10\",\"AU\",\"Aus\"\"tralia\"";

        List<String> result = CsvParser.parseLine(line);

        assertThat(result, IsNull.notNullValue());
        assertThat(result.size(), is(3));
        assertThat(result.get(0), is("10"));
        assertThat(result.get(1), is("AU"));
        assertThat(result.get(2), is("Aus\"tralia"));
    }

    @Test
    public void test_double_quotes_but_comma_in_column() {
        String line = "\"10\",\"AU\",\"Aus,tralia\"";

        List<String> result = CsvParser.parseLine(line);

        assertThat(result, IsNull.notNullValue());
        assertThat(result.size(), is(3));
        assertThat(result.get(0), is("10"));
        assertThat(result.get(1), is("AU"));
        assertThat(result.get(2), is("Aus,tralia"));
    }
}