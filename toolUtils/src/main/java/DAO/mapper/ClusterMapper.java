package DAO.mapper;


import DAO.entity.Cluster;

import java.util.List;

public interface ClusterMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CLUSTER
     *
     * @mbggenerated Thu May 18 10:00:35 CST 2017
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CLUSTER
     *
     * @mbggenerated Thu May 18 10:00:35 CST 2017
     */
    int insert(Cluster record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CLUSTER
     *
     * @mbggenerated Thu May 18 10:00:35 CST 2017
     */
    Cluster selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CLUSTER
     *
     * @mbggenerated Thu May 18 10:00:35 CST 2017
     */
    List<Cluster> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CLUSTER
     *
     * @mbggenerated Thu May 18 10:00:35 CST 2017
     */
    int updateByPrimaryKey(Cluster record);

    List<Cluster> selectUrlByClusterId(String clusterId);
}