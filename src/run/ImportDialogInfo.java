package run;

public class ImportDialogInfo {

    private boolean strict;
    private boolean ecrase;

    public ImportDialogInfo() {
    }

    public ImportDialogInfo(boolean s, boolean ns, boolean e, boolean u) {
        if (!s && ns) {
            strict = false;
        } else {
            strict = true;
        }
        if (!e && u) {
            ecrase = false;
        } else {
            ecrase = true;
        }
    }

    public boolean isStrict() {
        return strict;
    }

    public boolean isEcrase() {
        return ecrase;
    }
}
