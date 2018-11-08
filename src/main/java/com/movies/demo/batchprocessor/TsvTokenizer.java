package com.movies.demo.batchprocessor;

import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;

public class TsvTokenizer extends DelimitedLineTokenizer {

    public TsvTokenizer() {
        super("\t");
    }

    @Override
    protected boolean isQuoteCharacter(char c) {
        return false;
    }
}
