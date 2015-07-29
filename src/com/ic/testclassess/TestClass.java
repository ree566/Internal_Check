package com.ic.testclassess;

import com.ic.entity.Answer;
import com.ic.entity.Identit;
import com.ic.servlet.InsertData;
import com.ic.servlet.Login;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class TestClass {

    public static void main(String[] args) {
        BasicConfigurator.configure();
        Logger logger = Logger.getLogger(TestClass.class);
        logger.error("message test");
    }
}
