package br.com.ctmait.remittanceserviceapi.domain.models.user;


public class Document {

    private String value;
    private DocumentType documentType;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    @Override
    public String toString() {
        return "Document{" +
                "value=" + value +
                ", documentType=" + documentType +
                '}';
    }
}