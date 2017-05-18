package DAO.dao;

import DAO.entity.Cluster;
import DAO.entity.Htmls;
import DAO.mapper.ClusterMapper;
import DAO.mapper.HtmlsMapper;
import DAO.session.SqlseesionBuilder;
import org.apache.ibatis.session.SqlSession;

import java.util.*;

/**
 * Created by lisheng on 17-5-18.
 */
public class ReadFromDB {

    public static List<String> getUrl() {
        SqlSession sqlSession = SqlseesionBuilder.getSession();
        HtmlsMapper htmlsMapper = sqlSession.getMapper(HtmlsMapper.class);
        List<Htmls> htmles = htmlsMapper.selectAll();
        List<String> urls = new ArrayList<>();
        for (Htmls html : htmles) {
            urls.add(html.getUrl());
        }
        sqlSession.close();
        return urls;
    }

    public static List<String> getClusterIds() {
        SqlSession sqlSession = SqlseesionBuilder.getSession();
        ClusterMapper clusterMapper = sqlSession.getMapper(ClusterMapper.class);
        List<Cluster> clusters = clusterMapper.selectAll();
        List<String> clusterIds = new ArrayList<>();
        for (Cluster cluster : clusters) {
            clusterIds.add(cluster.getClusterId());
        }
        sqlSession.close();
        return clusterIds;
    }

    public static Map<String, List<String>> getTwoUrl(Set<String> clusterIds) {
        SqlSession sqlSession = SqlseesionBuilder.getSession();
        ClusterMapper clusterMapper = sqlSession.getMapper(ClusterMapper.class);
        Map<String, List<String>> map = new HashMap<>();
        for (String clusterId : clusterIds) {
            List<String> twoUrls = new ArrayList<>();
            List<Cluster> clusters = clusterMapper.selectUrlByClusterId(clusterId);
            int count = 0;
            for (Cluster cluster : clusters) {
                if (count == 2)
                    break;
                else if (getHtmlsByUrl(cluster.getUrl()).getProcesscontent() != null) {
                    twoUrls.add(cluster.getUrl());
                    count++;
                } else {
                    continue;
                }
            }
            map.put(clusterId, twoUrls);
        }
        return map;
    }

    public static Htmls getHtmlsByUrl(String url) {
        SqlSession sqlSession = SqlseesionBuilder.getSession();
        HtmlsMapper htmlsMapper = sqlSession.getMapper(HtmlsMapper.class);
        Htmls htmls = htmlsMapper.selectByUrl(url);
        sqlSession.close();
        return htmls;
    }

}
