package org.tuxdude.yani.utils;

import ch.qos.logback.classic.Level;
import ch.qos.logback.core.pattern.CompositeConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * A custom highlighting class for logback.
 */
public class HighlightingConverter extends CompositeConverter<ILoggingEvent> {

    protected static final String ESC_START = "\033[";
    protected static final String ESC_END = "m";

    protected final static String COLOR_BOLD = "1;";

    protected final static String COLOR_BLACK = "0";
    protected final static String COLOR_RED = "1";
    protected final static String COLOR_GREEN = "2";
    protected final static String COLOR_YELLOW = "3";
    protected final static String COLOR_BLUE = "4";
    protected final static String COLOR_MAGENTA = "5";
    protected final static String COLOR_CYAN = "6";
    protected final static String COLOR_WHITE = "7";

    protected final static String COLOR_RESET = "0";

    protected final static String FG_PREFIX = "3";
    protected final static String BG_PREFIX = "4";

    public final static String COLOR_FG_BLACK = ESC_START + FG_PREFIX + COLOR_BLACK + ESC_END;
    public final static String COLOR_FG_RED = ESC_START + FG_PREFIX + COLOR_RED + ESC_END;
    public final static String COLOR_FG_GREEN = ESC_START + FG_PREFIX + COLOR_GREEN + ESC_END;
    public final static String COLOR_FG_YELLOW = ESC_START + FG_PREFIX + COLOR_YELLOW + ESC_END;
    public final static String COLOR_FG_BLUE = ESC_START + FG_PREFIX + COLOR_BLUE + ESC_END;
    public final static String COLOR_FG_MAGENTA = ESC_START + FG_PREFIX + COLOR_MAGENTA + ESC_END;
    public final static String COLOR_FG_CYAN = ESC_START + FG_PREFIX + COLOR_CYAN + ESC_END;
    public final static String COLOR_FG_WHITE = ESC_START + FG_PREFIX + COLOR_WHITE + ESC_END;

    public final static String COLOR_FG_BOLD_BLACK =
            ESC_START + COLOR_BOLD + FG_PREFIX + COLOR_BLACK + ESC_END;
    public final static String COLOR_FG_BOLD_RED =
            ESC_START + COLOR_BOLD + FG_PREFIX + COLOR_RED + ESC_END;
    public final static String COLOR_FG_BOLD_GREEN =
            ESC_START + COLOR_BOLD + FG_PREFIX + COLOR_GREEN + ESC_END;
    public final static String COLOR_FG_BOLD_YELLOW =
            ESC_START + COLOR_BOLD + FG_PREFIX + COLOR_YELLOW + ESC_END;
    public final static String COLOR_FG_BOLD_BLUE =
            ESC_START + COLOR_BOLD + FG_PREFIX + COLOR_BLUE + ESC_END;
    public final static String COLOR_FG_BOLD_MAGENTA =
            ESC_START + COLOR_BOLD + FG_PREFIX + COLOR_MAGENTA + ESC_END;
    public final static String COLOR_FG_BOLD_CYAN =
            ESC_START + COLOR_BOLD + FG_PREFIX + COLOR_CYAN + ESC_END;
    public final static String COLOR_FG_BOLD_WHITE =
            ESC_START + COLOR_BOLD + FG_PREFIX + COLOR_WHITE + ESC_END;

    public final static String COLOR_RESET_ALL = ESC_START + COLOR_RESET + ESC_END;


    @Override
    protected String transform(ILoggingEvent event, String in) {
        StringBuilder sb = new StringBuilder();
        Boolean isColorEnabled = true;
        switch (event.getLevel().toInt()) {
            case Level.ERROR_INT:
                sb.append(COLOR_FG_BOLD_RED);
                break;
            case Level.WARN_INT:
                sb.append(COLOR_FG_BOLD_YELLOW);
                break;
            case Level.INFO_INT:
                sb.append(COLOR_FG_BOLD_GREEN);
                break;
            case Level.DEBUG_INT:
                sb.append(COLOR_FG_BLUE);
                break;
            case Level.TRACE_INT:
                sb.append(COLOR_FG_CYAN);
                break;
            default:
                isColorEnabled = false;
                break;
        }

        if (isColorEnabled) {
            sb.append(in);
            sb.append(COLOR_RESET_ALL);
            return sb.toString();
        }
        else {
            return in;
        }
    }
}
