package App.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

import java.util.*;

public class ListOfTransaction{
    private ObservableList<Transaction> list;
    private HashMap<String, Integer> categories = new HashMap<>();

    public ListOfTransaction(){
        list = FXCollections.observableArrayList();
    }
    public ListOfTransaction(List<Transaction> list){
        this.list = FXCollections.observableArrayList(list);
        for(Transaction t : list){
            addToCategories(t);
        }
    }
    public ListOfTransaction(ObservableList<Transaction> list){
        this.list = list;
        for(Transaction t : list){
            addToCategories(t);
        }
    }

    /**
     * 增加一筆交易紀錄
     * @param transaction 交易記錄
     */
    public void add(Transaction transaction){
        list.add(transaction);
        addToCategories(transaction);
    }
    public void addToCategories(Transaction transaction){
        categories.put(transaction.getCategory(), categories.getOrDefault(transaction.getCategory(), 0)+1);
    }
    /**
     * 刪除指定位置的交易記錄
     * @param index 索引值
     */
    public void remove(int index){
        removeFromCategories(list.get(index));
        list.remove(index);
    }
    public void remove(Transaction transaction){
        removeFromCategories(transaction);
        list.remove(transaction);
    }
    public void removeFromCategories(Transaction transaction){
        categories.replace(transaction.getCategory(), categories.get(transaction.getCategory())-1);
        if(categories.get(transaction.getCategory()) == 0) categories.remove(transaction.getCategory());
    }
    /**
     * 修改指定位置的交易紀錄
     * @param index 索引值
     * @param transaction 交易紀錄
     */
    public void modify(int index, Transaction transaction){
        list.set(index, transaction);
    }

    public void clearCategories(){
        categories.clear();
    }

    public void setAll(ObservableList<Transaction> list){
        clearCategories();
        this.list.setAll(list);
        for(Transaction t : list){
            addToCategories(t);
        }
    }
    public void setAll(ListOfTransaction list){
        clearCategories();
        this.list.setAll(list.getList());
        for(Transaction t : list.getList()){
            addToCategories(t);
        }
    }
    public void setAll(List<Transaction> list){
        clearCategories();
        this.list.setAll(FXCollections.observableArrayList(list));
        for(Transaction t : list){
            addToCategories(t);
        }
    }


    /**
     * 計算List中所有記錄金額總和
     * @return 收入為正數 支出為負數
     */
    public int sumAll(){
        return sumWithRange(0, list.size()-1);
    }
    /**
     * 根據範圍(閉區間[l:r])計算金額總和
     * @param l 左界
     * @param r 右界
     * @return 收入為正數 支出為負數
     */
    public int sumWithRange(int l, int r){
        int res = 0;
        for(int i = l; i <= r; i++)
            res += list.get(i).getAmountWithKind();
        return res;
    }
    public int sumWithCategory(String category){
        int res = 0;
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getCategory() == category)
                res += list.get(i).getAmount();
        }
        return res;
    }

    public List<Pair<String, Integer>> getCategoryAndAmount(){
        List<Pair<String, Integer>> ret = new ArrayList<>();
        for(String category : categories.keySet()){
            ret.add(new Pair<>(category, sumWithCategory(category)));
        }
        return ret;
    }

    /**
     * 以"年份"篩選出資料
     * @param year 年份
     * @return 篩選出來的 ListOfTransactioin 物件
     */
    public ListOfTransaction selectByYear(int year){
        ListOfTransaction filtered = new ListOfTransaction();
        for(Transaction t : list)
            if(t.getYear() == year) filtered.add(t);
        return filtered;
    }

    /**
     * 以"月份"篩選出資料
     * @param month 月份
     * @return 篩選出來的 ListOfTransaction 物件
     */
    public ListOfTransaction selectByMonth(int month){
        ListOfTransaction filtered = new ListOfTransaction();
        for(Transaction t : list)
            if(t.getMonth() == month) filtered.add(t);
        return filtered;
    }

    /**
     * 同時以"年份"和"月份"篩選資料
     * @param year 月份
     * @param month 年份
     * @return 篩選出來的 ListOfTransaction 物件
     */
    public ListOfTransaction selectByYearAndMonth(int year, int month){
        ListOfTransaction filtered = new ListOfTransaction();
        for(Transaction t : list)
            if(t.getYear() == year && t.getMonth() == month) filtered.add(t);
        return filtered;
    }

    /**
     * 以"支出/收入"篩選資料
     * @param kind 支出/收入
     * @return 篩選出來的 ListOfTransaction 物件
     */
    public ListOfTransaction selectByKind(KindOfTransaction kind){
        ListOfTransaction filtered = new ListOfTransaction();
        for(Transaction t : list)
            if(t.getKind() == kind) filtered.add(t);
        return filtered;
    }

    /**
     * 以"類別"篩選資料
     * @param category 類別
     * @return 篩選出來的 ListOfTransaction 物件
     */
    public ListOfTransaction selectByCategory(String category){
        ListOfTransaction filtered = new ListOfTransaction();
        for(Transaction t : list)
            if(t.getCategory().equals(category)) filtered.add(t);
        return filtered;
    }

    /**
     * 獲得物件裡面存的 List
     * @return
     */
    public ObservableList<Transaction> getList(){
        return list;
    }

    /**
     * 以金額排序
     */
    public void sortByAmount(){
        sortByAmount(false);
    }

    /**
     * 以金額排序
     * @param reverse 為 true 時降序, 預設為false
     */
    public void sortByAmount(boolean reverse){
        if(reverse) list.sort(Comparator.comparing(Transaction::getAmount).reversed());
        else list.sort(Comparator.comparing(Transaction::getAmount));
    }

    /**
     * 以創建時間排序
     */
    public void sortByCreatedTime(){
        sortByCreatedTime(false);
    }

    /**
     * 以創建時間排序
     * @param reverse 為 true 時降序, 預設為false
     */
    public void sortByCreatedTime(boolean reverse){
        if(reverse) list.sort(Comparator.comparing(Transaction::getCreatedTime).reversed());
        else list.sort(Comparator.comparing(Transaction::getCreatedTime));
    }
}