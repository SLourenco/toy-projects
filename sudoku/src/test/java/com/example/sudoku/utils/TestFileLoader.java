package com.example.sudoku.utils;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TestFileLoader {

    @Test
    public void loadingInvalidFile() {
        FileLoader loader = new FileLoader();
        String data = loader.loadFile("invalidFile");
        Assert.assertTrue(StringUtils.isBlank(data));
    }
    
    @Test
    public void loadingValidFile() {
        FileLoader loader = new FileLoader();
        String data = loader.loadFile("sudoku.txt");
        Assert.assertEquals("PUZZLE TEST!", data);
    }
}