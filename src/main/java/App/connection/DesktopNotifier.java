package App.connection;
import java.awt.*;
import java.awt.TrayIcon.MessageType;

public class DesktopNotifier {
    public static void showMessage(String title, String message, Runnable onClickAction){
        if(!SystemTray.isSupported()){
            System.err.println("沒有支援");
            return;
        }
        try{
            SystemTray tray = SystemTray.getSystemTray();
            Image img = Toolkit.getDefaultToolkit().getImage("C:\\Users\\user\\Documents\\GitHub\\java2025_5\\src\\main\\resources\\img.png");

            TrayIcon mytrayIcon = new TrayIcon(img, "查看分析報告");
            mytrayIcon.setImageAutoSize(true);

            //新增事件
            mytrayIcon.addActionListener(e -> {
                if (onClickAction != null) {
                    onClickAction.run();
                }
            });

            tray.add(mytrayIcon);
            mytrayIcon.displayMessage(title, message, MessageType.INFO);

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
