import java.awt.*;

public class Untouchable implements PersonType {

    @Override
    public Types getType() {
        return Types.Untouchable;
    }

    @Override
    public Color getColor() {
        return Color.YELLOW;
    }

}
