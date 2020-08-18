package xyz.shandiankulishe.smcl;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoadAnimation extends Application{
    Rectangle in;
    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Color back=Color.web("#f12e3b");
        Font MpjangABC=Font.loadFont(getClass().getResourceAsStream("Mojang-ABC.ttf"),80);
        Rectangle out=new Rectangle(1000,30,back);
        in=new Rectangle(0,28,Color.WHITE);
        out.setStroke(Color.WHITE);
        out.setX(150);
        out.setY(500);
        in.setX(152);
        in.setY(501);
        Text text=new Text(400,300,"BUGJUMP");
        text.setFill(Color.WHITE);
        text.setFont(MpjangABC);
        Group root=new Group();
        root.getChildren().add(text);
        root.getChildren().add(out);
        root.getChildren().add(in);
        Scene scene=new Scene(root,1280,720,back);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1280);
        primaryStage.setMinHeight(720);
        new Thread(addWidth).start();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }
    Task<Void> addWidth=new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            for (int i=0;i<998;i++){
                in.setWidth(i);
                if (i<700){
                    Thread.sleep(7);
                } else {
                    Thread.sleep(10);
                }
            }
            return null;
        }
    };
}
