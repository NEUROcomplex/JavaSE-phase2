package day_01;

// ???
public class StaticFactoryDemo {
    //public static Game gameFactory(Game )
    public static void main(String[] args) {
        Game g1 = new Chess("五子棋");
        Chess c1 = (Chess) g1;
    }
}

interface Game {
    Game getInstance();
    void play();
}

class Chess implements Game {
    private String chessName;

    public Chess(String chessName) {
        this.chessName = chessName;
    }

    @Override
    public Game getInstance() {
        return this;
    }

    @Override
    public void play() {
        System.out.println("play" + chessName);
    }
}

class Ball implements Game {
    private String ballName;

    public Ball(String ballName) {
        this.ballName = ballName;
    }

    @Override
    public Game getInstance() {
        return this;
    }

    @Override
    public void play() {
        System.out.println("play" + ballName);
    }
}