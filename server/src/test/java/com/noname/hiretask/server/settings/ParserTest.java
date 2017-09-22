package com.noname.hiretask.server.settings;

import org.junit.Assert;
import org.junit.Test;


/**
 * Tests for app arguments parser
 */
public class ParserTest {

    private final static String DEFAULT_FOLDER =
            System.getProperty("user.home") + System.getProperty("file.separator") + "serverdata";

    private Parser parser = new AppArgumentsParser();

    @Test
    public void parse6_ok() throws Exception {
        assertSettings(parser.parse(new String[]{Parser.ARG_PORT, "4000", Parser.ARG_DATA, "data_folder", Parser.ARG_PROC_COUNT, "3"}),
                       4000, "data_folder", 3);
    }

    @Test
    public void parse4_ok() throws Exception {
        assertSettings(parser.parse(new String[]{Parser.ARG_PORT, "4000", Parser.ARG_DATA, "data_folder"}),
                       4000, "data_folder", 2);
        assertSettings(parser.parse(new String[]{Parser.ARG_PORT, "4000", Parser.ARG_PROC_COUNT, "3"}),
                       4000, DEFAULT_FOLDER, 3);
        assertSettings(parser.parse(new String[]{Parser.ARG_DATA, "data_folder", Parser.ARG_PROC_COUNT, "3"}),
                       3000, "data_folder", 3);
    }

    @Test(expected = InitializationParameterException.class)
    public void parse4_duplicatedOption_fail() throws Exception {
        parser.parse(new String[]{Parser.ARG_PORT, "4000", Parser.ARG_PORT, "2222"});
    }

    @Test
    public void parse2_ok() throws Exception {
        assertSettings(parser.parse(new String[]{Parser.ARG_PORT, "4000"}),
                       4000, DEFAULT_FOLDER, 2);
        assertSettings(parser.parse(new String[]{Parser.ARG_DATA, "data_folder"}),
                       3000, "data_folder", 2);
        assertSettings(parser.parse(new String[]{Parser.ARG_PROC_COUNT, "3"}),
                       3000, DEFAULT_FOLDER, 3);
    }

    @Test
    public void parse0_ok() throws Exception {
        assertSettings(parser.parse(new String[]{}), 3000, DEFAULT_FOLDER, 2);
    }

    @Test(expected = InitializationParameterException.class)
    public void parse7_fail() throws Exception {
        parser.parse(new String[]{Parser.ARG_PORT, "4000", Parser.ARG_DATA, "data_folder", Parser.ARG_PROC_COUNT, "3", "seven"});
    }

    @Test(expected = InitializationParameterException.class)
    public void parse5_fail() throws Exception {
        parser.parse(new String[]{Parser.ARG_PORT, "4000", Parser.ARG_DATA, "data_folder", Parser.ARG_PROC_COUNT});
    }

    @Test(expected = InitializationParameterException.class)
    public void parse3_fail() throws Exception {
        parser.parse(new String[]{Parser.ARG_PORT, "4000", Parser.ARG_DATA});
    }

    @Test(expected = InitializationParameterException.class)
    public void parse1_fail() throws Exception {
        parser.parse(new String[]{Parser.ARG_PORT});
    }

    @Test(expected = InitializationParameterException.class)
    public void incorrectPortType() throws Exception {
        parser.parse(new String[]{Parser.ARG_PORT, "aaa"});
    }

    @Test(expected = InitializationParameterException.class)
    public void incorrectPortRange() throws Exception {
        parser.parse(new String[]{Parser.ARG_PORT, "-1"});
    }

    @Test(expected = InitializationParameterException.class)
    public void emptyPort() throws Exception {
        parser.parse(new String[]{Parser.ARG_PORT, ""});
    }

    @Test(expected = InitializationParameterException.class)
    public void emptyDataFolder() throws Exception {
        parser.parse(new String[]{Parser.ARG_DATA, ""});
    }

    @Test(expected = InitializationParameterException.class)
    public void incorrectProcCountType() throws Exception {
        parser.parse(new String[]{Parser.ARG_PROC_COUNT, "aaa"});
    }

    @Test(expected = InitializationParameterException.class)
    public void incorrectProcCountRange() throws Exception {
        parser.parse(new String[]{Parser.ARG_PROC_COUNT, "-1"});
    }

    @Test(expected = InitializationParameterException.class)
    public void emptyProcCount() throws Exception {
        parser.parse(new String[]{Parser.ARG_PROC_COUNT, ""});
    }


    private void assertSettings(Settings settings, int port, String folder, int procCount) {
        Assert.assertNotNull(settings);
        Assert.assertEquals(port, settings.getPort());
        Assert.assertEquals(folder, settings.getDataFolder());
        Assert.assertEquals(procCount, settings.getProcCount());
    }

}