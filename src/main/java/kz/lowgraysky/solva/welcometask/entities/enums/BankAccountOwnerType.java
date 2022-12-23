package kz.lowgraysky.solva.welcometask.entities.enums;

public enum BankAccountOwnerType {
    CLIENT("CLIENT"),
    CONTR_AGENT("CONTR_AGENT");

    BankAccountOwnerType(String id){
        this.id = id;
    }

    private final String id;

    public String getId() {
        return id;
    }

    public static BankAccountOwnerType fromId(String id) {
        for (BankAccountOwnerType exc : BankAccountOwnerType.values()) {
            if (exc.getId().equalsIgnoreCase(id)) {
                return exc;
            }
        }
        return null;
    }
}
