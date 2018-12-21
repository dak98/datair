package utility;

/**
 * Class for a single json's key-value pair.
 */
public class JsonRecord {
    private String key;
    private String value;

    private JsonRecord(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Default constructor.
     */
    public JsonRecord() {}

    /**
     *
     * @return Key of the instance of json's record.
     */
    public String getJsonRecordKey() {
        return this.key;
    }

    /**
     *
     * @return Value of the instance of json's record.
     */
    public String getJsonRecordValue() {
        return this.value;
    }

    /**
     *
     * @param recordLine
     *         String of the form "key": value,
     * @return {@link utility.JsonRecord} with key and value from recordLine.
     *         null if string is not in a correct form.
     */
    public JsonRecord getJsonRecord(String recordLine) {
        String key = null;
        String value = null;
        try {
            recordLine = recordLine.substring(1);
            key = recordLine.substring(0, recordLine.indexOf('\"'));
            value = recordLine.substring(recordLine.indexOf(':') + 1, recordLine.lastIndexOf(','));
            value = value.replaceAll("\"", "");
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        return new JsonRecord(key, value);
    }
}
