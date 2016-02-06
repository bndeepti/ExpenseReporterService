package main.service;

import com.netflix.hystrix.Hystrix;
import com.netflix.hystrix.HystrixCommand;
import main.entity.Expense;
import main.persistor.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;
import rx.functions.Func1;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    public HashMap<String, String> addExpense(Expense expense) throws SQLException {
        return expenseRepository.addExpense(expense);
    }

    public Observable<Expense> getAllExpenses() throws SQLException {
        List<Map<String, Object>> allExpenses = expenseRepository.getAllExpenses();
        return Observable.from(allExpenses).map(new Func1<Map<String, Object>, Expense>() {
            @Override
            public Expense call(Map<String, Object> exp) {
                Expense expense = new Expense();
                expense.setId((String) exp.get("id"));
                expense.setCost((Integer) exp.get("cost"));
                expense.setDate((Date) exp.get("date"));
                return expense;
            }
        });
    }
}
