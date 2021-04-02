package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main { //extends Application {

    private static Button button;

    //@Override // использовать пока не будем
    public void start(Stage primaryStage) throws Exception{
        /*
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

         */
    }

    private static void show() { // метод show
        JFrame frame = new JFrame("JavaFX in Swing"); // создаем фрейм
        final JFXPanel panel = new JFXPanel(); // FXPanel необходим для работы с JavaFX

        JPanel jPanel = new JPanel(); // создаем панель Swing на которой будем все размещать.
        JButton moveLeft = new JButton("Move <<"); //  создаем кнопку
        JButton moveRight = new JButton("Move >>");
        jPanel.add(moveLeft); //  размещаем на панели
        jPanel.add(moveRight);

        jPanel.add(panel); // добавляем панель с FX компонентами
        frame.add(jPanel); // добавляем панель на фрэйм

        frame.setSize(400,200); // размеры
        frame.setVisible(true); // видимый
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // выход

        Platform.runLater(new Runnable() { // Platform.runLater это также запуск в новом потоке метода, для осуществления запуска инициализации нашей панели методом initFX(panel).
            @Override                       // Этот метод будет выполняться перед другими Runnable. (Runnable работает с JavaFX).
            public void run() { // как раз добавим JavaFX в swing
                initFX(panel); // Этот метод займется тем, чтобы поместить содержимое сцены на панель
            }
        });

        moveRight.addActionListener(new ActionListener() { //добавляем обработчик событий к кнопке.
            @Override
            public void actionPerformed(ActionEvent e) { // если активировали кнопку
                Platform.runLater(new Runnable() {// запускаем поток
                    @Override
                    public void run() {
                        double positionX = button.getLayoutX() + 2;
                        button.setLayoutX(positionX);
                    }
                });
            }
        });

        moveLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        double positionX = button.getLayoutX() - 2;
                        button.setLayoutX(positionX);
                    }
                });
            }
        });
    }

    private static Scene createScene(){ // создание сцены
        Group root = new Group(); // тут всё по анологии, панель(лайяут)
        Scene scene = new Scene(root, Color.YELLOW); // сцена с панелью, цвет жёлтый
        Button button = new Button("Hello World!"); // кнопка
        button.setLayoutX(50); // место
        button.setLayoutY(50);

        button.setOnAction(new EventHandler<javafx.event.ActionEvent>() { // перехвачиваем событие клика.
            @Override
            public void handle(javafx.event.ActionEvent actionEvent) {
                SwingUtilities.invokeLater(new Runnable() { // изменения для Swing так же должны происходить в его потоке. Точнее в EDT(Event Dispatcher Thread)
                    @Override
                    public void run() { // диалоговое окно
                        JOptionPane.showMessageDialog(null,"Clicked!"); // вывод сообщения о клике
                    }
                });
            }
        });

        root.getChildren().add(button); // на панель
        return scene; // вернуть панель
    }

    private static void initFX(JFXPanel panel){
        Scene scene = createScene(); // создаем сцену
        panel.setScene(scene); // добавляем на панель
    }

    public static void main(String[] args) {
        //launch(args);

        // Метод invokeLater говорит, что нужно обновить GUI поток. Он будет выполняться асинхронно с AWT.
        SwingUtilities.invokeLater(new Runnable() { //с новым поток
            @Override
            public void run() {
                show(); // запускаем метод show
            }
        });
    }
}

