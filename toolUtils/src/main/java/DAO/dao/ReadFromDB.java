package DAO.dao;

import DAO.entity.Channel;
import DAO.entity.Cluster;
import DAO.entity.Htmls;
import DAO.mapper.ChannelMapper;
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
                else if (getHtmlsByUrl(cluster.getUrl()).getProcesscontent() != null && isValidateUrl(cluster.getUrl())) {
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

    public static List<Channel> getChannels() {
        SqlSession sqlSession = SqlseesionBuilder.getSession();
        ChannelMapper channelMapper = sqlSession.getMapper(ChannelMapper.class);
        return channelMapper.selectAll();
    }

    public static Map<String, String> getText(int channelId) {
        Map<String, String> map = new HashMap<>();
        SqlSession sqlseesion = SqlseesionBuilder.getSession();
        HtmlsMapper htmlsMapper = sqlseesion.getMapper(HtmlsMapper.class);
        List<Htmls> htmls = htmlsMapper.selectByChannelId(channelId);
        List<String> texts = new ArrayList<>();
        for (Htmls htmls1 : htmls) {
            map.put(htmls1.getUrl(), htmls1.getProcesscontent());
        }
        sqlseesion.close();
        return map;
    }

    public static boolean isValidateUrl(String url) {
        String pattern = ".*(/.+){4,}";
        if (url.matches(pattern)) {
            if (!url.contains("photo") && !url.contains("view") && !url.contains("tuku"))
                return true;
            else
                return false;
        } else {
            return false;
        }
    }
}
