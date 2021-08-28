package io.github.jorgerojasdev.libraries.directorydb.jpa.commons.enumeration.filetype;

public enum FileType {
    BD_FILE("bd"), METADATA_FILE("mtd");

    private String prefix;

    FileType(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

}
