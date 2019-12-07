package com.example.sudoku.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.lang3.StringUtils;

public class FileLoader {

    private static final Logger LOG = LoggerFactory.getLogger(FileLoader.class);

    public String loadFile(String filepath) {
    	
    	if(StringUtils.isBlank(filepath)) {
    		return StringUtils.EMPTY;
    	}
    	
    	URL url = getClass().getClassLoader()
    	        .getResource(filepath);
    	
    	if(url == null) {
    		LOG.debug("File {} not found.", filepath);
    		return StringUtils.EMPTY;
    	}
    	
        try(Stream<String> lines = Files.lines(Paths.get(url.toURI()))) {
            return lines.collect(Collectors.joining("\n"));
        } catch (IOException|URISyntaxException ex) {
            LOG.error("Error loading file {}. Error: ", filepath, ex);
        }
        
        return StringUtils.EMPTY;
        
    }
}