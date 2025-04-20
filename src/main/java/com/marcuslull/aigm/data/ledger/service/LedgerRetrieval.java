package com.marcuslull.aigm.data.ledger.service;

import com.marcuslull.aigm.data.ledger.model.LedgerQueries;
import com.marcuslull.aigm.router.CommunicationSender;
import com.marcuslull.aigm.router.model.CommunicationPacket;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LedgerRetrieval {

    private final CommunicationSender communicationSender;
    private final DataSource dataSource;

    public LedgerRetrieval(CommunicationSender communicationSender, DataSource dataSource) {
        this.communicationSender = communicationSender;
        this.dataSource = dataSource;
    }

    public void handle(CommunicationPacket communicationPacket) {

        if (communicationPacket != null) {
            if (communicationPacket.hasLedgerSearch() && communicationPacket.getLedgerSearch().getCategory() != null) {

                String result;
                String statement;

                if (communicationPacket.getLedgerSearch().getName() ==  null) {
                    statement = getSQLStatement(communicationPacket.getLedgerSearch().getCategory(), true);
                    result = getAll(statement);
                }
                else {
                    statement = getSQLStatement(communicationPacket.getLedgerSearch().getCategory(), false);
                    result = getOne(statement, communicationPacket.getLedgerSearch().getName());
                }

                communicationPacket.getLedgerSearch().setResponse(result);
                System.out.println("LEDGER SEARCH - " + communicationPacket.getAuthor() + ": " + communicationPacket);
            }
            communicationPacket.getLedgerSearch().setResponse("Category needed for Ledger Search - Please try again");
            communicationSender.send(communicationPacket);
        }
        // should not be here
    }

    private String getOne(String sql, String name) {

        String response;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            System.out.println("sql = " + sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            response = parseSQLResultSet(resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return response;
    }

    private String getAll(String sql) {

        String response;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            System.out.println("sql = " + sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            response = parseSQLResultSet(resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return response;
    }

    private String getSQLStatement(String category, boolean getAll) {
        return
            switch (category) {
                case "armor" -> getAll ? LedgerQueries.armor : LedgerQueries.armorByName;
                case "character" -> getAll ? LedgerQueries.character : LedgerQueries.characterByName;
                case "feat" -> getAll ? LedgerQueries.feat : LedgerQueries.featByName;
                case "item" -> getAll ? LedgerQueries.item : LedgerQueries.itemByName;
                case "spell" -> getAll ? LedgerQueries.spell : LedgerQueries.spellByName;
                case "weapon" -> getAll ? LedgerQueries.weapon : LedgerQueries.weaponByName;
                case null, default -> "";
        };
    }

    private static String parseSQLResultSet(ResultSet resultSet) throws SQLException {

        if (resultSet.getMetaData().getColumnCount() == 0) return "";

        StringBuilder stringBuilder = new StringBuilder();

        int numberOfColumns = resultSet.getMetaData().getColumnCount();
        List<String> columnNames = new ArrayList<>();

        // prefetch columnNames
        for (int i = 1; i <= numberOfColumns; i++) {
            columnNames.add(resultSet.getMetaData().getColumnName(i));
        }

        while (resultSet.next()) {
            for (String columnName : columnNames) {
                stringBuilder.append(resultSet.getString(columnName)).append(" | ");
            }
            stringBuilder.append("\n");
        }
//        System.out.println("stringBuilder = " + stringBuilder);
        return stringBuilder.toString();
    }
}
