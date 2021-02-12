import java.awt.*;

public class Hospitalized implements PersonType {


    @Override
    public Types getType() {
        return Types.Hospitalized;
    }

    @Override
    public Color getColor() {
        return null;
    }

}
