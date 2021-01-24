package com.epam.esm.impl;

import com.epam.esm.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DBCPDataSourceException;
import com.epam.esm.exception.DaoException;
import com.epam.esm.pool.DBCPDataSource;
import com.epam.esm.util.RequestCreator;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.ChronoField;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class CertificateDaoImpl implements GiftCertificateDao {

    private static final String SQL_CREATE_CERTIFICATE = "insert into gift_certificate (name, description, price," +
            " duration, create_date, last_update_date) values (?, ?, ?, ?, ?, ?)";
    private static final String SQL_CREATE_JUNCTIONS_WITH_TAGS = "insert into junction_gift_cerficates_and_tags " +
            "(id_certificate, id_tag) values (?, ?)";
    private static final String SQL_FIND_ID_SPECIFIC_CERTIFICATE = "select id_certificate from gift_certificate " +
            "where name = ?";
    private static final String SQL_FIND_ID_SPECIFIC_TAG = "select id_tag from tag_for_certificates " +
            "where id_tag = ?";
    private static final String SQL_FIND_LAST_ID = "select max(id_certificate) from gift_certificate";
    private static final String SQL_DELETE_CERTIFICATE = "delete from gift_certificate where id_certificate = ?";
    private static final String SQL_DELETE_JUNCTIONS = "delete from junction_gift_cerficates_and_tags " +
            "where id_certificate = ?";
    private static final String SQL_UPDATE_CERTIFICATE = "update gift_certificate set name = ?, description = ?," +
            " price = ?, duration = ?, last_update_date = ? where id_certificate = ?";
    private static final String SQL_FIND_ALL_CERTIFICATES = "select gift_certificate.id_certificate," +
            " gift_certificate.name, description, price, duration, create_date, last_update_date," +
            " tag_for_certificates.id_tag," +
            " tag_for_certificates.name from gift_certificate" +
            " inner join junction_gift_cerficates_and_tags on gift_certificate.id_certificate =" +
            " junction_gift_cerficates_and_tags.id_certificate" +
            " inner join tag_for_certificates on junction_gift_cerficates_and_tags.id_tag =" +
            " tag_for_certificates.id_tag";
    private static final String SQL_FIND_SPECIFIC_CERTIFICATE = "select gift_certificate.id_certificate," +
            " gift_certificate.name, description, price, duration, create_date, last_update_date," +
            " tag_for_certificates.id_tag," +
            " tag_for_certificates.name from gift_certificate" +
            " inner join junction_gift_cerficates_and_tags on gift_certificate.id_certificate =" +
            " junction_gift_cerficates_and_tags.id_certificate" +
            " inner join tag_for_certificates on junction_gift_cerficates_and_tags.id_tag = " +
            " tag_for_certificates.id_tag where gift_certificate.name = ?";
    private static final String SQL_SEARCH_BY_PART_OF_NAME = "select gift_certificate.id_certificate," +
            " gift_certificate.name, description, price, duration, create_date, last_update_date," +
            " tag_for_certificates.id_tag," +
            " tag_for_certificates.name from gift_certificate" +
            " inner join junction_gift_cerficates_and_tags on gift_certificate.id_certificate =" +
            " junction_gift_cerficates_and_tags.id_certificate" +
            " inner join tag_for_certificates on junction_gift_cerficates_and_tags.id_tag = " +
            " tag_for_certificates.id_tag where gift_certificate.name like ";
    private static final String SQL_SEARCH_BY_PART_OF_DESCRIPTION = "select gift_certificate.id_certificate," +
            " gift_certificate.name, description, price, duration, create_date, last_update_date," +
            " tag_for_certificates.id_tag," +
            " tag_for_certificates.name from gift_certificate" +
            " inner join junction_gift_cerficates_and_tags on gift_certificate.id_certificate =" +
            " junction_gift_cerficates_and_tags.id_certificate" +
            " inner join tag_for_certificates on junction_gift_cerficates_and_tags.id_tag = " +
            " tag_for_certificates.id_tag where gift_certificate.description like ";
    private static final String SQL_SORT_CERTIFICATES = "select gift_certificate.id_certificate," +
            " gift_certificate.name, description, price, duration, create_date, last_update_date," +
            " tag_for_certificates.id_tag," +
            " tag_for_certificates.name from gift_certificate" +
            " inner join junction_gift_cerficates_and_tags on gift_certificate.id_certificate =" +
            " junction_gift_cerficates_and_tags.id_certificate" +
            " inner join tag_for_certificates on junction_gift_cerficates_and_tags.id_tag = " +
            " tag_for_certificates.id_tag order by ";
    private static final String SQL_FIND_BY_TAGS = "select gift_certificate.id_certificate," +
            " gift_certificate.name, description, price, duration, create_date, last_update_date," +
            " tag_for_certificates.id_tag," +
            " tag_for_certificates.name from gift_certificate" +
            " inner join junction_gift_cerficates_and_tags on gift_certificate.id_certificate =" +
            " junction_gift_cerficates_and_tags.id_certificate" +
            " inner join tag_for_certificates on junction_gift_cerficates_and_tags.id_tag = " +
            " tag_for_certificates.id_tag where tag_for_certificates.id_tag = ? ";

    @Autowired
    private DBCPDataSource dataSource;

    @Override
    public int create(GiftCertificate certificate) throws DaoException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            try {
                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE_CERTIFICATE);
                ps.setString(1, certificate.getName());
                ps.setString(2, certificate.getDescription());
                ps.setLong(3, certificate.getPrice());
                ps.setDate(4, Date.valueOf(certificate.getDuration().addTo(LocalDate.now()).toString()));
                ps.setTimestamp(5, Timestamp.from(certificate.getCreateDate()));
                ps.setTimestamp(6, Timestamp.from(certificate.getLastUpdateDate()));
                ps.executeUpdate();
                ps = connection.prepareStatement(SQL_FIND_ID_SPECIFIC_TAG);
                for (Tag tag : certificate.getTagsDependsOnCertificate()) {
                    ps.setInt(1, (int) tag.getId());
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        connection.rollback();
                        throw new DaoException("tag no found");
                    }
                }
                ps = connection.prepareStatement(SQL_FIND_LAST_ID);
                ResultSet rs = ps.executeQuery();
                int id = 0;
                while (rs.next()) {
                    id = rs.getInt(1);
                }
                ps = connection.prepareStatement(SQL_CREATE_JUNCTIONS_WITH_TAGS);
                for (Tag tag : certificate.getTagsDependsOnCertificate()) {
                    ps.setInt(1, id);
                    ps.setInt(2, (int) tag.getId());
                    ps.executeUpdate();
                }
                connection.commit();
                return id;
            } catch (SQLException throwables) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    throw new DaoException("error occurs due to rollback", throwables);
                }
                throw new DaoException("error occurs while executing request", throwables);
            } finally {
                dataSource.closeConnection(connection);
            }
        } catch (DBCPDataSourceException e) {
            throw new DaoException(e.getMessage());
        }

    }

    @Override
    public void delete(long id) throws DaoException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            try {
                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement(SQL_DELETE_JUNCTIONS);
                ps.setInt(1, (int) id);
                ps.executeUpdate();
                ps = connection.prepareStatement(SQL_DELETE_CERTIFICATE);
                ps.setInt(1, (int) id);
                ps.executeUpdate();
                connection.commit();
            } catch (SQLException throwables) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    throw new DaoException("error occurs due to rollback", throwables);
                }
                throw new DaoException("error occurs while executing request", throwables);
            } finally {
                dataSource.closeConnection(connection);
            }
        } catch (DBCPDataSourceException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public List<GiftCertificate> find(String name) throws DaoException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
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
        } catch (DBCPDataSourceException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public List<GiftCertificate> findAll() throws DaoException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
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
        } catch (DBCPDataSourceException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public void update(Map<String, String> paramsMap, long id, Instant timeOfUpdate) throws DaoException {
        try {
            Connection connection = dataSource.getConnection();
            String specificRequest = RequestCreator.createUpdate(paramsMap);
            try {
                PreparedStatement ps = connection.prepareStatement(specificRequest);
                int i = 1;
                for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
                    if (entry.getKey().equals("price")) {
                        ps.setLong(i, Long.parseLong(entry.getValue()));
                    } else if (entry.getKey().equals("duration")) {
                        ps.setDate(i, Date.valueOf(Period.parse(entry.getValue())
                                .addTo(LocalDate.now()).toString()));
                    } else {
                        ps.setString(i, entry.getValue());
                    }
                    i++;
                }
                ps.setTimestamp(i, Timestamp.from(timeOfUpdate));
                ps.setInt(i + 1, (int) id);
                ps.executeUpdate();
            } catch (SQLException throwables) {
                throw new DaoException("error occurs while executing request", throwables);
            } finally {
                dataSource.closeConnection(connection);
            }
        } catch (DBCPDataSourceException e) {
            throw new DaoException(e.getMessage());
        }

    }

    @Override
    public List<GiftCertificate> sortCertificates(Map<String, String> paramsMap) throws DaoException {
        try {
            Connection connection = dataSource.getConnection();
            try {
                String specificRequest = createSpecificRequest(paramsMap);
                PreparedStatement ps = connection.prepareStatement(specificRequest);
                ResultSet rs = ps.executeQuery();
                return createFoundList(rs);
            } catch (SQLException throwables) {
                throw new DaoException("error occurs while executing request", throwables);
            } finally {
                dataSource.closeConnection(connection);
            }
        } catch (DBCPDataSourceException e) {
            throw new DaoException(e.getMessage());
        }

    }

    @Override
    public List<GiftCertificate> searchByName(String name) throws DaoException {
        return searchCertificatesByParam(name, SQL_SEARCH_BY_PART_OF_NAME);
    }

    @Override
    public List<GiftCertificate> searchByDescription(String description) throws DaoException {
        return searchCertificatesByParam(description, SQL_SEARCH_BY_PART_OF_DESCRIPTION);
    }

    private List<GiftCertificate> searchCertificatesByParam(String param, String sqlSearchRequest)
            throws DaoException {
        try {
            Connection connection = dataSource.getConnection();
            try {
                String specificRequest = createSpecificRequest(param, sqlSearchRequest);
                PreparedStatement ps = connection.prepareStatement(specificRequest);
                ResultSet rs = ps.executeQuery();
                return createFoundList(rs);
            } catch (SQLException throwables) {
                throw new DaoException("error occurs while executing request", throwables);
            } finally {
                dataSource.closeConnection(connection);
            }
        } catch (DBCPDataSourceException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public List<GiftCertificate> searchByTag(long idOfTag) throws DaoException {
        try {
            Connection connection = dataSource.getConnection();
            try {
                PreparedStatement ps = connection.prepareStatement(SQL_FIND_BY_TAGS);
                ps.setInt(1, (int) idOfTag);
                ResultSet rs = ps.executeQuery();
                return createFoundList(rs);
            } catch (SQLException throwables) {
                throw new DaoException("error occurs while executing request", throwables);
            } finally {
                dataSource.closeConnection(connection);
            }
        } catch (DBCPDataSourceException e) {
            throw new DaoException(e.getMessage());
        }

    }

    private List<GiftCertificate> createFoundList(ResultSet rs) throws SQLException {
        List<GiftCertificate> resultList = new ArrayList<>();
        boolean flag;
        while (rs.next()) {
            flag = false;
            long id = rs.getLong(1);
            String name = rs.getString(2);
            String description = rs.getString(3);
            long price = rs.getLong(4);
            LocalDate duration = rs.getDate(5).toLocalDate();
            LocalDateTime createDate = rs.getTimestamp(6).toLocalDateTime();
            LocalDateTime lastUpdateDate = rs.getTimestamp(7).toLocalDateTime();
            long idTag = rs.getLong(8);
            String nameOfTag = rs.getString(9);
            for (GiftCertificate existCertificate : resultList) {
                if (existCertificate.getId() == id) {
                    existCertificate.setTagsDependsOnCertificate(new Tag.TagBuilder().buildId(idTag)
                            .buildName(nameOfTag).finishBuilding());
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                LocalDate correctLocalDate = duration
                        .minusDays(LocalDate.now().getLong(ChronoField.DAY_OF_MONTH))
                        .minusMonths(LocalDate.now().getLong(ChronoField.MONTH_OF_YEAR))
                        .minusYears(LocalDate.now().getLong(ChronoField.YEAR));
                resultList.add(new GiftCertificate.GiftCertificateBuilder()
                        .buildId(id).buildName(name).buildDescription(description)
                        .buildPrice(price)
                        .buildDuration(Period.of(correctLocalDate.getYear(), correctLocalDate.getMonthValue(),
                                correctLocalDate.getDayOfMonth()))
                        .buildCreateDate(createDate.toInstant(ZoneOffset.UTC))
                        .buildLastUpdateDate(lastUpdateDate.toInstant(ZoneOffset.UTC))
                        .buildTagDependsOnCertificate(new Tag.TagBuilder().buildId(idTag)
                                .buildName(nameOfTag).finishBuilding())
                        .finishBuilding());
            }

        }
        return resultList;
    }

    private String createSpecificRequest(Map<String, String> paramsMap) {
        StringBuilder sb = new StringBuilder(SQL_SORT_CERTIFICATES);
        sb.append(paramsMap.get("sort"));
        sb.append(" ");
        sb.append(paramsMap.get("method"));
        return sb.toString();
    }

    private String createSpecificRequest(String param, String baseRequest) {
        StringBuilder sb = new StringBuilder(baseRequest);
        sb.append("'%");
        sb.append(param);
        sb.append("%'");
        return sb.toString();
    }

}
