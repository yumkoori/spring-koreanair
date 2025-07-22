package org.doit.util;

public enum TripType {
	ROUND, ONEWAY;
	
    public static TripType fromDisplayName(String tripTypeStr) {
        if (tripTypeStr == null) return ONEWAY;

        switch (tripTypeStr.trim().toLowerCase()) {
            case "왕복":
            case "round":
                return ROUND;
            case "편도":
            case "oneway":
                return ONEWAY;
            default:
                throw new IllegalArgumentException("Unknown trip type: " + tripTypeStr);
        }
    }
}
