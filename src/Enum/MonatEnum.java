package Enum;

import com.sun.istack.internal.Nullable;

/**
 * Enum-Klasse für eine schnelle Umwandlung von einer Monatszahl zu der deutschen Monatsbezeichnung.
 */
public enum MonatEnum {
    Januar(1), Februar(2), Maerz(3), April(4), Mai(5), Juni(6),
    Juli(7), August(8), September(9), Oktober(10), November(11), Dezember(12);

    private int id;

    MonatEnum(int i) {
        this.id = i;
    }

    @Nullable
    public static MonatEnum getFromId(int id) {
        for (MonatEnum monatEnum : MonatEnum.values()) {
            if (monatEnum.getId() == id) {
                return monatEnum;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

}
