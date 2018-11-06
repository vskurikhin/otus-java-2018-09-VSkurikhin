package ru.otus.gwt.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ru.otus.gwt.shared.Emp;

import java.util.List;

public interface InsideServiceAsync
{
    void getEmpsList(AsyncCallback<List<Emp>> async);
    void addNewEmp(Emp emp, AsyncCallback<Void> voidAsyncCallback);
    void setEmpFirstName(long id, String value, AsyncCallback<Void> async);
    void setEmpSecondName(long id, String value, AsyncCallback<Void> async);
    void setEmpSurName(long id, String value, AsyncCallback<Void> async);
    void deleteEmp(long id, AsyncCallback<Void> async);

}
