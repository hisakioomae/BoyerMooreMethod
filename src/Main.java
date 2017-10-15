import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Main extends Application {

    public void start(Stage primaryStage) throws Exception {

        FileChooser fileChooser = new FileChooser();//「ファイルを開く」ダイアログを表示する
        File file = fileChooser.showOpenDialog(primaryStage);//選択されたファイルパスを取得する
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        String text = br.readLine();
        String key = "look";
        bmSearch(text, key);

    }

    private static void bmSearch(String text, String pattern) {

        long start = System.nanoTime();

        int textLen = text.length();
        int patnLen = pattern.length();
        int[] skip = new int[128];
        for (int i = 0; i < 128; i++) {
            skip[i] = patnLen;
        }
        for (int i = 0; i < patnLen - 1; i++) { // アルファベットを配列のキーとして，最後に出現したキー位置の計算結果だけ格納
            skip[pattern.charAt(i)] = patnLen - i - 1;
        }
        int i = patnLen - 1; // iはtext中で着目している位置
        while (i < textLen) {
            int j = patnLen - 1; // jはpattern中で着目している位置
            while (text.charAt(i) == pattern.charAt(j)) {
                if (j == 0) {
                    System.out.println(i + 1);
                    break;
                }
                i--;
                j--;
            }
            i += Math.max(patnLen - j, skip[text.charAt(i)]);
        }

        long end = System.nanoTime();
        System.out.println((end - start)  + "ns"); // 総時間
    }

    public static void main(String[] args) {
        launch(args);
    }
}
