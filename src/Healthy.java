import java.awt.*;

public class Healthy implements PersonType {

    @Override
    public Types getType() {
        return Types.Healthy;
    }

    @Override
    public Color getColor() {

        return Color.BLACK;
    }



}
