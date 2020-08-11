package ir.batna.mdm.utils.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Objects;
import ir.batna.mdm.utils.XmlParser;

/**
 * Created by Mehdi-git on August 09,2020
 */
public class MyProvider extends ContentProvider {


    private static final UriMatcher sUriMatcher = buildUriMatcher();
    static final String AUTHORITY = "ir.batna.mdm.utils.provider.MyProvider";
    private static final int APPS = 1;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mMatcher.addURI(AUTHORITY, "apps", APPS);
        return mMatcher;
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {

        //Log.d("MBD", "Calling package:   " + getCallingPackage());
        final int match = sUriMatcher.match(uri);
        Cursor c;
        if (match == APPS) {
            c = getCursorFromXml(Objects.requireNonNull(getCallingPackage()));
        } else {
            throw new IllegalArgumentException("Unknown URI:" + uri);
        }
        c.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case APPS:
                return AppsContract.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown Uri " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

    public Cursor getCursorFromXml(String packageName) {

        MatrixCursor mCursor = new MatrixCursor(
                new String[]{"_id", "url1", "url2", "url3"}
        );

        if (packageName != null)
            switch (packageName) {

                case "ir.batna.messaging":
                    XmlParser mXmlReader = new XmlParser(getContext(), AppsContract.MESSAGING_APP_TAG);
                    Log.d("MBD", "package name:" + packageName + "   url:  " + mXmlReader.getUrl());
                    mCursor.newRow()
                            .add("_id", 0)
                            .add("url1", mXmlReader.getUrl());
                    break;
            }
        return mCursor;
    }
}
