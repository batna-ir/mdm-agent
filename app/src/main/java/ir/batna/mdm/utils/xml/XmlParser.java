package ir.batna.mdm.utils.xml;
import android.content.Context;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.InputStream;

import ir.batna.mdm.utils.Constants;

/**
 * Created by Mehdi-git on August 09,2020
 * This class parses XML file and return specific tags content
 */
public class XmlParser {

    private XmlPullParser parser;
    private InputStream inputStream;
    private Context mContext;

    public XmlParser(Context context) {
        this.mContext = context;
        parseXML();
    }

    public void parseXML() {
        try {
            XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
            parser = parserFactory.newPullParser();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    /**
     * parse Xml file from Assets folder
     * To get content of specific xml tag
     */
    public String getContent(String name) {

        String content = "";

        try {
            inputStream = mContext.getAssets().open(Constants.XML_CONFIG_FILE);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName;
                if (eventType == XmlPullParser.START_TAG) {
                    tagName = parser.getName();

                    if (tagName.equalsIgnoreCase(name)) {
                        content = parser.nextText();
                    }
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        if (!(content.isEmpty() || content.startsWith(" "))) {
            return content;

        } else {
            return null;
        }
    }
}
