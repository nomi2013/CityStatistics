package com.nk.citystatistics.event;

/**
 * Created by Noman Khan on 28/05/18.
 */
public class MessageEvent {

    public static class saveStatus {
        private boolean isSave;

        public boolean isSave() {
            return isSave;
        }

        public void setSave(boolean save) {
            isSave = save;
        }
    }
}
