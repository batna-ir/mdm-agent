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

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
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
        Cursor cursor = null;
        if (match == Constants.URI_CODE) {
            try {
                cursor = getCursorFromXml(Objects.requireNonNull(getCallingPackage()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException(Constants.UNKNOWN_URI + uri);
        }
        if (cursor != null) {
            cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);

        if (match == Constants.URI_CODE) {
            return Constants.CONTENT_ITEM_TYPE;
        }
        throw new IllegalArgumentException(Constants.UNKNOWN_URI + uri);
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri,
                      @Nullable ContentValues values) {
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
                new String[]{
                        Constants.COLUMN_ID,
                        Constants.COLUMN_URL_1,
                        Constants.COLUMN_NAME,
                        Constants.COLUMN_DESC,
                        Constants.COLUMN_VERSION,
                        Constants.COLUMN_ENABLED,
                        Constants.COLUMN_PRIORITY,
                        Constants.COLUMN_PUSH_REQUEST,
                        Constants.COLUMN_Hash
                });

        XmlParser xmlParser = new XmlParser(getContext());

        switch (packageName) {
            case Constants.MESSAGING_APP_ID:
                mCursor.newRow()
                        .add(Constants.COLUMN_ID,
                                0)
                        .add(Constants.COLUMN_URL_1,
                                xmlParser.getContent(Constants.MESSAGING_URL_TAG));
                break;

            case Constants.VOIP_APP_ID:
                mCursor.newRow()
                        .add(Constants.COLUMN_ID,
                                0)
                        .add(Constants.COLUMN_URL_1,
                                xmlParser.getContent(Constants.VOIP_URL_TAG));
                break;

            case Constants.CLOUD_APP_ID:
                mCursor.newRow()
                        .add(Constants.COLUMN_ID,
                                0)
                        .add(Constants.COLUMN_URL_1,
                                xmlParser.getContent(Constants.CLOUD_URL_TAG));
                break;

            case Constants.MARKET_APP_ID:
                mCursor.newRow()
                        .add(Constants.COLUMN_ID,
                                0)
                        .add(Constants.COLUMN_URL_1,
                                xmlParser.getContent(Constants.MARKET_URL_TAG))
                        .add(Constants.COLUMN_NAME,
                                xmlParser.getContent(Constants.MARKET_NAME_TAG))
                        .add(Constants.COLUMN_DESC,
                                xmlParser.getContent(Constants.MARKET_DESC_TAG))
                        .add(Constants.COLUMN_VERSION,
                                xmlParser.getContent(Constants.MARKET_VERSION_TAG))
                        .add(Constants.COLUMN_ENABLED,
                                xmlParser.getContent(Constants.MARKET_ENABLED_TAG))
                        .add(Constants.COLUMN_PRIORITY,
                                xmlParser.getContent(Constants.MARKET_PRIORITY_TAG))
                        .add(Constants.COLUMN_PUSH_REQUEST,
                                xmlParser.getContent(Constants.MARKET_PUSH_TAG))
                        .add(Constants.COLUMN_Hash,
                                xmlParser.getContent(Constants.MARKET_HASH_TAG));
                break;
        }
        return mCursor;
    }
}
