package com.noname.hiretask.client.settings;

import com.noname.hiretask.client.ClientCommand;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests arguments passed to the app.
 */
public class AppSettingsParserTest {

    @Test(expected = IllegalArgumentException.class)
    public void case1() throws Exception {
        AppSettingsParser.parseArguments(new String[0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void case2() throws Exception {
        String array[] = {""};
        AppSettingsParser.parseArguments(array);
    }

    @Test(expected = IllegalArgumentException.class)
    public void case3() throws Exception {
        String array[] = {"-serverPort"};
        AppSettingsParser.parseArguments(array);
    }

    @Test(expected = IllegalArgumentException.class)
    public void case4() throws Exception {
        String array[] = {"3000"};
        AppSettingsParser.parseArguments(array);
    }

    @Test(expected = IllegalArgumentException.class)
    public void case5() throws Exception {
        String array[] = {"-serverPort", "3000"};
        AppSettingsParser.parseArguments(array);
    }

    @Test(expected = IllegalArgumentException.class)
    public void case6() throws Exception {
        String array[] = {"-serverPort", "3000", ""};
        AppSettingsParser.parseArguments(array);
    }

    @Test(expected = IllegalArgumentException.class)
    public void case7() throws Exception {
        String array[] = {"-serverPort", "3000", "-something"};
        AppSettingsParser.parseArguments(array);
    }

    @Test(expected = Test.None.class)
    public void case8() throws Exception {
        String array[] = {"-serverPort", "3000", "-quit"};
        final ParametersHolder parametersHolder = AppSettingsParser.parseArguments(array);
        Assert.assertEquals(3000, parametersHolder.getPort());
        Assert.assertEquals(ClientCommand.QUIT, parametersHolder.getClientCommand());
    }

    @Test(expected = IllegalArgumentException.class)
    public void case9() throws Exception {
        String array[] = {"serverPort", "3000", "-quit"};
        AppSettingsParser.parseArguments(array);
    }

    @Test(expected = IllegalArgumentException.class)
    public void case10() throws Exception {
        String array[] = {"-serverPort", "-1", "-quit"};
        AppSettingsParser.parseArguments(array);
    }

    @Test(expected = IllegalArgumentException.class)
    public void case11() throws Exception {
        String array[] = {"-serverPort", "65536", "-quit"};
        AppSettingsParser.parseArguments(array);
    }

}