package zefryuuko.chat.commdata;

import java.util.HashMap;

public class TextEditorSessionsData extends CommData
{
    private final HashMap<String, String> sessions;

    /**
     * Stores the currently active sessions
     * Key: sessionID
     * Value: filename
     * @param sessions
     */
    public TextEditorSessionsData(HashMap<String, String> sessions)
    {
        super("TextEditorSessionsData");
        this.sessions = sessions;
    }

    public HashMap<String, String> getSessions()
    {
        return sessions;
    }
}
