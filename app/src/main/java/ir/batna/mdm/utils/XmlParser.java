package ir.batna.mdm.utils;
import android.content.Context;
import android.util.Log;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Bakhtiari on August 09,2020
 * This class parses XML file and return specific tags content
 */
public class XmlParser {

    private String url = null;
    private String appName;

    public XmlParser(Context context, String appName) {
        this.appName = appName;
        parseXML(context);
    }

    public String getUrl() {
        return this.url;
    }

    /**
     * to parse Xml file from Assets folder
     */
    private void parseXML(Context context) {

        XmlPullParserFactory parserFactory;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream inputStream = context.getAssets().open("config.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            processParsing(parser);
        } catch (XmlPullParserException | IOException e) {
            Log.d("MBD", e.getMessage());
        }
    }

    /**
     * To get content of specific tag(appName)
     */
    private void processParsing(XmlPullParser parser) throws IOException, XmlPullParserException {

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String eltName = null;
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    eltName = parser.getName();
                    if (appName.equalsIgnoreCase(eltName)) {
                        this.url = parser.nextText();
                    }
                    break;
            }
            eventType = parser.next();
        }
    }
}
