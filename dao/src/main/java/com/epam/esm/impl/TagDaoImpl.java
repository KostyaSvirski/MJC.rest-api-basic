package com.epam.esm.impl;

import com.epam.esm.BaseDao;
import com.epam.esm.exception.DBCPDataSourceException;
import com.epam.esm.exception.DaoException;
import com.epam.esm.pool.DBCPDataSource;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TagDaoImpl implements BaseDao<Tag> {

    // TODO: 14.01.2021 make junctions with tags
    private static final String SQL_CREATE_TAG = "insert into tag_for_certificates (name) values (?)";
    private static final String SQL_DELETE_TAG = "delete from tag_for_certificates where id_tag = ?";
    private static final String SQL_UPDATE_TAG = "update tag_for_certificates set name = ? where id_tag = ?";
    private static final String SQL_FIND_ALL_TAGS = "select tag_for_certificates.id_tag, tag_for_certificates.name " +
            " from tag_for_certificates";
    private static final String SQL_FIND_SPECIFIC_TAGS = "select tag_for_certificates.id_tag, tag_for_certificates.name,  " +
            " from tag_for_certificates" +
            " where tag_for_certificate.name = ?";

    @Autowired
    private DBCPDataSource dataSource;

    @Override
    public void create(Tag tag) throws DBCPDataSourceException, DaoException {
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_CREATE_TAG);
            ps.setString(1, tag.getName());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException("error occurs while executing request", throwables);
        } finally {
            dataSource.closeConnection(connection);
        }

    }

    @Override
    public void delete(Tag tag) throws DBCPDataSourceException, DaoException {
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_DELETE_TAG);
            ps.setInt(1, (int)tag.getId());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException("error occurs while executing request", throwables);
        } finally {
            dataSource.closeConnection(connection);
        }
    }

    @Override
    public List<Tag> find(String name) throws DBCPDataSourceException, DaoException {
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_FIND_SPECIFIC_TAGS);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            List<Tag> listOfEntities = createListOfEntities(rs);
            return listOfEntities;
        } catch (SQLException throwables) {
            throw new DaoException("error occurs while executing request", throwables);
        } finally {
            dataSource.closeConnection(connection);
        }
    }

    @Override
    public List<Tag> findAll() throws DBCPDataSourceException, DaoException {
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_FIND_ALL_TAGS);
            ResultSet rs = ps.executeQuery();
            List<Tag> listOfEntities = createListOfEntities(rs);
            return listOfEntities;
        } catch (SQLException throwables) {
            throw new DaoException("error occurs while executing request", throwables);
        } finally {
            dataSource.closeConnection(connection);
        }
    }

    private List<Tag> createListOfEntities(ResultSet rs) throws SQLException {
        List<Tag> resultList = new ArrayList<>();
        while(rs.next()) {
            Tag newTag = new Tag.TagBuilder()
                    .buildId(Long.parseLong(Integer.toString(rs.getInt(1))))
                    .buildName(rs.getString(2))
                    .finishBuilding();
            resultList.add(newTag);
        }
        return resultList;
    }

    @Override
    public void update(Tag tag) throws DBCPDataSourceException, DaoException {
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_TAG);
            ps.setString(1, tag.getName());
            ps.setInt(2, (int)tag.getId());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException("error occurs while executing request", throwables);
        } finally {
            dataSource.closeConnection(connection);
        }

    }
}
