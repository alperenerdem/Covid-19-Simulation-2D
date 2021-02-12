import java.awt.*;

public class Dead implements PersonType {


    @Override
    public Types getType() {
        return Types.Dead;
    }

    @Override
    public Color getColor() {
        return null;
    }


}
