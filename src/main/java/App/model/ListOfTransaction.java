package App.model;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class ListOfTransaction {
    private List<Transaction> list;

    public ListOfTransaction(){
        list = new ArrayList<>();
    }
    public ListOfTransaction(List<Transaction> list){
        this.list = list;
    }

    /**
     * 增加一筆交易紀錄
     * @param transaction 交易記錄
     */
    public void add(Transaction transaction){
        list.add(transaction);
    }
    /**
     * 刪除指定位置的交易記錄
     * @param index 索引值
     */
    public void remove(int index){
        list.remove(index);
    }
    /**
     * 修改指定位置的交易紀錄
     * @param index 索引值
     * @param transaction 交易紀錄
     */
    public void modify(int index, Transaction transaction){
        list.set(index, transaction);
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
     * 獲得物件裡面存的 List
     * @return
     */
    public List<Transaction> getList(){
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