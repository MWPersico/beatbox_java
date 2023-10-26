public class App {
    private static MySound mySound;
    public static void main(String[] args) throws Exception {
        App app = new App();
        app.play();
    }

    public void play(){
        mySound = new MySound();
        new MyScreen(mySound);
    }
}