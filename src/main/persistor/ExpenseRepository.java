package main.persistor;

import main.entity.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ExpenseRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public HashMap<String, String> addExpense(Expense expense) throws SQLException {
        HashMap<String, String> response = new HashMap<>();
        String sql = "INSERT INTO EXPENSE (ID,DATE,COST) "
                + "VALUES (" + "'" + expense.getId() + "'" + "," + "'" + expense.getDate() + "'" + "," + expense.getCost() + ");";
        int update = jdbcTemplate.update(sql);
        if(update == 1) {
            response.put("Status", "Success");
        }
        else {
            response.put("Status", "Failure");
        }
        return response;
    }

    public List<Map<String, Object>> getAllExpenses() throws SQLException {
        String sql = "SELECT * FROM EXPENSE;";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }
}
