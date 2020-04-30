package com.bluto.fc.mapper;

import com.bluto.fc.model.Agent;
import com.bluto.fc.model.AgentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AgentMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AGENT
     *
     * @mbggenerated Thu Apr 30 11:12:19 CST 2020
     */
    int countByExample(AgentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AGENT
     *
     * @mbggenerated Thu Apr 30 11:12:19 CST 2020
     */
    int deleteByExample(AgentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AGENT
     *
     * @mbggenerated Thu Apr 30 11:12:19 CST 2020
     */
    int deleteByPrimaryKey(Long AGENT_ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AGENT
     *
     * @mbggenerated Thu Apr 30 11:12:19 CST 2020
     */
    int insert(Agent record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AGENT
     *
     * @mbggenerated Thu Apr 30 11:12:19 CST 2020
     */
    int insertSelective(Agent record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AGENT
     *
     * @mbggenerated Thu Apr 30 11:12:19 CST 2020
     */
    List<Agent> selectByExample(AgentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AGENT
     *
     * @mbggenerated Thu Apr 30 11:12:19 CST 2020
     */
    Agent selectByPrimaryKey(Long AGENT_ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AGENT
     *
     * @mbggenerated Thu Apr 30 11:12:19 CST 2020
     */
    int updateByExampleSelective(@Param("record") Agent record, @Param("example") AgentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AGENT
     *
     * @mbggenerated Thu Apr 30 11:12:19 CST 2020
     */
    int updateByExample(@Param("record") Agent record, @Param("example") AgentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AGENT
     *
     * @mbggenerated Thu Apr 30 11:12:19 CST 2020
     */
    int updateByPrimaryKeySelective(Agent record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AGENT
     *
     * @mbggenerated Thu Apr 30 11:12:19 CST 2020
     */
    int updateByPrimaryKey(Agent record);
}