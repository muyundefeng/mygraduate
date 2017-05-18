package DAO.dao;

import DAO.entity.Cluster;
import DAO.mapper.ClusterMapper;
import DAO.session.SqlseesionBuilder;
import DAO.entity.Htmls;
import DAO.mapper.HtmlsMapper;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HashUtils;
import utils.ProcessHtmlUtils;

/**
 * Created by lisheng on 17-5-18.
 */
public class SaveDB {

    public static Logger logger = LoggerFactory.getLogger(SaveDB.class);

    public static void insertHtml(int channelId, String content, String url) {
        try {
            String afterContent = ProcessHtmlUtils.rmSomeScript(content);
            SqlSession sqlSession = SqlseesionBuilder.getSession();
            HtmlsMapper htmlsMapper = sqlSession.getMapper(HtmlsMapper.class);
            Htmls htmls = new Htmls();
            htmls.setChannelId(channelId);
            htmls.setContent(content);
            htmls.setProcesscontent(afterContent);
            htmls.setUrl(url);
            htmlsMapper.insert(htmls);
            sqlSession.commit();
            sqlSession.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static void insertCluster(String clusterId, String url) {
        try {
            SqlSession sqlSession = SqlseesionBuilder.getSession();
            ClusterMapper clusterMapper = sqlSession.getMapper(ClusterMapper.class);
            Cluster cluster = new Cluster();
            cluster.setClusterId(clusterId);
            cluster.setUrl(url);
            cluster.setId(HashUtils.generateHash(clusterId + url));
            clusterMapper.insert(cluster);
            sqlSession.commit();
            sqlSession.close();

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
