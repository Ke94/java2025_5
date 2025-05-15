package App.model;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class ListOfTransaction {
    private List<Transaction> list = new ArrayList<>();

    public ListOfTransaction(){}
    public ListOfTransaction(List<Transaction> list){
        this.list = list;
    }

    public void add(Transaction transaction){
        list.add(transaction);
    }
    public void remove(int index){
        list.remove(index);
    }

    public ListOfTransaction selectByYear(int year){
        ListOfTransaction filtered = new ListOfTransaction();
        for(Transaction t : list)
            if(t.getYear() == year) filtered.add(t);
        return filtered;
    }
    public ListOfTransaction selectByMonth(int month){
        ListOfTransaction filtered = new ListOfTransaction();
        for(Transaction t : list)
            if(t.getMonth() == month) filtered.add(t);
        return filtered;
    }
    public ListOfTransaction selectByYearAndMonth(int year, int month){
        ListOfTransaction filtered = new ListOfTransaction();
        for(Transaction t : list)
            if(t.getYear() == year && t.getMonth() == month) filtered.add(t);
        return filtered;
    }
    public List<Transaction> getList(){
        return list;
    }

    public void sortByAmount(){
        sortByAmount(false);
    }
    public void sortByAmount(boolean reverse){
        if(reverse) list.sort(Comparator.comparing(Transaction::getAmount).reversed());
        else list.sort(Comparator.comparing(Transaction::getAmount));
    }
    public void sortByCreatedTime(){
        sortByCreatedTime(false);
    }
    public void sortByCreatedTime(boolean reverse){
        if(reverse) list.sort(Comparator.comparing(Transaction::getCreatedTime).reversed());
        else list.sort(Comparator.comparing(Transaction::getCreatedTime));
    }
}