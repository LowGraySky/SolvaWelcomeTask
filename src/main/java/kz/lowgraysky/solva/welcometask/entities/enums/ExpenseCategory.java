package kz.lowgraysky.solva.welcometask.entities.enums;


public enum ExpenseCategory {
    PRODUCT("PRODUCT"),
    SERVICE("SERVICE");

    ExpenseCategory(String id) {
        this.id = id;
    }

    private final String id;

    public String getId() {
        return id;
    }

    public static ExpenseCategory fromId(String id) {
        for (ExpenseCategory exc : ExpenseCategory.values()) {
            if (exc.getId().equalsIgnoreCase(id)) {
                return exc;
            }
        }
        return null;
    }
}

