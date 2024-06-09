import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;

public class Colors {

    public String colored_text(String text, int number_color) {
        switch (number_color) {
            case 1:
                return colorize(text, TEXT_COLOR(255, 0, 0)); // Red
            case 2:
                return colorize(text, TEXT_COLOR(0, 255, 0)); // Green
            case 3:
                return colorize(text, TEXT_COLOR(0, 0, 255)); // Blue
            case 4:
                return colorize(text, TEXT_COLOR(255, 255, 0)); // Yellow
            case 5:
                return colorize(text, TEXT_COLOR(255, 165, 0)); // Orange
            default:
                return text; // Default: no color
        }
    }
}
