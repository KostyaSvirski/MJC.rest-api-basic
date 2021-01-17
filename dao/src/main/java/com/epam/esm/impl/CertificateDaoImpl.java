package com.epam.esm.impl;

import com.epam.esm.BaseDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DBCPDataSourceException;
import com.epam.esm.exception.DaoException;
import com.epam.esm.pool.DBCPDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CertificateDaoImpl implements BaseDao<GiftCertificate> {

    // TODO: 14.01.2021 make junctions with tags
    private static final String SQL_CREATE_CERTIFICATE = "insert into gift_certificate (name, description, price," +
            " duration, create_date, last_update_date) values (?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE_CERTIFICATE = "delete from gift_certificate where id_certificate = ?";
    private static final String SQL_UPDATE_CERTIFICATE = "update gift_certificate set name = ?, description = ?," +
            " price = ?, duration = ?, last_update_date = ? where id_certificate = ?";
    private static final String SQL_FIND_ALL_CERTIFICATES = "select gift_certificate.id_certificate" +
            " ,gift_certificate.name, description, price, duration, create_date, last_update_date," +
            " tag_for_certificates.id_tag," +
            " tag_for_certificates.name from gift_certificate" +
            " inner join junction_gift_cerficates_and_tags on gift_certificate.id_certificate =" +
            " junction_gift_cerficates_and_tags.id_certificate" +
            " inner join tag_for_certificates on junction_gift_cerficates_and_tags.id_tag = tag_for_certificates.id_tag";
    private static final String SQL_FIND_SPECIFIC_CERTIFICATE = "select gift_certificate.id_certificate" +
            " ,gift_certificate.name, description, price, duration, create_date, last_update_date," +
            " tag_for_certificates.id_tag," +
            " tag_for_certificates.name from gift_certificate" +
            " inner join junction_gift_cerficates_and_tags on gift_certificate.id_certificate =" +
            " junction_gift_cerficates_and_tags.id_certificate" +
            " inner join tag_for_certificates on junction_gift_cerficates_and_tags.id_tag = " +
            " tag_for_certificates.id_tag where gift_certificate.name = ?";

    @Autowired
    private DBCPDataSource dataSource;

    @Override
    public void create(GiftCertificate certificate) throws DBCPDataSourceException, DaoException {
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_CREATE_CERTIFICATE);
            ps.setString(1, certificate.getName());
            ps.setString(2, certificate.getDescription());
            ps.setLong(3, certificate.getPrice());
            ps.setTimestamp(4, Timestamp.valueOf(certificate.getDuration()));
            ps.setTimestamp(5, Timestamp.valueOf(certificate.getCreateDate()));
            ps.setTimestamp(6, Timestamp.valueOf(certificate.getLastUpdateDate()));
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException("error occurs while executing request", throwables);
        } finally {
           dataSource.closeConnection(connection);
        }

    }

    @Override
    public void delete(GiftCertificate certificate) throws DBCPDataSourceException, DaoException {
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_DELETE_CERTIFICATE);
            ps.setInt(1, (int)certificate.getId());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException("error occurs while executing request", throwables);
        } finally {
            dataSource.closeConnection(connection);
        }
    }

    @Override
    public List<GiftCertificate> find(String name) throws DBCPDataSourceException, DaoException {
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_FIND_SPECIFIC_CERTIFICATE);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            List<GiftCertificate> specificCertificates = createFoundList(rs);
            return specificCertificates;
        } catch (SQLException throwables) {
            throw new DaoException("error occurs while executing request", throwables);
        } finally {
            dataSource.closeConnection(connection);
        }
    }

    @Override
    public List<GiftCertificate> findAll() throws DBCPDataSourceException, DaoException {
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_FIND_ALL_CERTIFICATES);
            ResultSet rs = ps.executeQuery();
            List<GiftCertificate> allCertificates = createFoundList(rs);
            return allCertificates;
        } catch (SQLException throwables) {
            throw new DaoException("error occurs while executing request", throwables);
        } finally {
            dataSource.closeConnection(connection);
        }
    }

    private List<GiftCertificate> createFoundList(ResultSet rs) throws SQLException {
        List<GiftCertificate> resultList = new ArrayList<>();
        while(rs.next()) {
            long id = rs.getLong(1);
            String name = rs.getString(2);
            String description = rs.getString(3);
            long price = rs.getLong(4);
            LocalDateTime duration = rs.getTimestamp(5).toLocalDateTime();
            LocalDateTime createDate = rs.getTimestamp(6).toLocalDateTime();
            LocalDateTime lastUpdateDate = rs.getTimestamp(7).toLocalDateTime();
            long idTag = rs.getLong(8);
            String nameOfTag = rs.getString(9);
            resultList.add(new GiftCertificate.GiftCertificateBuilder()
                    .buildId(id).buildName(name).buildDescription(description)
                    .buildPrice(price).buildDuration(duration).buildCreateDate(createDate)
                    .buildLastUpdateDate(lastUpdateDate)
                    .buildTagDependsOnCertificate(new Tag(idTag, nameOfTag))
                    .finishBuilding());

        }
        return resultList;
    }

    @Override
    public void update(GiftCertificate certificate) throws DBCPDataSourceException, DaoException {
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_CERTIFICATE);
            ps.setString(1, certificate.getName());
            ps.setString(2, certificate.getDescription());
            ps.setLong(3, certificate.getPrice());
            ps.setTimestamp(4, Timestamp.valueOf(certificate.getDuration()));
            ps.setTimestamp(4, Timestamp.valueOf(certificate.getLastUpdateDate()));
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException("error occurs while executing request", throwables);
        } finally {
            dataSource.closeConnection(connection);
        }
    }
}
