package ir.batna.mdm.utils.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Objects;

import ir.batna.mdm.utils.Constants;
import ir.batna.mdm.utils.xml.XmlParser;

/**
 * Created by Mehdi-git on August 09,2020
 */
public class MyProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        final UriMatcher mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mMatcher.addURI(Constants.AUTHORITY, Constants.PATH, Constants.URI_CODE);
        return mMatcher;
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {

        final int match = sUriMatcher.match(uri);
        Cursor c;
        if (match == Constants.URI_CODE) {
            c = getCursorFromXml(Objects.requireNonNull(getCallingPackage()));
        } else {
            throw new IllegalArgumentException(Constants.UNKNOWN_URI + uri);
        }
        c.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case Constants.URI_CODE:
                return Constants.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException(Constants.UNKNOWN_URI + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int delete(@NonNull Uri uri,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int update(@NonNull Uri uri,
                      @Nullable ContentValues values,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

    public Cursor getCursorFromXml(String packageName) {

        MatrixCursor mCursor = new MatrixCursor(
                new String[]{Constants.COLUMN_ID, Constants.COLUMN_URL_1}
        );

        if (packageName != null)
            switch (packageName) {

                case Constants.MESSAGING_APP_ID:
                    XmlParser mXmlReader = new XmlParser(getContext(), Constants.MESSAGING_APP_TAG);
                    mCursor.newRow()
                            .add(Constants.COLUMN_ID, 0)
                            .add(Constants.COLUMN_URL_1, mXmlReader.getUrl());
                    break;
            }
        return mCursor;
    }
}
