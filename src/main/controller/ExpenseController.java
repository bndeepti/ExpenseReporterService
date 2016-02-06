package main.controller;

import main.entity.Expense;
import main.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

import java.sql.SQLException;
import java.util.*;

@RestController
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @RequestMapping(value = "/api/expense", method = RequestMethod.POST)
    public HashMap<String, String> addExpense(@RequestBody Expense expense) throws SQLException {
        return expenseService.addExpense(expense);
    }

    @RequestMapping(value = "/api/expense", method = RequestMethod.GET)
    public DeferredResult<List<Expense>> getAllExpenses() throws SQLException {
        return convertToDeferredResult(expenseService.getAllExpenses());
    }

    private DeferredResult<List<Expense>> convertToDeferredResult(Observable<Expense> allExpenses) {
        DeferredResult<List<Expense>> deferredResult = new DeferredResult<>();
        List<Expense> arrayList = new ArrayList<>();
//        allExpenses.subscribe(expense -> arrayList.add(expense),
//                error -> deferredResult.setErrorResult(error),
//                () -> deferredResult.setResult(arrayList));
        allExpenses.subscribe(new Action1<Expense>() {
                                  @Override
                                  public void call(Expense expense) {
                                      arrayList.add(expense);
                                  }
                              },
                new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        deferredResult.setErrorResult(throwable);
                    }
                },
                new Action0() {
                    @Override
                    public void call() {
                        deferredResult.setResult(arrayList);
                    }
                });
        return deferredResult;
    }
}
