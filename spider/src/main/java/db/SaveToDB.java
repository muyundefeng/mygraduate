package db;

import DAO.dao.ReadFromDB;
import DAO.dao.SaveDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lisheng on 17-5-18.
 */
public class SaveToDB {

    public static Logger logger = LoggerFactory.getLogger(SaveToDB.class);
    public static Set<String> set = new HashSet<String>();

    public static void initSet() {
        set.addAll(ReadFromDB.getUrl());
    }

    public static void saveToDB(String html, String url, int channleId) {
        if (set.add(url))
            SaveDB.insertHtml(channleId, html, url);
    }
}
