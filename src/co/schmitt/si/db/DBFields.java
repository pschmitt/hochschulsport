package co.schmitt.si.db;

/**
 * User: pschmitt
 * Date: 8/11/13
 * Time: 4:06 PM
 */
class DBFields {
    private DBFields() {}

    protected static final String FIELD_NAME = "COURSE_NAME";
    protected static final String FIELD_FEES = "FEES";
    protected static final String FIELD_MIN_PARTICIPANTS = "MIN_PARTICIPANTS";
    protected static final String FIELD_MAX_PARTICIPANTS = "MAX_PARTICIPANTS";
    protected static final String FIELD_PARTICIPANTS = "PARTICIPANTS";
    protected static final String FIELD_WEEK_DAY = "WEEKDAY";
    protected static final String FIELD_START_TIME = "START_TIME";
    protected static final String FIELD_END_TIME = "END_TIME";

    protected static final String FIELD_VALUE_UNLIMITED = "Unbegrenzt";

    protected static final String FIELD_NAME_LEGACY = "Name";
    protected static final String FIELD_FEES_LEGACY = "Gebuehren";
    protected static final String FIELD_MIN_PARTICIPANTS_LEGACY = "min_teilnehmer";
    protected static final String FIELD_MAX_PARTICIPANTS_LEGACY = "max_teilnehmer";
    protected static final String FIELD_PARTICIPANTS_LEGACY = "teilnehmeranzahl";
    protected static final String FIELD_WEEK_DAY_LEGACY = "WOCHENTAG";
    protected static final String FIELD_START_TIME_LEGACY = "UHRZEIT_VON";
    protected static final String FIELD_END_TIME_LEGACY = "UHRZEIT_BIS";
}
