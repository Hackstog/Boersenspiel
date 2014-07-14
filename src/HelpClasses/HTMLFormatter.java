/**
 * XMLFormatter.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     01.06.2014
 * Version:   1.0
 */


package HelpClasses;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Definiert das HTML-Ausgabeformat für die Logdatei
 */
public class HTMLFormatter extends Formatter{
    /**
     * Formatiert den Logeintrag
     * @param log: LogRecord
     * @return Formatierten Logeintrag als String
     */
    @Override
    public String format(LogRecord log){
        StringBuilder buf = new StringBuilder(1000);
        buf.append("\t\t<tr>\n");
        
        /**
         * Einträge mit Level "Warning" oder höher in Rot ausgeben
         */
        if(log.getLevel().intValue() >= Level.WARNING.intValue()){
            buf.append("\t\t\t<td style=\" color:red\">");
            buf.append("<b>");
            buf.append(log.getLevel());
            buf.append("</b>");
        }else{
            buf.append("\t\t\t<td>");
            buf.append(log.getLevel());
        }
        
        buf.append("</td>\n\t\t\t");
        buf.append("<td>");
        buf.append(calcDate(log.getMillis()));
        buf.append("</td>\n\t\t\t");
        buf.append("<td>");
        buf.append(formatMessage(log));
        buf.append("</td>\n\t");
        buf.append("</tr>\n");
        
        return buf.toString();
    }
    
    /**
     * Datumsformat definieren
     */
    private String calcDate(long millisecs){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-d  HH:mm");
        Date result = new Date(millisecs);
        return format.format(result);
    }
    
    /**
     * Dateikopf definieren
     * Wird aufgerufen, wenn der Handler der den Formatter nutzt erzeugt wird
     * @return HTML-Dateikopf als String
     */
    @Override
    public String getHead(Handler h){
        return "<!DOCTYPE html>\n<head>\n\t<style "
                + "type=\"text/css\">\n\t\t"
                + "table {width: 100%}\n\t\t"
                + "th {font:bold 10pt Verdana}\n\t\t"
                + "td {font:normal 10pt Verdana}\n\t\t"
                + "h1 {font:normal 12pt Verdana}\n\t"
                + "</style>\n</head>\n<body>\n\t<h1>"
                + (new Date())
                + "</h1>\n\t"
                + "<table border=\"0\" cellpadding=\"5\" cellspacing=\"3\">\n\t\t"
                + "<tr align=\"left\">\n\t\t\t"
                + "<th style=\"width:10%\">LogLevel</th>\n\t\t\t"
                + "<th style=\"width:15%\">Zeit</th>\n\t\t\t"
                + "<th style=\"width:75%\">Log-Eintrag</th>\n\t\t"
                + "</tr>\n";
    }
    
    /**
     * Dateiende definieren
     * Wird aufgerufen, wenn der Handler beendet wird
     * @return String
     */
    @Override
    public String getTail(Handler h){
        return "\t</table>\n</body>\n</html>";
    }
}
