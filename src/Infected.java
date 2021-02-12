import java.awt.*;

public class Infected implements PersonType {


    @Override
    public Types getType() {
        return Types.Infected;
    }

    @Override
    public Color getColor() {

        return Color.red;
    }



}
