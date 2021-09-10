package nl.svdoetelaar;

public abstract class DocumentDecoder {
    protected final int DOCUMENT_PREFIX_OFFSET= 1;

    protected boolean isDocumentSpam(String document) {
        return document.charAt(0) == '1';
    }

    protected String[] splitWordsInDocument(String document) {
        return document.split(" ");
    }

}
