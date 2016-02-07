package main.persistor;

import com.netflix.hystrix.HystrixCommand;
import main.entity.Expense;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AllExpensesCommand extends HystrixCommand {
    JdbcTemplate jdbcTemplate;

    protected AllExpensesCommand(Setter group, JdbcTemplate jdbcTemplate) {
        super(group);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    protected List<Expense> run() throws Exception {
        String sql = "SELECT * FROM EXPENSE;";
        return jdbcTemplate.query(sql, new ResultSetExtractor<List<Expense>>() {
            @Override
            public List<Expense> extractData(ResultSet rs) throws SQLException, DataAccessException {

                List<Expense> list = new ArrayList<>();

                while (rs.next()) {
                    Expense expense = new Expense();
                    expense.setId(rs.getString(1));
                    expense.setDate(rs.getDate(2));
                    expense.setCost(rs.getInt(3));
                    list.add(expense);
                }
                return list;
            }
        });
    }
}
