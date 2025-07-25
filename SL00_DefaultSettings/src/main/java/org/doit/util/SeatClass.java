package org.doit.util;

public enum SeatClass {
	ECONOMY, FIR, PRE, ECON;
	
    public static SeatClass fromDisplayName(String seatClassStr) {
        if (seatClassStr == null) return ECONOMY;

        switch (seatClassStr.trim()) {
            case "일반석":
                return ECONOMY;
            case "일등석":
                return FIR;
            case "프리미엄석":
                return PRE;
            case "이코노미석":
                return ECON;
            default:
                throw new IllegalArgumentException("Unknown seat class: " + seatClassStr);
        }
    }
}
